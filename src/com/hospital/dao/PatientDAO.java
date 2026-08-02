package com.hospital.dao;

import com.hospital.db.DatabaseManager;
import com.hospital.model.Patient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) for Patient SQL operations.
 */
public class PatientDAO {

    public void save(Patient patient) {
        String sql = "INSERT INTO patients (id, name, age, gender, contact, blood_group) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, patient.getId());
            pstmt.setString(2, patient.getName());
            pstmt.setInt(3, patient.getAge());
            pstmt.setString(4, patient.getGender());
            pstmt.setString(5, patient.getContactNumber());
            pstmt.setString(6, patient.getBloodGroup());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("❌ Error saving patient to SQL DB: " + e.getMessage());
        }
    }

    public Patient findById(String id) {
        String sql = "SELECT * FROM patients WHERE id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Patient p = mapRowToPatient(rs);
                loadMedicalHistory(conn, p);
                return p;
            }
        } catch (SQLException e) {
            System.err.println("❌ Error querying patient by ID: " + e.getMessage());
        }
        return null;
    }

    public List<Patient> findAll() {
        List<Patient> list = new ArrayList<>();
        String sql = "SELECT * FROM patients";
        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Patient p = mapRowToPatient(rs);
                loadMedicalHistory(conn, p);
                list.add(p);
            }
        } catch (SQLException e) {
            System.err.println("❌ Error querying all patients: " + e.getMessage());
        }
        return list;
    }

    public List<Patient> searchByName(String nameQuery) {
        List<Patient> list = new ArrayList<>();
        String sql = "SELECT * FROM patients WHERE LOWER(name) LIKE ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + nameQuery.toLowerCase() + "%");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Patient p = mapRowToPatient(rs);
                loadMedicalHistory(conn, p);
                list.add(p);
            }
        } catch (SQLException e) {
            System.err.println("❌ Error searching patients by name: " + e.getMessage());
        }
        return list;
    }

    public boolean addMedicalRecord(String patientId, String record) {
        String sql = "INSERT INTO medical_history (patient_id, record) VALUES (?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, patientId);
            pstmt.setString(2, record);
            int rows = pstmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.err.println("❌ Error adding medical record: " + e.getMessage());
        }
        return false;
    }

    private void loadMedicalHistory(Connection conn, Patient patient) throws SQLException {
        String sql = "SELECT record FROM medical_history WHERE patient_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, patient.getId());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                patient.addMedicalRecord(rs.getString("record"));
            }
        }
    }

    private Patient mapRowToPatient(ResultSet rs) throws SQLException {
        return new Patient(
                rs.getString("id"),
                rs.getString("name"),
                rs.getInt("age"),
                rs.getString("gender"),
                rs.getString("contact"),
                rs.getString("blood_group")
        );
    }
}
