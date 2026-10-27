package com.brewery.web.model.organization;

import com.brewery.web.model.record.RecordStatus;
import tools.jackson.databind.annotation.JsonDeserialize;
import tools.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.UUID;
import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("crm_organization_ids_by_name")
@JsonDeserialize(builder = OrganizationIdsByName.Builder.class)
public class OrganizationIdsByName {
    @PrimaryKeyColumn(name = "workspace_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private UUID workspaceId;

    @PrimaryKeyColumn(name = "normalized_name", ordinal = 1, type = PrimaryKeyType.PARTITIONED)
    private String normalizedName;

    @PrimaryKeyColumn(name = "organization_id", ordinal = 2, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.ASCENDING)
    private UUID organizationId;

    @Column("name")
    private String name;

    @Column("status")
    private RecordStatus status;

    public static Builder builder() {
        return new Builder();
    }

    public UUID getWorkspaceId() {
        return this.workspaceId;
    }

    public String getNormalizedName() {
        return this.normalizedName;
    }

    public UUID getOrganizationId() {
        return this.organizationId;
    }

    public String getName() {
        return this.name;
    }

    public RecordStatus getStatus() {
        return this.status;
    }

    @JsonPOJOBuilder(withPrefix = "with")
    public static class Builder {
        private UUID workspaceId;
        private String normalizedName;
        private UUID organizationId;
        private String name;
        private RecordStatus status;

        private Builder() {}

        public Builder withWorkspaceId(UUID workspaceId) {
            this.workspaceId = workspaceId;
            return this;
        }

        public Builder withNormalizedName(String normalizedName) {
            this.normalizedName = normalizedName;
            return this;
        }

        public Builder withOrganizationId(UUID organizationId) {
            this.organizationId = organizationId;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withStatus(RecordStatus status) {
            this.status = status;
            return this;
        }

        public OrganizationIdsByName build() {
            OrganizationIdsByName value = new OrganizationIdsByName();
            value.workspaceId = this.workspaceId;
            value.normalizedName = this.normalizedName;
            value.organizationId = this.organizationId;
            value.name = this.name;
            value.status = this.status;
            return value;
        }
    }
}
