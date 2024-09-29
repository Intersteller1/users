package com.galvatron.users.filters;

import com.galvatron.users.entities.User;
import com.galvatron.users.repositories.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.stream.Collectors;

public class JwtTokenGeneratorFilter extends OncePerRequestFilter {
    private final UserRepository userRepository;  // Add UserRepository dependency

    public JwtTokenGeneratorFilter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (null != authentication) {
            Environment env = getEnvironment();
            if (null != env) {
                String jwtSecret = env.getProperty("JWT_SECRET", "dummy-jwt-secret-key-for-testing-only123456789");
                SecretKey secretKey = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
                User user = userRepository.findByUsername(authentication.getName())
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
                String jwtToken = Jwts
                        .builder()
                        .setIssuer("Shashank")
                        .setSubject(authentication.getName())
                        .claim("username", authentication.getName())
                        .claim("status", user.getStatus())
                        .claim("authorities", authentication.getAuthorities().stream().map(
                                GrantedAuthority::getAuthority).collect(Collectors.joining(",")))
                        .setIssuedAt(new Date())
                        .setExpiration(new Date((new Date()).getTime() + 30000000))
                        .signWith(secretKey)
                        .compact();

                response.setHeader("Authorization", jwtToken);

            }

        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getServletPath().equals("/api/login");
    }
}
