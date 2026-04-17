package com.bank;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BankOperations {

    // 1. Create a new account
	public void createAccount(String holderName, double initialBalance) {
	    String sql = "INSERT INTO accounts (holder_name, balance) VALUES (?, ?)";

	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement pstmt =
	         conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

	        pstmt.setString(1, holderName);
	        pstmt.setDouble(2, initialBalance);

	        pstmt.executeUpdate();

	        ResultSet rs = pstmt.getGeneratedKeys();

	        if (rs.next()) {
	            int accNo = rs.getInt(1);
	            System.out.println("✅ Account created successfully for " + holderName);
	            System.out.println("🏦 Your Account Number is: " + accNo);
	        }

	    } catch (Exception e) {
	        System.out.println("❌ Error creating account");
	    }
	}

    // 2. Deposit money
    public void deposit(int accountNumber, double amount) {
        if (amount <= 0) {
            System.out.println("❌ Amount must be greater than zero.");
            return;
        }

        String sql = "UPDATE accounts SET balance = balance + ? WHERE account_number = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setDouble(1, amount);
            pstmt.setInt(2, accountNumber);
            
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("✅ Successfully deposited ₹" + amount + " into account " + accountNumber);
            } else {
                System.out.println("❌ Account not found!");
            }
            
        } catch (SQLException e) {
            System.out.println("❌ Error depositing money: " + e.getMessage());
        }
    }

    // 3. Withdraw money
    public void withdraw(int accountNumber, double amount) {
        if (amount <= 0) {
            System.out.println("❌ Amount must be greater than zero.");
            return;
        }

        // First, check current balance
        double currentBalance = getBalance(accountNumber);
        
        if (currentBalance == -1) {
            System.out.println("❌ Account not found!");
            return;
        }
        
        if (currentBalance < amount) {
            System.out.println("❌ Insufficient balance! Your current balance is: ₹" + currentBalance);
            return;
        }

        // If balance is sufficient, deduct the amount
        String sql = "UPDATE accounts SET balance = balance - ? WHERE account_number = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setDouble(1, amount);
            pstmt.setInt(2, accountNumber);
            
            pstmt.executeUpdate();
            System.out.println("✅ Successfully withdrew ₹" + amount + ". Remaining balance: ₹" + (currentBalance - amount));
            
        } catch (SQLException e) {
            System.out.println("❌ Error withdrawing money: " + e.getMessage());
        }
    }

    // 4. Check balance
    public double getBalance(int accountNumber) {
        String sql = "SELECT balance FROM accounts WHERE account_number = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, accountNumber);
            ResultSet rs = pstmt.executeQuery(); // Use executeQuery for SELECT statements
            
            if (rs.next()) {
                return rs.getDouble("balance");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error checking balance: " + e.getMessage());
        }
        return -1; // Return -1 if account doesn't exist or an error occurs
    }

    // 5. View account details
    public void viewAccountDetails(int accountNumber) {
        String sql = "SELECT * FROM accounts WHERE account_number = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, accountNumber);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                System.out.println("\n--- Account Details ---");
                System.out.println("Account Number : " + rs.getInt("account_number"));
                System.out.println("Holder Name    : " + rs.getString("holder_name"));
                System.out.println("Current Balance: ₹" + rs.getDouble("balance"));
                System.out.println("-----------------------");
            } else {
                System.out.println("❌ Account not found!");
            }
            
        } catch (SQLException e) {
            System.out.println("❌ Error fetching details: " + e.getMessage());
        }
    }
}