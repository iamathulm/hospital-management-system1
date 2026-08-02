package com.hospital.model;

/**
 * Class representing a Bill/Invoice for patient services.
 * Demonstrates Encapsulation and Business Logic calculation.
 */
public class Bill {
    private String billId;
    private String appointmentId;
    private String patientId;
    private double consultationFee;
    private double medicationFee;
    private double roomCharges;
    private double totalAmount;
    private boolean isPaid;

    public Bill(String billId, String appointmentId, String patientId, double consultationFee, double medicationFee, double roomCharges) {
        this.billId = billId;
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.consultationFee = consultationFee;
        this.medicationFee = medicationFee;
        this.roomCharges = roomCharges;
        this.isPaid = false;
        calculateTotal();
    }

    public void calculateTotal() {
        this.totalAmount = consultationFee + medicationFee + roomCharges;
    }

    public String getBillId() {
        return billId;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public String getPatientId() {
        return patientId;
    }

    public double getConsultationFee() {
        return consultationFee;
    }

    public double getMedicationFee() {
        return medicationFee;
    }

    public double getRoomCharges() {
        return roomCharges;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public String getInvoiceSummary() {
        return String.format(
            "=========================================\n" +
            "            INVOICE RECEIPT              \n" +
            "=========================================\n" +
            "Bill ID        : %s\n" +
            "Patient ID     : %s\n" +
            "Appointment ID : %s\n" +
            "-----------------------------------------\n" +
            "Consultation Fee: $%.2f\n" +
            "Medication Fee  : $%.2f\n" +
            "Room Charges    : $%.2f\n" +
            "-----------------------------------------\n" +
            "TOTAL AMOUNT    : $%.2f\n" +
            "PAYMENT STATUS  : %s\n" +
            "=========================================",
            billId, patientId, appointmentId, consultationFee, medicationFee, roomCharges, totalAmount,
            (isPaid ? "PAID [SUCCESS]" : "UNPAID [PENDING]")
        );
    }

    @Override
    public String toString() {
        return String.format("Bill ID: %-8s | Patient: %-8s | Total: $%-8.2f | Status: %s",
                billId, patientId, totalAmount, (isPaid ? "PAID" : "UNPAID"));
    }
}
