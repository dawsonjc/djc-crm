package com.brewery.web.services;

import com.brewery.web.dto.UserDTO;
import com.brewery.web.dto.formdata.RegisterFormData;
import com.brewery.web.dto.formdata.auth.CompanyUserFormData;
import com.brewery.web.model.record.RecordStatus;
import com.brewery.web.model.Role;
import com.brewery.web.model.User;
import com.brewery.web.model.UserRole;
import com.brewery.web.model.user.UserIdsByEmail;
import com.brewery.web.model.user.UserIdsByUsername;
import com.brewery.web.model.user.UsersByVerificationStatus;
import com.brewery.web.repositories.UserTableRepository;
import com.brewery.web.repositories.user.UserIdsByEmailRepository;
import com.brewery.web.repositories.user.UserIdsByUsernameRepository;
import com.brewery.web.repositories.user.UsersByVerificationStatusRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service(value = "UserTableService")
public class UserTableService {

    @Autowired
    private UserTableRepository repo;

    @Autowired
    private UserIdsByEmailRepository userIdsByEmailRepository;

    @Autowired
    private UserIdsByUsernameRepository userIdsByUsernameRepository;

    @Autowired
    private UsersByVerificationStatusRepository usersByVerificationStatusRepository;

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
        List<UserIdsByEmail> userIds = this.userIdsByEmailRepository.findByEmailAndStatus(email, RecordStatus.ACTIVE);

        UUID userId = null;
        if(userIds != null && !userIds.isEmpty()) {
            userId = userIds.getFirst().getUserId();
        }

        return userId;
    }

    public UUID getUserIdByUsername(String username) {
        List<UserIdsByUsername> userIds = this.userIdsByUsernameRepository.findByUsernameAndStatus(username, RecordStatus.ACTIVE);

        UUID userId = null;
        if(userIds != null && !userIds.isEmpty()) {
            userId = userIds.getFirst().getUserId();
        }

        return userId;
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

    public List<UserDTO> getUnverifiedUsers() {
        List<UsersByVerificationStatus> users = this.usersByVerificationStatusRepository.findByAccountVerificationStatusAndStatus(User.VerificationStatus.PENDING, RecordStatus.ACTIVE);

        List<UserDTO> unverified = users.stream()
                .map(UserDTO::from)
                .toList();

        return unverified;
    }

    public User registerUser(CompanyUserFormData companyUserFormData) {
        Instant now = Instant.now();

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
                .withStatus(RecordStatus.fromString(companyUserFormData.status()))
                .withUsername(companyUserFormData.username())
                .withEmail(companyUserFormData.email())
                .withFirstName(companyUserFormData.firstName())
                .withLastName(companyUserFormData.lastName())
                .withFullName(companyUserFormData.firstName() + " " + companyUserFormData.lastName())
                .withLanguagePreference(companyUserFormData.languagePreference())
                .withTimezone(companyUserFormData.timezone())
                .withAccountVerificationStatus(User.VerificationStatus.PENDING)
                .withRoles(List.of(userRole))
                .build();

        User savedUser = this.save(user);
        this.userRolesService.save(userRole);

        return savedUser;
    }

    public User authenticateRegisteredUser(RegisterFormData registerFormData) {
        UUID userId = this.getUserIdByEmail(registerFormData.email());
        User user = this.getUserById(userId);

        Instant now = Instant.now();

        String hashed = BCrypt.hashpw(registerFormData.password(), BCrypt.gensalt(10));

        user = user.toBuilder()
                .withUpdateDate(now)
                .withStatus(RecordStatus.ACTIVE)
                .withUsername(registerFormData.username())
                .withPassword(hashed)
                .withFirstName(registerFormData.firstName())
                .withLastName(registerFormData.lastName())
                .withFullName(registerFormData.firstName() + " " + registerFormData.lastName())
                .withAccountVerificationStatus(User.VerificationStatus.VERIFIED)
                .build();

        User savedUser = this.save(user);

        return savedUser;
    }

    public User save(User user) {
        User savedUser = this.repo.save(user);
        this.userIdsByEmailRepository.save(UserIdsByEmail.builder()
                .withEmail(user.getEmail())
                .withStatus(user.getStatus())
                .withUserId(user.getUserId())
                .build()
        );
        this.userIdsByUsernameRepository.save(UserIdsByUsername.builder()
                .withUsername(user.getUsername())
                .withStatus(user.getStatus())
                .withUserId(user.getUserId())
                .build()
        );
        this.usersByVerificationStatusRepository.save(UsersByVerificationStatus.builder()
                .withUserId(user.getUserId())
                .withCreateDate(user.getCreateDate())
                .withUpdateDate(user.getUpdateDate())
                .withStatus(user.getStatus())
                .withUsername(user.getUsername())
                .withEmail(user.getEmail())
                .withFirstName(user.getFirstName())
                .withLastName(user.getLastName())
                .withFullName(user.getFullName())
                .withLanguagePreference(user.getLanguagePreference())
                .withTimezone(user.getTimezone())
                .withLastLoginDate(user.getLastLoginDate())
                .withAccountVerificationStatus(user.getAccountVerificationStatus())
                .withTwoFactorAuthentication(user.isTwoFactorAuthentication())
                .withCompanyId(user.getCompanyId())
                .withCompanyName(user.getCompanyName())
                .build()
        );

        return savedUser;
    }
}
