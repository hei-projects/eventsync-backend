package com.example.eventsync_backend.security;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Order(1)
public class AuthFilter implements Filter {

    private final TokenStore tokenStore;

    public AuthFilter(TokenStore tokenStore) {
        this.tokenStore = tokenStore;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String method = req.getMethod();
        String path = req.getRequestURI();

        if ("GET".equalsIgnoreCase(method) || path.startsWith("/auth/")) {
            chain.doFilter(request, response);
            return;
        }

        String authHeader = req.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            res.setStatus(401);
            res.setContentType("application/json");
            res.getWriter().write("{\"error\":\"Missing or invalid token\"}");
            return;
        }

        String token = authHeader.substring(7);
        if (!tokenStore.isValid(token)) {
            res.setStatus(401);
            res.setContentType("application/json");
            res.getWriter().write("{\"error\":\"Invalid token\"}");
            return;
        }

        chain.doFilter(request, response);
    }
}
