package com.brewery.web.dto;

import com.brewery.web.configuration.CassandraConversionConfig;
import com.brewery.web.model.User;
import com.brewery.web.model.UserRole;
import com.brewery.web.model.record.RecordStatus;
import com.brewery.web.model.user.UserIdsByEmail;
import com.brewery.web.model.user.UserIdsByUsername;
import com.brewery.web.model.user.UsersByVerificationStatus;
import com.datastax.oss.driver.api.core.CqlIdentifier;
import tools.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.data.cassandra.core.convert.MappingCassandraConverter;
import org.springframework.data.cassandra.core.mapping.CassandraMappingContext;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class UserDTOTest {
    private final ObjectMapper mapper = tools.jackson.databind.json.JsonMapper.builder()
            .findAndAddModules().build();

    @Test
    void mapsBaseUserProfileAndRelationsWithoutExposingCredentials() throws Exception {
        UUID userId = UUID.randomUUID();
        UUID companyId = UUID.randomUUID();
        Instant created = Instant.parse("2026-10-26T07:00:00Z");
        User user = User.builder().withUserId(userId).withCompanyId(companyId)
                .withCompanyName("Brewery").withUsername("test-user").withEmail("test@example.com")
                .withPassword("private-hash").withAuthApiToken(UUID.randomUUID())
                .withCreateDate(created).withStatus(RecordStatus.ACTIVE)
                .withAccountVerificationStatus(User.VerificationStatus.PENDING)
                .withTwoFactorAuthentication(false)
                .withRoles(List.of(new UserRole(null, null, RecordStatus.ACTIVE, UUID.randomUUID(), "User", userId)))
                .withConversations(List.of()).build();

        UserDTO dto = UserDTO.from(user);
        var json = mapper.readTree(mapper.writeValueAsString(dto));

        assertThat(dto.userId()).isEqualTo(userId);
        assertThat(dto.companyId()).isEqualTo(companyId);
        assertThat(dto.createDate()).isEqualTo(created);
        assertThat(dto.twoFactorAuthentication()).isFalse();
        assertThat(dto.roles()).isSameAs(user.getRoles());
        assertThat(dto.conversations()).isEmpty();
        assertThat(json.has("password")).isFalse();
        assertThat(json.has("authApiToken")).isFalse();
    }

    @Test
    void lookupRowsLeaveMissingProfileFieldsNull() {
        UUID id = UUID.randomUUID();
        UserDTO email = UserDTO.from(UserIdsByEmail.builder()
                .withEmail("test@example.com").withStatus(RecordStatus.ACTIVE).withUserId(id).build());
        UserDTO username = UserDTO.from(UserIdsByUsername.builder()
                .withUsername("test-user").withStatus(RecordStatus.DISABLED).withUserId(id).build());

        assertThat(email.email()).isEqualTo("test@example.com");
        assertThat(email.userId()).isEqualTo(id);
        assertThat(email.status()).isEqualTo(RecordStatus.ACTIVE);
        assertThat(email.username()).isNull();
        assertThat(email.twoFactorAuthentication()).isNull();
        assertThat(username.username()).isEqualTo("test-user");
        assertThat(username.status()).isEqualTo(RecordStatus.DISABLED);
        assertThat(username.email()).isNull();
    }

    @Test
    @SuppressWarnings("removal")
    void verificationProjectionUsesExistingStatusConvertersAndVerificationEnumNames() {
        UUID id = UUID.randomUUID();
        UsersByVerificationStatus row = UsersByVerificationStatus.builder()
                .withUserId(id).withStatus(RecordStatus.ACTIVE)
                .withAccountVerificationStatus(User.VerificationStatus.PENDING)
                .withEmail("test@example.com").withCompanyName("Brewery")
                .withTwoFactorAuthentication(false).build();
        CassandraMappingContext context = new CassandraMappingContext();
        var conversions = new CassandraConversionConfig().cassandraCustomConversions();
        context.setSimpleTypeHolder(conversions.getSimpleTypeHolder());
        context.afterPropertiesSet();
        MappingCassandraConverter converter = new MappingCassandraConverter(context);
        converter.setCustomConversions(conversions);
        converter.afterPropertiesSet();
        Map<CqlIdentifier, Object> columns = new LinkedHashMap<>();
        converter.write(row, columns);

        assertThat(columns.get(CqlIdentifier.fromCql("userid"))).isEqualTo(id);
        assertThat(columns.get(CqlIdentifier.fromCql("status"))).isEqualTo("Active");
        assertThat(columns.get(CqlIdentifier.fromCql("account_verification_status"))).isEqualTo("PENDING");
        assertThat(columns.get(CqlIdentifier.fromCql("two_factor_authentication"))).isEqualTo(false);
        UserDTO dto = UserDTO.from(row);
        assertThat(dto.accountVerificationStatus()).isEqualTo(User.VerificationStatus.PENDING);
        assertThat(dto.companyName()).isEqualTo("Brewery");
        assertThat(dto.roles()).isNull();
    }
}
