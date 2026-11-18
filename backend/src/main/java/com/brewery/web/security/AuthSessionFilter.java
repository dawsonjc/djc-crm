package com.brewery.web.security;

import com.brewery.web.model.Role;
import com.brewery.web.model.User;
import com.brewery.web.user.AuthSessionUser;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.node.ObjectNode;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/** Requires a logged-in auth user after the service credential has been verified. */
public final class AuthSessionFilter extends OncePerRequestFilter {
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI().substring(request.getContextPath().length());
        return path.equals("/auth/login");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                  FilterChain filterChain) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Object sessionUser = session == null ? null : session.getAttribute(AuthSessionUser.AUTH_SESSION_USER);

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode respJson = mapper.createObjectNode();
        respJson.put("success", false);
        respJson.put("messsage", "");
        ObjectNode data = respJson.putObject("data");

        if(!(sessionUser instanceof User user)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setHeader("Cache-Control", "no-store");
            response.setContentType("application/json");
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());

            respJson.put("message", "Please sign in again.");

            response.getWriter().write(respJson.toString());
            return;
        }

        if(user.getRoles().stream().noneMatch(role -> role.getRoleNameEnum() == Role.Name.SUPER_ADMIN)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setHeader("Cache-Control", "no-store");
            response.setContentType("application/json");
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());

            respJson.put("messsage", "Unauthorized Access.");

            response.getWriter().write(respJson.toString());
            return;
        }

        request.setAttribute(AuthSessionUser.AUTH_SESSION_USER, user);
        filterChain.doFilter(request, response);
    }
}
