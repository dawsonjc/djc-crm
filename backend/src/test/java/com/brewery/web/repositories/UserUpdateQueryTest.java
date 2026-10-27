package com.brewery.web.repositories;

import com.brewery.web.configuration.CassandraConversionConfig;
import com.brewery.web.model.User;
import com.brewery.web.model.record.RecordStatus;
import com.brewery.web.repositories.user.UsersByVerificationStatusRepository;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.cql.Statement;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.convert.MappingCassandraConverter;
import org.springframework.data.cassandra.core.mapping.CassandraMappingContext;
import org.springframework.data.cassandra.repository.support.CassandraRepositoryFactory;

import java.time.Instant;
import java.util.ArrayList;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserUpdateQueryTest {
    @Test
    @SuppressWarnings({"removal", "unchecked", "rawtypes"})
    void loginRepositoryMethodsExecutePartialUpdatesAndConvertVerificationKeys() {
        CassandraMappingContext context = new CassandraMappingContext();
        var conversions = new CassandraConversionConfig().cassandraCustomConversions();
        context.setSimpleTypeHolder(conversions.getSimpleTypeHolder());
        context.afterPropertiesSet();
        MappingCassandraConverter converter = new MappingCassandraConverter(context);
        converter.setCustomConversions(conversions);
        converter.afterPropertiesSet();
        CassandraOperations operations = mock(CassandraOperations.class);
        when(operations.getConverter()).thenReturn(converter);
        when(operations.execute(any(Statement.class))).thenReturn(mock(ResultSet.class));
        CassandraRepositoryFactory factory = new CassandraRepositoryFactory(operations);
        UserTableRepository canonical = factory.getRepository(UserTableRepository.class);
        UsersByVerificationStatusRepository projection = factory.getRepository(UsersByVerificationStatusRepository.class);
        UUID userId = UUID.randomUUID();
        UUID token = UUID.randomUUID();
        Instant date = Instant.parse("2026-10-26T12:00:00Z");

        canonical.recordLogin(userId, date, token);
        projection.recordLogin(userId, User.VerificationStatus.VERIFIED, RecordStatus.ACTIVE, date);

        ArgumentCaptor<Statement> statements = ArgumentCaptor.forClass(Statement.class);
        verify(operations, times(2)).execute(statements.capture());
        SimpleStatement canonicalStatement = (SimpleStatement) statements.getAllValues().get(0);
        SimpleStatement projectionStatement = (SimpleStatement) statements.getAllValues().get(1);
        assertThat(canonicalStatement.getQuery()).startsWith("UPDATE user_table SET last_login_date")
                .contains("auth_api_token", "WHERE userid").doesNotContain("password", "first_name");
        assertThat(projectionStatement.getQuery()).startsWith("UPDATE users_by_verification_status SET last_login_date")
                .contains("account_verification_status", "status", "userid");
        var values = new ArrayList<>(projectionStatement.getPositionalValues());
        values.addAll(projectionStatement.getNamedValues().values());
        assertThat(values).contains("VERIFIED", "Active", userId);
        verify(operations, never()).select(any(Statement.class), any(Class.class));
    }
}
