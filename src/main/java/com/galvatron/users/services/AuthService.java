package com.galvatron.users.services;

import com.galvatron.users.utils.Request.LoginRequest;
import com.galvatron.users.utils.Response.AuthenticationResponse;

public interface AuthService {
    AuthenticationResponse login(LoginRequest request);
}
