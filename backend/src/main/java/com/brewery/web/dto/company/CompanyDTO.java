package com.brewery.web.dto.company;

import com.brewery.web.model.company.Company;
import com.brewery.web.model.company.CrmAddress;
import com.brewery.web.model.record.RecordStatus;
import java.time.Instant;
import java.util.UUID;

/** Tenant company response. Customer organization projections have separate models. */
public record CompanyDTO(
        UUID companyId,
        String name,
        String legalName,
        String billingEmail,
        String phone,
        String website,
        UUID primaryAdminUserId,
        CrmAddress billingAddress,
        String status,
        Instant createdAt,
        Instant updatedAt,
        UUID createdByUserId,
        UUID updatedByUserId
) {
    public static CompanyDTO from(Company company) {
        return new CompanyDTO(
                company.getCompanyId(),
                company.getName(),
                company.getLegalName(),
                company.getBillingEmail(),
                company.getPhone(),
                company.getWebsite(),
                company.getPrimaryAdminUserId(),
                company.getBillingAddress(),
                company.getStatus() == null ? null : company.getStatus().getStatus(),
                company.getCreatedAt(),
                company.getUpdatedAt(),
                company.getCreatedByUserId(),
                company.getUpdatedByUserId()
        );
    }

    public Company toEntity() {
        return Company.builder()
                .withCompanyId(companyId)
                .withName(name)
                .withLegalName(legalName)
                .withBillingEmail(billingEmail)
                .withPhone(phone)
                .withWebsite(website)
                .withPrimaryAdminUserId(primaryAdminUserId)
                .withBillingAddress(billingAddress)
                .withStatus(status == null ? null : RecordStatus.fromString(status))
                .withCreatedAt(createdAt)
                .withUpdatedAt(updatedAt)
                .withCreatedByUserId(createdByUserId)
                .withUpdatedByUserId(updatedByUserId)
                .build();
    }
}
