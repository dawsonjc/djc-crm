package com.brewery.web.controller.account;

import com.brewery.web.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(path = { "/account" })
public class ForgotPassword {
    // TODO: Emails


    @GetMapping(path = { "/forgot-password" })
    public String forgetPasswordView(HttpServletRequest request) {
        request.setAttribute("user", new User());
        return "account/forgot-password";
    }

    @PostMapping(path = { "/forgot-password" })
    public ResponseEntity<String> forgetPassword() {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body((new UnsupportedOperationException()).getMessage());
    }
}
