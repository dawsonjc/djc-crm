package com.brewery.web.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class UserBuilderTest {
    @Test
    void toBuilderPreservesAllExistingFieldsWithoutMutatingOriginal() {
        User original = User.builder()
                .withUserId(UUID.randomUUID())
                .withCreateDate(Instant.parse("2026-10-20T07:00:00Z"))
                .withUpdateDate(Instant.parse("2026-10-20T08:00:00Z"))
                .withStatus(com.brewery.web.model.record.RecordStatus.ACTIVE)
                .withUsername("original")
                .withEmail("test@example.com")
                .withPassword("test-hash")
                .withFirstName("Test")
                .withLastName("User")
                .withFullName("Test User")
                .withLanguagePreference("en")
                .withTimezone("America/Phoenix")
                .withLastLoginDate(Instant.parse("2026-10-20T09:00:00Z"))
                .withAccountVerificationStatus(User.VerificationStatus.VERIFIED)
                .withTwoFactorAuthentication(true)
                .withCompanyId(UUID.randomUUID())
                .withCompanyName("Brewery")
                .withAuthApiToken(UUID.randomUUID())
                .withRoles(List.of(new UserRole()))
                .withConversations(List.of())
                .build();

        User updated = original.toBuilder().withUsername("updated").build();

        assertThat(updated).usingRecursiveComparison().ignoringFields("username").isEqualTo(original);
        assertThat(original.getUsername()).isEqualTo("original");
        assertThat(updated.getUsername()).isEqualTo("updated");
    }

    @Test
    void jacksonUsesTheBuilderForCompanyFieldsDatesAndRelations() throws Exception {
        UUID userId = UUID.randomUUID();
        UUID companyId = UUID.randomUUID();
        User user = new ObjectMapper().findAndRegisterModules().readValue("""
                {
                    "userId": "%s",
                    "username": "test-user",
                    "companyId": "%s",
                    "companyName": "Brewery",
                    "createDate": "2026-10-20T07:00:00Z",
                    "twoFactorAuthentication": false,
                    "roles": [],
                    "conversations": []
                }
                """.formatted(userId, companyId), User.class);

        assertThat(user.getUserId()).isEqualTo(userId);
        assertThat(user.getCompanyId()).isEqualTo(companyId);
        assertThat(user.getCompanyName()).isEqualTo("Brewery");
        assertThat(user.getCreateDate()).isEqualTo(Instant.parse("2026-10-20T07:00:00Z"));
        assertThat(user.isTwoFactorAuthentication()).isFalse();
        assertThat(user.getRoles()).isEmpty();
        assertThat(user.getConversations()).isEmpty();
        assertThat(user.toSessionUser().username()).isEqualTo("test-user");
    }

    @Test
    void reusingBuilderCreatesSeparateUsersAndKeepsOptionalValuesNullable() {
        User.Builder builder = User.builder().withUsername("first").withRoles(List.of());
        User first = builder.build();
        User second = builder.withUsername("second").build();

        assertThat(first).isNotSameAs(second);
        assertThat(first.getUsername()).isEqualTo("first");
        assertThat(second.getUsername()).isEqualTo("second");
        assertThat(first.isTwoFactorAuthentication()).isNull();
    }
}
