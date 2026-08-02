# 🏥 Hospital Management System (Pure Java & OOP)

A lightweight, console-based **Hospital Management System** built with **Pure Standard Java SE (JDK 8+)** demonstrating fundamental Object-Oriented Programming (OOP) principles without external libraries or frameworks.

---

## 🌟 Key Features

- **👤 Patient Management**: Register patients, search by name, append medical history, and view medical records.
- **🩺 Doctor Management**: Onboard doctors with specializations, qualifications, consultation fees, and available schedules.
- **📅 Appointment Scheduling**: Book appointments linking patients and doctors, manage appointment lifecycle (`SCHEDULED`, `COMPLETED`, `CANCELLED`).
- **💳 Billing & Invoices**: Generate itemized invoices, process payments, and view formatted printable receipt cards.
- **📊 System Dashboard**: Real-time view of system statistics (total patients, doctors, appointments, and total revenue collected).

---

## 🛠️ Object-Oriented Programming (OOP) Concepts

1. **Abstraction**: Abstract `Person` base class hiding low-level details and enforcing role contract method implementations.
2. **Inheritance**: `Patient` and `Doctor` classes inherit shared state (`id`, `name`, `age`, `gender`, `contactNumber`) from `Person`.
3. **Polymorphism**: Overridden `getRoleDetails()` and `toString()` methods across concrete entity types.
4. **Encapsulation**: Private class attributes exposed exclusively via controlled getters/setters and validation logic.

---

## 📁 Project Structure

```text
src/com/hospital
 ├── model
 │    ├── Person.java            # Abstract Base Class
 │    ├── Patient.java           # Patient Entity
 │    ├── Doctor.java            # Doctor Entity
 │    ├── Appointment.java       # Appointment Model
 │    ├── Bill.java              # Invoice Model
 │    └── AppointmentStatus.java # Appointment Status Enum
 ├── service
 │    ├── PatientService.java    # Patient Logic & Storage
 │    ├── DoctorService.java     # Doctor Management Logic
 │    ├── AppointmentService.java# Booking & Status Logic
 │    └── BillingService.java    # Invoice & Payment Processing
 ├── util
 │    ├── InputValidator.java    # Safe CLI Input Parsing
 │    └── IdGenerator.java       # Unique ID Generator
 └── Main.java                   # Interactive CLI Entry Point
```

---

## 🚀 Getting Started

### Prerequisites
- **Java SE Development Kit (JDK 8 or higher)** installed.

### Compilation & Execution

1. **Clone the repository:**
   ```bash
   git clone https://github.com/iamathulm/hospital-management-system1.git
   cd hospital-management-system1
   ```

2. **Compile the Java source files:**
   ```bash
   javac -d bin src/com/hospital/Main.java src/com/hospital/model/*.java src/com/hospital/service/*.java src/com/hospital/util/*.java
   ```

3. **Run the Application:**
   ```bash
   java -cp bin com.hospital.Main
   ```
