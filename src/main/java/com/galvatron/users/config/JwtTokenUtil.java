package com.galvatron.users.config;

import com.galvatron.users.helper.CustomUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtTokenUtil {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenUtil.class);
    private final String SECRET_KEY = "dummy-jwt-secret-key-for-testing-only123456789"; // It should be at least 256 bits

    private final Long EXPIRE_TIME = 30000000L;
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(CustomUserDetails userDetails) {
        return Jwts.builder()
                .setIssuer("Shashank")
                .setSubject(userDetails.getUsername())
                .claim("username", userDetails.getUsername())
                .claim("userId", userDetails.getUserId())
                .claim("authorities", userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.joining(",")))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_TIME)) // 30,000,000 milliseconds
                .signWith(getSigningKey())
                .compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUserNameFromJwtToken(token);
        return (username != null && username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public String getUserNameFromJwtToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (Exception e) {
            logger.error("Failed to parse JWT token: {}", e.getMessage());
            return null;
        }
    }

    public Claims extractAllClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            logger.error("Failed to extract claims from JWT token: {}", e.getMessage());
            return null;
        }
    }

    public Boolean isTokenExpired(String token) {
        try {
            Claims claims = extractAllClaims(token);
            return claims != null && claims.getExpiration().before(new Date());
        } catch (Exception e) {
            logger.error("Failed to check if JWT token is expired: {}", e.getMessage());
            return true;
        }
    }
    public long getExpirationTime() {
        return EXPIRE_TIME;
    }
}
