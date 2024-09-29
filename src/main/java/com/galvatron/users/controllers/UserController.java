package com.galvatron.users.controllers;

import com.galvatron.users.config.jwt.JwtTokenUtil;
import com.galvatron.users.services.AuthService;
import com.galvatron.users.utils.Request.LoginRequest;
import com.galvatron.users.utils.Response.AuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticateUser(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @GetMapping("/users")
    public String getUsers(){
        return "All users";
    }
}
