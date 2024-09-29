
-- Insert user.create permission
INSERT INTO permissions (permission_name, description, status, created_at, updated_at)
VALUES ('user.create', 'Permission to create new users', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert user.delete permission
INSERT INTO permissions (permission_name, description, status, created_at, updated_at)
VALUES ('user.delete', 'Permission to delete users', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert user.read permission
INSERT INTO permissions (permission_name, description, status, created_at, updated_at)
VALUES ('user.read', 'Permission to read user data', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert user.update permission
INSERT INTO permissions (permission_name, description, status, created_at, updated_at)
VALUES ('user.update', 'Permission to update user information', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert admin.access permission (example of broader module access)
INSERT INTO permissions (permission_name, description, status, created_at, updated_at)
VALUES ('admin.access', 'Permission to access admin-level functions', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
