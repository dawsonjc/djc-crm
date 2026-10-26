package com.brewery.web.dto;

import com.brewery.web.model.User;
import com.brewery.web.model.UserRole;
import com.brewery.web.model.record.RecordStatus;
import com.brewery.web.model.user.UserIdsByEmail;
import com.brewery.web.model.user.UserIdsByUsername;
import com.brewery.web.model.user.UsersByVerificationStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

/** User profile response; lookup projections leave unavailable fields null. Credentials stay on the base entity. */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(builder = UserDTO.Builder.class)
public record UserDTO(
        UUID userId,
        Instant createDate,
        Instant updateDate,
        RecordStatus status,
        String username,
        String email,
        String firstName,
        String lastName,
        String fullName,
        String languagePreference,
        String timezone,
        Instant lastLoginDate,
        User.VerificationStatus accountVerificationStatus,
        Boolean twoFactorAuthentication,
        UUID companyId,
        String companyName,
        List<UserRole> roles,
        List<ConversationDTO> conversations
) {
    public static Builder builder() {
        return new Builder();
    }

    public static UserDTO from(User user) {
        return builder()
                .withUserId(user.getUserId())
                .withCreateDate(user.getCreateDate())
                .withUpdateDate(user.getUpdateDate())
                .withStatus(user.getStatus())
                .withUsername(user.getUsername())
                .withEmail(user.getEmail())
                .withFirstName(user.getFirstName())
                .withLastName(user.getLastName())
                .withFullName(user.getFullName())
                .withLanguagePreference(user.getLanguagePreference())
                .withTimezone(user.getTimezone())
                .withLastLoginDate(user.getLastLoginDate())
                .withAccountVerificationStatus(user.getAccountVerificationStatus())
                .withTwoFactorAuthentication(user.isTwoFactorAuthentication())
                .withCompanyId(user.getCompanyId())
                .withCompanyName(user.getCompanyName())
                .withRoles(user.getRoles())
                .withConversations(user.getConversations())
                .build();
    }

    public static UserDTO from(UserIdsByEmail user) {
        return builder()
                .withEmail(user.getEmail())
                .withStatus(user.getStatus())
                .withUserId(user.getUserId())
                .build();
    }

    public static UserDTO from(UserIdsByUsername user) {
        return builder()
                .withUsername(user.getUsername())
                .withStatus(user.getStatus())
                .withUserId(user.getUserId())
                .build();
    }

    public static UserDTO from(UsersByVerificationStatus user) {
        return builder()
                .withUserId(user.getUserId())
                .withCreateDate(user.getCreateDate())
                .withUpdateDate(user.getUpdateDate())
                .withStatus(user.getStatus())
                .withUsername(user.getUsername())
                .withEmail(user.getEmail())
                .withFirstName(user.getFirstName())
                .withLastName(user.getLastName())
                .withFullName(user.getFullName())
                .withLanguagePreference(user.getLanguagePreference())
                .withTimezone(user.getTimezone())
                .withLastLoginDate(user.getLastLoginDate())
                .withAccountVerificationStatus(user.getAccountVerificationStatus())
                .withTwoFactorAuthentication(user.isTwoFactorAuthentication())
                .withCompanyId(user.getCompanyId())
                .withCompanyName(user.getCompanyName())
                .build();
    }

    @JsonPOJOBuilder(withPrefix = "with")
    public static class Builder {
        private UUID userId;
        private Instant createDate;
        private Instant updateDate;
        private RecordStatus status;
        private String username;
        private String email;
        private String firstName;
        private String lastName;
        private String fullName;
        private String languagePreference;
        private String timezone;
        private Instant lastLoginDate;
        private User.VerificationStatus accountVerificationStatus;
        private Boolean twoFactorAuthentication;
        private UUID companyId;
        private String companyName;
        private List<UserRole> roles;
        private List<ConversationDTO> conversations;

        private Builder() {}

        public Builder withUserId(UUID userId) {
            this.userId = userId;
            return this;
        }

        public Builder withCreateDate(Instant createDate) {
            this.createDate = createDate;
            return this;
        }

        public Builder withUpdateDate(Instant updateDate) {
            this.updateDate = updateDate;
            return this;
        }

        public Builder withStatus(RecordStatus status) {
            this.status = status;
            return this;
        }

        public Builder withUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder withEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder withFullName(String fullName) {
            this.fullName = fullName;
            return this;
        }

        public Builder withLanguagePreference(String languagePreference) {
            this.languagePreference = languagePreference;
            return this;
        }

        public Builder withTimezone(String timezone) {
            this.timezone = timezone;
            return this;
        }

        public Builder withLastLoginDate(Instant lastLoginDate) {
            this.lastLoginDate = lastLoginDate;
            return this;
        }

        public Builder withAccountVerificationStatus(User.VerificationStatus accountVerificationStatus) {
            this.accountVerificationStatus = accountVerificationStatus;
            return this;
        }

        public Builder withTwoFactorAuthentication(Boolean twoFactorAuthentication) {
            this.twoFactorAuthentication = twoFactorAuthentication;
            return this;
        }

        public Builder withCompanyId(UUID companyId) {
            this.companyId = companyId;
            return this;
        }

        public Builder withCompanyName(String companyName) {
            this.companyName = companyName;
            return this;
        }

        public Builder withRoles(List<UserRole> roles) {
            this.roles = roles;
            return this;
        }

        public Builder withConversations(List<ConversationDTO> conversations) {
            this.conversations = conversations;
            return this;
        }

        public UserDTO build() {
            return new UserDTO(
                    this.userId,
                    this.createDate,
                    this.updateDate,
                    this.status,
                    this.username,
                    this.email,
                    this.firstName,
                    this.lastName,
                    this.fullName,
                    this.languagePreference,
                    this.timezone,
                    this.lastLoginDate,
                    this.accountVerificationStatus,
                    this.twoFactorAuthentication,
                    this.companyId,
                    this.companyName,
                    this.roles,
                    this.conversations
            );
        }
    }
}
