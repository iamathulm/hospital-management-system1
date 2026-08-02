package com.hospital.model;

/**
 * Concrete class representing a Doctor.
 * Demonstrates OOP Inheritance and Polymorphism.
 */
public class Doctor extends Person {
    private String specialization;
    private String qualification;
    private double consultationFee;
    private String availableSlot;

    public Doctor(String id, String name, int age, String gender, String contactNumber,
                  String specialization, String qualification, double consultationFee, String availableSlot) {
        super(id, name, age, gender, contactNumber);
        this.specialization = specialization;
        this.qualification = qualification;
        this.consultationFee = consultationFee;
        this.availableSlot = availableSlot;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public double getConsultationFee() {
        return consultationFee;
    }

    public void setConsultationFee(double consultationFee) {
        this.consultationFee = consultationFee;
    }

    public String getAvailableSlot() {
        return availableSlot;
    }

    public void setAvailableSlot(String availableSlot) {
        this.availableSlot = availableSlot;
    }

    @Override
    public String getRoleDetails() {
        return String.format("Role: Doctor | Specialization: %s | Qualification: %s | Fee: $%.2f | Timing: %s",
                specialization, qualification, consultationFee, availableSlot);
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" | Spec: %-15s | Fee: $%-6.2f | Timing: %s",
                specialization, consultationFee, availableSlot);
    }
}
