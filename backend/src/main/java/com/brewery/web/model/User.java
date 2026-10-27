package com.brewery.web.model;

import com.brewery.web.dto.ConversationDTO;
import com.brewery.web.model.record.RecordStatus;
import com.brewery.web.user.SessionUser;
import tools.jackson.databind.annotation.JsonDeserialize;
import tools.jackson.databind.annotation.JsonPOJOBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Table(value = "user_table")
@JsonDeserialize(builder = User.Builder.class)
public class User {

    @Id
    @Column(value = "userid")
    @PrimaryKeyColumn(name = "userid", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private UUID userId;

    @Column(value = "create_date")
    @CassandraType(type = CassandraType.Name.TIMESTAMP)
    private Instant createDate;

    @Column(value = "update_date")
    @CassandraType(type = CassandraType.Name.TIMESTAMP)
    private Instant updateDate;

    @Column(value = "status")
    @CassandraType(type = CassandraType.Name.TEXT)
    private RecordStatus status;

    @Column(value = "username")
    @CassandraType(type = CassandraType.Name.TEXT)
    private String username;

    @Column(value = "email")
    @CassandraType(type = CassandraType.Name.TEXT)
    private String email;

    @Column(value = "password")
    @CassandraType(type = CassandraType.Name.TEXT)
    private String password;

    @Column(value = "first_name")
    @CassandraType(type = CassandraType.Name.TEXT)
    private String firstName;

    @Column(value = "last_name")
    @CassandraType(type = CassandraType.Name.TEXT)
    private String lastName;

    @Column(value = "full_name")
    @CassandraType(type = CassandraType.Name.TEXT)
    private String fullName;

    @Column(value = "language_preference")
    @CassandraType(type = CassandraType.Name.TEXT)
    private String languagePreference;

    @Column(value = "timezone")
    @CassandraType(type = CassandraType.Name.TEXT)
    private String timezone;

    @Column(value = "last_login_date")
    @CassandraType(type = CassandraType.Name.TIMESTAMP)
    private Instant lastLoginDate;

    @Column(value = "account_verification_status")
    @CassandraType(type = CassandraType.Name.TEXT)
    private VerificationStatus accountVerificationStatus;

    @Column(value = "two_factor_authentication")
    @CassandraType(type = CassandraType.Name.BOOLEAN)
    private Boolean twoFactorAuthentication;

    @Column(value = "company_id")
    @CassandraType(type = CassandraType.Name.UUID)
    private UUID companyId;

    @Column(value = "company_name")
    @CassandraType(type = CassandraType.Name.TEXT)
    private String companyName;
    
    @Column(value = "auth_api_token")
    @CassandraType(type = CassandraType.Name.UUID)
    private UUID authApiToken;

    @Transient
    private List<UserRole> roles;

    @Transient
    private List<ConversationDTO> conversations;

    private User() {}

    public static Builder builder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return builder()
                .withUserId(this.userId)
                .withCreateDate(this.createDate)
                .withUpdateDate(this.updateDate)
                .withStatus(this.status)
                .withUsername(this.username)
                .withEmail(this.email)
                .withPassword(this.password)
                .withFirstName(this.firstName)
                .withLastName(this.lastName)
                .withFullName(this.fullName)
                .withLanguagePreference(this.languagePreference)
                .withTimezone(this.timezone)
                .withLastLoginDate(this.lastLoginDate)
                .withAccountVerificationStatus(this.accountVerificationStatus)
                .withTwoFactorAuthentication(this.twoFactorAuthentication)
                .withCompanyId(this.companyId)
                .withCompanyName(this.companyName)
                .withRoles(this.roles)
                .withConversations(this.conversations)
                .withAuthApiToken(this.authApiToken);
    }

