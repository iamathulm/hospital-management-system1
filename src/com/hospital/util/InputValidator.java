package com.hospital.util;

import java.util.Scanner;

/**
 * Utility class for safe console input parsing and validation.
 */
public class InputValidator {

    public static int readInt(Scanner scanner, String prompt, int min, int max) {
        int choice = -1;
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                choice = Integer.parseInt(input);
                if (choice >= min && choice <= max) {
                    return choice;
                } else {
                    System.out.println("❌ Please enter a number between " + min + " and " + max + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid input! Please enter a valid integer.");
            }
        }
    }

    public static double readDouble(Scanner scanner, String prompt, double min) {
        double value = -1.0;
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                value = Double.parseDouble(input);
                if (value >= min) {
                    return value;
                } else {
                    System.out.println("❌ Value must be at least " + min + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid input! Please enter a numeric value.");
            }
        }
    }

    public static String readNonEmptyString(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("❌ Field cannot be empty. Please try again.");
        }
    }
}
