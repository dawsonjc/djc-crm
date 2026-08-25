package com.brewery.web.user;

public record SessionUser(
        String userId
) {
    public static final String SESSION_USER = "current_user";
}
