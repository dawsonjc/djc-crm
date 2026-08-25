package com.brewery.web.configuration;

import com.brewery.web.model.User;

import com.brewery.web.user.SessionUser;
import jakarta.servlet.*;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class AuthHook implements Filter {
    public static final HashSet<String> PUBLIC_ENDPOINTS = new HashSet<String>(Set.of(
            "/account/login",
            "/account/register",
            "/account/forgot-password"
    ));

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String basePath = request.getContextPath();
        String servletPath = request.getServletPath();

        if(servletPath.endsWith("/login") && request.getMethod().equalsIgnoreCase("post")) {
            filterChain.doFilter(request, response);
            return;
        }

        if(servletPath.startsWith("/static")) {
            filterChain.doFilter(request, response);
            return;
        }

        HttpSession session = request.getSession(false);
        User loggedIn = session == null
                ? null
                : (User) session.getAttribute(SessionUser.SESSION_USER);

        if(loggedIn == null) {
            if(!servletPath.startsWith("/account")) {
                response.sendRedirect(basePath + "/account/login");
                return;
            }
        } else {
            if(servletPath.startsWith("/admin") && !loggedIn.getRoles().contains("Admin")) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied");
                return;
            }

            if(servletPath.startsWith("/message") && loggedIn.getAccountVerificationStatus().equals(User.VerificationStatus.PENDING)) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() { Filter.super.destroy(); }
}
