package com.hospital;

import com.hospital.model.*;
import com.hospital.service.*;
import com.hospital.util.IdGenerator;
import com.hospital.util.InputValidator;

import java.util.List;
import java.util.Scanner;

/**
 * Main application class driving the Hospital Management System CLI.
 */
public class Main {

    private final PatientService patientService = new PatientService();
    private final DoctorService doctorService = new DoctorService();
    private final AppointmentService appointmentService = new AppointmentService();
    private final BillingService billingService = new BillingService();
    private final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Main app = new Main();
        app.seedSampleData();
        app.run();
    }

    /**
     * Seeds initial sample data for quick demonstration and testing.
     */
    private void seedSampleData() {
        // Register Sample Doctors
        Doctor d1 = new Doctor(IdGenerator.generateDoctorId(), "Dr. Sarah Connor", 42, "Female", "555-0192",
                "Cardiology", "MD, FACC", 150.0, "09:00 AM - 01:00 PM");
        Doctor d2 = new Doctor(IdGenerator.generateDoctorId(), "Dr. Alan Grant", 50, "Male", "555-0144",
                "Pediatrics", "MBBS, MD", 120.0, "02:00 PM - 06:00 PM");
        Doctor d3 = new Doctor(IdGenerator.generateDoctorId(), "Dr. Elena Rostova", 38, "Female", "555-0188",
                "Orthopedics", "MS (Ortho)", 180.0, "10:00 AM - 03:00 PM");

        doctorService.registerDoctor(d1);
        doctorService.registerDoctor(d2);
        doctorService.registerDoctor(d3);

        // Register Sample Patients
        Patient p1 = new Patient(IdGenerator.generatePatientId(), "John Doe", 35, "Male", "555-1122", "O+");
        Patient p2 = new Patient(IdGenerator.generatePatientId(), "Emily Watson", 28, "Female", "555-3344", "A+");

        p1.addMedicalRecord("Initial Consultation - Routine Checkup (2025-10-10)");
        p1.addMedicalRecord("Prescribed Vitamin D3 supplements");
        p2.addMedicalRecord("Treated for Seasonal Allergies (2026-01-15)");

        patientService.registerPatient(p1);
        patientService.registerPatient(p2);

        // Create Sample Appointment
        Appointment apt1 = new Appointment(IdGenerator.generateAppointmentId(), p1.getId(), d1.getId(),
                "2026-08-05", "10:00 AM", "Annual Cardiac Evaluation");
        appointmentService.bookAppointment(apt1);

        // Create Completed Appointment & Bill
        Appointment apt2 = new Appointment(IdGenerator.generateAppointmentId(), p2.getId(), d2.getId(),
                "2026-08-01", "03:00 PM", "Pediatric Consultation");
        apt2.setStatus(AppointmentStatus.COMPLETED);
        appointmentService.bookAppointment(apt2);

        Bill bill1 = billingService.createBill(apt2.getAppointmentId(), p2.getId(), d2.getConsultationFee(), 35.0, 0.0);
        bill1.setPaid(true);
    }

    public void run() {
        System.out.println("==================================================");
        System.out.println("   🏥 WELCOME TO PURE JAVA HOSPITAL SYSTEM 🏥   ");
        System.out.println("==================================================");

        while (true) {
            printMainMenu();
            int choice = InputValidator.readInt(scanner, "Enter option (1-6): ", 1, 6);

            switch (choice) {
                case 1:
                    handlePatientMenu();
                    break;
                case 2:
                    handleDoctorMenu();
                    break;
                case 3:
                    handleAppointmentMenu();
                    break;
                case 4:
                    handleBillingMenu();
                    break;
                case 5:
                    showSystemStatistics();
                    break;
                case 6:
                    System.out.println("\nThank you for using Hospital Management System. Goodbye!");
                    return;
            }
        }
    }

    private void printMainMenu() {
        System.out.println("\n---------------- MAIN MENU ----------------");
        System.out.println("1. 👤 Patient Management");
        System.out.println("2. 🩺 Doctor Management");
        System.out.println("3. 📅 Appointment Scheduling");
        System.out.println("4. 💳 Billing & Invoices");
        System.out.println("5. 📊 System Summary / Statistics");
        System.out.println("6. 🚪 Exit");
        System.out.println("-------------------------------------------");
    }

    // ================= PATIENT MANAGEMENT =================
    private void handlePatientMenu() {
        while (true) {
            System.out.println("\n--- 👤 PATIENT MANAGEMENT ---");
            System.out.println("1. Register New Patient");
            System.out.println("2. View All Patients");
            System.out.println("3. Search Patient by Name");
            System.out.println("4. Add Medical History Record");
            System.out.println("5. View Patient Medical History");
            System.out.println("6. Back to Main Menu");

            int option = InputValidator.readInt(scanner, "Choose action (1-6): ", 1, 6);
            switch (option) {
                case 1:
                    registerPatient();
                    break;
                case 2:
                    viewAllPatients();
                    break;
                case 3:
                    searchPatientByName();
                    break;
                case 4:
                    addMedicalRecord();
                    break;
                case 5:
                    viewMedicalHistory();
                    break;
                case 6:
                    return;
            }
        }
    }

    private void registerPatient() {
        System.out.println("\n--- Register New Patient ---");
        String name = InputValidator.readNonEmptyString(scanner, "Enter Name: ");
        int age = InputValidator.readInt(scanner, "Enter Age: ", 0, 120);
        String gender = InputValidator.readNonEmptyString(scanner, "Enter Gender (Male/Female/Other): ");
        String phone = InputValidator.readNonEmptyString(scanner, "Enter Contact Phone: ");
        String blood = InputValidator.readNonEmptyString(scanner, "Enter Blood Group (e.g. O+, A-): ");

        String patientId = IdGenerator.generatePatientId();
        Patient p = new Patient(patientId, name, age, gender, phone, blood);
        patientService.registerPatient(p);
        System.out.println("✅ Patient registered successfully! Generated ID: " + patientId);
    }

    private void viewAllPatients() {
        System.out.println("\n--- 📋 Registered Patients ---");
        List<Patient> patients = patientService.getAllPatients();
        if (patients.isEmpty()) {
            System.out.println("No patients found.");
            return;
        }
        for (Patient p : patients) {
            System.out.println(p);
        }
    }

    private void searchPatientByName() {
        String query = InputValidator.readNonEmptyString(scanner, "Enter patient name to search: ");
        List<Patient> results = patientService.searchPatientsByName(query);
        if (results.isEmpty()) {
            System.out.println("❌ No matching patients found for '" + query + "'.");
        } else {
            System.out.println("\nFound " + results.size() + " match(es):");
            for (Patient p : results) {
                System.out.println(p);
            }
        }
    }

    private void addMedicalRecord() {
        String pid = InputValidator.readNonEmptyString(scanner, "Enter Patient ID (e.g. PAT-101): ");
        Patient p = patientService.getPatientById(pid);
        if (p == null) {
            System.out.println("❌ Patient with ID " + pid + " not found.");
            return;
        }
        String record = InputValidator.readNonEmptyString(scanner, "Enter Medical Record Details: ");
        patientService.addMedicalRecord(pid, record);
        System.out.println("✅ Medical record added for " + p.getName());
    }

    private void viewMedicalHistory() {
        String pid = InputValidator.readNonEmptyString(scanner, "Enter Patient ID: ");
        Patient p = patientService.getPatientById(pid);
        if (p == null) {
            System.out.println("❌ Patient with ID " + pid + " not found.");
            return;
        }
        System.out.println("\n--- Medical History for " + p.getName() + " (" + p.getId() + ") ---");
        List<String> history = p.getMedicalHistory();
        if (history.isEmpty()) {
            System.out.println("No medical records on file.");
        } else {
            for (int i = 0; i < history.size(); i++) {
                System.out.println((i + 1) + ". " + history.get(i));
            }
        }
    }

    // ================= DOCTOR MANAGEMENT =================
    private void handleDoctorMenu() {
        while (true) {
            System.out.println("\n--- 🩺 DOCTOR MANAGEMENT ---");
            System.out.println("1. Register New Doctor");
            System.out.println("2. View All Doctors");
            System.out.println("3. Search Doctors by Specialization");
            System.out.println("4. Back to Main Menu");

            int option = InputValidator.readInt(scanner, "Choose action (1-4): ", 1, 4);
            switch (option) {
                case 1:
                    registerDoctor();
                    break;
                case 2:
                    viewAllDoctors();
                    break;
                case 3:
                    searchDoctorBySpecialization();
                    break;
                case 4:
                    return;
            }
        }
    }

    private void registerDoctor() {
        System.out.println("\n--- Register New Doctor ---");
        String name = InputValidator.readNonEmptyString(scanner, "Enter Doctor Name (e.g. Dr. John): ");
        int age = InputValidator.readInt(scanner, "Enter Age: ", 25, 90);
        String gender = InputValidator.readNonEmptyString(scanner, "Enter Gender: ");
        String phone = InputValidator.readNonEmptyString(scanner, "Enter Phone: ");
        String spec = InputValidator.readNonEmptyString(scanner, "Enter Specialization (e.g. Cardiology): ");
        String qual = InputValidator.readNonEmptyString(scanner, "Enter Qualification (e.g. MD): ");
        double fee = InputValidator.readDouble(scanner, "Enter Consultation Fee ($): ", 0.0);
        String slot = InputValidator.readNonEmptyString(scanner, "Enter Available Slot (e.g. 09:00 AM - 12:00 PM): ");

        String docId = IdGenerator.generateDoctorId();
        Doctor doc = new Doctor(docId, name, age, gender, phone, spec, qual, fee, slot);
        doctorService.registerDoctor(doc);
        System.out.println("✅ Doctor registered successfully! Generated ID: " + docId);
    }

    private void viewAllDoctors() {
        System.out.println("\n--- 🩺 Available Doctors ---");
        List<Doctor> doctors = doctorService.getAllDoctors();
        if (doctors.isEmpty()) {
            System.out.println("No doctors registered.");
            return;
        }
        for (Doctor d : doctors) {
            System.out.println(d);
            System.out.println("   └─ " + d.getRoleDetails());
        }
    }

    private void searchDoctorBySpecialization() {
        String spec = InputValidator.readNonEmptyString(scanner, "Enter Specialization: ");
        List<Doctor> docs = doctorService.getDoctorsBySpecialization(spec);
        if (docs.isEmpty()) {
            System.out.println("❌ No doctors found under specialization '" + spec + "'.");
        } else {
            System.out.println("\nFound " + docs.size() + " doctor(s) specializing in " + spec + ":");
            for (Doctor d : docs) {
                System.out.println(d);
            }
        }
    }

    // ================= APPOINTMENT SCHEDULING =================
    private void handleAppointmentMenu() {
        while (true) {
            System.out.println("\n--- 📅 APPOINTMENT MANAGEMENT ---");
            System.out.println("1. Book New Appointment");
            System.out.println("2. View All Appointments");
            System.out.println("3. View Appointments by Patient");
            System.out.println("4. Mark Appointment as Completed");
            System.out.println("5. Cancel Appointment");
            System.out.println("6. Back to Main Menu");

            int option = InputValidator.readInt(scanner, "Choose action (1-6): ", 1, 6);
            switch (option) {
                case 1:
                    bookAppointment();
                    break;
                case 2:
                    viewAllAppointments();
                    break;
                case 3:
                    viewAppointmentsByPatient();
                    break;
                case 4:
                    completeAppointment();
                    break;
                case 5:
                    cancelAppointment();
                    break;
                case 6:
                    return;
            }
        }
    }

    private void bookAppointment() {
        System.out.println("\n--- Book Appointment ---");
        String pid = InputValidator.readNonEmptyString(scanner, "Enter Patient ID: ");
        Patient patient = patientService.getPatientById(pid);
        if (patient == null) {
            System.out.println("❌ Patient ID not found. Please register patient first.");
            return;
        }

        String did = InputValidator.readNonEmptyString(scanner, "Enter Doctor ID: ");
        Doctor doctor = doctorService.getDoctorById(did);
        if (doctor == null) {
            System.out.println("❌ Doctor ID not found.");
            return;
        }

        String date = InputValidator.readNonEmptyString(scanner, "Enter Date (YYYY-MM-DD): ");
        String slot = InputValidator.readNonEmptyString(scanner, "Enter Time Slot (e.g. 10:30 AM): ");
        String notes = InputValidator.readNonEmptyString(scanner, "Enter Reason/Notes: ");

        String aptId = IdGenerator.generateAppointmentId();
        Appointment appointment = new Appointment(aptId, pid, did, date, slot, notes);
        appointmentService.bookAppointment(appointment);
        patient.setAssignedDoctorId(did);

        System.out.println("✅ Appointment booked successfully! Appointment ID: " + aptId);
    }

    private void viewAllAppointments() {
        System.out.println("\n--- 📅 Scheduled Appointments ---");
        List<Appointment> list = appointmentService.getAllAppointments();
        if (list.isEmpty()) {
            System.out.println("No appointments scheduled.");
            return;
        }
        for (Appointment a : list) {
            System.out.println(a);
        }
    }

    private void viewAppointmentsByPatient() {
        String pid = InputValidator.readNonEmptyString(scanner, "Enter Patient ID: ");
        List<Appointment> list = appointmentService.getAppointmentsByPatient(pid);
        if (list.isEmpty()) {
            System.out.println("No appointments found for Patient ID: " + pid);
        } else {
            System.out.println("\nAppointments for Patient " + pid + ":");
            for (Appointment a : list) {
                System.out.println(a);
            }
        }
    }

    private void completeAppointment() {
        String aptId = InputValidator.readNonEmptyString(scanner, "Enter Appointment ID to complete: ");
        Appointment apt = appointmentService.getAppointmentById(aptId);
        if (apt == null) {
            System.out.println("❌ Appointment ID not found.");
            return;
        }

        if (apt.getStatus() == AppointmentStatus.COMPLETED) {
            System.out.println("ℹ️ Appointment is already marked as COMPLETED.");
            return;
        }

        appointmentService.updateStatus(aptId, AppointmentStatus.COMPLETED);
        System.out.println("✅ Appointment " + aptId + " marked as COMPLETED.");
    }

    private void cancelAppointment() {
        String aptId = InputValidator.readNonEmptyString(scanner, "Enter Appointment ID to cancel: ");
        Appointment apt = appointmentService.getAppointmentById(aptId);
        if (apt == null) {
            System.out.println("❌ Appointment ID not found.");
            return;
        }

        appointmentService.updateStatus(aptId, AppointmentStatus.CANCELLED);
        System.out.println("✅ Appointment " + aptId + " CANCELLED.");
    }

    // ================= BILLING & INVOICES =================
    private void handleBillingMenu() {
        while (true) {
            System.out.println("\n--- 💳 BILLING & INVOICES ---");
            System.out.println("1. Generate Bill for Appointment");
            System.out.println("2. View All Bills");
            System.out.println("3. Pay Bill");
            System.out.println("4. Print Invoice Receipt");
            System.out.println("5. Back to Main Menu");

            int option = InputValidator.readInt(scanner, "Choose action (1-5): ", 1, 5);
            switch (option) {
                case 1:
                    generateBill();
                    break;
                case 2:
                    viewAllBills();
                    break;
                case 3:
                    payBill();
                    break;
                case 4:
                    printReceipt();
                    break;
                case 5:
                    return;
            }
        }
    }

    private void generateBill() {
        String aptId = InputValidator.readNonEmptyString(scanner, "Enter Appointment ID: ");
        Appointment apt = appointmentService.getAppointmentById(aptId);
        if (apt == null) {
            System.out.println("❌ Appointment not found.");
            return;
        }

        Doctor doc = doctorService.getDoctorById(apt.getDoctorId());
        double defaultDocFee = (doc != null) ? doc.getConsultationFee() : 100.0;

        System.out.println("Suggested Consultation Fee based on Doctor: $" + defaultDocFee);
        double consultationFee = InputValidator.readDouble(scanner, "Enter Consultation Fee ($): ", 0.0);
        double medFee = InputValidator.readDouble(scanner, "Enter Medication Fee ($): ", 0.0);
        double roomCharges = InputValidator.readDouble(scanner, "Enter Room/Facility Charges ($): ", 0.0);

        Bill bill = billingService.createBill(aptId, apt.getPatientId(), consultationFee, medFee, roomCharges);
        System.out.println("✅ Bill generated successfully! Bill ID: " + bill.getBillId());
        System.out.println("Total Amount Due: $" + String.format("%.2f", bill.getTotalAmount()));
    }

    private void viewAllBills() {
        System.out.println("\n--- 💳 All Invoices ---");
        List<Bill> bills = billingService.getAllBills();
        if (bills.isEmpty()) {
            System.out.println("No bills generated yet.");
            return;
        }
        for (Bill b : bills) {
            System.out.println(b);
        }
    }

    private void payBill() {
        String billId = InputValidator.readNonEmptyString(scanner, "Enter Bill ID to Pay: ");
        boolean success = billingService.processPayment(billId);
        if (success) {
            System.out.println("✅ Payment processed successfully for Bill ID: " + billId);
        } else {
            Bill bill = billingService.getBillById(billId);
            if (bill == null) {
                System.out.println("❌ Bill ID not found.");
            } else if (bill.isPaid()) {
                System.out.println("ℹ️ Bill ID " + billId + " is already paid.");
            }
        }
    }

    private void printReceipt() {
        String billId = InputValidator.readNonEmptyString(scanner, "Enter Bill ID: ");
        Bill bill = billingService.getBillById(billId);
        if (bill == null) {
            System.out.println("❌ Bill ID not found.");
        } else {
            System.out.println("\n" + bill.getInvoiceSummary());
        }
    }

    // ================= STATISTICS & OVERVIEW =================
    private void showSystemStatistics() {
        int patientCount = patientService.getAllPatients().size();
        int doctorCount = doctorService.getAllDoctors().size();
        int appointmentCount = appointmentService.getAllAppointments().size();
        List<Bill> bills = billingService.getAllBills();
        double totalRevenue = 0.0;
        int paidBillsCount = 0;

        for (Bill b : bills) {
            if (b.isPaid()) {
                totalRevenue += b.getTotalAmount();
                paidBillsCount++;
            }
        }

        System.out.println("\n=============================================");
        System.out.println("       📊 HOSPITAL SYSTEM DASHBOARD          ");
        System.out.println("=============================================");
        System.out.println(" Total Registered Patients   : " + patientCount);
        System.out.println(" Total Active Doctors        : " + doctorCount);
        System.out.println(" Total Appointments Scheduled: " + appointmentCount);
        System.out.println(" Invoices Issued             : " + bills.size() + " (" + paidBillsCount + " Paid)");
        System.out.println(" Total Revenue Collected     : $" + String.format("%.2f", totalRevenue));
        System.out.println("=============================================");
    }
}
