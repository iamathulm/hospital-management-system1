package com.hospital.service;

import com.hospital.dao.AppointmentDAO;
import com.hospital.model.Appointment;
import com.hospital.model.AppointmentStatus;

import java.util.List;

/**
 * Service managing Appointment booking and status, delegating persistence to AppointmentDAO.
 */
public class AppointmentService {
    private final AppointmentDAO appointmentDAO = new AppointmentDAO();

    public void bookAppointment(Appointment appointment) {
        appointmentDAO.save(appointment);
    }

    public Appointment getAppointmentById(String id) {
        return appointmentDAO.findById(id);
    }

    public List<Appointment> getAllAppointments() {
        return appointmentDAO.findAll();
    }

    public List<Appointment> getAppointmentsByPatient(String patientId) {
        return appointmentDAO.findByPatientId(patientId);
    }

    public List<Appointment> getAppointmentsByDoctor(String doctorId) {
        return appointmentDAO.findByDoctorId(doctorId);
    }

    public boolean updateStatus(String appointmentId, AppointmentStatus status) {
        return appointmentDAO.updateStatus(appointmentId, status);
    }
}
