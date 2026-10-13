package com.brewery.web.model.company;

import com.brewery.web.controller.auth.CompanyController;
import com.brewery.web.model.record.RecordStatus;
import com.brewery.web.services.auth.CompanyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CompanyContractTest {
    private CompanyService service;
    private MockMvc mvc;

    @BeforeEach
    void setUp() {
        service = mock(CompanyService.class);
        when(service.addCompany(any())).thenAnswer(invocation -> invocation.getArgument(0));
        ObjectMapper mapper = new ObjectMapper().findAndRegisterModules()
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mvc = MockMvcBuilders.standaloneSetup(new CompanyController(service, mapper))
                .setMessageConverters(new MappingJackson2HttpMessageConverter(mapper)).build();
    }

    @Test
    void createsTenantWithoutWorkspaceAndReturnsSavedWireContract() throws Exception {
        mvc.perform(post("/auth/company/add").contentType(MediaType.APPLICATION_JSON).content("""
                {"name":"  Example Business  ","legalName":"Example LLC",
                 "billingEmail":"billing@example.com","billingAddress":{"city":"Phoenix"}}
                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.companyId").isNotEmpty())
                .andExpect(jsonPath("$.data.name").value("Example Business"))
                .andExpect(jsonPath("$.data.legalName").value("Example LLC"))
                .andExpect(jsonPath("$.data.status").value("Active"))
                .andExpect(jsonPath("$.data.billingAddress.city").value("Phoenix"))
                .andExpect(jsonPath("$.data.createdAt").isString())
                .andExpect(jsonPath("$.data.updatedAt").isString())
                .andExpect(jsonPath("$.data.workspaceId").doesNotExist())
                .andExpect(jsonPath("$.data.shippingAddress").doesNotExist());
        var saved = org.mockito.ArgumentCaptor.forClass(Company.class);
        verify(service).addCompany(saved.capture());
        assertThat(saved.getValue().getStatus()).isEqualTo(RecordStatus.ACTIVE);
        assertThat(saved.getValue().getCreatedAt()).isEqualTo(saved.getValue().getUpdatedAt());
    }

    @Test
    void acceptsExistingDisabledStatus() throws Exception {
        mvc.perform(post("/auth/company/add").contentType(MediaType.APPLICATION_JSON)
                        .content(""" 
                                {"name":"Example","status":"Disabled"}
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.status").value("Disabled"));
    }

    @Test
    void rejectsInvalidStatusBeforePersistence() throws Exception {
        mvc.perform(post("/auth/company/add").contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"name":"Example","status":"Suspended"}
                                """))
                .andExpect(status().isBadRequest());
        verify(service, never()).addCompany(any());
    }
}
