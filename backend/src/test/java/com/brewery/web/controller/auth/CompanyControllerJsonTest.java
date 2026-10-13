package com.brewery.web.controller.auth;

import com.brewery.web.dto.company.CompanyDTO;
import com.brewery.web.dto.formdata.auth.CompanyFormData;
import com.brewery.web.model.company.Company;
import com.brewery.web.services.auth.CompanyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.mock.web.MockHttpServletRequest;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CompanyControllerJsonTest {
    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(JacksonAutoConfiguration.class));
    private final Instant createdAt = Instant.parse("2026-10-12T21:00:00Z");
    private final Instant updatedAt = Instant.parse("2026-10-12T21:01:00Z");
    private final Company company = Company.builder().withName("Brewery")
            .withCreatedAt(createdAt).withUpdatedAt(updatedAt).build();

    @Test
    void addCompanySerializesTimestampsUsingTheConfiguredMapper() {
        contextRunner.run(context -> {
            ObjectMapper mapper = context.getBean(ObjectMapper.class);
            CompanyService service = mock(CompanyService.class);
            when(service.addCompanyByFormData(any(), any())).thenReturn(company);
            CompanyController controller = new CompanyController(service, mapper);

            var response = controller.addCompany(
                    new CompanyFormData("Brewery", null, null, null, null, null, null, "Active"),
                    new MockHttpServletRequest());
            var json = mapper.readTree(mapper.writeValueAsString(response.getBody()));

            assertThat(json.path("success").asBoolean()).isTrue();
            assertThat(json.path("data").path("createdAt").asText()).isEqualTo(createdAt.toString());
            assertThat(json.path("data").path("updatedAt").asText()).isEqualTo(updatedAt.toString());
        });
    }

    @Test
    void listCompaniesSerializesTimestampsUsingTheConfiguredMapper() {
        contextRunner.run(context -> {
            ObjectMapper mapper = context.getBean(ObjectMapper.class);
            CompanyService service = mock(CompanyService.class);
            when(service.getCompanies()).thenReturn(List.of(CompanyDTO.from(company)));
            CompanyController controller = new CompanyController(service, mapper);

            var json = mapper.readTree(mapper.writeValueAsString(controller.getCompanies().getBody()));

            assertThat(json.path("data").get(0).path("createdAt").asText()).isEqualTo(createdAt.toString());
            assertThat(json.path("data").get(0).path("updatedAt").asText()).isEqualTo(updatedAt.toString());
        });
    }
}
