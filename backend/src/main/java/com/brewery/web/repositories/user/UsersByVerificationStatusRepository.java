package com.brewery.web.repositories.user;

import com.brewery.web.model.user.UsersByVerificationStatus;
import com.brewery.web.model.record.RecordStatus;
import com.brewery.web.model.User;
import org.springframework.data.cassandra.repository.MapIdCassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersByVerificationStatusRepository extends MapIdCassandraRepository<UsersByVerificationStatus> {
    @Query("SELECT * FROM users_by_verification_status WHERE account_verification_status = :accountVerificationStatus AND status = :status")
    List<UsersByVerificationStatus> findByAccountVerificationStatusAndStatus(@Param("accountVerificationStatus") User.VerificationStatus accountVerificationStatus, @Param("status") RecordStatus status);
}
