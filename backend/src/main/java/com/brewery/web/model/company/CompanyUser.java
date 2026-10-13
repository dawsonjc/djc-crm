package com.brewery.web.model.company;

import com.brewery.web.model.record.RecordStatus;
import java.time.Instant;
import java.util.UUID;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

/** Company membership only. Credentials, profile and verification belong to User. */
@Table("company_users_by_company")
public record CompanyUser(
        @PrimaryKeyColumn(name = "company_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
        UUID companyId,
        @PrimaryKeyColumn(name = "user_id", ordinal = 1, type = PrimaryKeyType.CLUSTERED)
        UUID userId,
        @Column("role") String role,
        @Column("status") RecordStatus status,
        @Column("created_at") Instant createdAt,
        @Column("updated_at") Instant updatedAt
) {}
