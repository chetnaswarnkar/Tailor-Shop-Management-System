CREATE DATABASE IF NOT EXISTS tailor_shop_db;
USE tailor_shop_db;

CREATE TABLE IF NOT EXISTS users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    full_name VARCHAR(100) DEFAULT 'Shop Administrator'
);

INSERT INTO users (username, password, full_name)
SELECT 'admin', 'admin123', 'Shop Administrator'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'admin');

CREATE TABLE IF NOT EXISTS customers (
    customer_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    mobile VARCHAR(15) NOT NULL,
    address VARCHAR(255),
    email VARCHAR(100),
    registration_date DATE NOT NULL DEFAULT (CURRENT_DATE)
);

CREATE TABLE IF NOT EXISTS measurements (
    measurement_id INT PRIMARY KEY AUTO_INCREMENT,
    customer_id INT NOT NULL,
    dress_type VARCHAR(50) NOT NULL,
    chest DECIMAL(6,2),
    waist DECIMAL(6,2),
    shoulder DECIMAL(6,2),
    sleeve DECIMAL(6,2),
    length DECIMAL(6,2),
    neck DECIMAL(6,2),
    hip DECIMAL(6,2),
    thigh DECIMAL(6,2),
    bottom DECIMAL(6,2),
    notes VARCHAR(500),
    measurement_date DATE NOT NULL DEFAULT (CURRENT_DATE),
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS orders (
    order_id INT PRIMARY KEY AUTO_INCREMENT,
    customer_id INT NOT NULL,
    dress_type VARCHAR(50) NOT NULL,
    quantity INT NOT NULL,
    order_date DATE NOT NULL,
    delivery_date DATE NOT NULL,
    total_amount DECIMAL(10,2) NOT NULL,
    advance_payment DECIMAL(10,2) NOT NULL DEFAULT 0,
    remaining_amount DECIMAL(10,2) NOT NULL DEFAULT 0,
    status VARCHAR(30) NOT NULL DEFAULT 'PENDING',
    special_instructions VARCHAR(500),
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS payments (
    payment_id INT PRIMARY KEY AUTO_INCREMENT,
    order_id INT NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    payment_date DATE NOT NULL DEFAULT (CURRENT_DATE),
    payment_method VARCHAR(30) NOT NULL DEFAULT 'Cash',
    FOREIGN KEY (order_id) REFERENCES orders(order_id) ON DELETE CASCADE
);

CREATE INDEX idx_orders_delivery ON orders(delivery_date);
CREATE INDEX idx_orders_status ON orders(status);
