package com.jsp.Hospital_Management_system;

import java.sql.*;
import java.util.Scanner;

public class DoctorPatientDriver {

    public static void main(String[] args) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Scanner scanner = new Scanner(System.in);

        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/hospital", "root", "root");

            Patient patient = new Patient(connection, scanner);
            Doctor doctor = new Doctor(connection);
            Appointment appointment = new Appointment(connection, scanner);

            boolean exit = false;

            while (!exit) {
                System.out.println("\n=== HOSPITAL MANAGEMENT SYSTEM ===");
                System.out.println("1. Patient");
                System.out.println("2. Doctor");
                System.out.println("3. Appointment");
                System.out.println("4. Exit");
                System.out.print("Enter your choice: ");
                int mainChoice = scanner.nextInt();
                scanner.nextLine();

                switch (mainChoice) {

                    // ================= PATIENT MENU =================
                    case 1:
                        boolean patientExit = false;
                        while (!patientExit) {
                            System.out.println("\n--- PATIENT MENU ---");
                            System.out.println("1. Add Patient");
                            System.out.println("2. View Patients");
                            System.out.println("3. Update Patient");
                            System.out.println("4. Delete Patient");
                            System.out.println("5. Back to Main Menu");
                            System.out.print("Enter your choice: ");
                            int patientChoice = scanner.nextInt();
                            scanner.nextLine();

                            switch (patientChoice) {
                                case 1:
                                    patient.addPatient();
                                    break;
                                case 2:
                                    patient.viewPatients();
                                    break;
                                case 3:
                                    patient.updatePatient();
                                    break;
                                case 4:
                                    patient.deletePatient();
                                    break;
                                case 5:
                                    patientExit = true;
                                    continue;
                                default:
                                    System.out.println("Enter valid choice!");
                                    continue;
                            }

                            System.out.print("\nDo you want to continue in Patient menu? (yes/no): ");
                            String cont = scanner.nextLine();
                            if (cont.equalsIgnoreCase("no")) {
                                patientExit = true;
                            }
                        }
                        break;

                    // ================= DOCTOR MENU =================
                    case 2:
                        boolean doctorExit = false;
                        while (!doctorExit) {
                            System.out.println("\n--- DOCTOR MENU ---");
                            System.out.println("1. Add Doctor");
                            System.out.println("2. View Doctors");
                            System.out.println("3. Update Doctor");
                            System.out.println("4. Delete Doctor");
                            System.out.println("5. Back to Main Menu");
                            System.out.print("Enter your choice: ");
                            int doctorChoice = scanner.nextInt();
                            scanner.nextLine();

                            switch (doctorChoice) {
                                case 1:
                                    doctor.addDoctor();
                                    break;
                                case 2:
                                    doctor.viewDoctors();
                                    break;
                                case 3:
                                    doctor.updateDoctor();
                                    break;
                                case 4:
                                    doctor.deleteDoctor();
                                    break;
                                case 5:
                                    doctorExit = true;
                                    continue;
                                default:
                                    System.out.println("Enter valid choice!");
                                    continue;
                            }

                            System.out.print("\nDo you want to continue in Doctor menu? (yes/no): ");
                            String cont = scanner.nextLine();
                            if (cont.equalsIgnoreCase("no")) {
                                doctorExit = true;
                            }
                        }
                        break;

                    // ================= APPOINTMENT MENU =================
                    case 3:
                        boolean appointmentExit = false;
                        while (!appointmentExit) {
                            System.out.println("\n--- APPOINTMENT MENU ---");
                            System.out.println("1. Book Appointment");
                            System.out.println("2. View Appointments");
                            System.out.println("3. Check Doctor Availability");
                            System.out.println("4. Back to Main Menu");
                            System.out.print("Enter your choice: ");
                            int appointmentChoice = scanner.nextInt();
                            scanner.nextLine();

                            switch (appointmentChoice) {
                                case 1:
                                    appointment.bookAppointment(patient, doctor, connection, scanner);
                                    break;
                                case 2:
                                	appointment.viewAppointments(connection);
                                    break;
                                case 3:
                                    System.out.print("Enter Doctor ID: ");
                                    int doctorId = scanner.nextInt();
                                    scanner.nextLine(); // consume newline
                                    System.out.print("Enter Appointment Date (YYYY-MM-DD): ");
                                    String date = scanner.nextLine();

                                    boolean available = appointment.checkDoctorAvailability(doctorId, date, connection);
                                    if (available) {
                                        System.out.println("✅ Doctor is available on " + date + ".");
                                    } else {
                                        System.out.println("❌ Doctor is NOT available on " + date + ".");
                                    }
                                    break;

                                case 4:
                                    appointmentExit = true;
                                    continue;
                                default:
                                    System.out.println("Enter valid choice!");
                                    continue;
                            }

                            System.out.print("\nDo you want to continue in Appointment menu? (yes/no): ");
                            String cont = scanner.nextLine();
                            if (cont.equalsIgnoreCase("no")) {
                                appointmentExit = true;
                            }
                        }
                        break;

                    // ================= EXIT =================
                    case 4:
                        System.out.println("THANK YOU! FOR USING HOSPITAL MANAGEMENT SYSTEM!!");
                        exit = true;
                        break;

                    default:
                        System.out.println("Enter valid choice!");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
}
