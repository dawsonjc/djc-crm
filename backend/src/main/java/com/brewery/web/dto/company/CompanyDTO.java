package com.brewery.web.dto.company;

import com.brewery.web.model.company.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(builder = CompanyDTO.Builder.class)
public record CompanyDTO(
        UUID workspaceId,
        UUID companyId,
        String name,
        String normalizedName,
        String companyType,
        String industry,
        String website,
        String emailDomain,
        String phone,
        UUID ownerUserId,
        UUID parentCompanyId,
        Integer employeeCount,
        BigDecimal annualRevenue,
        String currency,
        CrmAddress billingAddress,
        CrmAddress shippingAddress,
        String description,
        String status,
        Map<String, String> customFields,
        Instant createdAt,
        Instant updatedAt,
        UUID createdByUserId,
        UUID updatedByUserId
) {
    public static Builder builder() {
        return new Builder();
    }

    public static CompanyDTO from(Company company) {
        return builder()
                .withWorkspaceId(company.getWorkspaceId())
                .withCompanyId(company.getcompanyId())
                .withName(company.getName())
                .withNormalizedName(company.getNormalizedName())
                .withCompanyType(company.getCompanyType())
                .withIndustry(company.getIndustry())
                .withWebsite(company.getWebsite())
                .withEmailDomain(company.getEmailDomain())
                .withPhone(company.getPhone())
                .withOwnerUserId(company.getOwnerUserId())
                .withParentCompanyId(company.getParentCompanyId())
                .withEmployeeCount(company.getEmployeeCount())
                .withAnnualRevenue(company.getAnnualRevenue())
                .withCurrency(company.getCurrency())
                .withBillingAddress(company.getBillingAddress())
                .withShippingAddress(company.getShippingAddress())
                .withDescription(company.getDescription())
                .withStatus(company.getStatus())
                .withCustomFields(company.getCustomFields())
                .withCreatedAt(company.getCreatedAt())
                .withUpdatedAt(company.getUpdatedAt())
                .withCreatedByUserId(company.getCreatedByUserId())
                .withUpdatedByUserId(company.getUpdatedByUserId())
                .build();
    }

    public static CompanyDTO from(CompaniesByOwner company) {
        return builder()
                .withWorkspaceId(company.getWorkspaceId())
                .withOwnerUserId(company.getOwnerUserId())
                .withStatus(company.getStatus())
                .withUpdatedAt(company.getUpdatedAt())
                .withCompanyId(company.getCompanyId())
                .withName(company.getName())
                .withCompanyType(company.getCompanyType())
                .withIndustry(company.getIndustry())
                .withPhone(company.getPhone())
                .build();
    }

    public static CompanyDTO from(CompanyIdsByName company) {
        return builder()
                .withWorkspaceId(company.getWorkspaceId())
                .withNormalizedName(company.getNormalizedName())
                .withCompanyId(company.getCompanyId())
                .withName(company.getName())
                .withStatus(company.getStatus())
                .build();
    }

    public static CompanyDTO from(CompanyIdsByDomain company) {
        return builder()
                .withWorkspaceId(company.getWorkspaceId())
                .withEmailDomain(company.getEmailDomain())
                .withCompanyId(company.getCompanyId())
                .withName(company.getName())
                .withStatus(company.getStatus())
                .build();
    }

    public Company toEntity() {
        return Company.builder()
                .withWorkspaceId(workspaceId)
                .withCompanyId(companyId)
                .withName(name)
                .withNormalizedName(normalizedName)
                .withCompanyType(companyType)
                .withIndustry(industry)
                .withWebsite(website)
                .withEmailDomain(emailDomain)
                .withPhone(phone)
                .withOwnerUserId(ownerUserId)
                .withParentCompanyId(parentCompanyId)
                .withEmployeeCount(employeeCount)
                .withAnnualRevenue(annualRevenue)
                .withCurrency(currency)
                .withBillingAddress(billingAddress)
                .withShippingAddress(shippingAddress)
                .withDescription(description)
                .withStatus(status)
                .withCustomFields(customFields)
                .withCreatedAt(createdAt)
                .withUpdatedAt(updatedAt)
                .withCreatedByUserId(createdByUserId)
                .withUpdatedByUserId(updatedByUserId)
                .build();
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

        public CompanyDTO build() {
            return new CompanyDTO(
                    this.workspaceId,
                    this.companyId,
                    this.name,
                    this.normalizedName,
                    this.companyType,
                    this.industry,
                    this.website,
                    this.emailDomain,
                    this.phone,
                    this.ownerUserId,
                    this.parentCompanyId,
                    this.employeeCount,
                    this.annualRevenue,
                    this.currency,
                    this.billingAddress,
                    this.shippingAddress,
                    this.description,
                    this.status,
                    this.customFields,
                    this.createdAt,
                    this.updatedAt,
                    this.createdByUserId,
                    this.updatedByUserId
            );
        }
    }
}
