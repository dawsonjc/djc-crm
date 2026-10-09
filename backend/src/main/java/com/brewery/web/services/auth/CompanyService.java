package com.brewery.web.services.auth;

import com.brewery.web.dto.company.CompanyDTO;
import com.brewery.web.model.company.Company;
import com.brewery.web.repositories.company.CompanyRepository;
import org.springframework.stereotype.Service;

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
}
