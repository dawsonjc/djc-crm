package com.brewery.web.services;

import com.brewery.web.model.User;
import com.brewery.web.model.record.RecordStatus;
import com.brewery.web.model.user.UsersByVerificationStatus;
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

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserVerificationProjectionTest {
    @Mock UserTableRepository repo;
    @Mock UserIdsByEmailRepository emailRepository;
    @Mock UserIdsByUsernameRepository usernameRepository;
    @Mock UsersByVerificationStatusRepository verificationRepository;
    @InjectMocks UserTableService service;

    private final User pending = User.builder().withUserId(UUID.randomUUID())
            .withUsername("test-user").withEmail("test@example.com")
            .withStatus(RecordStatus.ACTIVE)
            .withAccountVerificationStatus(User.VerificationStatus.PENDING).build();

    @Test
    void verificationMovesProjectionFromPendingToVerified() {
        when(repo.findById(pending.getUserId())).thenReturn(Optional.of(pending));
        User verified = service.changeVerificationStatus(pending.getUserId(), User.VerificationStatus.VERIFIED);
        assertThat(verified.getAccountVerificationStatus()).isEqualTo(User.VerificationStatus.VERIFIED);

        ArgumentCaptor<UsersByVerificationStatus> saved = ArgumentCaptor.forClass(UsersByVerificationStatus.class);
        ArgumentCaptor<UsersByVerificationStatus> deleted = ArgumentCaptor.forClass(UsersByVerificationStatus.class);
        var order = inOrder(repo, verificationRepository);
        order.verify(repo).findById(pending.getUserId());
        order.verify(repo).changeVerificationStatus(eq(pending.getUserId()), eq(User.VerificationStatus.VERIFIED), any());
        order.verify(verificationRepository).save(saved.capture());
        order.verify(verificationRepository).delete(deleted.capture());
        assertThat(saved.getValue().getAccountVerificationStatus()).isEqualTo(User.VerificationStatus.VERIFIED);
        assertThat(deleted.getValue().getAccountVerificationStatus()).isEqualTo(User.VerificationStatus.PENDING);
        assertThat(deleted.getValue().getStatus()).isEqualTo(RecordStatus.ACTIVE);
        assertThat(deleted.getValue().getUserId()).isEqualTo(pending.getUserId());
    }

    @Test
    void recordStatusChangeAlsoRemovesTheOldVerificationPartitionRow() {
        when(repo.findById(pending.getUserId())).thenReturn(Optional.of(pending));

        service.changeStatus(pending.getUserId(), RecordStatus.DISABLED);

        ArgumentCaptor<UsersByVerificationStatus> deleted = ArgumentCaptor.forClass(UsersByVerificationStatus.class);
        verify(verificationRepository).delete(deleted.capture());
        assertThat(deleted.getValue().getStatus()).isEqualTo(RecordStatus.ACTIVE);
        assertThat(deleted.getValue().getAccountVerificationStatus()).isEqualTo(User.VerificationStatus.PENDING);
    }

    @Test
    void unchangedVerificationKeyIsUpdatedWithoutDeletingIt() {
        when(repo.findById(pending.getUserId())).thenReturn(Optional.of(pending));

        service.updateProfile(pending.getUserId(), "Updated", "User", "Updated User", "en", "UTC");

        verify(verificationRepository).updateProfile(eq(pending.getUserId()), eq(User.VerificationStatus.PENDING),
                eq(RecordStatus.ACTIVE), eq("Updated"), eq("User"), eq("Updated User"), eq("en"), eq("UTC"), any());
        verify(verificationRepository, never()).delete(any(UsersByVerificationStatus.class));
    }

    @Test
    void newUserHasNoOldProjectionToDelete() {
        when(repo.save(pending)).thenReturn(pending);
        service.createUser(pending);

        verify(verificationRepository).save(any(UsersByVerificationStatus.class));
        verify(verificationRepository, never()).delete(any(UsersByVerificationStatus.class));
        verify(repo, never()).findById(any());
    }
}
