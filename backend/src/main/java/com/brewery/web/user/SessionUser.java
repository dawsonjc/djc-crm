package com.brewery.web.user;

import com.brewery.web.model.UserRole;

import java.util.List;
import java.util.UUID;

public record SessionUser(
        UUID userId,
        String username,
        List<UserRole> roles
) {
    public static final String SESSION_USER = "current_user";


}
