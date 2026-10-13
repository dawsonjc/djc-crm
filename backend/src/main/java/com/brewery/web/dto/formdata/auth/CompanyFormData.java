package com.brewery.web.dto.formdata.auth;

import com.brewery.web.dto.formdata.FormData;
import com.brewery.web.model.company.CrmAddress;
import com.brewery.web.model.record.RecordStatus;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/** Editable tenant details; IDs and timestamps are assigned by the backend. */
public record CompanyFormData(
        String name,
        String legalName,
        String billingEmail,
        String phone,
        String website,
        UUID primaryAdminUserId,
        CrmAddress billingAddress,
        String status
) implements FormData {
    @Override
    public Map<String, String> getErrors() {
        Map<String, String> errors = new HashMap<>();
        if (name == null || name.isBlank()) errors.put("name", "Company name is required.");
        if (billingEmail != null && !billingEmail.isBlank()
                && !billingEmail.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) {
            errors.put("billingEmail", "Enter a valid billing email.");
        }
        if (status != null) {
            try { RecordStatus.fromString(status); }
            catch (IllegalArgumentException exception) { errors.put("status", "Status must be Active or Disabled."); }
        }
        return errors;
    }

    @Override
    public boolean verify() { return getErrors().isEmpty(); }
}
