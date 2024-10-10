package com.galvatron.users.filters;

import com.galvatron.users.config.JwtTokenUtil;
import com.galvatron.users.entities.SessionToken;
import com.galvatron.users.exception.JwtTokenInvalidException;
import com.galvatron.users.exception.JwtTokenMissingException;
import com.galvatron.users.repositories.SessionTokenRepository;
import com.galvatron.users.utils.enums.Status;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private SessionTokenRepository sessionTokenRepository;
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        try {
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                jwt = authorizationHeader.substring(7);
                username = jwtTokenUtil.getUserNameFromJwtToken(jwt);
            }

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

                Optional<SessionToken> sessionTokenOpt = sessionTokenRepository.findByToken(jwt);

                if (sessionTokenOpt.isPresent()) {
                    SessionToken sessionToken = sessionTokenOpt.get();

                    if (jwtTokenUtil.validateToken(jwt, userDetails)
                            && Status.ACTIVE.name().equalsIgnoreCase(sessionToken.getStatus())
                            && sessionToken.getExpireTime() > System.currentTimeMillis()) {

                        UsernamePasswordAuthenticationToken authenticationToken =
                                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    } else {
                        throw new JwtTokenInvalidException("Token is invalid or expired.");
                    }
                } else {
                    throw new JwtTokenInvalidException("Token not found.");
                }
            }

            chain.doFilter(request, response);

        } catch (JwtTokenMissingException | JwtTokenInvalidException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ServletException("An error occurred while processing the JWT token", ex);
        }
    }
}