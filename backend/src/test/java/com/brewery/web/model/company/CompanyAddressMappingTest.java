package com.brewery.web.model.company;

import com.brewery.web.configuration.CassandraConversionConfig;
import com.brewery.web.model.record.RecordStatus;
import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.data.UdtValue;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.core.type.UserDefinedType;
import com.datastax.oss.driver.internal.core.type.DefaultUserDefinedType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.cassandra.core.convert.MappingCassandraConverter;
import org.springframework.data.cassandra.core.mapping.CassandraMappingContext;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class CompanyAddressMappingTest {
    private MappingCassandraConverter converter;

    @BeforeEach
    void setUp() {
        List<CqlIdentifier> fields = List.of("line1", "line2", "city", "region", "postal_code", "country_code")
                .stream().map(CqlIdentifier::fromCql).toList();
        UserDefinedType addressType = new DefaultUserDefinedType(
                CqlIdentifier.fromCql("mykeyspacename"), CqlIdentifier.fromCql("crm_address"),
                true, fields, Collections.nCopies(fields.size(), DataTypes.TEXT));
        CassandraMappingContext context = new CassandraMappingContext();
        context.setUserTypeResolver(name -> name.equals(addressType.getName()) ? addressType : null);
        context.setCustomConversions(new CassandraConversionConfig().cassandraCustomConversions());
        context.afterPropertiesSet();
        converter = new MappingCassandraConverter(context);
        converter.afterPropertiesSet();
    }

    @Test
    void writesTenantBillingAddressAsFrozenUserDefinedValue() {
        CrmAddress billing = CrmAddress.builder()
                .withLine1("123 Main St").withCity("Phoenix").withRegion("AZ")
                .withPostalCode("85001").withCountryCode("US").build();
        UUID companyId = UUID.randomUUID();
        Company company = Company.builder()
                .withCompanyId(companyId)
                .withBillingAddress(billing).build();

        Map<CqlIdentifier, Object> columns = new LinkedHashMap<>();
        converter.write(company, columns);

        UdtValue billingValue = (UdtValue) columns.get(CqlIdentifier.fromCql("billing_address"));
        assertThat(columns.get(CqlIdentifier.fromCql("company_id"))).isEqualTo(companyId);
        assertThat(columns).doesNotContainKeys(CqlIdentifier.fromCql("workspace_id"),
                CqlIdentifier.fromCql("organization_id"), CqlIdentifier.fromCql("shipping_address"));
        assertThat(billingValue.getType().isFrozen()).isTrue();
        assertThat(billingValue.getString("line1")).isEqualTo("123 Main St");
        assertThat(billingValue.getString("postal_code")).isEqualTo("85001");
        assertThat(billingValue.isNull("line2")).isTrue();
    }

    @Test
    void writesMembershipUnderCompanyAndUserKeysWithExistingRecordStatus() {
        UUID companyId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        CompanyUser membership = new CompanyUser(companyId, userId, "Admin",
                RecordStatus.DISABLED, null, null);
        Map<CqlIdentifier, Object> columns = new LinkedHashMap<>();
        converter.write(membership, columns);
        assertThat(columns.get(CqlIdentifier.fromCql("company_id"))).isEqualTo(companyId);
        assertThat(columns.get(CqlIdentifier.fromCql("user_id"))).isEqualTo(userId);
        assertThat(columns.get(CqlIdentifier.fromCql("status"))).isEqualTo("Disabled");
        assertThat(columns).doesNotContainKeys(CqlIdentifier.fromCql("password"),
                CqlIdentifier.fromCql("email"), CqlIdentifier.fromCql("account_verification_status"));
    }

    @Test
    void allowsMissingAddresses() {
        Map<CqlIdentifier, Object> columns = new LinkedHashMap<>();
        converter.write(Company.builder().withName("Brewery").build(), columns);

        assertThat(columns.get(CqlIdentifier.fromCql("billing_address"))).isNull();
        assertThat(columns.get(CqlIdentifier.fromCql("shipping_address"))).isNull();
    }

}
