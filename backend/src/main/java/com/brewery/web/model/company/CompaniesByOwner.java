package com.brewery.web.model.company;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.time.Instant;
import java.util.UUID;
import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("crm_organizations_by_owner")
@JsonDeserialize(builder = CompaniesByOwner.Builder.class)
public class CompaniesByOwner {
    @PrimaryKeyColumn(name = "workspace_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private UUID workspaceId;

    @PrimaryKeyColumn(name = "owner_user_id", ordinal = 1, type = PrimaryKeyType.PARTITIONED)
    private UUID ownerUserId;

    @PrimaryKeyColumn(name = "status", ordinal = 2, type = PrimaryKeyType.PARTITIONED)
    private String status;

    @PrimaryKeyColumn(name = "updated_at", ordinal = 3, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING)
    private Instant updatedAt;

    @PrimaryKeyColumn(name = "organization_id", ordinal = 4, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.ASCENDING)
    private UUID companyId;

    @Column("name")
    private String name;

    @Column("organization_type")
    private String companyType;

    @Column("industry")
    private String industry;

    @Column("phone")
    private String phone;

    public static Builder builder() {
        return new Builder();
    }

    public UUID getWorkspaceId() {
        return this.workspaceId;
    }

    public UUID getOwnerUserId() {
        return this.ownerUserId;
    }

    public String getStatus() {
        return this.status;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public UUID getCompanyId() {
        return this.companyId;
    }

    public String getName() {
        return this.name;
    }

    public String getCompanyType() {
        return this.companyType;
    }

    public String getIndustry() {
        return this.industry;
    }

    public String getPhone() {
        return this.phone;
    }

    @JsonPOJOBuilder(withPrefix = "with")
    public static class Builder {
        private UUID workspaceId;
        private UUID ownerUserId;
        private String status;
        private Instant updatedAt;
        private UUID companyId;
        private String name;
        private String companyType;
        private String industry;
        private String phone;

        private Builder() {}

        public Builder withWorkspaceId(UUID workspaceId) {
            this.workspaceId = workspaceId;
            return this;
        }

        public Builder withOwnerUserId(UUID ownerUserId) {
            this.ownerUserId = ownerUserId;
            return this;
        }

        public Builder withStatus(String status) {
            this.status = status;
            return this;
        }

        public Builder withUpdatedAt(Instant updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public Builder withCompanyId(UUID companyId) {
            this.companyId = companyId;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withCompanyType(String companyType) {
            this.companyType = companyType;
            return this;
        }

        public Builder withIndustry(String industry) {
            this.industry = industry;
            return this;
        }

        public Builder withPhone(String phone) {
            this.phone = phone;
            return this;
        }

        public CompaniesByOwner build() {
            CompaniesByOwner value = new CompaniesByOwner();
            value.workspaceId = this.workspaceId;
            value.ownerUserId = this.ownerUserId;
            value.status = this.status;
            value.updatedAt = this.updatedAt;
            value.companyId = this.companyId;
            value.name = this.name;
            value.companyType = this.companyType;
            value.industry = this.industry;
            value.phone = this.phone;
            return value;
        }
    }
}
