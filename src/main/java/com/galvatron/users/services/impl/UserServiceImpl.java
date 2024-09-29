package com.galvatron.users.services.impl;

import com.galvatron.users.config.jwt.JwtTokenGenerator;
import com.galvatron.users.dto.UserDto;
import com.galvatron.users.entities.Role;
import com.galvatron.users.entities.User;
import com.galvatron.users.helper.UserHelper;
import com.galvatron.users.repositories.RoleRepository;
import com.galvatron.users.repositories.UserRepository;
import com.galvatron.users.services.UserService;
import com.galvatron.users.utils.Request.AuthenticationRequest;
import com.galvatron.users.utils.Response.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserDetailsService, UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserHelper userHelper;
    private JwtTokenGenerator jwtTokenGenerator;

    @Autowired
    public void setJwtTokenGenerator(JwtTokenGenerator jwtTokenGenerator) {
        this.jwtTokenGenerator = jwtTokenGenerator;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("User details not found for username : " + username));

        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
                .collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }

    @Override
    public AuthenticationResponse login(AuthenticationRequest request) {
        String jwtToken = jwtTokenGenerator.generateJwtToken(request.username(), request.password());
        return new AuthenticationResponse(HttpStatus.OK.getReasonPhrase(), jwtToken);
    }

    @Override
    public UserDto createUser(UserDto userDto) throws Exception {
        try {
            log.info("Entering in createUser() fx");
            Role role = validateUserRequest(userDto);

            User user = new User();
            user.setUsername(userHelper.getUniqueUserName(userDto.getFirstName(), userDto.getLastName(), userDto.getMobileNumber()));
            user.setEmail(userDto.getEmail());
            user.setPassword(userDto.getPassword());
            user.setStatus("ACTIVE");
            user.setCreatedAt(new Date());
            user.setUpdatedAt(new Date());
            user.setRoles(Set.of(role));

            User savedUser = userRepository.save(user);
            savedUser.setImeiNumber(userHelper.createImeiNumber(savedUser.getId()));
            User createdUser = userRepository.save(savedUser);
            return convertToUserDto(createdUser);
        }catch (Exception e){
            log.error("Some error {}", e.getMessage() );
        }
        return null;
    }

    private Role validateUserRequest(UserDto userDto) throws Exception {
        Long roleId = userDto.getRoleId();
        return roleRepository.findById(roleId).orElseThrow(() ->
                new Exception("Role not found with roleId : " + roleId));
    }

    private UserDto convertToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setStatus(user.getStatus());
        userDto.setImeiNumber(user.getImeiNumber());
        return userDto;
    }
}
