package com.brewery.web.model.company;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.Frozen;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("crm_organizations_by_id")
@JsonDeserialize(builder = Company.Builder.class)
public class Company {
    @PrimaryKeyColumn(name = "workspace_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private UUID workspaceId;

    @PrimaryKeyColumn(name = "organization_id", ordinal = 1, type = PrimaryKeyType.PARTITIONED)
    private UUID companyId;

    @Column("name")
    private String name;

    @Column("normalized_name")
    private String normalizedName;

    @Column("organization_type")
    private String companyType;

    @Column("industry")
    private String industry;

    @Column("website")
    private String website;

    @Column("email_domain")
    private String emailDomain;

    @Column("phone")
    private String phone;

    @Column("owner_user_id")
    private UUID ownerUserId;

    @Column("parent_organization_id")
    private UUID parentCompanyId;

    @Column("employee_count")
    private Integer employeeCount;

    @Column("annual_revenue")
    private BigDecimal annualRevenue;

    @Column("currency")
    private String currency;

    @Column("billing_address")
    @Frozen
    @CassandraType(type = CassandraType.Name.UDT, userTypeName = "crm_address")
    private CrmAddress billingAddress;

    @Column("shipping_address")
    @Frozen
    @CassandraType(type = CassandraType.Name.UDT, userTypeName = "crm_address")
    private CrmAddress shippingAddress;

    @Column("description")
    private String description;

    @Column("status")
    private String status;

    @Column("custom_fields")
    private Map<String, String> customFields;

    @Column("created_at")
    private Instant createdAt;

    @Column("updated_at")
    private Instant updatedAt;

    @Column("created_by_user_id")
    private UUID createdByUserId;

    @Column("updated_by_user_id")
    private UUID updatedByUserId;

    public static Builder builder() {
        return new Builder();
    }

    public UUID getWorkspaceId() {
        return this.workspaceId;
    }

    public UUID getcompanyId() {
        return this.companyId;
    }

    public String getName() {
        return this.name;
    }

    public String getNormalizedName() {
        return this.normalizedName;
    }

    public String getCompanyType() {
        return this.companyType;
    }

    public String getIndustry() {
        return this.industry;
    }

    public String getWebsite() {
        return this.website;
    }

    public String getEmailDomain() {
        return this.emailDomain;
    }

    public String getPhone() {
        return this.phone;
    }

    public UUID getOwnerUserId() {
        return this.ownerUserId;
    }

    public UUID getParentCompanyId() {
        return this.parentCompanyId;
    }

    public Integer getEmployeeCount() {
        return this.employeeCount;
    }

    public BigDecimal getAnnualRevenue() {
        return this.annualRevenue;
    }

    public String getCurrency() {
        return this.currency;
    }

    public CrmAddress getBillingAddress() {
        return this.billingAddress;
    }

    public CrmAddress getShippingAddress() {
        return this.shippingAddress;
    }

    public String getDescription() {
        return this.description;
    }

    public String getStatus() {
        return this.status;
    }

    public Map<String, String> getCustomFields() {
        return this.customFields;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public UUID getCreatedByUserId() {
        return this.createdByUserId;
    }

    public UUID getUpdatedByUserId() {
        return this.updatedByUserId;
    }

    @JsonPOJOBuilder(withPrefix = "with")
    public static class Builder {
        private UUID workspaceId;
        private UUID companyId;
        private String name;
        private String normalizedName;
        private String companyType;
        private String industry;
        private String website;
        private String emailDomain;
        private String phone;
        private UUID ownerUserId;
        private UUID parentCompanyId;
        private Integer employeeCount;
        private BigDecimal annualRevenue;
        private String currency;
        private CrmAddress billingAddress;
        private CrmAddress shippingAddress;
        private String description;
        private String status;
        private Map<String, String> customFields;
        private Instant createdAt;
        private Instant updatedAt;
        private UUID createdByUserId;
        private UUID updatedByUserId;

        private Builder() {}

        public Builder withWorkspaceId(UUID workspaceId) {
            this.workspaceId = workspaceId;
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

        public Builder withNormalizedName(String normalizedName) {
            this.normalizedName = normalizedName;
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

        public Builder withWebsite(String website) {
            this.website = website;
            return this;
        }

        public Builder withEmailDomain(String emailDomain) {
            this.emailDomain = emailDomain;
            return this;
        }

        public Builder withPhone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder withOwnerUserId(UUID ownerUserId) {
            this.ownerUserId = ownerUserId;
            return this;
        }

        public Builder withParentCompanyId(UUID parentCompanyId) {
            this.parentCompanyId = parentCompanyId;
            return this;
        }

        public Builder withEmployeeCount(Integer employeeCount) {
            this.employeeCount = employeeCount;
            return this;
        }

        public Builder withAnnualRevenue(BigDecimal annualRevenue) {
            this.annualRevenue = annualRevenue;
            return this;
        }

        public Builder withCurrency(String currency) {
            this.currency = currency;
            return this;
        }

        public Builder withBillingAddress(CrmAddress billingAddress) {
            this.billingAddress = billingAddress;
            return this;
        }

        public Builder withShippingAddress(CrmAddress shippingAddress) {
            this.shippingAddress = shippingAddress;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder withStatus(String status) {
            this.status = status;
            return this;
        }

        public Builder withCustomFields(Map<String, String> customFields) {
            this.customFields = customFields;
            return this;
        }

        public Builder withCreatedAt(Instant createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder withUpdatedAt(Instant updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public Builder withCreatedByUserId(UUID createdByUserId) {
            this.createdByUserId = createdByUserId;
            return this;
        }

        public Builder withUpdatedByUserId(UUID updatedByUserId) {
            this.updatedByUserId = updatedByUserId;
            return this;
        }

        public Company build() {
            Company value = new Company();
            value.workspaceId = this.workspaceId;
            value.companyId = this.companyId;
            value.name = this.name;
            value.normalizedName = this.normalizedName;
            value.companyType = this.companyType;
            value.industry = this.industry;
            value.website = this.website;
            value.emailDomain = this.emailDomain;
            value.phone = this.phone;
            value.ownerUserId = this.ownerUserId;
            value.parentCompanyId = this.parentCompanyId;
            value.employeeCount = this.employeeCount;
            value.annualRevenue = this.annualRevenue;
            value.currency = this.currency;
            value.billingAddress = this.billingAddress;
            value.shippingAddress = this.shippingAddress;
            value.description = this.description;
            value.status = this.status;
            value.customFields = this.customFields;
            value.createdAt = this.createdAt;
            value.updatedAt = this.updatedAt;
            value.createdByUserId = this.createdByUserId;
            value.updatedByUserId = this.updatedByUserId;
            return value;
        }
    }
}
