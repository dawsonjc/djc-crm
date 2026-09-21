package com.brewery.web.security;

import com.brewery.web.configuration.AuthServiceProperties;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/** Verifies service identity only; it does not authenticate a user. */
@Component
public final class ServiceCredentialVerifier {
    private final byte[] expectedKey;

    public ServiceCredentialVerifier(AuthServiceProperties properties) {
        String key = properties.getSecretKey();
        this.expectedKey = key == null || key.isBlank() ? null : key.getBytes(StandardCharsets.UTF_8);
    }

    public boolean isConfigured() {
        return expectedKey != null;
    }

    public boolean isValid(String suppliedKey) {
        return isConfigured() && suppliedKey != null &&
               !suppliedKey.isBlank() && MessageDigest.isEqual(expectedKey, suppliedKey.getBytes(StandardCharsets.UTF_8));
    }
}
