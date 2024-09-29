-- Insert for SUPERADMIN role and user.create permission
INSERT INTO role_permissions (role_id, permission_id, status, is_default, created_at, updated_at)
VALUES (1, 1, 'ACTIVE', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert for SUPERADMIN role and user.delete permission
INSERT INTO role_permissions (role_id, permission_id, status, is_default, created_at, updated_at)
VALUES (1, 2, 'ACTIVE', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert for RESELLER role and user.read permission
INSERT INTO role_permissions (role_id, permission_id, status, is_default, created_at, updated_at)
VALUES (2, 3, 'ACTIVE', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert for RESELLER role and user.update permission
INSERT INTO role_permissions (role_id, permission_id, status, is_default, created_at, updated_at)
VALUES (2, 4, 'ACTIVE', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
