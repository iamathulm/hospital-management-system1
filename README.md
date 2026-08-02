# 🏥 Hospital Management System (Pure Java, OOP & SQLite SQL)

A lightweight, console-based **Hospital Management System** built with **Pure Standard Java SE (JDK 8+)**, **JDBC**, and an **embedded SQLite database** (`hospital.db`). Demonstrates Object-Oriented Programming (OOP) design patterns and Data Access Object (DAO) architecture with zero external database server setup required.

---

## 🌟 Key Features

- **💾 Persistent SQLite Database**: All records (patients, doctors, appointments, medical history, invoices) are stored permanently in `hospital.db`.
- **👤 Patient Management**: Register patients, search by name, append medical history, and view medical records via SQL queries.
- **🩺 Doctor Management**: Onboard doctors with specializations, qualifications, consultation fees, and available schedules.
- **📅 Appointment Scheduling**: Book appointments linking patients and doctors, manage appointment lifecycle (`SCHEDULED`, `COMPLETED`, `CANCELLED`).
- **💳 Billing & Invoices**: Generate itemized invoices, process payments, and view formatted printable receipt cards.
- **📊 System Dashboard**: Real-time view of system statistics queried directly from the SQL database.

---

## 🛠️ Architecture & Design Patterns

1. **Data Access Object (DAO) Pattern**: Decouples SQL database queries (`PatientDAO`, `DoctorDAO`, `AppointmentDAO`, `BillDAO`) from domain entities and CLI presentation logic.
2. **Abstraction**: Abstract `Person` base class hiding low-level details and enforcing role contract implementations.
3. **Inheritance**: `Patient` and `Doctor` extend `Person`.
4. **Polymorphism**: Overridden `getRoleDetails()` and `toString()` methods across concrete entity types.
5. **Encapsulation**: Private class attributes exposed exclusively via controlled getters/setters and domain logic.

---

## 📁 Project Structure

```text
src/com/hospital
 ├── dao
 │    ├── PatientDAO.java        # SQL operations for Patients & Medical History
 │    ├── DoctorDAO.java         # SQL operations for Doctors
 │    ├── AppointmentDAO.java    # SQL operations for Appointments
 │    └── BillDAO.java           # SQL operations for Bills & Payments
 ├── db
 │    └── DatabaseManager.java   # JDBC Connection & Schema Initialization
 ├── model
 │    ├── Person.java            # Abstract Base Class
 │    ├── Patient.java           # Patient Entity
 │    ├── Doctor.java            # Doctor Entity
 │    ├── Appointment.java       # Appointment Model
 │    ├── Bill.java              # Invoice Model
 │    └── AppointmentStatus.java # Appointment Status Enum
 ├── service
 │    ├── PatientService.java    # Delegates to PatientDAO
 │    ├── DoctorService.java     # Delegates to DoctorDAO
 │    ├── AppointmentService.java# Delegates to AppointmentDAO
 │    └── BillingService.java   # Delegates to BillDAO
 ├── util
 │    ├── InputValidator.java    # Safe CLI Input Parsing
 │    └── IdGenerator.java       # Unique ID Generator
 └── Main.java                   # Interactive CLI Entry Point

schema.sql                        # Database DDL Script
lib/sqlite-jdbc.jar              # Embedded SQLite JDBC Driver
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

2. **Compile the Java source files with SQLite JDBC driver:**
   ```cmd
   javac -cp "lib/*" -d bin src/com/hospital/Main.java src/com/hospital/model/*.java src/com/hospital/service/*.java src/com/hospital/dao/*.java src/com/hospital/db/*.java src/com/hospital/util/*.java
   ```

3. **Run the Application:**
   ```cmd
   java -cp "bin;lib/*" com.hospital.Main
   ```
