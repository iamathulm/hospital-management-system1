package com.hospital.service;

import com.hospital.dao.PatientDAO;
import com.hospital.model.Patient;

import java.util.List;

/**
 * Service managing Patient business operations, delegating persistence to PatientDAO.
 */
public class PatientService {
    private final PatientDAO patientDAO = new PatientDAO();

    public void registerPatient(Patient patient) {
        patientDAO.save(patient);
    }

    public Patient getPatientById(String id) {
        return patientDAO.findById(id);
    }

    public List<Patient> getAllPatients() {
        return patientDAO.findAll();
    }

    public boolean addMedicalRecord(String patientId, String record) {
        return patientDAO.addMedicalRecord(patientId, record);
    }

    public List<Patient> searchPatientsByName(String nameQuery) {
        return patientDAO.searchByName(nameQuery);
    }
}
