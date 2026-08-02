package com.hospital.dao;

import com.hospital.db.DatabaseManager;
import com.hospital.model.Doctor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) for Doctor SQL operations.
 */
public class DoctorDAO {

    public void save(Doctor doctor) {
        String sql = "INSERT INTO doctors (id, name, age, gender, contact, specialization, qualification, fee, available_slot) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, doctor.getId());
            pstmt.setString(2, doctor.getName());
            pstmt.setInt(3, doctor.getAge());
            pstmt.setString(4, doctor.getGender());
            pstmt.setString(5, doctor.getContactNumber());
            pstmt.setString(6, doctor.getSpecialization());
            pstmt.setString(7, doctor.getQualification());
            pstmt.setDouble(8, doctor.getConsultationFee());
            pstmt.setString(9, doctor.getAvailableSlot());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("❌ Error saving doctor to SQL DB: " + e.getMessage());
        }
    }

    public Doctor findById(String id) {
        String sql = "SELECT * FROM doctors WHERE id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return mapRowToDoctor(rs);
            }
        } catch (SQLException e) {
            System.err.println("❌ Error querying doctor by ID: " + e.getMessage());
        }
        return null;
    }

    public List<Doctor> findAll() {
        List<Doctor> list = new ArrayList<>();
        String sql = "SELECT * FROM doctors";
        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapRowToDoctor(rs));
            }
        } catch (SQLException e) {
            System.err.println("❌ Error querying all doctors: " + e.getMessage());
        }
        return list;
    }

    public List<Doctor> findBySpecialization(String specialization) {
        List<Doctor> list = new ArrayList<>();
        String sql = "SELECT * FROM doctors WHERE LOWER(specialization) = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, specialization.toLowerCase());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                list.add(mapRowToDoctor(rs));
            }
        } catch (SQLException e) {
            System.err.println("❌ Error searching doctors by specialization: " + e.getMessage());
        }
        return list;
    }

    private Doctor mapRowToDoctor(ResultSet rs) throws SQLException {
        return new Doctor(
                rs.getString("id"),
                rs.getString("name"),
                rs.getInt("age"),
                rs.getString("gender"),
                rs.getString("contact"),
                rs.getString("specialization"),
                rs.getString("qualification"),
                rs.getDouble("fee"),
                rs.getString("available_slot")
        );
    }
}
