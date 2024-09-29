package com.galvatron.users.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class CorsFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (request.getMethod().equals("OPTIONS")) {
            // This is an OPTIONS request (preflight check) sent by the browser
            // Handle it by setting the appropriate CORS headers and sending a response
            response.setHeader("Access-Control-Allow-Origin", "*"); // You may want to specify the allowed origin
            response.setHeader("Access-Control-Allow-Methods", "POST, GET");
            response.setHeader("Access-Control-Max-Age", "3600");
            response.setHeader("Access-Control-Allow-Headers", "authorization, content-type, xsrf-token");
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            // This is not an OPTIONS request; continue with the filter chain
            filterChain.doFilter(request, response);
        }
    }
}
