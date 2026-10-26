package com.brewery.web.repositories.user;

import com.brewery.web.model.user.UserIdsByUsername;
import com.brewery.web.model.record.RecordStatus;
import org.springframework.data.cassandra.repository.MapIdCassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserIdsByUsernameRepository extends MapIdCassandraRepository<UserIdsByUsername> {
    @Query("SELECT * FROM user_ids_by_username WHERE username = :username AND status = :status")
    List<UserIdsByUsername> findByUsernameAndStatus(@Param("username") String username, @Param("status") RecordStatus status);
}
