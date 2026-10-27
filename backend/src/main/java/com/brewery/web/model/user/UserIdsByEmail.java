package com.brewery.web.model.user;

import com.brewery.web.model.record.RecordStatus;
import tools.jackson.databind.annotation.JsonDeserialize;
import tools.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.UUID;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("user_ids_by_email")
@JsonDeserialize(builder = UserIdsByEmail.Builder.class)
public class UserIdsByEmail {
    @PrimaryKeyColumn(name = "email", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private String email;

    @PrimaryKeyColumn(name = "status", ordinal = 1, type = PrimaryKeyType.PARTITIONED)
    private RecordStatus status;

    @PrimaryKeyColumn(name = "userid", ordinal = 2, type = PrimaryKeyType.CLUSTERED)
    private UUID userId;

    private UserIdsByEmail() {}

    public static Builder builder() {
        return new Builder();
    }

    public String getEmail() {
        return this.email;
    }

    public RecordStatus getStatus() {
        return this.status;
    }

    public UUID getUserId() {
        return this.userId;
    }

    @JsonPOJOBuilder(withPrefix = "with")
    public static class Builder {
        private String email;
        private RecordStatus status;
        private UUID userId;

        private Builder() {}

        public Builder withEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder withStatus(RecordStatus status) {
            this.status = status;
            return this;
        }

        public Builder withUserId(UUID userId) {
            this.userId = userId;
            return this;
        }

        public UserIdsByEmail build() {
            UserIdsByEmail value = new UserIdsByEmail();
            value.email = this.email;
            value.status = this.status;
            value.userId = this.userId;
            return value;
        }
    }
}
