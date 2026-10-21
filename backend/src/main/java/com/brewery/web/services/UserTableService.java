package com.brewery.web.services;

import com.brewery.web.dto.formdata.RegisterFormData;
import com.brewery.web.model.record.RecordStatus;
import com.brewery.web.model.Role;
import com.brewery.web.model.User;
import com.brewery.web.model.UserRole;
import com.brewery.web.repositories.UserTableRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service(value = "UserTableService")
public class UserTableService {

    @Autowired
    private UserTableRepository repo;

    @Autowired
    private UserRolesService userRolesService;

    @Autowired
    private ConversationService conversationService;

    @Autowired
    private RoleService roleService;

    public boolean userExistsByEmail(String email) {
        if(email == null) {
            return false;
        }
        UUID userId = this.getUserIdByEmail(email);
        return userId != null;
    }

    public boolean userExistsByUsername(String username) {
        if(username == null) {
            return false;
        }
        UUID userId = this.getUserIdByUsername(username);
        return userId != null;
    }

    public User getUserById(UUID id) {
        User user = this.repo.findById(id).orElse(null);
        if(user != null) {
            user = this.populateUserRelations(user);
        }
        return user;
    }

    public UUID getUserIdByEmail(String email) {
        return this.repo.findUserIdByEmail(email);
    }

    public UUID getUserIdByUsername(String username) {
        return this.repo.findUserIdByUsername(username);
    }

    public User getUserByIdAndPassword(UUID id, String password) {
        User user = this.repo.findById(id).orElse(null);
        if(user == null) {
            return null;
        }

        if(!BCrypt.checkpw(password, user.getPassword())) {
            return null;
        }

        user = this.populateUserRelations(user);

        return user;
    }

    private User populateUserRelations(User user) {
        return user.toBuilder()
                .withRoles(this.getUsersRolesByUser(user))
                .build();
    }



    public List<UserRole> getUsersRolesByUser(User user) {
        return this.userRolesService.getUsersRolesByUserId(user.getUserId());
    }

    public List<User> getUnverifiedUsers() {
        return this.repo.getUnverifiedUsers();
    }

    public User register(RegisterFormData registerFormData) {
        Instant now = Instant.now();

        String hashed = BCrypt.hashpw(registerFormData.password(), BCrypt.gensalt(10));

        UUID userId = UUID.randomUUID();

        Role defaultRole = this.roleService.getRoleByName(Role.Name.USER.toString());

        UserRole userRole = new UserRole();
        userRole.setRoleId(defaultRole.getRoleId());
        userRole.setRoleName(defaultRole.getRoleName());
        userRole.setUserId(userId);
        userRole.setCreateDate(now);
        userRole.setUpdateDate(now);
        userRole.setStatus(RecordStatus.ACTIVE);

        User user = User.builder()
                .withUserId(userId)
                .withCreateDate(now)
                .withUpdateDate(now)
                .withStatus(RecordStatus.ACTIVE)
                .withUsername(registerFormData.username())
                .withEmail(registerFormData.email())
                .withPassword(hashed)
                .withFirstName(registerFormData.firstName())
                .withLastName(registerFormData.lastName())
                .withFullName(registerFormData.firstName() + " " + registerFormData.lastName())
                .withLanguagePreference("en")
                .withTimezone("en/us")
                .withAccountVerificationStatus(User.VerificationStatus.VERIFIED)
                .withRoles(List.of(userRole))
                .build();
        User savedUser = this.repo.save(user);
        this.userRolesService.save(userRole);

        return savedUser;
    }

    public void save(User user) {
        this.repo.save(user);
    }
}
