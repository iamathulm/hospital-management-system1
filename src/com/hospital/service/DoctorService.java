package com.hospital.service;

import com.hospital.dao.DoctorDAO;
import com.hospital.model.Doctor;

import java.util.List;

/**
 * Service managing Doctor onboarding & search, delegating persistence to DoctorDAO.
 */
public class DoctorService {
    private final DoctorDAO doctorDAO = new DoctorDAO();

    public void registerDoctor(Doctor doctor) {
        doctorDAO.save(doctor);
    }

    public Doctor getDoctorById(String id) {
        return doctorDAO.findById(id);
    }

    public List<Doctor> getAllDoctors() {
        return doctorDAO.findAll();
    }

    public List<Doctor> getDoctorsBySpecialization(String specialization) {
        return doctorDAO.findBySpecialization(specialization);
    }
}
