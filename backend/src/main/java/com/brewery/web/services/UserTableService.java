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
import com.brewery.web.user.SessionUser;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.Objects;

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

    public List<UserRole> getUsersRolesByUser(SessionUser user) {
        return this.userRolesService.getUsersRolesByUserId(user.userId());
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

        User savedUser = this.createUser(user);
        this.userRolesService.save(userRole);

        return savedUser;
    }

    public User authenticateRegisteredUser(RegisterFormData registerFormData) {
        UUID userId = this.getUserIdByEmail(registerFormData.email());
        User user = this.getUserById(userId);
        User previous = user;

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

        this.repo.completeRegistration(user.getUserId(), now, user.getStatus(), user.getUsername(), hashed,
                user.getFirstName(), user.getLastName(), user.getFullName(), user.getAccountVerificationStatus());
        if (previous.getStatus() != user.getStatus()) {
            replaceEmailLookup(previous, user);
        }
        if (previous.getStatus() != user.getStatus() || !Objects.equals(previous.getUsername(), user.getUsername())) {
            replaceUsernameLookup(previous, user);
        }
        replaceVerificationProjection(previous, user);
        return user;
    }

    /** Creation writes all query projections; updates use the operation-specific methods below. */
    public User createUser(User user) {
        User savedUser = this.repo.save(user);

        this.userIdsByEmailRepository.save(emailLookup(user));
        this.userIdsByUsernameRepository.save(usernameLookup(user));
        this.usersByVerificationStatusRepository.save(verificationProjection(user));

        return savedUser;
    }

    /** Uses the already-authenticated user, so login needs no additional read or full-entity save. */
    public User recordLogin(User user, Instant loginDate, UUID token) {
        this.repo.recordLogin(user.getUserId(), loginDate, token);
        this.usersByVerificationStatusRepository.recordLogin(user.getUserId(),
                user.getAccountVerificationStatus(), user.getStatus(), loginDate);

        return user.toBuilder()
                .withLastLoginDate(loginDate)
                .withAuthApiToken(token)
                .build();
    }

    public User updateProfile(UUID userId, String firstName, String lastName, String fullName, String languagePreference, String timezone) {
        User previous = this.requireUser(userId);
        Instant now = Instant.now();

        this.repo.updateProfile(userId, firstName, lastName, fullName, languagePreference, timezone, now);
        this.usersByVerificationStatusRepository.updateProfile(userId, previous.getAccountVerificationStatus(),
                previous.getStatus(), firstName, lastName, fullName, languagePreference, timezone, now);

        return previous.toBuilder()
                .withFirstName(firstName)
                .withLastName(lastName)
                .withFullName(fullName)
                .withLanguagePreference(languagePreference)
                .withTimezone(timezone)
                .withUpdateDate(now)
                .build();
    }

    public User changeEmail(UUID userId, String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email is required");
        }
        User previous = this.requireUser(userId);
        if (Objects.equals(previous.getEmail(), email)) {
            return previous;
        }
        Instant now = Instant.now();

        User updated = previous.toBuilder()
                .withEmail(email)
                .withUpdateDate(now)
                .build();

        this.repo.changeEmail(userId, email, now);
        this.replaceEmailLookup(previous, updated);
        this.usersByVerificationStatusRepository.changeEmail(userId, previous.getAccountVerificationStatus(),
                previous.getStatus(), email, now);

        return updated;
    }

    public User changeUsername(UUID userId, String username) {
        User previous = this.requireUser(userId);
        if(Objects.equals(previous.getUsername(), username)) {
            return previous;
        }
        Instant now = Instant.now();

        User updated = previous.toBuilder()
                .withUsername(username)
                .withUpdateDate(now)
                .build();

        this.repo.changeUsername(userId, username, now);
        this.replaceUsernameLookup(previous, updated);
        this.usersByVerificationStatusRepository.changeUsername(userId, previous.getAccountVerificationStatus(),
                previous.getStatus(), username, now);

        return updated;
    }

    public User changeVerificationStatus(UUID userId, User.VerificationStatus verificationStatus) {
        User previous = this.requireUser(userId);
        if(previous.getAccountVerificationStatus() == verificationStatus) {
            return previous;
        }
        Instant now = Instant.now();

        User updated = previous.toBuilder()
                .withAccountVerificationStatus(verificationStatus)
                .withUpdateDate(now)
                .build();

        this.repo.changeVerificationStatus(userId, verificationStatus, now);
        this.replaceVerificationProjection(previous, updated);

        return updated;
    }

    public User changeStatus(UUID userId, RecordStatus status) {
        User previous = this.requireUser(userId);
        if(previous.getStatus() == status) {
            return previous;
        }
        Instant now = Instant.now();

        User updated = previous.toBuilder()
                .withStatus(status)
                .withUpdateDate(now)
                .build();

        this.repo.changeStatus(userId, status, now);

        this.replaceEmailLookup(previous, updated);
        this.replaceUsernameLookup(previous, updated);
        this.replaceVerificationProjection(previous, updated);

        return updated;
    }

    private User requireUser(UUID userId) {
        return this.repo.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    private UserIdsByEmail emailLookup(User user) {
        return UserIdsByEmail.builder()
                .withEmail(user.getEmail())
                .withStatus(user.getStatus())
                .withUserId(user.getUserId())
                .build();
    }

    private UserIdsByUsername usernameLookup(User user) {
        return UserIdsByUsername.builder()
                .withUsername(user.getUsername())
                .withStatus(user.getStatus())
                .withUserId(user.getUserId())
                .build();
    }

    private void replaceEmailLookup(User previous, User updated) {
        this.userIdsByEmailRepository.save(emailLookup(updated));
        if(previous.getEmail() != null && previous.getStatus() != null) {
            this.userIdsByEmailRepository.delete(emailLookup(previous));
        }
    }

    private void replaceUsernameLookup(User previous, User updated) {
        this.userIdsByUsernameRepository.save(usernameLookup(updated));
        if(previous.getUsername() != null && previous.getStatus() != null) {
            this.userIdsByUsernameRepository.delete(usernameLookup(previous));
        }
    }

    private UsersByVerificationStatus verificationProjection(User user) {
        return UsersByVerificationStatus.builder()
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
                .build();
    }

    private void replaceVerificationProjection(User previous, User updated) {
        this.usersByVerificationStatusRepository.save(verificationProjection(updated));
        if (previous.getAccountVerificationStatus() != null && previous.getStatus() != null
                && (previous.getAccountVerificationStatus() != updated.getAccountVerificationStatus()
                || previous.getStatus() != updated.getStatus())
        ) {
            this.usersByVerificationStatusRepository.delete(UsersByVerificationStatus.builder()
                    .withUserId(previous.getUserId())
                    .withAccountVerificationStatus(previous.getAccountVerificationStatus())
                    .withStatus(previous.getStatus())
                    .build());
        }
    }
}
