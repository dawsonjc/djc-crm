package com.brewery.web.controller.account;

import com.brewery.web.dto.formdata.RegisterFormData;
import com.brewery.web.model.User;
import com.brewery.web.services.RegistrationEmailService;
import com.brewery.web.services.UserTableService;
import jakarta.servlet.http.HttpServletResponse;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.node.ObjectNode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Controller
@RequestMapping(value = { "/account" })
public class Register {

    private final UserTableService userService;
    private final RegistrationEmailService registrationEmailService;

    public Register(UserTableService userService, RegistrationEmailService registrationEmailService) {
        this.userService = userService;
        this.registrationEmailService = registrationEmailService;
    }

    @GetMapping("/register")
    public String registerView(@RequestParam(name = "verification", required = false) String verification,
                               Model model, HttpServletResponse response) {
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Referrer-Policy", "no-referrer");
        User user = findInvitedUser(verification);
        if (user == null) {
            return "redirect:/account/login";
        }
        model.addAttribute("registrationEmail", user.getEmail());
        model.addAttribute("registrationUsername", user.getUsername());
        model.addAttribute("verification", verification);
        return "account/register";
    }

    @ResponseBody
    @PostMapping(value = { "/register" })
    public ResponseEntity<ObjectNode> register(
            RegisterFormData userData,
            @RequestParam(name = "verification", required = false) String verification
    ) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode responseJson = mapper.createObjectNode();
        responseJson.put("success", false);
        responseJson.put("message", "");
        responseJson.putObject("data");

        User user = findInvitedUser(verification);
        if (user == null || !user.getEmail().equals(userData.email())
                || !Objects.equals(user.getUsername(), userData.username())) {
            return invalidRegistration(responseJson);
        }

        if(!userData.verify()) {
            responseJson.set("data", mapper.convertValue(userData.getErrors(), ObjectNode.class));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseJson);
        }
        String password = userData.password();
        if (password.codePointCount(0, password.length()) < 8
                || password.getBytes(StandardCharsets.UTF_8).length > 72) {
            responseJson.put("message", "Password must be at least 8 characters and at most 72 UTF-8 bytes");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseJson);
        }

        if (!this.userService.authenticateRegisteredUser(user, userData)) {
            return invalidRegistration(responseJson);
        }

        responseJson.put("success", true);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseJson);
    }

    private User findInvitedUser(String verification) {
        return registrationEmailService.verifyToken(verification)
                .map(invitation -> userService.findPendingRegistrationUser(invitation.userId(), invitation.email()))
                .orElse(null);
    }

    private ResponseEntity<ObjectNode> invalidRegistration(ObjectNode responseJson) {
        responseJson.put("message", "Invalid");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(responseJson);
    }
}
