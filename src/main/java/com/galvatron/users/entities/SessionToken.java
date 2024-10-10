package com.galvatron.users.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "session_tokens")
public class SessionToken extends BaseEntity {
    private Long userId;
    private String roleType;
    private String token;
    private String status;
    private Long expireTime;
    private String deviceType;
    private String deviceName;
    private String latitude;
    private String longitude;
    private String ip;

}
