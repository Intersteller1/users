package com.galvatron.users.services.impl;

import com.galvatron.users.config.JwtTokenUtil;
import com.galvatron.users.entities.SessionToken;
import com.galvatron.users.exception.AuthenticationException;
import com.galvatron.users.exception.JwtTokenInvalidException;
import com.galvatron.users.helper.CustomUserDetails;
import com.galvatron.users.repositories.RoleRepository;
import com.galvatron.users.repositories.SessionTokenRepository;
import com.galvatron.users.services.AuthService;
import com.galvatron.users.utils.HttpServletRequestUtil;
import com.galvatron.users.utils.Request.LoginRequest;
import com.galvatron.users.utils.Response.AuthenticationResponse;
import com.galvatron.users.utils.enums.Status;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private SessionTokenRepository sessionTokenRepository;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private JwtTokenUtil jwtUtil;

    @Override
    public AuthenticationResponse login(LoginRequest loginRequest, HttpServletRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password()));
        } catch (AuthenticationException e) {
            throw new JwtTokenInvalidException("Invalid username or password");
        }

        final CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(loginRequest.username());
        final String jwt = jwtUtil.generateToken(userDetails);

        SessionToken sessionToken = createSessionToken(jwt, userDetails, request);
        sessionTokenRepository.save(sessionToken);

        return new AuthenticationResponse(HttpStatus.CREATED.getReasonPhrase(), jwt);
    }

    private SessionToken createSessionToken(String jwt, CustomUserDetails userDetails, HttpServletRequest request) {
        SessionToken sessionToken = new SessionToken();

        Long userId = userDetails.getUserId();
        sessionToken.setUserId(userId);

        String roleName = userDetails.getAuthorities().iterator().next().getAuthority().substring(5);

        sessionToken.setRoleType(roleName);
        sessionToken.setToken(jwt);
        sessionToken.setStatus(Status.ACTIVE.name());
        sessionToken.setExpireTime(Instant.now().plusMillis(jwtUtil.getExpirationTime()).toEpochMilli());

        sessionToken.setDeviceType(HttpServletRequestUtil.getDeviceType(request));
        sessionToken.setDeviceName(HttpServletRequestUtil.getDeviceName(request));
        sessionToken.setIp(HttpServletRequestUtil.getClientIp(request));
        sessionToken.setLatitude("28.672");
        sessionToken.setLongitude("77.1784704");

        return sessionToken;
    }
}
