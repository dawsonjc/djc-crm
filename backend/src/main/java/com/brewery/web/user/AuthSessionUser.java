package com.brewery.web.user;

public record AuthSessionUser(
        String userId,
        String username
) {
    public static final String AUTH_SESSION_USER = "auth_session_user";
}
