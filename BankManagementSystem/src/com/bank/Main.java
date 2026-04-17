package com.bank;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BankOperations operations = new BankOperations();
        boolean running = true;

        System.out.println("==================================");
        System.out.println("🏦 Welcome to Java Console Bank 🏦");
        System.out.println("==================================");

        while (running) {
            System.out.println("\n--- MAIN MENU ---");
            System.out.println("1. Create New Account");
            System.out.println("2. Deposit Money");
            System.out.println("3. Withdraw Money");
            System.out.println("4. Check Balance");
            System.out.println("5. View Account Details");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Clear the newline character from buffer

                switch (choice) {
                    case 1:
                        System.out.print("Enter Account Holder Name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter Initial Deposit Amount: ");
                        double initialDeposit = scanner.nextDouble();
                        operations.createAccount(name, initialDeposit);
                        break;
                    case 2:
                        System.out.print("Enter Account Number: ");
                        int accToDeposit = scanner.nextInt();
                        System.out.print("Enter Amount to Deposit: ");
                        double depositAmount = scanner.nextDouble();
                        operations.deposit(accToDeposit, depositAmount);
                        break;
                    case 3:
                        System.out.print("Enter Account Number: ");
                        int accToWithdraw = scanner.nextInt();
                        System.out.print("Enter Amount to Withdraw: ");
                        double withdrawAmount = scanner.nextDouble();
                        operations.withdraw(accToWithdraw, withdrawAmount);
                        break;
                    case 4:
                        System.out.print("Enter Account Number: ");
                        int accToCheck = scanner.nextInt();
                        double bal = operations.getBalance(accToCheck);
                        if (bal != -1) {
                            System.out.println("💰 Current Balance: ₹" + bal);
                        }
                        break;
                    case 5:
                        System.out.print("Enter Account Number: ");
                        int accToView = scanner.nextInt();
                        operations.viewAccountDetails(accToView);
                        break;
                    case 6:
                        System.out.println("Thank you for using Java Console Bank. Goodbye! 👋");
                        running = false;
                        break;
                    default:
                        System.out.println("⚠️ Invalid choice. Please select a number between 1 and 6.");
                }
            } catch (InputMismatchException e) {
                System.out.println("⚠️ Invalid input! Please enter valid numbers only.");
                scanner.nextLine(); // Clear the bad input from scanner
            }
        }
        scanner.close();
    }
}