package com.brewery.web.dto.formdata.auth;

import com.brewery.web.dto.formdata.FormData;
import com.brewery.web.model.User;
import com.brewery.web.model.record.RecordStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/** Profile fields submitted by the auth service user form; ignores preserved authentication and audit metadata. */
@JsonIgnoreProperties(ignoreUnknown = true)
public record CompanyUserFormData(
        UUID userId,
        UUID companyId,
        String companyName,
        String username,
        String email,
        String firstName,
        String lastName,
        String fullName,
        String languagePreference,
        String timezone,
        String status,
        String accountVerificationStatus
) implements FormData {
    @Override
    public Map<String, String> getErrors() {
        Map<String, String> errors = new HashMap<>();

        if((companyId == null || companyName == null) || this.companyName.isBlank()) {
            errors.put("companyId", "Company name is required.");
            errors.put("companyName", "Company name is required.");
        }
        if(username == null || username.isBlank()) {
            errors.put("username", "Username is required.");
        }
        if(email == null || email.isBlank()) {
            errors.put("email", "Email is required.");
        } else if (!email.trim().matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) {
            errors.put("email", "Enter a valid email.");
        }
        if(status != null) {
            try {
                RecordStatus.fromString(status);
            } catch (IllegalArgumentException exception) {
                errors.put("status", "Status must be Active or Disabled.");
            }
        }
        if(accountVerificationStatus != null) {
            boolean valid = false;
            for(User.VerificationStatus value : User.VerificationStatus.values()) {
                if (value.name().equalsIgnoreCase(accountVerificationStatus)
                        || value.getStatus().equalsIgnoreCase(accountVerificationStatus)) {
                    valid = true;
                    break;
                }
            }
            if(!valid) {
                errors.put("accountVerificationStatus", "Account verification must be Pending, Verified or Blocked.");
            }
        }

        return errors;
    }

    @Override
    public boolean verify() {
        return getErrors().isEmpty();
    }
}
