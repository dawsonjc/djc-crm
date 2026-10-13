package com.brewery.web.model.company;

import com.brewery.web.dto.formdata.auth.CompanyFormData;
import com.brewery.web.model.record.RecordStatus;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.time.Instant;
import java.util.UUID;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.Frozen;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

/** A business using DJC CRM. Its ID is the tenant boundary, not a customer organization ID. */
@Table("companies_by_id")
@JsonDeserialize(builder = Company.Builder.class)
public class Company {
    @PrimaryKey("company_id")
    private UUID companyId;

    @Column("created_at")
    private Instant createdAt;

    @Column("updated_at")
    private Instant updatedAt;

    @Column("status")
    private RecordStatus status;

    @Column("name")
    private String name;

    @Column("legal_name")
    private String legalName;

    @Column("billing_email")
    private String billingEmail;

    @Column("phone")
    private String phone;

    @Column("website")
    private String website;

    @Column("primary_admin_user_id")
    private UUID primaryAdminUserId;

    @Column("billing_address")
    @Frozen
    private CrmAddress billingAddress;

    @Column("created_by_user_id")
    private UUID createdByUserId;

    @Column("updated_by_user_id")
    private UUID updatedByUserId;

    private Company() {}

    public static Builder builder() { return new Builder(); }

    public UUID getCompanyId() { return companyId; }
    public String getName() { return name; }
    public String getLegalName() { return legalName; }
    public String getBillingEmail() { return billingEmail; }
    public String getPhone() { return phone; }
    public String getWebsite() { return website; }
    public UUID getPrimaryAdminUserId() { return primaryAdminUserId; }
    public CrmAddress getBillingAddress() { return billingAddress; }
    public RecordStatus getStatus() { return status; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
    public UUID getCreatedByUserId() { return createdByUserId; }
    public UUID getUpdatedByUserId() { return updatedByUserId; }

    public static Company fromCompanyFormData(CompanyFormData data) {
        return builder()
                .withName(data.name().trim())
                .withLegalName(data.legalName())
                .withBillingEmail(data.billingEmail())
                .withPhone(data.phone())
                .withWebsite(data.website())
                .withPrimaryAdminUserId(data.primaryAdminUserId())
                .withBillingAddress(data.billingAddress())
                .withStatus(data.status() == null ? RecordStatus.ACTIVE : RecordStatus.fromString(data.status()))
                .build();
    }

    @JsonPOJOBuilder(withPrefix = "with")
    public static class Builder {
        private UUID companyId;
        private String name;
        private String legalName;
        private String billingEmail;
        private String phone;
        private String website;
        private UUID primaryAdminUserId;
        private CrmAddress billingAddress;
        private RecordStatus status;
        private Instant createdAt;
        private Instant updatedAt;
        private UUID createdByUserId;
        private UUID updatedByUserId;

        public Builder withCompanyId(UUID companyId) {
            this.companyId = companyId;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withLegalName(String legalName) {
            this.legalName = legalName;
            return this;
        }

        public Builder withBillingEmail(String billingEmail) {
            this.billingEmail = billingEmail;
            return this;
        }

        public Builder withPhone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder withWebsite(String website) {
            this.website = website;
            return this;
        }

        public Builder withPrimaryAdminUserId(UUID primaryAdminUserId) {
            this.primaryAdminUserId = primaryAdminUserId;
            return this;
        }

        public Builder withBillingAddress(CrmAddress billingAddress) {
            this.billingAddress = billingAddress;
            return this;
        }

        public Builder withStatus(RecordStatus status) {
            this.status = status;
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
            value.companyId = companyId;
            value.name = name;
            value.legalName = legalName;
            value.billingEmail = billingEmail;
            value.phone = phone;
            value.website = website;
            value.primaryAdminUserId = primaryAdminUserId;
            value.billingAddress = billingAddress;
            value.status = status;
            value.createdAt = createdAt;
            value.updatedAt = updatedAt;
            value.createdByUserId = createdByUserId;
            value.updatedByUserId = updatedByUserId;
            return value;
        }
    }
}
