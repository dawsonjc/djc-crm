package com.brewery.web.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class RegistrationEmailService {

    // Define a strong, random 256-bit key in your application.properties
    @Value("${CRM_REGISTRATION_ENCODE_EMAIL_SECRET}")
    private String secretKey;

    @Value("${CRM_BACKEND_URL}")
    private String url;

    private static final long EXPIRATION_MS = 24 * 60 * 60 * 1000; // 24 hours

    public String generateVerificationLink(String email) {
        long expiryTime = System.currentTimeMillis() + EXPIRATION_MS;
        String payload = email + ":" + expiryTime;
        String signature = sign(payload);

        String token = Base64.getUrlEncoder().withoutPadding().encodeToString((payload + ":" + signature).getBytes(StandardCharsets.UTF_8));

        return this.url + token;
    }

    public boolean verifyToken(String token, String expectedEmail) {
        try {
            String decoded = new String(Base64.getUrlDecoder().decode(token), StandardCharsets.UTF_8);
            String[] parts = decoded.split(":");
            if (parts.length != 3) return false;

            String email = parts[0];
            long expiryTime = Long.parseLong(parts[1]);
            String providedSignature = parts[2];

            if (!email.equalsIgnoreCase(expectedEmail)) return false;

            if (System.currentTimeMillis() > expiryTime) return false;

            String rebuildPayload = email + ":" + expiryTime;
            return sign(rebuildPayload).equals(providedSignature);

        } catch (Exception e) {
            return false;
        }
    }

    private String sign(String data) {
        try {
            Mac hmacSha256 = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(
                    secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            hmacSha256.init(secretKeySpec);
            byte[] hash = hmacSha256.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return Base64.getUrlEncoder().withoutPadding().encodeToString(hash);
        } catch (Exception e) {
            throw new RuntimeException("Failed to sign registration token", e);
        }
    }
}
