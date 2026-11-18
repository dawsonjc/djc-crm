package com.brewery.web.controller.auth;

import com.brewery.web.dto.UserDTO;
import com.brewery.web.dto.formdata.auth.CompanyUserFormData;
import com.brewery.web.model.User;
import com.brewery.web.services.RegistrationEmailService;
import com.brewery.web.services.UserTableService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.node.ObjectNode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.UUID;

@RestController
@RequestMapping(path = "/auth/user")
public class CompanyUserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyUserController.class);
    private final UserTableService userTableService;
    private final RegistrationEmailService registrationEmailService;
    private final ObjectMapper mapper;

    public CompanyUserController(UserTableService userTableService, RegistrationEmailService registrationEmailService,
                                 ObjectMapper mapper) {
        this.userTableService = userTableService;
        this.registrationEmailService = registrationEmailService;
        this.mapper = mapper;
    }

    @DeleteMapping(path = { "/{userId}" })
    public ResponseEntity<ObjectNode> deleteUser(@PathVariable(name = "userId") String userId,
                                                @RequestParam(name ="companyId") String companyId) {
        ObjectNode response = mapper.createObjectNode();
        response.put("success", false);
        response.putObject("data");
        try {
            userTableService.deleteCompanyUser(UUID.fromString(companyId), UUID.fromString(userId));
        } catch (IllegalArgumentException invalidRequest) {
            response.put("message", "Invalid company/user ID or user does not belong to this company.");
            return ResponseEntity.badRequest().body(response);
        }
        response.put("success", true);
        response.put("message", "User deleted.");
        return ResponseEntity.ok(response);
    }

    @PostMapping(path = { "/add" })
    public ResponseEntity<ObjectNode> addUser(@RequestBody CompanyUserFormData companyUserFormData) {
        ObjectNode respJson = mapper.createObjectNode();
        respJson.put("success", false);
        respJson.put("message", "");

        respJson.putObject("data");

        if(!companyUserFormData.verify()) {
            respJson.set("data", mapper.convertValue(companyUserFormData.getErrors(), ObjectNode.class));
            respJson.put("message", "Invalid form data");

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respJson);
        }

        User registeredUser = this.userTableService.registerUser(companyUserFormData);

        if (UserTableService.canCompleteRegistration(registeredUser)) {
            try {
                registrationEmailService.sendVerificationEmail(registeredUser);
            } catch (MailException | IllegalArgumentException | IllegalStateException failure) {
                LOGGER.warn("Verification email failed for user {} ({})", registeredUser.getUserId(),
                        failure.getClass().getSimpleName());
                respJson.put("message", "User created, but the verification email could not be sent.");
            }
        }

        respJson.put("success", true);
        respJson.set("data", mapper.convertValue(UserDTO.from(registeredUser), ObjectNode.class));

        return ResponseEntity.status(HttpStatus.CREATED).body(respJson);
    }
}
