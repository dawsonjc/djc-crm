package com.brewery.web.dto.formdata;

import java.util.Map;

public record LoginFormData(String username, String password) implements FormData {
    @Override
    public Map<String, String> getErrors() {
        Map<String, String> errors = new java.util.HashMap<String, String>();
        
        if(username == null || username.trim().isEmpty()) {
            errors.put("username", "Username is required");
        }
        if(password == null || password.trim().isEmpty()) {
            errors.put("password", "Password is required");
        }
        
        return errors;
    }

    @Override
    public boolean verify() {
        Map<String, String> errors = this.getErrors();
        return errors.isEmpty();
    }
}
