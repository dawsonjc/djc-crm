package com.brewery.web.controller.auth;

import com.brewery.web.services.RegistrationEmailService;
import com.brewery.web.services.UserTableService;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tools.jackson.databind.json.JsonMapper;

import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CompanyUserDeletionTest {
    private final UserTableService users = mock(UserTableService.class);
    private final MockMvc mvc = MockMvcBuilders.standaloneSetup(new CompanyUserController(
            users, mock(RegistrationEmailService.class), JsonMapper.builder().build())).build();
    private final UUID companyId = UUID.randomUUID();
    private final UUID userId = UUID.randomUUID();

    @Test
    void deletesThroughUserController() throws Exception {
        mvc.perform(delete("/auth/user/{userId}", userId).param("companyId", companyId.toString()))
                .andExpect(status().isOk()).andExpect(jsonPath("$.success").value(true));
        verify(users).deleteCompanyUser(companyId, userId);
    }

    @Test
    void rejectsMalformedIdsAndMissingCompanyWithoutDeleting() throws Exception {
        mvc.perform(delete("/auth/user/invalid").param("companyId", companyId.toString()))
                .andExpect(status().isBadRequest()).andExpect(jsonPath("$.success").value(false));
        mvc.perform(delete("/auth/user/{userId}", userId)).andExpect(status().isBadRequest());
        verifyNoInteractions(users);
    }

    @Test
    void rejectsUserFromAnotherCompany() throws Exception {
        doThrow(new IllegalArgumentException("Wrong company")).when(users).deleteCompanyUser(companyId, userId);
        mvc.perform(delete("/auth/user/{userId}", userId).param("companyId", companyId.toString()))
                .andExpect(status().isBadRequest()).andExpect(jsonPath("$.success").value(false));
    }
}
