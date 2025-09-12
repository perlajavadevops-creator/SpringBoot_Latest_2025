-- Initialize database with sample data
USE demo_db;

-- Create users table (if not already created by Hibernate)
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    phone_number VARCHAR(20),
    created_at DATETIME(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    updated_at DATETIME(6) DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(6),
    INDEX idx_email (email),
    INDEX idx_first_name (first_name),
    INDEX idx_last_name (last_name)
);

-- Insert sample data
INSERT INTO users (first_name, last_name, email, phone_number, created_at, updated_at)
VALUES
  ('John', 'Doe', 'john.doe@example.com', '+1234567890', NOW(), NOW()),
  ('Jane', 'Smith', 'jane.smith@example.com', '+1234567891', NOW(), NOW()),
  ('Mike', 'Johnson', 'mike.johnson@example.com', '+1234567892', NOW(), NOW()),
  ('Sarah', 'Williams', 'sarah.williams@example.com', '+1234567893', NOW(), NOW()),
  ('David', 'Brown', 'david.brown@example.com', '+1234567894', NOW(), NOW())
ON DUPLICATE KEY UPDATE updated_at = VALUES(updated_at);

-- Additional indexes for performance
CREATE INDEX idx_created_at ON users(created_at);
CREATE INDEX idx_full_name ON users(first_name, last_name);

COMMIT;
