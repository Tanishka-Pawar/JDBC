package com.jsp.Hospital_Management_system;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Appointment {

	private Connection connection;
    private Scanner scanner;

   public Appointment(Connection connection, Scanner scanner) {
    	this.connection = connection;
        this.scanner = scanner;
	}
   
// ================= BOOK APPOINTMENT =================
   public static void bookAppointment(Patient patient, Doctor doctor,
                                      Connection connection, Scanner scanner) {
       System.out.print("Enter Patient Id: ");
       int patientId = scanner.nextInt();
       System.out.print("Enter Doctor Id: ");
       int doctorId = scanner.nextInt();
       scanner.nextLine();
       System.out.print("Enter appointment date (YYYY-MM-DD): ");
       String appointmentDate = scanner.nextLine();

       if (patient.getPatientById(patientId) && doctor.getDoctorById(doctorId)) {
           if (checkDoctorAvailability(doctorId, appointmentDate, connection)) {
               try {
                   String query = "INSERT INTO appointments(patient_id, doctor_id, appointment_date) VALUES(?, ?, ?)";
                   PreparedStatement ps = connection.prepareStatement(query);
                   ps.setInt(1, patientId);
                   ps.setInt(2, doctorId);
                   ps.setString(3, appointmentDate);
                   int rows = ps.executeUpdate();
                   if (rows > 0)
                       System.out.println("Appointment Booked!");
                   else
                       System.out.println("Failed to Book Appointment!");
               } catch (SQLException e) {
                   e.printStackTrace();
               }
           } else {
               System.out.println("Doctor not available on this date!");
           }
       } else {
           System.out.println("Either doctor or patient doesn't exist!");
       }
   }


   // ================= VIEW APPOINTMENTS =================
   public static void viewAppointments(Connection connection) {
       try {
           String query = "SELECT a.id, p.name AS patient_name, d.name AS doctor_name, a.appointment_date " +
                          "FROM appointments a " +
                          "JOIN patients p ON a.patient_id = p.id " +
                          "JOIN doctors d ON a.doctor_id = d.id";
           PreparedStatement ps = connection.prepareStatement(query);
           ResultSet rs = ps.executeQuery();

           System.out.println("+------------+--------------------+--------------------+------------+");
           System.out.println("| Appt. ID   | Patient            | Doctor             | Date       |");
           System.out.println("+------------+--------------------+--------------------+------------+");

           boolean hasAppointments = false;
           while (rs.next()) {
               int id = rs.getInt("id");
               String patientName = rs.getString("patient_name");
               String doctorName = rs.getString("doctor_name");
               String date = rs.getString("appointment_date");
               System.out.printf("| %-10d | %-18s | %-18s | %-10s |\n", id, patientName, doctorName, date);
               System.out.println("+------------+--------------------+--------------------+------------+");
               hasAppointments = true;
           }

           if (!hasAppointments)
               System.out.println("No appointments found.");

       } catch (SQLException e) {
           e.printStackTrace();
       }
   }
   
// ================= CHECK DOCTOR AVAILABILITY =================
   public static boolean checkDoctorAvailability(int doctorId, String appointmentDate, Connection connection) {
       String query = "SELECT COUNT(*) FROM appointments WHERE doctor_id = ? AND appointment_date = ?";
       try {
           PreparedStatement ps = connection.prepareStatement(query);
           ps.setInt(1, doctorId);
           ps.setString(2, appointmentDate);
           ResultSet rs = ps.executeQuery();
           if (rs.next()) {
               return rs.getInt(1) == 0;
           }
       } catch (SQLException e) {
           e.printStackTrace();
       }
       return false;
   }

}
