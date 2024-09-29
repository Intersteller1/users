package com.galvatron.users.services.impl;

import com.galvatron.users.config.JwtTokenUtil;
import com.galvatron.users.exception.AuthenticationException;
import com.galvatron.users.services.AuthService;
import com.galvatron.users.utils.Request.LoginRequest;
import com.galvatron.users.utils.Response.AuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtUtil;
    @Override
    public AuthenticationResponse login(LoginRequest loginRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password()));
        } catch (AuthenticationException e) {
            throw new AuthenticationException("Invalid username or password");
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.username());
        final String jwt = jwtUtil.generateToken(userDetails);

        return new AuthenticationResponse(HttpStatus.CREATED.getReasonPhrase(), jwt);
    }

}
