package com.brewery.web.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public final class AuthServiceProperties {
    private static final String SECRET_KEY = System.getenv("AUTH_SERVICE_SECRET_KEY");

    public static String getSecretKey() {
        return SECRET_KEY;
    }
}
