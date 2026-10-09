package com.brewery.web.model.company;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.UUID;
import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("crm_organization_ids_by_name")
@JsonDeserialize(builder = CompanyIdsByName.Builder.class)
public class CompanyIdsByName {
    @PrimaryKeyColumn(name = "workspace_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private UUID workspaceId;

    @PrimaryKeyColumn(name = "normalized_name", ordinal = 1, type = PrimaryKeyType.PARTITIONED)
    private String normalizedName;

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

    public String getNormalizedName() {
        return this.normalizedName;
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
        private String normalizedName;
        private UUID companyId;
        private String name;
        private String status;

        private Builder() {}

        public Builder withWorkspaceId(UUID workspaceId) {
            this.workspaceId = workspaceId;
            return this;
        }

        public Builder withNormalizedName(String normalizedName) {
            this.normalizedName = normalizedName;
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

        public Builder withStatus(String status) {
            this.status = status;
            return this;
        }

        public CompanyIdsByName build() {
            CompanyIdsByName value = new CompanyIdsByName();
            value.workspaceId = this.workspaceId;
            value.normalizedName = this.normalizedName;
            value.companyId = this.companyId;
            value.name = this.name;
            value.status = this.status;
            return value;
        }
    }
}
