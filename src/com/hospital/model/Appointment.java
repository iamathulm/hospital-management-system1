package com.hospital.model;

/**
 * Class representing an Appointment between a Patient and a Doctor.
 * Demonstrates Encapsulation.
 */
public class Appointment {
    private String appointmentId;
    private String patientId;
    private String doctorId;
    private String date;
    private String timeSlot;
    private AppointmentStatus status;
    private String notes;

    public Appointment(String appointmentId, String patientId, String doctorId, String date, String timeSlot, String notes) {
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.date = date;
        this.timeSlot = timeSlot;
        this.status = AppointmentStatus.SCHEDULED;
        this.notes = notes;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return String.format("Apt ID: %-8s | Patient ID: %-8s | Doctor ID: %-8s | Date: %-10s | Time: %-10s | Status: %-10s | Notes: %s",
                appointmentId, patientId, doctorId, date, timeSlot, status, notes);
    }
}