    public User(UUID userId, Instant createDate, Instant updateDate, RecordStatus status,
                String username, String email, String password, String firstName,
                String lastName, String fullName,
                String languagePreference, String timezone, Instant lastLoginDate,
                VerificationStatus accountVerificationStatus, Boolean twoFactorAuthentication,
                UUID companyId, String companyName, UUID authApiToken
    ) {
        this.userId = userId;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.status = status;
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = fullName;
        this.languagePreference = languagePreference;
        this.timezone = timezone;
        this.lastLoginDate = lastLoginDate;
        this.accountVerificationStatus = accountVerificationStatus;
        this.twoFactorAuthentication = twoFactorAuthentication;
        this.companyId = companyId;
        this.companyName = companyName;
        this.authApiToken = authApiToken;
    }

    public enum VerificationStatus {
        PENDING("Pending"),
        VERIFIED("Verified"),
        BLOCKED("Blocked");

        private final String status;
        VerificationStatus(String status) {
            this.status = status;
        }

        public String getStatus() {
            return this.status;
        }

        public static VerificationStatus getStatus(String status) {
            for (VerificationStatus s : VerificationStatus.values()) {
                if (s.getStatus().equals(status)) {
                    return s;
                }
            }
            return null;
        }

        public String toString() {
            return this.status;
        }
    }

    public UUID getUserId() {
        return userId;
    }

    public Instant getCreateDate() {
        return createDate;
    }

    public Instant getUpdateDate() {
        return updateDate;
    }

    public RecordStatus getStatus() {
        return status;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        return fullName;
    }

    public String getLanguagePreference() {
        return languagePreference;
    }

    public String getTimezone() {
        return timezone;
    }

    public Instant getLastLoginDate() {
        return lastLoginDate;
    }

    public VerificationStatus getAccountVerificationStatus() {
        return accountVerificationStatus;
    }

    public Boolean isTwoFactorAuthentication() {
        return twoFactorAuthentication;
    }

    public UUID getCompanyId() {
        return companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public List<UserRole> getRoles() {
        return this.roles;
    }

    public List<ConversationDTO> getConversations() {
        return this.conversations;
    }

    public UUID getAuthApiToken() {
        return this.authApiToken;
    }

    public SessionUser toSessionUser() {
        return new SessionUser(this.userId, this.username, this.roles);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                ", status='" + status + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", fullName='" + fullName + '\'' +
                ", languagePreference='" + languagePreference + '\'' +
                ", timezone='" + timezone + '\'' +
                ", lastLoginDate=" + lastLoginDate +
                ", accountVerificationStatus='" + accountVerificationStatus + '\'' +
                ", twoFactorAuthentication=" + twoFactorAuthentication +
                ", companyId=" + companyId +
                ", companyName='" + companyName + '\'' +
                '}';
    }

    @JsonPOJOBuilder(withPrefix = "with")
    public static class Builder {
        private UUID userId;
        private Instant createDate;
        private Instant updateDate;
        private RecordStatus status;
        private String username;
        private String email;
        private String password;
        private String firstName;
        private String lastName;
        private String fullName;
        private String languagePreference;
        private String timezone;
        private Instant lastLoginDate;
        private VerificationStatus accountVerificationStatus;
        private Boolean twoFactorAuthentication;
        private UUID companyId;
        private String companyName;
        private UUID authApiToken;
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

        public Builder withPassword(String password) {
            this.password = password;
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

        public Builder withAccountVerificationStatus(VerificationStatus accountVerificationStatus) {
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

        public Builder withAuthApiToken(UUID authApiToken) {
            this.authApiToken = authApiToken;
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

        public User build() {
            User user = new User();
            user.userId = this.userId;
            user.createDate = this.createDate;
            user.updateDate = this.updateDate;
            user.status = this.status;
            user.username = this.username;
            user.email = this.email;
            user.password = this.password;
            user.firstName = this.firstName;
            user.lastName = this.lastName;
            user.fullName = this.fullName;
            user.languagePreference = this.languagePreference;
            user.timezone = this.timezone;
            user.lastLoginDate = this.lastLoginDate;
            user.accountVerificationStatus = this.accountVerificationStatus;
            user.twoFactorAuthentication = this.twoFactorAuthentication;
            user.companyId = this.companyId;
            user.companyName = this.companyName;
            user.authApiToken = this.authApiToken;
            user.roles = this.roles;
            user.conversations = this.conversations;
            return user;
        }
    }
}
