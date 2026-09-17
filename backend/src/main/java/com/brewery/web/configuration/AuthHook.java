package com.brewery.web.configuration;

import com.brewery.web.model.User;
import com.brewery.web.user.SessionUser;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class AuthHook implements Filter {
    public static final HashSet<String> PUBLIC_ENDPOINTS = new HashSet<String>(Set.of(
            "/account/login",
            "/account/register",
            "/account/forgot-password"
    ));

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String servletPath = request.getServletPath();

        if (PUBLIC_ENDPOINTS.contains(servletPath) || isPathWithin(servletPath, "/static") || isPathWithin(servletPath, "/company")) {
            filterChain.doFilter(request, response);
            return;
        }

        HttpSession session = request.getSession(false);
        Object sessionUser = session == null ? null : session.getAttribute(SessionUser.SESSION_USER);

        if (!(sessionUser instanceof User loggedIn)) {
            response.sendRedirect(request.getContextPath() + "/account/login");
            return;
        }

        if (isPathWithin(servletPath, "/admin")
                && (loggedIn.getRoles() == null || !loggedIn.getRoles().contains("Admin"))) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied");
            return;
        }

        if (isPathWithin(servletPath, "/message")
                && loggedIn.getAccountVerificationStatus() != User.VerificationStatus.VERIFIED) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied");
            return;
        }

        filterChain.doFilter(request, response);
    }

    private static boolean isPathWithin(String path, String root) {
        return path.equals(root) || path.startsWith(root + "/");
    }
}
