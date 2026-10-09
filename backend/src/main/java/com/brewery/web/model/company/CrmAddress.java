package com.brewery.web.model.company;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

@UserDefinedType("crm_address")
@JsonDeserialize(builder = CrmAddress.Builder.class)
public class CrmAddress {
    @Column("line1")
    private String line1;

    @Column("line2")
    private String line2;

    @Column("city")
    private String city;

    @Column("region")
    private String region;

    @Column("postal_code")
    private String postalCode;

    @Column("country_code")
    private String countryCode;

    public static Builder builder() {
        return new Builder();
    }

    public String getLine1() {
        return this.line1;
    }

    public String getLine2() {
        return this.line2;
    }

    public String getCity() {
        return this.city;
    }

    public String getRegion() {
        return this.region;
    }

    public String getPostalCode() {
        return this.postalCode;
    }

    public String getCountryCode() {
        return this.countryCode;
    }

    @JsonPOJOBuilder(withPrefix = "with")
    public static class Builder {
        private String line1;
        private String line2;
        private String city;
        private String region;
        private String postalCode;
        private String countryCode;

        private Builder() {}

        public Builder withLine1(String line1) {
            this.line1 = line1;
            return this;
        }

        public Builder withLine2(String line2) {
            this.line2 = line2;
            return this;
        }

        public Builder withCity(String city) {
            this.city = city;
            return this;
        }

        public Builder withRegion(String region) {
            this.region = region;
            return this;
        }

        public Builder withPostalCode(String postalCode) {
            this.postalCode = postalCode;
            return this;
        }

        public Builder withCountryCode(String countryCode) {
            this.countryCode = countryCode;
            return this;
        }

        public CrmAddress build() {
            CrmAddress value = new CrmAddress();
            value.line1 = this.line1;
            value.line2 = this.line2;
            value.city = this.city;
            value.region = this.region;
            value.postalCode = this.postalCode;
            value.countryCode = this.countryCode;
            return value;
        }
    }
}
