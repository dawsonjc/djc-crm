package com.brewery.web.security;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Enumeration;

/** Registered only by FilterConfig, on routes requiring a service credential. */
public final class ServiceAuthenticationFilter implements Filter {
    public static final String HEADER_NAME = "X-Auth-Service-Secret-Key";

    private final ServiceCredentialVerifier verifier;

    public ServiceAuthenticationFilter(ServiceCredentialVerifier verifier) {
        this.verifier = verifier;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if(!verifier.isConfigured()) {
            reject(response, HttpServletResponse.SC_SERVICE_UNAVAILABLE, "Service authentication is not configured.");
            return;
        }

        Enumeration<String> values = request.getHeaders(HEADER_NAME);
        String credential = values != null && values.hasMoreElements() ? values.nextElement() : null;
        if((values != null && values.hasMoreElements()) || !verifier.isValid(credential)) {
            reject(response, HttpServletResponse.SC_UNAUTHORIZED, "Invalid service credential.");
            return;
        }

        chain.doFilter(request, response);
    }

    private static void reject(HttpServletResponse response, int status, String message) throws IOException {
        response.setStatus(status);
        response.setHeader("Cache-Control", "no-store");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"success\":false,\"message\":\"" + message + "\"}");
    }
}
