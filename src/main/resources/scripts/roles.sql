SELECT * FROM public.roles
ORDER BY id ASC

-- Insert SUPERADMIN role
INSERT INTO roles (role_name, level, created_at, updated_at)
VALUES ('SUPERADMIN', 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert RESELLER role
INSERT INTO roles (role_name, level, created_at, updated_at)
VALUES ('RESELLER', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert WHITELABEL role
INSERT INTO roles (role_name, level, created_at, updated_at)
VALUES ('WHITELABEL', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert APIPARTNER role
INSERT INTO roles (role_name, level, created_at, updated_at)
VALUES ('APIPARTNER', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert SUPERDISTRIBUTOR role
INSERT INTO roles (role_name, level, created_at, updated_at)
VALUES ('SUPERDISTRIBUTOR', 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert DISTRIBUTOR role
INSERT INTO roles (role_name, level, created_at, updated_at)
VALUES ('DISTRIBUTOR', 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert RETAILER role
INSERT INTO roles (role_name, level, created_at, updated_at)
VALUES ('RETAILER', 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
