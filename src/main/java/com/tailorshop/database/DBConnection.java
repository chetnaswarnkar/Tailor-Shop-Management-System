package com.tailorshop.database;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public final class DBConnection {
    private static final Properties PROPS = new Properties();

    static {
        try (InputStream in = DBConnection.class.getResourceAsStream("/db.properties")) {
            if (in == null) throw new IllegalStateException("db.properties not found.");
            PROPS.load(in);
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            throw new ExceptionInInitializerError("Database configuration error: " + e.getMessage());
        }
    }

    private DBConnection() {}

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(
                PROPS.getProperty("DB_URL"),
                PROPS.getProperty("DB_USER"),
                PROPS.getProperty("DB_PASSWORD")
        );
    }
}
