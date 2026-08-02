package com.hospital.service;

import com.hospital.model.Appointment;
import com.hospital.model.AppointmentStatus;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Service managing Appointment creation, status changes, and query filters.
 */
public class AppointmentService {
    private final Map<String, Appointment> appointmentMap = new LinkedHashMap<>();

    public void bookAppointment(Appointment appointment) {
        appointmentMap.put(appointment.getAppointmentId(), appointment);
    }

    public Appointment getAppointmentById(String id) {
        return appointmentMap.get(id);
    }

    public List<Appointment> getAllAppointments() {
        return new ArrayList<>(appointmentMap.values());
    }

    public List<Appointment> getAppointmentsByPatient(String patientId) {
        List<Appointment> result = new ArrayList<>();
        for (Appointment app : appointmentMap.values()) {
            if (app.getPatientId().equalsIgnoreCase(patientId)) {
                result.add(app);
            }
        }
        return result;
    }

    public List<Appointment> getAppointmentsByDoctor(String doctorId) {
        List<Appointment> result = new ArrayList<>();
        for (Appointment app : appointmentMap.values()) {
            if (app.getDoctorId().equalsIgnoreCase(doctorId)) {
                result.add(app);
            }
        }
        return result;
    }

    public boolean updateStatus(String appointmentId, AppointmentStatus status) {
        Appointment app = getAppointmentById(appointmentId);
        if (app != null) {
            app.setStatus(status);
            return true;
        }
        return false;
    }
}
