package com.brewery.web.user;

import java.util.List;

public record SessionUser(
        String userId,
        String username,
        List<String> roles
) {
    public static final String SESSION_USER = "current_user";


}
