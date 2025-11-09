package com.jsp.Hospital_Management_system;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Doctor {

	private Connection connection;

    public Doctor(Connection connection){
        this.connection = connection;
    }

    public void addDoctor() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Doctor Name: ");
        String name = scanner.nextLine(); // allow full names

        System.out.print("Enter Doctor Specialization: ");
        String specialization = scanner.nextLine();

        try {
            String query = "INSERT INTO doctors(name, specialization) VALUES(?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, specialization);

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Doctor Added Successfully!!");
            } else {
                System.out.println("Failed to add Doctor!!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void viewDoctors(){
        String query = "select * from doctors";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("Doctors: ");
            System.out.println("+------------+--------------------+------------------+");
            System.out.println("| Doctor Id  | Name               | Specialization   |");
            System.out.println("+------------+--------------------+------------------+");
            while(resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String specialization = resultSet.getString("specialization");
                System.out.printf("| %-10s | %-18s | %-16s |\n", id, name, specialization);
                System.out.println("+------------+--------------------+------------------+");
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public boolean getDoctorById(int id){
        String query = "SELECT * FROM doctors WHERE id = ?";
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
    
    public void updateDoctor() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Doctor ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // consume leftover newline

        System.out.print("Enter new Doctor Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter new Doctor Specialization: ");
        String specialization = scanner.nextLine();

        try {
            String query = "UPDATE doctors SET name = ?, specialization = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, specialization);
            preparedStatement.setInt(3, id);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Doctor Updated Successfully!");
            } else {
                System.out.println("No doctor found with ID " + id);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteDoctor() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Doctor ID to delete: ");
        int id = scanner.nextInt();

        try {
            String query = "DELETE FROM doctors WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Doctor Deleted Successfully!");
            } else {
                System.out.println("No doctor found with ID " + id);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
 
}
