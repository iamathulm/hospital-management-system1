package com.hospital.dao;

import com.hospital.db.DatabaseManager;
import com.hospital.model.Appointment;
import com.hospital.model.AppointmentStatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) for Appointment SQL operations.
 */
public class AppointmentDAO {

    public void save(Appointment appointment) {
        String sql = "INSERT INTO appointments (id, patient_id, doctor_id, appointment_date, time_slot, status, notes) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, appointment.getAppointmentId());
            pstmt.setString(2, appointment.getPatientId());
            pstmt.setString(3, appointment.getDoctorId());
            pstmt.setString(4, appointment.getDate());
            pstmt.setString(5, appointment.getTimeSlot());
            pstmt.setString(6, appointment.getStatus().name());
            pstmt.setString(7, appointment.getNotes());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("❌ Error saving appointment to SQL DB: " + e.getMessage());
        }
    }

    public Appointment findById(String id) {
        String sql = "SELECT * FROM appointments WHERE id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return mapRowToAppointment(rs);
            }
        } catch (SQLException e) {
            System.err.println("❌ Error querying appointment by ID: " + e.getMessage());
        }
        return null;
    }

    public List<Appointment> findAll() {
        List<Appointment> list = new ArrayList<>();
        String sql = "SELECT * FROM appointments";
        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapRowToAppointment(rs));
            }
        } catch (SQLException e) {
            System.err.println("❌ Error querying all appointments: " + e.getMessage());
        }
        return list;
    }

    public List<Appointment> findByPatientId(String patientId) {
        List<Appointment> list = new ArrayList<>();
        String sql = "SELECT * FROM appointments WHERE patient_id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, patientId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                list.add(mapRowToAppointment(rs));
            }
        } catch (SQLException e) {
            System.err.println("❌ Error querying appointments by patient ID: " + e.getMessage());
        }
        return list;
    }

    public List<Appointment> findByDoctorId(String doctorId) {
        List<Appointment> list = new ArrayList<>();
        String sql = "SELECT * FROM appointments WHERE doctor_id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, doctorId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                list.add(mapRowToAppointment(rs));
            }
        } catch (SQLException e) {
            System.err.println("❌ Error querying appointments by doctor ID: " + e.getMessage());
        }
        return list;
    }

    public boolean updateStatus(String id, AppointmentStatus status) {
        String sql = "UPDATE appointments SET status = ? WHERE id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, status.name());
            pstmt.setString(2, id);
            int affected = pstmt.executeUpdate();
            return affected > 0;
        } catch (SQLException e) {
            System.err.println("❌ Error updating appointment status: " + e.getMessage());
        }
        return false;
    }

    private Appointment mapRowToAppointment(ResultSet rs) throws SQLException {
        Appointment apt = new Appointment(
                rs.getString("id"),
                rs.getString("patient_id"),
                rs.getString("doctor_id"),
                rs.getString("appointment_date"),
                rs.getString("time_slot"),
                rs.getString("notes")
        );
        apt.setStatus(AppointmentStatus.valueOf(rs.getString("status")));
        return apt;
    }
}
