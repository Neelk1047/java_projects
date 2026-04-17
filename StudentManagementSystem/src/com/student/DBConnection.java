package com.student;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    // Database credentials
    private static final String URL = "jdbc:mysql://localhost:3306/studentdb?useSSL=false&allowPublicKeyRetrieval=true";
    private static final String USER = "YOUR_PASSWORD";
    
    // IMPORTANT: Replace "password" with your actual MySQL password!
    private static final String PASSWORD = "root"; 

    // This method returns the connection to MySQL
    public static Connection getConnection() {
        Connection connection = null;
        try {
            // Establishing the connection
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("❌ Database Connection Failed! Please check your password and URL.");
            e.printStackTrace();
        }
        return connection;
    }
}
