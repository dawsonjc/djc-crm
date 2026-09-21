package com.brewery.web.controller.account;

import com.brewery.web.dto.formdata.LoginFormData;
import com.brewery.web.model.User;
import com.brewery.web.services.UserTableService;
import com.brewery.web.user.SessionUser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

@Controller
@RequestMapping(value = { "/account" })
public class Login {

    @Autowired
    private UserTableService userService;

    @GetMapping(value = { "/login" })
    public String loginView(Model model, HttpServletRequest request) {
        model.addAttribute("new_user", new User());
        model.addAttribute("login_user", new User());
        return "account/login";
    }

    @ResponseBody
    @PostMapping(path = { "/auth/login" }, produces = { "application/json" })
    public ResponseEntity<ObjectNode> authLogin(HttpServletRequest request, LoginFormData formData) {
        boolean isValidAuthLoginRequest = request.getHeader("Authorization") != null && !request.getHeader("Authorization").isEmpty()
                && request.getHeader("Authorization").equals();

        if(!isValidAuthLoginRequest) {
            
        }


        ObjectMapper mapper = new ObjectMapper();
        ObjectNode respJson = mapper.createObjectNode();
        respJson.put("success", false);
        respJson.put("message", "");
        ObjectNode data = respJson.putObject("data");



        return ResponseEntity.status(200).body(respJson);
    }


    @ResponseBody
    @PostMapping(value = { "/login" }, consumes = { "application/json" }, produces = {  "application/json" })
    public ResponseEntity<ObjectNode> login(
            HttpServletRequest request,
            @RequestBody LoginFormData formUser
    ) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode respJson = mapper.createObjectNode();
        respJson.put("success", false);
        respJson.put("message", "");
        ObjectNode data = respJson.putObject("data");

        if(!formUser.verify()) {
            respJson.set("data", mapper.convertValue(formUser.getErrors(), ObjectNode.class));
            return ResponseEntity.status(400).body(respJson);
        }

        Pattern emailPattern = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");

        boolean isEmail = emailPattern.matcher(formUser.username()).matches();

        UUID userId;
        if(isEmail) {
            if(!this.userService.userExistsByEmail(formUser.username())) {
                return ResponseEntity.status(401).body(respJson);
            }

            userId = this.userService.getUserIdByEmail(formUser.username());
        } else {
            if(!this.userService.userExistsByUsername(formUser.username())) {
                return ResponseEntity.status(401).body(respJson);
            }

            userId = this.userService.getUserIdByUsername(formUser.username());
        }

        User user = this.userService.getUserByIdAndPassword(userId, formUser.password());

        if(user == null) {
            return ResponseEntity.status(401).body(respJson);
        }
        user.setLastLoginDate(Instant.now());
        user.setAuthApiToken(UUID.randomUUID());
        this.userService.save(user); // last login date

        respJson.put("success", true);

        data.put("userId", userId.toString());
        data.put("username", user.getUsername());
        ArrayNode rolesJsonArray = data.putArray("roles");
        List<String> roles = user.getRoles();
        for(String role : roles) {
            rolesJsonArray.add(role);
        }

        request.getSession().setAttribute(SessionUser.SESSION_USER, user.toSessionUser());

        return ResponseEntity.status(HttpStatus.OK).body(respJson);
    }
}
