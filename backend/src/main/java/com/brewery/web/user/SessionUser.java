package com.brewery.web.user;

import com.brewery.web.model.UserRole;

import java.util.List;

public record SessionUser(
        String userId,
        String username,
        List<UserRole> roles
) {
    public static final String SESSION_USER = "current_user";


}
