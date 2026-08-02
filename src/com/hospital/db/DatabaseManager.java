package com.hospital.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Manages SQLite database connection and initial schema setup.
 * Demonstrates JDBC and SQL integration in Pure Java.
 */
public class DatabaseManager {
    private static final String DB_URL = "jdbc:sqlite:hospital.db";

    static {
        try {
            // Load SQLite JDBC Driver
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("❌ SQLite JDBC Driver not found. Ensure sqlite-jdbc.jar is in classpath.");
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    /**
     * Initializes database tables if they do not exist yet.
     */
    public static void initializeDatabase() {
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            // Enable Foreign Key support in SQLite
            stmt.execute("PRAGMA foreign_keys = ON;");

            // Table: patients
            stmt.execute("CREATE TABLE IF NOT EXISTS patients (" +
                    "id TEXT PRIMARY KEY, " +
                    "name TEXT NOT NULL, " +
                    "age INTEGER, " +
                    "gender TEXT, " +
                    "contact TEXT, " +
                    "blood_group TEXT);");

            // Table: medical_history
            stmt.execute("CREATE TABLE IF NOT EXISTS medical_history (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "patient_id TEXT NOT NULL, " +
                    "record TEXT NOT NULL, " +
                    "FOREIGN KEY(patient_id) REFERENCES patients(id) ON DELETE CASCADE);");

            // Table: doctors
            stmt.execute("CREATE TABLE IF NOT EXISTS doctors (" +
                    "id TEXT PRIMARY KEY, " +
                    "name TEXT NOT NULL, " +
                    "age INTEGER, " +
                    "gender TEXT, " +
                    "contact TEXT, " +
                    "specialization TEXT, " +
                    "qualification TEXT, " +
                    "fee REAL, " +
                    "available_slot TEXT);");

            // Table: appointments
            stmt.execute("CREATE TABLE IF NOT EXISTS appointments (" +
                    "id TEXT PRIMARY KEY, " +
                    "patient_id TEXT NOT NULL, " +
                    "doctor_id TEXT NOT NULL, " +
                    "appointment_date TEXT, " +
                    "time_slot TEXT, " +
                    "status TEXT, " +
                    "notes TEXT, " +
                    "FOREIGN KEY(patient_id) REFERENCES patients(id), " +
                    "FOREIGN KEY(doctor_id) REFERENCES doctors(id));");

            // Table: bills
            stmt.execute("CREATE TABLE IF NOT EXISTS bills (" +
                    "id TEXT PRIMARY KEY, " +
                    "appointment_id TEXT NOT NULL, " +
                    "patient_id TEXT NOT NULL, " +
                    "consultation_fee REAL, " +
                    "medication_fee REAL, " +
                    "room_charges REAL, " +
                    "total_amount REAL, " +
                    "is_paid INTEGER DEFAULT 0);");

        } catch (SQLException e) {
            System.err.println("❌ Database Initialization Error: " + e.getMessage());
        }
    }
}
