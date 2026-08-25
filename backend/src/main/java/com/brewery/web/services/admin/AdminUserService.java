package com.brewery.web.services.admin;

import com.brewery.web.model.Role;
import com.brewery.web.model.User;
import com.brewery.web.services.RoleService;
import com.brewery.web.services.UserRolesService;
import com.brewery.web.services.UserTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AdminUserService {
    private final RoleService roleService;

    private final UserRolesService userRolesService;

    private final UserTableService userTableService;

    public AdminUserService(UserTableService userTableService, UserRolesService userRolesService, RoleService roleService ) {
        this.userTableService = userTableService;
        this.userRolesService = userRolesService;
        this.roleService = roleService;
    }

    public List<User> getUnverifiedUsers() {
        return this.userTableService.getUnverifiedUsers();
    }

    public User getUserById(UUID userId) {
        return this.userTableService.getUserById(userId);
    }

    public void saveUser(User user) {
        this.userTableService.save(user);
    }

    public List<Role> getActiveRoles() {
        return this.roleService.getActiveRoles();
    }
}
