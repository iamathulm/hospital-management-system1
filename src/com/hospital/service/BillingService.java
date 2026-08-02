package com.hospital.service;

import com.hospital.dao.BillDAO;
import com.hospital.model.Bill;
import com.hospital.util.IdGenerator;

import java.util.List;

/**
 * Service managing Invoice creation & payments, delegating persistence to BillDAO.
 */
public class BillingService {
    private final BillDAO billDAO = new BillDAO();

    public Bill createBill(String appointmentId, String patientId, double consultationFee, double medicationFee, double roomCharges) {
        String billId = IdGenerator.generateBillId();
        Bill bill = new Bill(billId, appointmentId, patientId, consultationFee, medicationFee, roomCharges);
        billDAO.save(bill);
        return bill;
    }

    public Bill getBillById(String billId) {
        return billDAO.findById(billId);
    }

    public List<Bill> getAllBills() {
        return billDAO.findAll();
    }

    public List<Bill> getBillsByPatient(String patientId) {
        return billDAO.findByPatientId(patientId);
    }

    public boolean processPayment(String billId) {
        return billDAO.markPaid(billId);
    }
}
