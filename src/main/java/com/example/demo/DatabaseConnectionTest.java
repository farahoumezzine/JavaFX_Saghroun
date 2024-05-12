package com.example.demo;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnectionTest {
    public static void main(String[] args) {
        // Create an instance of DatabaseConnection
        DatabaseConnection dbConnection = new DatabaseConnection();

        // Get the connection
        Connection connection = dbConnection.getConnection();

        // Check if the connection is not null
        if (connection != null) {
            System.out.println("Database connection successful.");

        } else {
            System.err.println("Failed to establish database connection.");
        }
    }
}
