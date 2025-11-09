package com.jsp.Hospital_Management_system;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient {

	private Connection connection;
    private Scanner scanner;

    public Patient(Connection connection, Scanner scanner){
        this.connection = connection;
        this.scanner = scanner;
    }

    public void addPatient() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Patient Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Patient Age: ");
        int age = scanner.nextInt();
        scanner.nextLine(); 

        System.out.print("Enter Patient Gender: ");
        String gender = scanner.nextLine();

        System.out.print("Enter Disease: ");
        String disease = scanner.nextLine();

        try {
            String query = "INSERT INTO patients(name, age, gender, disease) VALUES(?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, age);
            preparedStatement.setString(3, gender);
            preparedStatement.setString(4, disease);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Patient Added Successfully!");
            } else {
                System.out.println("Failed to add Patient!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void viewPatients() {
        try {
            String query = "SELECT * FROM patients";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            System.out.println("+------------+--------------------+-----+----------+----------------------+");
            System.out.println("| Patient Id | Name               | Age | Gender   | Disease              |");
            System.out.println("+------------+--------------------+-----+----------+----------------------+");


            boolean hasPatients = false;
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String gender = resultSet.getString("gender");
                String disease = resultSet.getString("disease");

                System.out.printf("| %-10d | %-18s | %-3d | %-8s | %-20s |\n", id, name, age, gender, disease);
                System.out.println("+------------+--------------------+-----+----------+----------------------+");
                hasPatients = true;
            }

            if (!hasPatients) {
                System.out.println("No patients found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public boolean getPatientById(int id){
        String query = "SELECT * FROM patients WHERE id = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return true;
            }else{
                return false;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    
    public void updatePatient() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Patient ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // consume newline

        System.out.print("Enter new Patient Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter new Patient Age: ");
        int age = scanner.nextInt();
        scanner.nextLine(); // consume newline

        System.out.print("Enter new Patient Gender: ");
        String gender = scanner.nextLine();

        System.out.print("Enter new Disease: ");
        String disease = scanner.nextLine();

        try {
            String query = "UPDATE patients SET name = ?, age = ?, gender = ?, disease = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, age);
            preparedStatement.setString(3, gender);
            preparedStatement.setString(4, disease);
            preparedStatement.setInt(5, id);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Patient Updated Successfully!");
            } else {
                System.out.println("No patient found with ID " + id);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void deletePatient() {
        System.out.print("Enter Patient ID to delete: ");
        int id = scanner.nextInt();

        try {
            // Delete all appointments for this patient
            String deleteAppointments = "DELETE FROM appointments WHERE patient_id = ?";
            PreparedStatement psAppointments = connection.prepareStatement(deleteAppointments);
            psAppointments.setInt(1, id);
            int apptRows = psAppointments.executeUpdate();  // optional: can show how many appointments were deleted

            // Now delete the patient
            String deletePatient = "DELETE FROM patients WHERE id = ?";
            PreparedStatement psPatient = connection.prepareStatement(deletePatient);
            psPatient.setInt(1, id);
            int patientRows = psPatient.executeUpdate();

            if (patientRows > 0) {
                System.out.println("Patient deleted successfully!");
                if (apptRows > 0) {
                    System.out.println(apptRows + " related appointment(s) were also deleted.");
                }
            } else {
                System.out.println("Patient not found!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}
