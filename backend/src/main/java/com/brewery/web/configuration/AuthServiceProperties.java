package com.brewery.web.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/** Server-side configuration. Never serialize or log the secret. */
@Component
public final class AuthServiceProperties {
    private final String secretKey;

    public AuthServiceProperties(@Value("${AUTH_SERVICE_SECRET_KEY:}") String secretKey) {
        this.secretKey = secretKey;
    }

    public String getSecretKey() {
        return this.secretKey;
    }
}
