package com.hospital.service;

import com.hospital.model.Bill;
import com.hospital.util.IdGenerator;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Service managing invoice creation, payment updates, and billing history.
 */
public class BillingService {
    private final Map<String, Bill> billMap = new LinkedHashMap<>();

    public Bill createBill(String appointmentId, String patientId, double consultationFee, double medicationFee, double roomCharges) {
        String billId = IdGenerator.generateBillId();
        Bill bill = new Bill(billId, appointmentId, patientId, consultationFee, medicationFee, roomCharges);
        billMap.put(billId, bill);
        return bill;
    }

    public Bill getBillById(String billId) {
        return billMap.get(billId);
    }

    public List<Bill> getAllBills() {
        return new ArrayList<>(billMap.values());
    }

    public List<Bill> getBillsByPatient(String patientId) {
        List<Bill> list = new ArrayList<>();
        for (Bill b : billMap.values()) {
            if (b.getPatientId().equalsIgnoreCase(patientId)) {
                list.add(b);
            }
        }
        return list;
    }

    public boolean processPayment(String billId) {
        Bill bill = getBillById(billId);
        if (bill != null && !bill.isPaid()) {
            bill.setPaid(true);
            return true;
        }
        return false;
    }
}
