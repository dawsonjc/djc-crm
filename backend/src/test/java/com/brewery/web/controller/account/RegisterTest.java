package com.brewery.web.controller.account;

import com.brewery.web.dto.formdata.RegisterFormData;
import com.brewery.web.model.User;
import com.brewery.web.services.UserTableService;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class RegisterTest {
    private final UserTableService service = mock(UserTableService.class);
    private final Register controller = new Register(service);
    private final RegisterFormData form = new RegisterFormData(
            "Test", "User", "test-user", "test@example.com", "test-password");

    private void existingUser(User.VerificationStatus status) {
        UUID userId = UUID.randomUUID();
        when(service.userExistsByEmail(form.email())).thenReturn(true);
        when(service.userExistsByUsername(form.username())).thenReturn(true);
        when(service.getUserIdByEmail(form.email())).thenReturn(userId);
        when(service.getUserById(userId)).thenReturn(User.builder()
                .withUserId(userId).withAccountVerificationStatus(status).build());
    }

    @Test
    void verifiedUserGetsInvalidWithoutUpdatingCredentials() {
        existingUser(User.VerificationStatus.VERIFIED);

        var response = controller.register(form);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
        assertThat(response.getBody().path("success").asBoolean()).isFalse();
        assertThat(response.getBody().path("message").asText()).isEqualTo("Invalid");
        verify(service, never()).authenticateRegisteredUser(any());
    }

    @Test
    void pendingUserCanStillCompleteRegistration() {
        existingUser(User.VerificationStatus.PENDING);

        var response = controller.register(form);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody().path("success").asBoolean()).isTrue();
        verify(service).authenticateRegisteredUser(form);
    }
}
