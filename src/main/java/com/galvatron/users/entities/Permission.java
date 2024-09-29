package com.galvatron.users.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Shashank on 2024:09:17 01:01 AM
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "permissions")
public class Permission extends BaseEntity {
    @Column(name = "permission_name", unique = true, nullable = false)
    private String permissionName;

    @Column
    private String description;

    @Column
    private String status;
}
