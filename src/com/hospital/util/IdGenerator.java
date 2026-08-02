package com.hospital.util;

/**
 * Utility class for auto-generating unique entity identifiers.
 */
public class IdGenerator {
    private static int patientCounter = 101;
    private static int doctorCounter = 101;
    private static int appointmentCounter = 101;
    private static int billCounter = 101;

    public static synchronized String generatePatientId() {
        return "PAT-" + (patientCounter++);
    }

    public static synchronized String generateDoctorId() {
        return "DOC-" + (doctorCounter++);
    }

    public static synchronized String generateAppointmentId() {
        return "APT-" + (appointmentCounter++);
    }

    public static synchronized String generateBillId() {
        return "INV-" + (billCounter++);
    }
}
