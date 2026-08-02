package com.hospital.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Concrete class representing a Patient.
 * Demonstrates OOP Inheritance and Polymorphism.
 */
public class Patient extends Person {
    private String bloodGroup;
    private List<String> medicalHistory;
    private String assignedDoctorId;

    public Patient(String id, String name, int age, String gender, String contactNumber, String bloodGroup) {
        super(id, name, age, gender, contactNumber);
        this.bloodGroup = bloodGroup;
        this.medicalHistory = new ArrayList<>();
        this.assignedDoctorId = "Unassigned";
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public List<String> getMedicalHistory() {
        return medicalHistory;
    }

    public void addMedicalRecord(String record) {
        this.medicalHistory.add(record);
    }

    public String getAssignedDoctorId() {
        return assignedDoctorId;
    }

    public void setAssignedDoctorId(String assignedDoctorId) {
        this.assignedDoctorId = assignedDoctorId;
    }

    @Override
    public String getRoleDetails() {
        return String.format("Role: Patient | Blood Group: %s | Doctor: %s | History Records: %d",
                bloodGroup, assignedDoctorId, medicalHistory.size());
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" | Blood: %-4s | Doctor: %s", bloodGroup, assignedDoctorId);
    }
}
