package com.brewery.web.repositories.company;

import com.brewery.web.model.company.Company;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.UUID;

public interface CompanyRepository extends CassandraRepository<Company, UUID> {
}
