package com.student;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StudentOperations operations = new StudentOperations();
        boolean running = true;

        System.out.println("=========================================");
        System.out.println("🎓 Welcome to Student Management System 🎓");
        System.out.println("=========================================");

        while (running) {
            System.out.println("\n--- MAIN MENU ---");
            System.out.println("1. Add New Student");
            System.out.println("2. View All Students");
            System.out.println("3. Search Student by ID");
            System.out.println("4. Update Student Details (Course & Marks)");
            System.out.println("5. Delete Student");
            System.out.println("6. Exit");
            System.out.print("👉 Enter your choice: ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Clear the newline character left by nextInt()

                switch (choice) {
                    case 1:
                        System.out.print("Enter Full Name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter Email: ");
                        String email = scanner.nextLine();
                        System.out.print("Enter Course: ");
                        String course = scanner.nextLine();
                        System.out.print("Enter Marks: ");
                        double marks = scanner.nextDouble();
                        
                        operations.addStudent(name, email, course, marks);
                        break;
                        
                    case 2:
                        operations.viewAllStudents();
                        break;
                        
                    case 3:
                        System.out.print("Enter Student ID to search: ");
                        int searchId = scanner.nextInt();
                        operations.searchStudent(searchId);
                        break;
                        
                    case 4:
                        System.out.print("Enter Student ID to update: ");
                        int updateId = scanner.nextInt();
                        scanner.nextLine(); // Clear buffer
                        System.out.print("Enter New Course: ");
                        String newCourse = scanner.nextLine();
                        System.out.print("Enter New Marks: ");
                        double newMarks = scanner.nextDouble();
                        
                        operations.updateStudent(updateId, newCourse, newMarks);
                        break;
                        
                    case 5:
                        System.out.print("Enter Student ID to delete: ");
                        int deleteId = scanner.nextInt();
                        operations.deleteStudent(deleteId);
                        break;
                        
                    case 6:
                        System.out.println("Exiting the system. Have a great day! 👋");
                        running = false;
                        break;
                        
                    default:
                        System.out.println("⚠️ Invalid choice! Please select a number between 1 and 6.");
                }
            } catch (InputMismatchException e) {
                System.out.println("⚠️ Invalid input! Please enter correct data types (e.g., numbers for ID and Marks).");
                scanner.nextLine(); // Clear the bad input to prevent an infinite loop
            }
        }
        scanner.close();
    }
}