package com.galvatron.users.dto;

import com.galvatron.users.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * Data Transfer Object for User
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String username;
    private String firstName;
    private String lastName;
    private String mobileNumber;
    private String email;
    private String password;
    private String status;
    private String imeiNumber;
    private Long roleId;
}
