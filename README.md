# Tailor Shop Order & Measurement Management System

A Java Swing/AWT desktop project using JDBC + MySQL.

## Features
- Admin login
- Dashboard statistics
- Customer management
- Customer measurements
- Order management
- Smart order status tracker
- Automatic delivery-date reminders
- Payment tracking
- Delivery management
- Reports
- MySQL database

## Default login
Username: admin
Password: admin123

## Requirements
- JDK 21
- MySQL Server 8.x
- MySQL Workbench (optional but recommended for database setup)
- VS Code + Extension Pack for Java
- Apache Maven 3.9+

## Setup
1. Install JDK 21 and verify:
   java -version
2. Install MySQL Server and remember the root password.
3. Install MySQL Workbench.
4. Open this project folder in VS Code.
5. Open `src/main/resources/db.properties` and replace:
   DB_PASSWORD=YOUR_MYSQL_PASSWORD
   with your MySQL root password.
6. Open `database/schema.sql` in MySQL Workbench and run it.
7. In VS Code terminal, from the project root run:
   mvn clean compile
   mvn exec:java
   OR double-click `run.bat` on Windows.
8. Login with admin / admin123.

If Maven is not recognized, install Apache Maven 3.9+ and reopen VS Code.

## Important
Do not put this project on GitHub with your real database password in `db.properties`.
For a college demo this simple configuration is acceptable, but real production software should use environment variables/secrets.
