package com.brewery.web.controller;

import com.brewery.web.services.ConversationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class Index {
    @GetMapping(value = { "/" })
    public String index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        return "index";
    }
}
