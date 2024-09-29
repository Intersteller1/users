package com.galvatron.users.services;

import com.galvatron.users.dto.UserDto;
import com.galvatron.users.entities.User;
import com.galvatron.users.utils.Request.AuthenticationRequest;
import com.galvatron.users.utils.Response.AuthenticationResponse;

public interface UserService {
    AuthenticationResponse login(AuthenticationRequest request);
    UserDto createUser(UserDto user) throws Exception;
}
