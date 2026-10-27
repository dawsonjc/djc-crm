package com.brewery.web.controller.admin;

import com.brewery.web.dto.UserDTO;
import com.brewery.web.model.User;
import com.brewery.web.services.admin.AdminUserService;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.node.ObjectNode;
import jdk.jshell.spi.ExecutionControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.UUID;

@RestController
@RequestMapping(value = { "/admin" })
public class Admin {


    @Autowired
    private AdminUserService adminUserService;

    @GetMapping(path = { "/login" })
    public String index(HttpServletRequest request) {
        request.setAttribute("unverified_users", this.adminUserService.getUnverifiedUsers());
        request.setAttribute("all_roles", this.adminUserService.getActiveRoles());
        return "admin/admin-index";
    }

    @ResponseBody
    @PostMapping(path = { "/user/verify" })
    public ResponseEntity<ObjectNode> verify(
            @RequestParam(value = "userId") UUID userId
    ) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode responseJson = mapper.createObjectNode();
        responseJson.put("success", false);
        responseJson.put("message", (new UnsupportedOperationException("Not implemented yet.")).getMessage());


        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(responseJson);
    }

}
