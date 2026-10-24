package com.brewery.web.services.auth;

import com.brewery.web.dto.formdata.auth.CompanyFormData;
import com.brewery.web.security.AuthSessionFilter;
import com.brewery.web.model.User;
import com.brewery.web.model.company.Company;
import com.brewery.web.repositories.company.CompanyRepository;
import com.brewery.web.user.AuthSessionUser;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpSession;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CompanyServiceSessionTest {
    private final CompanyRepository repository = mock(CompanyRepository.class);
    private final CompanyService service = new CompanyService(repository);
    @Test
    void rejectsMissingSessionWithoutCreatingOneOrSavingCompany() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain chain = new MockFilterChain();
        new AuthSessionFilter().doFilter(request, response, chain);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        assertThat(chain.getRequest()).isNull();
        assertThat(request.getSession(false)).isNull();
        verifyNoInteractions(repository);
    }
    @Test
    void rejectsSessionWithoutLoggedInUser() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setSession(new MockHttpSession());
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain chain = new MockFilterChain();
        new AuthSessionFilter().doFilter(request, response, chain);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        assertThat(chain.getRequest()).isNull();
        verifyNoInteractions(repository);
    }
    @Test
    void assignsAuditFieldsFromTheLoginSessionUser() {
        UUID userId = UUID.randomUUID();
        User user = User.builder().withUserId(userId).build();
        MockHttpSession session = new MockHttpSession();
        session.setAttribute(AuthSessionUser.AUTH_SESSION_USER, user);
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setSession(session);
        when(repository.save(any(Company.class))).thenAnswer(invocation -> invocation.getArgument(0));
        Company saved = service.addCompanyByFormData(
                new CompanyFormData("Brewery", null, null, null, null, null, null, "Active"), user);
        assertThat(saved.getCreatedByUserId()).isEqualTo(userId);
        assertThat(saved.getUpdatedByUserId()).isEqualTo(userId);
        assertThat(request.getSession(false)).isSameAs(session);
        verify(repository).save(saved);
    }
}
