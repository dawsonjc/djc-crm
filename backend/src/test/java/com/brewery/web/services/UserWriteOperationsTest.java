package com.brewery.web.services;

import com.brewery.web.dto.formdata.RegisterFormData;
import com.brewery.web.model.User;
import com.brewery.web.model.record.RecordStatus;
import com.brewery.web.model.user.UserIdsByEmail;
import com.brewery.web.model.user.UserIdsByUsername;
import com.brewery.web.repositories.UserTableRepository;
import com.brewery.web.repositories.user.UserIdsByEmailRepository;
import com.brewery.web.repositories.user.UserIdsByUsernameRepository;
import com.brewery.web.repositories.user.UsersByVerificationStatusRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserWriteOperationsTest {
    @Mock UserTableRepository repo;
    @Mock UserIdsByEmailRepository emailRepository;
    @Mock UserIdsByUsernameRepository usernameRepository;
    @Mock UsersByVerificationStatusRepository verificationRepository;
    @Mock UserRolesService userRolesService;
    @InjectMocks UserTableService service;

    private final User user = User.builder().withUserId(UUID.randomUUID())
            .withEmail("before@example.com").withUsername("before")
            .withStatus(RecordStatus.ACTIVE).withAccountVerificationStatus(User.VerificationStatus.PENDING)
            .withCompanyId(UUID.randomUUID()).withPassword("existing-hash").build();

    @Test
    void loginMakesOnlyTwoPartialWritesWithoutReadingOrSavingTheWholeUser() {
        Instant loginDate = Instant.parse("2026-10-26T12:00:00Z");
        UUID token = UUID.randomUUID();

        User updated = service.recordLogin(user, loginDate, token);

        verify(repo).recordLogin(user.getUserId(), loginDate, token);
        verify(verificationRepository).recordLogin(user.getUserId(), User.VerificationStatus.PENDING,
                RecordStatus.ACTIVE, loginDate);
        verifyNoMoreInteractions(repo, verificationRepository);
        verifyNoInteractions(emailRepository, usernameRepository);
        assertThat(updated.getLastLoginDate()).isEqualTo(loginDate);
        assertThat(updated.getAuthApiToken()).isEqualTo(token);
        assertThat(updated.getPassword()).isEqualTo("existing-hash");
        assertThat(updated.getCompanyId()).isEqualTo(user.getCompanyId());
        assertThat(user.getLastLoginDate()).isNull();
    }

    @Test
    void profileEditOnlyUpdatesProfileColumnsInTheTwoProfileTables() {
        when(repo.findById(user.getUserId())).thenReturn(Optional.of(user));

        User updated = service.updateProfile(user.getUserId(), "First", "Last", "First Last", "en", "UTC");

        verify(repo).updateProfile(eq(user.getUserId()), eq("First"), eq("Last"), eq("First Last"),
                eq("en"), eq("UTC"), any());
        verify(verificationRepository).updateProfile(eq(user.getUserId()), eq(User.VerificationStatus.PENDING),
                eq(RecordStatus.ACTIVE), eq("First"), eq("Last"), eq("First Last"), eq("en"), eq("UTC"), any());
        verify(repo, never()).save(any(User.class));
        verifyNoInteractions(emailRepository, usernameRepository);
        assertThat(updated.getPassword()).isEqualTo(user.getPassword());
        assertThat(updated.getFullName()).isEqualTo("First Last");
    }

    @Test
    void emailChangeReplacesItsLookupWithoutTouchingUsernameLookup() {
        when(repo.findById(user.getUserId())).thenReturn(Optional.of(user));

        service.changeEmail(user.getUserId(), "after@example.com");

        verify(repo).changeEmail(eq(user.getUserId()), eq("after@example.com"), any());
        var saved = ArgumentCaptor.forClass(UserIdsByEmail.class);
        var deleted = ArgumentCaptor.forClass(UserIdsByEmail.class);
        verify(emailRepository).save(saved.capture());
        verify(emailRepository).delete(deleted.capture());
        assertThat(saved.getValue().getEmail()).isEqualTo("after@example.com");
        assertThat(deleted.getValue().getEmail()).isEqualTo("before@example.com");
        assertThat(deleted.getValue().getUserId()).isEqualTo(user.getUserId());
        verify(verificationRepository).changeEmail(eq(user.getUserId()), eq(User.VerificationStatus.PENDING),
                eq(RecordStatus.ACTIVE), eq("after@example.com"), any());
        verifyNoInteractions(usernameRepository);
        verify(repo, never()).save(any(User.class));
    }

    @Test
    void usernameChangeReplacesItsLookupWithoutTouchingEmailLookup() {
        when(repo.findById(user.getUserId())).thenReturn(Optional.of(user));

        service.changeUsername(user.getUserId(), "after");

        var deleted = ArgumentCaptor.forClass(UserIdsByUsername.class);
        verify(usernameRepository).delete(deleted.capture());
        assertThat(deleted.getValue().getUsername()).isEqualTo("before");
        verify(verificationRepository).changeUsername(eq(user.getUserId()), eq(User.VerificationStatus.PENDING),
                eq(RecordStatus.ACTIVE), eq("after"), any());
        verifyNoInteractions(emailRepository);
        verify(repo, never()).save(any(User.class));
    }

    @Test
    void unchangedEmailOrVerificationStatusDoesNotWriteAnything() {
        when(repo.findById(user.getUserId())).thenReturn(Optional.of(user));

        service.changeEmail(user.getUserId(), user.getEmail());
        service.changeVerificationStatus(user.getUserId(), user.getAccountVerificationStatus());

        verify(repo, never()).save(any(User.class));
        verifyNoInteractions(emailRepository, usernameRepository, verificationRepository);
    }

    @Test
    void registrationCompletionDoesNotRewriteUnchangedIdentityLookups() {
        when(emailRepository.findByEmailAndStatus(user.getEmail(), RecordStatus.ACTIVE)).thenReturn(List.of(
                UserIdsByEmail.builder().withEmail(user.getEmail()).withUserId(user.getUserId())
                        .withStatus(RecordStatus.ACTIVE).build()));
        when(repo.findById(user.getUserId())).thenReturn(Optional.of(user));
        RegisterFormData form = new RegisterFormData("First", "Last", user.getUsername(), user.getEmail(), "test-password");

        User updated = service.authenticateRegisteredUser(form);

        verify(repo).completeRegistration(eq(user.getUserId()), any(), eq(RecordStatus.ACTIVE),
                eq(user.getUsername()), anyString(), eq("First"), eq("Last"), eq("First Last"),
                eq(User.VerificationStatus.VERIFIED));
        verify(emailRepository, never()).save(any());
        verify(emailRepository, never()).delete(any(UserIdsByEmail.class));
        verifyNoInteractions(usernameRepository);
        verify(repo, never()).save(any(User.class));
        assertThat(updated.getAccountVerificationStatus()).isEqualTo(User.VerificationStatus.VERIFIED);
        assertThat(updated.getPassword()).isNotEqualTo(user.getPassword());
    }
}
