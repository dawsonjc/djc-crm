package com.brewery.web.services;

import com.brewery.web.controller.account.Register;
import com.brewery.web.controller.auth.CompanyUserController;
import com.brewery.web.mail.RegistrationEmail;
import com.brewery.web.model.User;
import com.brewery.web.model.record.RecordStatus;
import com.brewery.web.repositories.UserTableRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.support.StaticListableBeanFactory;
import org.springframework.http.MediaType;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tools.jackson.databind.json.JsonMapper;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class RegistrationFlowTest {
    private static final String SECRET = "test-only-registration-secret-32-bytes";
    private static final String REGISTER = "/account/register";
    private final User pending = User.builder().withUserId(UUID.randomUUID())
            .withCompanyId(UUID.randomUUID()).withCompanyName("Brewery")
            .withCreateDate(Instant.now()).withEmail("invitee@example.com").withUsername("invitee")
            .withStatus(RecordStatus.ACTIVE).withAccountVerificationStatus(User.VerificationStatus.PENDING).build();
    private final JavaMailSender sender = mock(JavaMailSender.class);
    private final RegistrationEmailService emailService = new RegistrationEmailService(SECRET,
            "https://crm.example.com/", registrationMail());

    @Test
    void creatingAnActiveUserSendsAnInvitationThatOpensTheRegistrationView() throws Exception {
        UserTableService users = mock(UserTableService.class);
        when(users.registerUser(any())).thenReturn(pending);
        when(users.findPendingRegistrationUser(pending.getUserId(), pending.getEmail())).thenReturn(pending);
        MockMvc mvc = mvc(users);

        mvc.perform(createUser()).andExpect(status().isCreated()).andExpect(jsonPath("$.success").value(true));

        var message = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(sender).send(message.capture());
        assertThat(message.getValue().getTo()).containsExactly(pending.getEmail());
        assertThat(message.getValue().getFrom()).isEqualTo("crm@example.com");
        String link = message.getValue().getText().lines().filter(line -> line.startsWith("https://")).findFirst().orElseThrow();
        assertThat(link).startsWith("https://crm.example.com/account/register?verification=");

        mvc.perform(get(REGISTER).param("verification", link.substring(link.indexOf('=') + 1)))
                .andExpect(status().isOk()).andExpect(view().name("account/register"))
                .andExpect(model().attribute("registrationEmail", pending.getEmail()))
                .andExpect(header().string("Referrer-Policy", "no-referrer"));
        verify(users, never()).authenticateRegisteredUser(any(), any());
    }

    @Test
    void invalidOrIneligibleInvitationsRedirectToLogin() throws Exception {
        UserTableRepository repo = mock(UserTableRepository.class);
        UserTableService users = new UserTableService();
        ReflectionTestUtils.setField(users, "repo", repo);
        MockMvc mvc = mvc(users);
        String token = tokenFor(pending);
        for (String invalid : new String[] { "", "garbage", token + "a", expiredToken() }) {
            mvc.perform(get(REGISTER).param("verification", invalid)).andExpect(redirectedUrl("/account/login"));
        }
        mvc.perform(get(REGISTER)).andExpect(redirectedUrl("/account/login"));
        verifyNoInteractions(repo);

        mvc.perform(get(REGISTER).param("verification", token)).andExpect(redirectedUrl("/account/login"));
        for (User ineligible : List.of(
                pending.toBuilder().withAccountVerificationStatus(User.VerificationStatus.VERIFIED).build(),
                pending.toBuilder().withAccountVerificationStatus(User.VerificationStatus.BLOCKED).build(),
                pending.toBuilder().withStatus(RecordStatus.DISABLED).build(),
                pending.toBuilder().withEmail("other@example.com").build(),
                pending.toBuilder().withPassword("existing-hash").build())) {
            when(repo.findById(pending.getUserId())).thenReturn(Optional.of(ineligible));
            mvc.perform(get(REGISTER).param("verification", token)).andExpect(redirectedUrl("/account/login"));
        }
    }

    @Test
    void postRequiresAnInvitationForTheSameUserAndHandlesAnAlreadyConsumedInvite() throws Exception {
        UserTableService users = mock(UserTableService.class);
        when(users.findPendingRegistrationUser(pending.getUserId(), pending.getEmail())).thenReturn(pending);
        MockMvc mvc = mvc(users);
        String token = tokenFor(pending);

        mvc.perform(registration(pending.getUsername())).andExpect(status().isConflict());
        mvc.perform(registration("another-user").param("verification", token)).andExpect(status().isConflict());
        verify(users, never()).authenticateRegisteredUser(any(), any());

        when(users.authenticateRegisteredUser(eq(pending), any())).thenReturn(true, false);
        mvc.perform(registration(pending.getUsername()).param("verification", token)).andExpect(status().isCreated());
        mvc.perform(registration(pending.getUsername()).param("verification", token)).andExpect(status().isConflict());
    }

    @Test
    void smtpFailureReturnsTheSavedUserWithADeliveryWarning() throws Exception {
        UserTableService users = mock(UserTableService.class);
        when(users.registerUser(any())).thenReturn(pending);
        doThrow(new MailSendException("SMTP unavailable")).when(sender).send(any(SimpleMailMessage.class));

        mvc(users).perform(createUser()).andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.userId").value(pending.getUserId().toString()))
                .andExpect(jsonPath("$.message").value("User created, but the verification email could not be sent."));
    }

    private RegistrationEmail registrationMail() {
        var beans = new StaticListableBeanFactory();
        beans.addBean("mailSender", sender);
        return new RegistrationEmail(beans.getBeanProvider(JavaMailSender.class), "crm@example.com");
    }

    private MockMvc mvc(UserTableService users) {
        return MockMvcBuilders.standaloneSetup(new Register(users, emailService),
                new CompanyUserController(users, emailService, JsonMapper.builder().findAndAddModules().build())).build();
    }

    private MockHttpServletRequestBuilder createUser() {
        return post("/auth/user/add").contentType(MediaType.APPLICATION_JSON).content("""
                {"companyId":"%s","companyName":"Brewery","email":"invitee@example.com","username":"invitee","status":"Active"}
                """.formatted(pending.getCompanyId()));
    }

    private MockHttpServletRequestBuilder registration(String username) {
        return post(REGISTER).param("email", pending.getEmail()).param("username", username)
                .param("firstName", "Test").param("lastName", "User").param("password", "test-password");
    }

    private String tokenFor(User user) {
        return URI.create(emailService.generateVerificationLink(user.getUserId(), user.getEmail()))
                .getQuery().substring("verification=".length());
    }

    private String expiredToken() throws Exception {
        var encoder = Base64.getUrlEncoder().withoutPadding();
        String payload = pending.getUserId() + ":1:" + pending.getEmail();
        String encoded = encoder.encodeToString(payload.getBytes(StandardCharsets.UTF_8));
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(SECRET.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
        return encoded + "." + encoder.encodeToString(mac.doFinal(encoded.getBytes(StandardCharsets.US_ASCII)));
    }
}
