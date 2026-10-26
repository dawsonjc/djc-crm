package com.brewery.web.repositories.user;

import com.brewery.web.model.user.UserIdsByEmail;
import com.brewery.web.model.record.RecordStatus;
import org.springframework.data.cassandra.repository.MapIdCassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserIdsByEmailRepository extends MapIdCassandraRepository<UserIdsByEmail> {
    @Query("SELECT * FROM user_ids_by_email WHERE email = :email AND status = :status")
    List<UserIdsByEmail> findByEmailAndStatus(@Param("email") String email, @Param("status") RecordStatus status);
}
