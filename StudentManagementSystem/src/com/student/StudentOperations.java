package com.student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentOperations {

	// 1. ADD NEW STUDENT (Insert with Generated Keys)
    public void addStudent(String fullName, String email, String course, double marks) {
        String sql = "INSERT INTO students (full_name, email, course, marks) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
             // Important Change: Request the generated keys from MySQL
             PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, fullName);
            pstmt.setString(2, email);
            pstmt.setString(3, course);
            pstmt.setDouble(4, marks);
            
            int rowsAffected = pstmt.executeUpdate();
            
            // Check if insertion was successful
            if (rowsAffected > 0) {
                // Get the automatically generated ID
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        long newStudentId = generatedKeys.getLong(1);
                        System.out.println("✅ Student '" + fullName + "' added successfully!");
                        // Print the new ID for the user
                        System.out.println("Your Student ID is: " + newStudentId); 
                    } else {
                        System.out.println("⚠️ Student added, but could not retrieve the ID.");
                    }
                }
            }
            
        } catch (SQLException e) {
            // Friendly error if email already exists
            if (e.getMessage().contains("Duplicate entry")) {
                System.out.println("❌ Error: A student with this email already exists!");
            } else {
                System.out.println("❌ Error adding student: " + e.getMessage());
            }
        }
    }
    // 2. VIEW ALL STUDENTS (Select)
    public void viewAllStudents() {
        String sql = "SELECT * FROM students";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            System.out.println("\n--- 📋 All Students List ---");
            boolean hasRecords = false;
            
            // Loop through the result set row by row
            while (rs.next()) {
                hasRecords = true;
                System.out.println("ID: " + rs.getInt("student_id") + 
                                   " | Name: " + rs.getString("full_name") + 
                                   " | Email: " + rs.getString("email") + 
                                   " | Course: " + rs.getString("course") + 
                                   " | Marks: " + rs.getDouble("marks"));
            }
            
            if (!hasRecords) {
                System.out.println("⚠️ No students found in the database.");
            }
            
        } catch (SQLException e) {
            System.out.println("❌ Error fetching students: " + e.getMessage());
        }
    }

    // 3. SEARCH STUDENT BY ID (Select with condition)
    public void searchStudent(int studentId) {
        String sql = "SELECT * FROM students WHERE student_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, studentId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                System.out.println("\n--- 🔍 Student Found ---");
                System.out.println("Student ID : " + rs.getInt("student_id"));
                System.out.println("Full Name  : " + rs.getString("full_name"));
                System.out.println("Email      : " + rs.getString("email"));
                System.out.println("Course     : " + rs.getString("course"));
                System.out.println("Marks      : " + rs.getDouble("marks"));
            } else {
                System.out.println("❌ Student with ID " + studentId + " not found.");
            }
            
        } catch (SQLException e) {
            System.out.println("❌ Error searching student: " + e.getMessage());
        }
    }

    // 4. UPDATE STUDENT DETAILS (Update)
    public void updateStudent(int studentId, String newCourse, double newMarks) {
        String sql = "UPDATE students SET course = ?, marks = ? WHERE student_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, newCourse);
            pstmt.setDouble(2, newMarks);
            pstmt.setInt(3, studentId);
            
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("✅ Student ID " + studentId + " updated successfully!");
            } else {
                System.out.println("❌ Update failed! Student ID " + studentId + " not found.");
            }
            
        } catch (SQLException e) {
            System.out.println("❌ Error updating student: " + e.getMessage());
        }
    }

    // 5. DELETE STUDENT (Delete)
    public void deleteStudent(int studentId) {
        String sql = "DELETE FROM students WHERE student_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, studentId);
            
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("✅ Student ID " + studentId + " deleted successfully!");
            } else {
                System.out.println("❌ Delete failed! Student ID " + studentId + " not found.");
            }
            
        } catch (SQLException e) {
            System.out.println("❌ Error deleting student: " + e.getMessage());
        }
    }
}