package com.brewery.web.services;

import com.brewery.web.dto.UserDTO;
import com.brewery.web.model.User;
import com.brewery.web.model.record.RecordStatus;
import com.brewery.web.model.user.UsersByCompany;
import com.brewery.web.model.user.UsersByCompanyVerificationStatus;
import com.brewery.web.repositories.UserTableRepository;
import com.brewery.web.repositories.user.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.SliceImpl;
import java.time.Instant;
import java.util.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CompanyUserProjectionTest {
    @Mock UserTableRepository repo;
    @Mock UserIdsByEmailRepository emailRepository;
    @Mock UserIdsByUsernameRepository usernameRepository;
    @Mock UsersByVerificationStatusRepository verificationRepository;
    @Mock UsersByCompanyRepository companyRepository;
    @Mock UsersByCompanyVerificationStatusRepository companyVerificationRepository;
    @Mock UserRolesService userRolesService;
    @InjectMocks UserTableService service;
    private final User user = User.builder().withUserId(UUID.randomUUID()).withCompanyId(UUID.randomUUID())
            .withCompanyName("Company A").withEmail("user@example.com").withUsername("user")
            .withStatus(RecordStatus.ACTIVE).withAccountVerificationStatus(User.VerificationStatus.PENDING).build();

    @Test void creationSavesBothDirectoryViews() {
        when(repo.save(user)).thenReturn(user);
        service.createUser(user);
        var row = ArgumentCaptor.forClass(UsersByCompany.class);
        verify(companyRepository).save(row.capture());
        assertThat(UserDTO.from(row.getValue()).companyId()).isEqualTo(user.getCompanyId());
        assertThat(UserDTO.from(row.getValue()).lastLoginDate()).isNull();
        verify(companyVerificationRepository).save(any(UsersByCompanyVerificationStatus.class));
    }

    @Test void transferMovesBothOldCompanyKeysWithoutChangingLoginLookups() {
        when(repo.findById(user.getUserId())).thenReturn(Optional.of(user));
        UUID destination = UUID.randomUUID();
        User updated = service.changeCompany(user.getUserId(), destination, "Company B");
        assertThat(updated.getCompanyId()).isEqualTo(destination);
        verify(repo).changeCompany(eq(user.getUserId()), eq(destination), eq("Company B"), any());
        var deleted = ArgumentCaptor.forClass(UsersByCompany.class);
        verify(companyRepository).delete(deleted.capture());
        assertThat(deleted.getValue().getCompanyId()).isEqualTo(user.getCompanyId());
        var saved = ArgumentCaptor.forClass(UsersByCompanyVerificationStatus.class);
        verify(companyVerificationRepository).save(saved.capture());
        assertThat(UserDTO.from(saved.getValue()).companyId()).isEqualTo(destination);
        verify(companyVerificationRepository).delete(any(UsersByCompanyVerificationStatus.class));
        verifyNoInteractions(emailRepository, usernameRepository);
    }

    @Test void verificationChangeDeletesOnlyOldCompanyVerificationKey() {
        when(repo.findById(user.getUserId())).thenReturn(Optional.of(user));
        service.changeVerificationStatus(user.getUserId(), User.VerificationStatus.VERIFIED);
        var deleted = ArgumentCaptor.forClass(UsersByCompanyVerificationStatus.class);
        verify(companyVerificationRepository).delete(deleted.capture());
        assertThat(deleted.getValue().getAccountVerificationStatus()).isEqualTo(User.VerificationStatus.PENDING);
        verify(companyRepository, never()).delete(any(UsersByCompany.class));
    }

    @Test void statusChangeDeletesBothOldCompanyRows() {
        when(repo.findById(user.getUserId())).thenReturn(Optional.of(user));
        service.changeStatus(user.getUserId(), RecordStatus.DISABLED);
        var old = ArgumentCaptor.forClass(UsersByCompany.class);
        verify(companyRepository).delete(old.capture());
        assertThat(old.getValue().getStatus()).isEqualTo(RecordStatus.ACTIVE);
        verify(companyVerificationRepository).delete(any(UsersByCompanyVerificationStatus.class));
    }

    @Test void pagedReadsPreserveContinuationAndCompanyScope() {
        var page = PageRequest.of(0, 25);
        var row = UsersByCompany.builder().withUserId(user.getUserId()).withCompanyId(user.getCompanyId()).build();
        when(companyRepository.findByCompanyId(user.getCompanyId(), page))
                .thenReturn(new SliceImpl<>(List.of(row), page, true));
        var result = service.getUsersByCompanyId(user.getCompanyId(), page);
        assertThat(result.hasNext()).isTrue();
        assertThat(result.getContent().getFirst().companyId()).isEqualTo(user.getCompanyId());
        assertThatThrownBy(() -> service.getUsersByCompanyId(user.getCompanyId(), Pageable.unpaged()))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> service.getUsersByCompanyId(user.getCompanyId(), PageRequest.of(0, 501)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test void loginDoesNotWriteDirectoryRowsAndGlobalAdminsDoNotGetCompanyRows() {
        service.recordLogin(user, Instant.now(), UUID.randomUUID());
        service.saveCompanyProjections(user.toBuilder().withCompanyId(null).build());
        verifyNoInteractions(companyRepository, companyVerificationRepository);
    }
    @Test void deletionCleansDirectoriesAndRolesBeforeCanonicalUser() {
        when(repo.findById(user.getUserId())).thenReturn(Optional.of(user));
        service.deleteCompanyUser(user.getCompanyId(), user.getUserId());
        var order = inOrder(companyRepository, companyVerificationRepository, emailRepository,
                usernameRepository, verificationRepository, userRolesService, repo);
        order.verify(companyRepository).delete(any());
        order.verify(companyVerificationRepository).delete(any());
        order.verify(emailRepository).delete(any());
        order.verify(usernameRepository).delete(any());
        order.verify(verificationRepository).delete(any());
        order.verify(userRolesService).deleteForUser(user.getUserId());
        order.verify(repo).deleteById(user.getUserId());
    }

    @Test void deletionRejectsOtherCompanyWithoutWrites() {
        when(repo.findById(user.getUserId())).thenReturn(Optional.of(user));
        assertThatThrownBy(() -> service.deleteCompanyUser(UUID.randomUUID(), user.getUserId()))
                .isInstanceOf(IllegalArgumentException.class);
        verify(repo, never()).deleteById(any());
        verifyNoInteractions(companyRepository, companyVerificationRepository, emailRepository,
                usernameRepository, verificationRepository, userRolesService);
    }

    @Test void repeatedDeletionOfMissingUserSucceeds() {
        when(repo.findById(user.getUserId())).thenReturn(Optional.empty());
        service.deleteCompanyUser(user.getCompanyId(), user.getUserId());
        verifyNoInteractions(companyRepository, userRolesService);
        verify(repo, never()).deleteById(any());
    }

    @Test void cleanupFailureKeepsCanonicalUserForRetry() {
        when(repo.findById(user.getUserId())).thenReturn(Optional.of(user));
        doThrow(new IllegalStateException("unavailable")).when(companyVerificationRepository).delete(any());
        assertThatThrownBy(() -> service.deleteCompanyUser(user.getCompanyId(), user.getUserId()))
                .isInstanceOf(IllegalStateException.class);
        verify(repo, never()).deleteById(any());
    }
}
