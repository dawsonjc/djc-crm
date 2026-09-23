package com.brewery.web.controller.auth;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = { "/auth" })
public class AuthController {

    @RequestMapping(path = { "/login" })
    public ResponseEntity<ObjectNode> login() {
        System.err.println("Auth login requested.");
        return ResponseEntity.ok().build();
    }

}
