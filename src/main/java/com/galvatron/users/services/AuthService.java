package com.galvatron.users.services;

import com.galvatron.users.utils.Request.LoginRequest;
import com.galvatron.users.utils.Response.AuthenticationResponse;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthService {
    AuthenticationResponse login(LoginRequest loginRequest, HttpServletRequest request);
}
