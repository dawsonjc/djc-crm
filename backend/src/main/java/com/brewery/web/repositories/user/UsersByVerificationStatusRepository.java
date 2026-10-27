package com.brewery.web.repositories.user;

import com.brewery.web.model.user.UsersByVerificationStatus;
import com.brewery.web.model.record.RecordStatus;
import com.brewery.web.model.User;
import org.springframework.data.cassandra.repository.MapIdCassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
import java.time.Instant;
import com.datastax.oss.driver.api.core.cql.ResultSet;

@Repository
public interface UsersByVerificationStatusRepository extends MapIdCassandraRepository<UsersByVerificationStatus> {
    @Query("""
            SELECT *
            FROM users_by_verification_status
            WHERE account_verification_status = :accountVerificationStatus
              AND status = :status
    """)
    List<UsersByVerificationStatus> findByAccountVerificationStatusAndStatus(
            @Param(value = "accountVerificationStatus") User.VerificationStatus accountVerificationStatus,
            @Param(value = "status") RecordStatus status
    );

    @Query("""
           UPDATE users_by_verification_status
           SET
               last_login_date = :loginDate
           WHERE account_verification_status = :verification
             AND status = :status
             AND userid = :userId
    """)
    ResultSet recordLogin(
            @Param(value = "userId") UUID userId,
            @Param(value = "verification") User.VerificationStatus verification,
            @Param(value = "status") RecordStatus status,
            @Param(value = "loginDate") Instant loginDate
    );

    @Query("""
            UPDATE users_by_verification_status
            SET
                first_name = :firstName,
                last_name = :lastName,
                full_name = :fullName,
                language_preference = :language,
                timezone = :timezone,
                update_date = :updatedAt
            WHERE account_verification_status = :verification
              AND status = :status
              AND userid = :userId
    """)
    ResultSet updateProfile(
            @Param(value = "userId") UUID userId,
            @Param(value = "verification") User.VerificationStatus verification,
            @Param(value = "status") RecordStatus status,
            @Param(value = "firstName") String firstName,
            @Param(value = "lastName") String lastName,
            @Param(value = "fullName") String fullName,
            @Param(value = "language") String language,
            @Param(value = "timezone") String timezone,
            @Param(value = "updatedAt") Instant updatedAt
    );

    @Query("""
            UPDATE users_by_verification_status
            SET
                email = :email,
                update_date = :updatedAt
            WHERE account_verification_status = :verification
              AND status = :status
              AND userid = :userId
    """)
    ResultSet changeEmail(
            @Param(value = "userId") UUID userId,
            @Param(value = "verification") User.VerificationStatus verification,
            @Param(value = "status") RecordStatus status,
            @Param(value = "email") String email,
            @Param(value = "updatedAt") Instant updatedAt
    );

    @Query("""
            UPDATE users_by_verification_status
            SET
                username = :username,
                update_date = :updatedAt
            WHERE account_verification_status = :verification
              AND status = :status
              AND userid = :userId
    """)
    ResultSet changeUsername(
            @Param(value = "userId") UUID userId,
            @Param(value = "verification") User.VerificationStatus verification,
            @Param(value = "status") RecordStatus status,
            @Param(value = "username") String username,
            @Param(value = "updatedAt") Instant updatedAt
    );
}
