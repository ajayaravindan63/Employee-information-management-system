package com.eims;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DBConnection handles the setup and connectivity to the MySQL database.
 * Ensure that you change the USER and PASSWORD fields if your MySQL configuration differs.
 */
public class DBConnection {

    // Database configuration constants
    private static final String URL = "jdbc:mysql://localhost:3306/employee_db";
    private static final String USER = "root"; // Default username in most XAMPP/WAMP/MySQL setups
    private static final String PASSWORD = "password"; // CHANGE THIS to your MySQL root password!

    /**
     * Establishes a connection to the database.
     * @return Connection object
     * @throws SQLException if a database access error occurs
     */
    public static Connection getConnection() throws SQLException {
        try {
            // Load the MySQL JDBC Driver
            // For older versions of MySQL, this might be "com.mysql.jdbc.Driver"
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found. Please add the connector jar to your classpath.");
            throw new SQLException("JDBC Driver Class Not Found", e);
        }
    }
}
