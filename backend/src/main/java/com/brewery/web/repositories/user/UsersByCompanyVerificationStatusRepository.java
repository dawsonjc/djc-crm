package com.brewery.web.repositories.user;

import com.brewery.web.model.User;
import com.brewery.web.model.record.RecordStatus;
import com.brewery.web.model.user.UsersByCompanyVerificationStatus;
import org.springframework.data.cassandra.repository.MapIdCassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import java.util.UUID;

public interface UsersByCompanyVerificationStatusRepository extends MapIdCassandraRepository<UsersByCompanyVerificationStatus> {

    @Query("SELECT * FROM users_by_company_verification_status WHERE company_id = :companyId AND account_verification_status = :verification AND status = :status")
    Slice<UsersByCompanyVerificationStatus> findByCompanyIdAndVerificationAndStatus(@Param("companyId") UUID companyId,
            @Param("verification") User.VerificationStatus verification, @Param("status") RecordStatus status, Pageable pageable);
}
