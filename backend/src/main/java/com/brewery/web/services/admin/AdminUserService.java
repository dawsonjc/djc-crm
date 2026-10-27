package com.brewery.web.services.admin;

import com.brewery.web.dto.UserDTO;
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

    public List<UserDTO> getUnverifiedUsers() {
        return this.userTableService.getUnverifiedUsers();
    }

    public UserDTO getUserById(UUID userId) {
        return UserDTO.from(this.userTableService.getUserById(userId));
    }

    public UserDTO changeVerificationStatus(UUID userId, User.VerificationStatus status) {
        return UserDTO.from(this.userTableService.changeVerificationStatus(userId, status));
    }

    public List<Role> getActiveRoles() {
        return this.roleService.getActiveRoles();
    }
}
