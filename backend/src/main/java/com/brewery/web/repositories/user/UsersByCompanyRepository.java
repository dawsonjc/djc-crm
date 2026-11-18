package com.brewery.web.repositories.user;

import com.brewery.web.model.User;
import com.brewery.web.model.record.RecordStatus;
import com.brewery.web.model.user.UsersByCompany;
import org.springframework.data.cassandra.repository.MapIdCassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import java.util.UUID;

public interface UsersByCompanyRepository extends MapIdCassandraRepository<UsersByCompany> {

    @Query("SELECT * FROM users_by_company WHERE company_id = :companyId")
    Slice<UsersByCompany> findByCompanyId(@Param("companyId") UUID companyId, Pageable pageable);

    @Query("SELECT * FROM users_by_company WHERE company_id = :companyId AND status = :status")
    Slice<UsersByCompany> findByCompanyIdAndStatus(@Param("companyId") UUID companyId, @Param("status") RecordStatus status, Pageable pageable);
}
