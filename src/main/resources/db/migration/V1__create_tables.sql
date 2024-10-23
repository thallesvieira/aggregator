CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username varchar(50) NOT NULL,
    password varchar(500) NOT NULL,
    role varchar(20) NOT NULL
);

CREATE INDEX idx_username ON users (username);

-- Charge user table
INSERT INTO users(id, username, password, role) VALUES (1, 'service', '', 'SERVICE');
INSERT INTO users(id, username, password, role) VALUES (2, 'user', '$2a$10$rFXuhNxdgaaihLRJwjDs1uIwnzuQE95ovnNrOMERY4Db4szvCXJ1u', 'USER');
