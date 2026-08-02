package com.hospital.service;

import com.hospital.model.Doctor;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Service managing Doctor onboarding, schedules, and search.
 */
public class DoctorService {
    private final Map<String, Doctor> doctorMap = new LinkedHashMap<>();

    public void registerDoctor(Doctor doctor) {
        doctorMap.put(doctor.getId(), doctor);
    }

    public Doctor getDoctorById(String id) {
        return doctorMap.get(id);
    }

    public List<Doctor> getAllDoctors() {
        return new ArrayList<>(doctorMap.values());
    }

    public List<Doctor> getDoctorsBySpecialization(String specialization) {
        List<Doctor> result = new ArrayList<>();
        for (Doctor d : doctorMap.values()) {
            if (d.getSpecialization().equalsIgnoreCase(specialization)) {
                result.add(d);
            }
        }
        return result;
    }
}
