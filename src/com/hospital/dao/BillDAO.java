package com.hospital.dao;

import com.hospital.db.DatabaseManager;
import com.hospital.model.Bill;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) for Bill SQL operations.
 */
public class BillDAO {

    public void save(Bill bill) {
        String sql = "INSERT INTO bills (id, appointment_id, patient_id, consultation_fee, medication_fee, room_charges, total_amount, is_paid) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, bill.getBillId());
            pstmt.setString(2, bill.getAppointmentId());
            pstmt.setString(3, bill.getPatientId());
            pstmt.setDouble(4, bill.getConsultationFee());
            pstmt.setDouble(5, bill.getMedicationFee());
            pstmt.setDouble(6, bill.getRoomCharges());
            pstmt.setDouble(7, bill.getTotalAmount());
            pstmt.setInt(8, bill.isPaid() ? 1 : 0);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("❌ Error saving bill to SQL DB: " + e.getMessage());
        }
    }

    public Bill findById(String id) {
        String sql = "SELECT * FROM bills WHERE id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return mapRowToBill(rs);
            }
        } catch (SQLException e) {
            System.err.println("❌ Error querying bill by ID: " + e.getMessage());
        }
        return null;
    }

    public List<Bill> findAll() {
        List<Bill> list = new ArrayList<>();
        String sql = "SELECT * FROM bills";
        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapRowToBill(rs));
            }
        } catch (SQLException e) {
            System.err.println("❌ Error querying all bills: " + e.getMessage());
        }
        return list;
    }

    public List<Bill> findByPatientId(String patientId) {
        List<Bill> list = new ArrayList<>();
        String sql = "SELECT * FROM bills WHERE patient_id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, patientId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                list.add(mapRowToBill(rs));
            }
        } catch (SQLException e) {
            System.err.println("❌ Error querying bills by patient ID: " + e.getMessage());
        }
        return list;
    }

    public boolean markPaid(String billId) {
        String sql = "UPDATE bills SET is_paid = 1 WHERE id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, billId);
            int affected = pstmt.executeUpdate();
            return affected > 0;
        } catch (SQLException e) {
            System.err.println("❌ Error updating payment status in SQL DB: " + e.getMessage());
        }
        return false;
    }

    private Bill mapRowToBill(ResultSet rs) throws SQLException {
        Bill bill = new Bill(
                rs.getString("id"),
                rs.getString("appointment_id"),
                rs.getString("patient_id"),
                rs.getDouble("consultation_fee"),
                rs.getDouble("medication_fee"),
                rs.getDouble("room_charges")
        );
        bill.setPaid(rs.getInt("is_paid") == 1);
        return bill;
    }
}
