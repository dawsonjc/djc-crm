package com.brewery.web.model.user;

import com.brewery.web.model.User;
import com.brewery.web.model.record.RecordStatus;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.time.Instant;
import java.util.UUID;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("users_by_verification_status")
@JsonDeserialize(builder = UsersByVerificationStatus.Builder.class)
public class UsersByVerificationStatus {
    @PrimaryKeyColumn(name = "userid", ordinal = 2, type = PrimaryKeyType.CLUSTERED)
    private UUID userId;

    @Column("create_date")
    private Instant createDate;

    @Column("update_date")
    private Instant updateDate;

    @PrimaryKeyColumn(name = "status", ordinal = 1, type = PrimaryKeyType.PARTITIONED)
    private RecordStatus status;

    @Column("username")
    private String username;

    @Column("email")
    private String email;

    @Column("first_name")
    private String firstName;

    @Column("last_name")
    private String lastName;

    @Column("full_name")
    private String fullName;

    @Column("language_preference")
    private String languagePreference;

    @Column("timezone")
    private String timezone;

    @Column("last_login_date")
    private Instant lastLoginDate;

    @PrimaryKeyColumn(name = "account_verification_status", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private User.VerificationStatus accountVerificationStatus;

    @Column("two_factor_authentication")
    private Boolean twoFactorAuthentication;

    @Column("company_id")
    private UUID companyId;

    @Column("company_name")
    private String companyName;

    private UsersByVerificationStatus() {}

    public static Builder builder() {
        return new Builder();
    }

    public UUID getUserId() {
        return this.userId;
    }

    public Instant getCreateDate() {
        return this.createDate;
    }

    public Instant getUpdateDate() {
        return this.updateDate;
    }

    public RecordStatus getStatus() {
        return this.status;
    }

    public String getUsername() {
        return this.username;
    }

    public String getEmail() {
        return this.email;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getFullName() {
        return this.fullName;
    }

    public String getLanguagePreference() {
        return this.languagePreference;
    }

    public String getTimezone() {
        return this.timezone;
    }

    public Instant getLastLoginDate() {
        return this.lastLoginDate;
    }

    public User.VerificationStatus getAccountVerificationStatus() {
        return this.accountVerificationStatus;
    }

    public Boolean isTwoFactorAuthentication() {
        return this.twoFactorAuthentication;
    }

    public UUID getCompanyId() {
        return this.companyId;
    }

    public String getCompanyName() {
        return this.companyName;
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

        public UsersByVerificationStatus build() {
            UsersByVerificationStatus value = new UsersByVerificationStatus();
            value.userId = this.userId;
            value.createDate = this.createDate;
            value.updateDate = this.updateDate;
            value.status = this.status;
            value.username = this.username;
            value.email = this.email;
            value.firstName = this.firstName;
            value.lastName = this.lastName;
            value.fullName = this.fullName;
            value.languagePreference = this.languagePreference;
            value.timezone = this.timezone;
            value.lastLoginDate = this.lastLoginDate;
            value.accountVerificationStatus = this.accountVerificationStatus;
            value.twoFactorAuthentication = this.twoFactorAuthentication;
            value.companyId = this.companyId;
            value.companyName = this.companyName;
            return value;
        }
    }
}
