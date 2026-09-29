package com.brewery.web.controller.auth;

import com.brewery.web.dto.formdata.LoginFormData;
import com.brewery.web.model.Role;
import com.brewery.web.model.User;
import com.brewery.web.services.UserTableService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.regex.Pattern;

@RestController
@RequestMapping(path = { "/auth" })
public class AuthController {
    private final UserTableService userTableService;

    public AuthController(UserTableService userTableService) {
        this.userTableService = userTableService;
    }


    @RequestMapping(path = { "/login" })
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
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respJson);
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

        if(user.getRoles().stream().noneMatch(role -> role.getRoleName().equals(Role.Name.ADMIN.toString()))) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respJson);
        }

        request.getSession().setAttribute("auth_user", user);

        respJson.put("success", true);

        return ResponseEntity.status(HttpStatus.OK).body(respJson);
    }
}
