package com.brewery.web.controller.auth;

import com.brewery.web.dto.formdata.LoginFormData;
import com.brewery.web.model.Role;
import com.brewery.web.model.User;
import com.brewery.web.services.RegistrationEmailService;
import com.brewery.web.services.UserTableService;
import com.brewery.web.user.AuthSessionUser;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.node.ObjectNode;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.time.Instant;
import java.util.regex.Pattern;

@RestController
@RequestMapping(path = { "/auth" })
public class AuthController {
    private final UserTableService userTableService;
    private final RegistrationEmailService emailService;

    public AuthController(UserTableService userTableService, RegistrationEmailService emailService) {
        this.userTableService = userTableService;
        this.emailService = emailService;
    }


    @PostMapping(path = { "/login" })
    public ResponseEntity<ObjectNode> login(
            @RequestBody LoginFormData formUser,
            HttpServletRequest request
    ) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode respJson = mapper.createObjectNode();
        respJson.put("success", false);
        respJson.put("message", "");
        ObjectNode data = respJson.putObject("data");
        if(!formUser.verify()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respJson);
        }
        Pattern emailPattern = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
        boolean isEmail = emailPattern.matcher(formUser.username()).matches();

        if(!isEmail) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respJson);
        }
        if(!this.userTableService.userExistsByEmail(formUser.username())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respJson);
        }

        UUID userId = this.userTableService.getUserIdByEmail(formUser.username());

        User user = this.userTableService.getUserByIdAndPassword(userId, formUser.password());
        if(user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respJson);
        }

        if(user.getAccountVerificationStatus() != User.VerificationStatus.VERIFIED &&
           user.getRoles().stream().noneMatch(role -> role.getRoleName().equals(Role.Name.SUPER_ADMIN.toString()))
        ) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respJson);
        }

        user = this.userTableService.recordLogin(user, Instant.now(), UUID.randomUUID());
        request.getSession(true ).setAttribute(AuthSessionUser.AUTH_SESSION_USER, user);

        respJson.put("success", true);

        return ResponseEntity.status(HttpStatus.OK).body(respJson);
    }
}
