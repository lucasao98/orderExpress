CREATE TABLE IF NOT EXISTS users (
    user_id TEXT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    role VARCHAR(10) NOT NULL,
    created_at TIMESTAMP DEFAULT (CURRENT_TIMESTAMP) NOT NULL,
    updated_at TIMESTAMP DEFAULT (CURRENT_TIMESTAMP) NOT NULL
);


-- V2__seed_admin_user.sql
INSERT INTO users (user_id, name, email, password, role)
SELECT
    'c8296eb9-e3a1-4ebb-b66d-3137b2cb1e5e',
    'Admin',
    'admin@foody.com',
    '$2a$10$fs/iSKxAJpeDPIP2FbNgv.I8f4qP0d4D/oMrtinwYtYiqV1V47sg.',
    0
    WHERE NOT EXISTS (
   SELECT 1 FROM users WHERE email = 'admin@foody.com'
);