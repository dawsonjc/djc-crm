package com.brewery.web.controller.auth;

import com.brewery.web.dto.formdata.auth.CompanyUserFormData;
import com.brewery.web.services.UserTableService;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.node.ObjectNode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/auth/user")
public class UserController {
    private final UserTableService userTableService;

    public UserController(UserTableService userTableService) {
        this.userTableService = userTableService;
    }

    @PostMapping(path = { "/add" })
    public ResponseEntity<ObjectNode> addUser(@RequestBody CompanyUserFormData companyUserFormData) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode respJson = mapper.createObjectNode();
        respJson.put("success", false);
        respJson.put("message", "");

        ObjectNode data = respJson.putObject("data");

        if(!companyUserFormData.verify()) {
            respJson.set("data", mapper.convertValue(companyUserFormData.getErrors(), ObjectNode.class));
            respJson.put("message", "Invalid form data");

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respJson);
        }

        this.userTableService.registerUser(companyUserFormData);

        respJson.put("success", true);

        return ResponseEntity.status(HttpStatus.CREATED).body(respJson);
    }
}
