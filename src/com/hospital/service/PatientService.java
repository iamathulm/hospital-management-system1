package com.hospital.service;

import com.hospital.model.Patient;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Service managing Patient business operations and storage.
 */
public class PatientService {
    private final Map<String, Patient> patientMap = new LinkedHashMap<>();

    public void registerPatient(Patient patient) {
        patientMap.put(patient.getId(), patient);
    }

    public Patient getPatientById(String id) {
        return patientMap.get(id);
    }

    public List<Patient> getAllPatients() {
        return new ArrayList<>(patientMap.values());
    }

    public boolean addMedicalRecord(String patientId, String record) {
        Patient patient = getPatientById(patientId);
        if (patient != null) {
            patient.addMedicalRecord(record);
            return true;
        }
        return false;
    }

    public List<Patient> searchPatientsByName(String nameQuery) {
        List<Patient> results = new ArrayList<>();
        for (Patient p : patientMap.values()) {
            if (p.getName().toLowerCase().contains(nameQuery.toLowerCase())) {
                results.add(p);
            }
        }
        return results;
    }
}
