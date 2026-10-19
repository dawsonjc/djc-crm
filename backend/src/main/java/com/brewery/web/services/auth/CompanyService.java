package com.brewery.web.services.auth;

import com.brewery.web.dto.company.CompanyDTO;
import com.brewery.web.dto.formdata.auth.CompanyFormData;
import com.brewery.web.model.User;
import com.brewery.web.model.company.Company;
import com.brewery.web.model.record.RecordStatus;
import com.brewery.web.repositories.company.CompanyRepository;
import com.brewery.web.user.AuthSessionUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public Company getCompanyById(UUID companyId) {
        return companyRepository.findById(companyId).orElse(null);
    }

    public List<CompanyDTO> getCompanies() {
        return companyRepository.findAll().stream()
                .map(CompanyDTO::from)
                .toList();
    }

    public Company addCompanyByFormData(CompanyFormData formData, User user) {
        Instant now = Instant.now();
        Company company = Company.builder()
                .withCompanyId(UUID.randomUUID())
                .withCreatedAt(now)
                .withUpdatedAt(now)
                .withStatus(RecordStatus.fromString(formData.status()))
                .withName(formData.name())
                .withLegalName(formData.legalName())
                .withBillingEmail(formData.billingEmail())
                .withPhone(formData.phone())
                .withWebsite(formData.website())
                .withPrimaryAdminUserId(null)
                .withBillingAddress(formData.billingAddress())
                .withCreatedByUserId(user.getUserId())
                .withUpdatedByUserId(user.getUserId())
                .build();

        return this.saveCompany(company);
    }

    public Company saveCompany(Company company) {
        return companyRepository.save(company);
    }
}
