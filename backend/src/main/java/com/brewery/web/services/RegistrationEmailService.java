package com.brewery.web.services;

import com.brewery.web.mail.RegistrationEmail;
import com.brewery.web.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

@Service
public class RegistrationEmailService {
    private static final Duration VALIDITY = Duration.ofHours(24);
    private static final Base64.Encoder ENCODER = Base64.getUrlEncoder().withoutPadding();
    private static final Base64.Decoder DECODER = Base64.getUrlDecoder();

    private final byte[] secret;
    private final String publicUrl;
    private final RegistrationEmail mail;

    public RegistrationEmailService(
            @Value("${CRM_REGISTRATION_ENCODE_EMAIL_SECRET:}") String secret,
            @Value("${CRM_REGISTRATION_PUBLIC_URL:${CRM_BACKEND_URL:http://localhost:8080}}") String publicUrl,
            RegistrationEmail mail) {
        this.secret = secret.getBytes(StandardCharsets.UTF_8);
        this.publicUrl = publicUrl;
        this.mail = mail;
    }

    public void sendVerificationEmail(User user) {
        String link = generateVerificationLink(user.getUserId(), user.getEmail());
        mail.send(user.getEmail(), link);
    }

    String generateVerificationLink(UUID userId, String email) {
        URI base = URI.create(publicUrl);
        if (base.getHost() == null || !("https".equalsIgnoreCase(base.getScheme())
                || "http".equalsIgnoreCase(base.getScheme())) || base.getRawUserInfo() != null
                || base.getRawQuery() != null || base.getRawFragment() != null) {
            throw new IllegalArgumentException("Registration public URL must be an HTTP(S) base URL");
        }

        String payload = userId + ":" + Instant.now().plus(VALIDITY).toEpochMilli() + ":" + email;
        String encoded = ENCODER.encodeToString(payload.getBytes(StandardCharsets.UTF_8));
        String token = encoded + "." + ENCODER.encodeToString(sign(encoded));
        return UriComponentsBuilder.fromUri(base).pathSegment("account", "register")
                .queryParam("verification", token).build(true).toUriString();
    }

    public Optional<Invitation> verifyToken(String token) {
        if (secret.length < 32 || token == null || token.length() > 2048) {
            return Optional.empty();
        }
        String[] pieces = token.split("\\.", -1);
        if (pieces.length != 2) {
            return Optional.empty();
        }
        try {
            if (!MessageDigest.isEqual(sign(pieces[0]), DECODER.decode(pieces[1]))) {
                return Optional.empty();
            }

            String[] fields = new String(DECODER.decode(pieces[0]), StandardCharsets.UTF_8).split(":", 3);
            if (fields.length != 3 || fields[2].isBlank()
                    || Long.parseLong(fields[1]) <= System.currentTimeMillis()) {
                return Optional.empty();
            }
            return Optional.of(new Invitation(UUID.fromString(fields[0]), fields[2]));
        } catch (IllegalArgumentException invalidToken) {
            return Optional.empty();
        }
    }

    private byte[] sign(String payload) {
        if (secret.length < 32) {
            throw new IllegalStateException("Registration signing secret must be at least 32 bytes");
        }
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(secret, "HmacSHA256"));
            return mac.doFinal(payload.getBytes(StandardCharsets.US_ASCII));
        } catch (GeneralSecurityException failure) {
            throw new IllegalStateException("Unable to sign registration token", failure);
        }
    }

    public record Invitation(UUID userId, String email) {}
}
