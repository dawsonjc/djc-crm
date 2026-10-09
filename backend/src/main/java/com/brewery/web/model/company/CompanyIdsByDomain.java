package com.brewery.web.model.company;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.UUID;
import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("crm_organization_ids_by_domain")
@JsonDeserialize(builder = CompanyIdsByDomain.Builder.class)
public class CompanyIdsByDomain {
    @PrimaryKeyColumn(name = "workspace_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private UUID workspaceId;

    @PrimaryKeyColumn(name = "email_domain", ordinal = 1, type = PrimaryKeyType.PARTITIONED)
    private String emailDomain;

    @PrimaryKeyColumn(name = "organization_id", ordinal = 2, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.ASCENDING)
    private UUID companyId;

    @Column("name")
    private String name;

    @Column("status")
    private String status;

    public static Builder builder() {
        return new Builder();
    }

    public UUID getWorkspaceId() {
        return this.workspaceId;
    }

    public String getEmailDomain() {
        return this.emailDomain;
    }

    public UUID getCompanyId() {
        return this.companyId;
    }

    public String getName() {
        return this.name;
    }

    public String getStatus() {
        return this.status;
    }

    @JsonPOJOBuilder(withPrefix = "with")
    public static class Builder {
        private UUID workspaceId;
        private String emailDomain;
        private UUID companyId;
        private String name;
        private String status;

        private Builder() {}

        public Builder withWorkspaceId(UUID workspaceId) {
            this.workspaceId = workspaceId;
            return this;
        }

        public Builder withEmailDomain(String emailDomain) {
            this.emailDomain = emailDomain;
            return this;
        }

        public Builder withOrganizationId(UUID companyId) {
            this.companyId = companyId;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withStatus(String status) {
            this.status = status;
            return this;
        }

        public CompanyIdsByDomain build() {
            CompanyIdsByDomain value = new CompanyIdsByDomain();
            value.workspaceId = this.workspaceId;
            value.emailDomain = this.emailDomain;
            value.companyId = this.companyId;
            value.name = this.name;
            value.status = this.status;
            return value;
        }
    }
}
