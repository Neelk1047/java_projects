package com.bank;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    // Database credentials
    private static final String URL = "jdbc:mysql://localhost:3306/bankdb?useSSL=false&allowPublicKeyRetrieval=true";
    private static final String USER = "root";
    
        private static final String PASSWORD = "YOUR_PASSWORD"; 

    // This method returns the connection to MySQL
    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Database Connection Failed! Check your password and URL.");
            e.printStackTrace();
        }
        return connection;
    }
}
