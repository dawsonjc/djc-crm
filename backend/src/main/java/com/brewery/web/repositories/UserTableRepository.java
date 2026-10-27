package com.brewery.web.repositories;

import com.brewery.web.model.User;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.time.Instant;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.brewery.web.model.record.RecordStatus;

@Repository
public interface UserTableRepository extends CassandraRepository<User, UUID> {
    @Query("UPDATE user_table SET last_login_date = :loginDate, auth_api_token = :token WHERE userid = :userId")
    ResultSet recordLogin(@Param("userId") UUID userId, @Param("loginDate") Instant loginDate, @Param("token") UUID token);

    @Query("UPDATE user_table SET first_name = :firstName, last_name = :lastName, full_name = :fullName, language_preference = :language, timezone = :timezone, update_date = :updatedAt WHERE userid = :userId")
    ResultSet updateProfile(@Param("userId") UUID userId, @Param("firstName") String firstName,
                       @Param("lastName") String lastName, @Param("fullName") String fullName,
                       @Param("language") String language, @Param("timezone") String timezone,
                       @Param("updatedAt") Instant updatedAt);

    @Query("UPDATE user_table SET email = :email, update_date = :updatedAt WHERE userid = :userId")
    ResultSet changeEmail(@Param("userId") UUID userId, @Param("email") String email, @Param("updatedAt") Instant updatedAt);

    @Query("UPDATE user_table SET username = :username, update_date = :updatedAt WHERE userid = :userId")
    ResultSet changeUsername(@Param("userId") UUID userId, @Param("username") String username, @Param("updatedAt") Instant updatedAt);

    @Query("UPDATE user_table SET account_verification_status = :verification, update_date = :updatedAt WHERE userid = :userId")
    ResultSet changeVerificationStatus(@Param("userId") UUID userId, @Param("verification") User.VerificationStatus verification,
                                  @Param("updatedAt") Instant updatedAt);

    @Query("UPDATE user_table SET status = :status, update_date = :updatedAt WHERE userid = :userId")
    ResultSet changeStatus(@Param("userId") UUID userId, @Param("status") RecordStatus status, @Param("updatedAt") Instant updatedAt);

    @Query("UPDATE user_table SET update_date = :updatedAt, status = :status, username = :username, password = :password, first_name = :firstName, last_name = :lastName, full_name = :fullName, account_verification_status = :verification WHERE userid = :userId")
    ResultSet completeRegistration(@Param("userId") UUID userId, @Param("updatedAt") Instant updatedAt,
                              @Param("status") RecordStatus status, @Param("username") String username,
                              @Param("password") String password, @Param("firstName") String firstName,
                              @Param("lastName") String lastName, @Param("fullName") String fullName,
                              @Param("verification") User.VerificationStatus verification);
}
