package com.galvatron.users.controllers;
import com.galvatron.users.dto.UserDto;
import com.galvatron.users.entities.User;
import com.galvatron.users.services.UserService;
import com.galvatron.users.utils.Request.AuthenticationRequest;
import com.galvatron.users.utils.Response.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
        AuthenticationResponse loginResponse = userService.login(request);
        return ResponseEntity.status(HttpStatus.OK).body(loginResponse);
    }

    @GetMapping("/users")
    public String getUsers(){
        return "All users";
    }
    @PostMapping("/user/create")
    public ResponseEntity<UserDto> createUsers(@RequestBody UserDto userDto){
        UserDto createdUser = null;
        try {
            createdUser = userService.createUser(userDto);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<>(createdUser, HttpStatusCode.valueOf(201));
    }
}
