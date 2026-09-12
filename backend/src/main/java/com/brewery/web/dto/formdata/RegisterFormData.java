package com.brewery.web.dto.formdata;

import java.util.Map;

public record RegisterFormData(String firstName, String lastName, String username, String email, String password) implements FormData {

    @Override
    public Map<String, String> getErrors() {
        Map<String, String> errors = new java.util.HashMap<>();

        if(firstName == null || firstName.trim().isEmpty()) {
            errors.put("firstName", "First name is required");
        }

        if(lastName == null || lastName.trim().isEmpty()) {
            errors.put("lastName", "Last name is required");
        }

        if(username == null || username.trim().isEmpty()) {
            errors.put("username", "Username is required");
        }

        if(email == null || email.trim().isEmpty()) {
            errors.put("email", "Email is required");
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
