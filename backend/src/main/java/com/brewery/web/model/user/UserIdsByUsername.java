package com.brewery.web.model.user;

import com.brewery.web.model.record.RecordStatus;
import tools.jackson.databind.annotation.JsonDeserialize;
import tools.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.UUID;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("user_ids_by_username")
@JsonDeserialize(builder = UserIdsByUsername.Builder.class)
public class UserIdsByUsername {
    @PrimaryKeyColumn(name = "username", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private String username;

    @PrimaryKeyColumn(name = "status", ordinal = 1, type = PrimaryKeyType.PARTITIONED)
    private RecordStatus status;

    @PrimaryKeyColumn(name = "userid", ordinal = 2, type = PrimaryKeyType.CLUSTERED)
    private UUID userId;

    private UserIdsByUsername() {}

    public static Builder builder() {
        return new Builder();
    }

    public String getUsername() {
        return this.username;
    }

    public RecordStatus getStatus() {
        return this.status;
    }

    public UUID getUserId() {
        return this.userId;
    }

    @JsonPOJOBuilder(withPrefix = "with")
    public static class Builder {
        private String username;
        private RecordStatus status;
        private UUID userId;

        private Builder() {}

        public Builder withUsername(String username) {
            this.username = username;
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

        public UserIdsByUsername build() {
            UserIdsByUsername value = new UserIdsByUsername();
            value.username = this.username;
            value.status = this.status;
            value.userId = this.userId;
            return value;
        }
    }
}
