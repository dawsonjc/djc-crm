package com.brewery.web.security;

import com.brewery.web.configuration.FilterConfig;
import com.brewery.web.controller.auth.CompanyController;
import com.brewery.web.controller.auth.UserController;
import com.brewery.web.model.User;
import com.brewery.web.model.company.Company;
import com.brewery.web.services.UserTableService;
import com.brewery.web.services.auth.CompanyService;
import com.brewery.web.user.AuthSessionUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AuthSessionFilterTest {
    private final CompanyService companyService = mock(CompanyService.class);
    private final UserTableService userService = mock(UserTableService.class);
    private final ServiceCredentialVerifier verifier = mock(ServiceCredentialVerifier.class);
    private MockMvc mvc;

    @BeforeEach
    void setUp() {
        when(verifier.isConfigured()).thenReturn(true);
        when(verifier.isValid("test-secret")).thenReturn(true);
        mvc = MockMvcBuilders.standaloneSetup(
                        new CompanyController(companyService, new ObjectMapper().findAndRegisterModules()),
                        new UserController(userService), new TestEndpoints())
                .addFilter(new ServiceAuthenticationFilter(verifier), "/auth/*")
                .addFilter(new AuthSessionFilter(), "/auth/*")
                .build();
    }

    @Test
    void loginSkipsSessionCheckButStillRequiresServiceCredential() throws Exception {
        mvc.perform(get("/auth/login").header(ServiceAuthenticationFilter.HEADER_NAME, "test-secret"))
                .andExpect(status().isOk());
        mvc.perform(get("/auth/login"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("message").value("Invalid service credential."));
    }

    @Test
    void missingSessionBlocksCompanyUserAndFutureAuthRoutes() throws Exception {
        for (String path : List.of("/auth/company/all", "/auth/user/add", "/auth/new-endpoint")) {
            var result = mvc.perform(post(path).header(ServiceAuthenticationFilter.HEADER_NAME, "test-secret"))
                    .andExpect(status().isUnauthorized())
                    .andExpect(header().string("Cache-Control", "no-store"))
                    .andExpect(jsonPath("success").value(false))
                    .andExpect(jsonPath("message").value("Please sign in again."))
                    .andReturn();
            assertThat(result.getRequest().getSession(false)).isNull();
        }
        verifyNoInteractions(companyService, userService);
    }

    @Test
    void wrongSessionAttributeTypeIsRejected() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute(AuthSessionUser.AUTH_SESSION_USER, "not-a-user");
        mvc.perform(get("/auth/company/all").session(session)
                        .header(ServiceAuthenticationFilter.HEADER_NAME, "test-secret"))
                .andExpect(status().isUnauthorized());
        verifyNoInteractions(companyService);
    }

    @Test
    void authenticatedUserIsAvailableToEndpointWithoutSessionLookup() throws Exception {
        User user = User.builder().withUserId(UUID.randomUUID()).build();
        MockHttpSession session = session(user);
        mvc.perform(get("/auth/whoami").session(session)
                        .header(ServiceAuthenticationFilter.HEADER_NAME, "test-secret"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("userId").value(user.getUserId().toString()));

        when(companyService.addCompanyByFormData(any(), any()))
                .thenReturn(Company.builder().withName("Brewery").build());
        mvc.perform(post("/auth/company/add").session(session)
                        .header(ServiceAuthenticationFilter.HEADER_NAME, "test-secret")
                        .contentType("application/json").content("{\"name\":\"Brewery\"}"))
                .andExpect(status().isOk());
        verify(companyService).addCompanyByFormData(any(), same(user));
    }

    @Test
    void serviceCredentialIsRequiredEvenWithValidSession() throws Exception {
        mvc.perform(get("/auth/company/all").session(session(User.builder().build())))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("message").value("Invalid service credential."));
        verifyNoInteractions(companyService);
    }

    @Test
    void loginExceptionHandlesContextPathAndDoesNotMatchOtherRoutes() throws Exception {
        mvc.perform(get("/crm/auth/login").contextPath("/crm")
                        .header(ServiceAuthenticationFilter.HEADER_NAME, "test-secret"))
                .andExpect(status().isOk());
        mvc.perform(get("/auth/login/other").header(ServiceAuthenticationFilter.HEADER_NAME, "test-secret"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void registrationProtectsAuthRoutesAfterServiceCredentialCheck() {
        FilterConfig config = new FilterConfig();
        var sessionRegistration = config.authSessionFilter();
        var serviceRegistration = config.serviceAuthFilter(verifier);
        assertThat(sessionRegistration.getUrlPatterns()).containsExactly("/auth/*");
        assertThat(sessionRegistration.getOrder()).isGreaterThan(serviceRegistration.getOrder());
    }

    private MockHttpSession session(User user) {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute(AuthSessionUser.AUTH_SESSION_USER, user);
        return session;
    }

    @RestController
    static class TestEndpoints {
        @GetMapping("/auth/login")
        public Map<String, Boolean> login() { return Map.of("success", true); }

        @GetMapping("/auth/whoami")
        public Map<String, String> whoami(@RequestAttribute(AuthSessionUser.AUTH_SESSION_USER) User user) {
            return Map.of("userId", user.getUserId().toString());
        }
    }
}
