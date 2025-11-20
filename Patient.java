package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient {
    private Connection connection;
    private Scanner sc;

    public Patient(Connection connection, Scanner sc){
        this.connection = connection;
        this.sc = sc;
    }

    public void addPatient(){
        System.out.println("ENTER PATIENT NAME : ");
        String name = sc.nextLine();
        System.out.println("ENTER PATIENT AGE : ");
        int age = 0;
        while (true) {
            try {
                age = Integer.parseInt(sc.nextLine()); // safer than nextInt()
                break;
            } catch (NumberFormatException e) {
                System.out.print("❌ Invalid input! Please enter a valid number for age: ");
            }
        }
        System.out.println("ENTER PATIENT GENDER : ");
        String gender = sc.nextLine();

        try {
            String query =  "INSERT INTO Patients(name, age, gender) VALUES (?, ?, ? )";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, age);
            preparedStatement.setString(3, gender);

            int affectedRow = preparedStatement.executeUpdate();
            if (affectedRow>0){
                System.out.println("PATIENT DATA INSERTED SUCCESSFULLY ✅");
            }else{
                System.out.println("FAILED TO ADD PATIENT ❌ ");
            }

        }catch (SQLException e){
            System.out.println("YOU ARE GETTING SQL EXCEPTION");
        }
    }
    public void viewPatient(){
        String query = "SELECT * FROM Patients";
     try {
         PreparedStatement ps = connection.prepareStatement(query);
         ResultSet resultSet = ps.executeQuery();
         System.out.println("PATIENTS : ");
         System.out.println("+-------------+----------------------+----------+----------+");
         System.out.println("| Patient Id  |         Name         |     Age  |  Gender  |");
         System.out.println("+-------------+----------------------+----------+----------+");
         while (resultSet.next()){
             int id = resultSet.getInt("Id");
             String name = resultSet.getString("Name");
             int age = resultSet.getInt("Age");
             String gender = resultSet.getString("Gender");
             System.out.printf("|%-13s|%-22s|%-10s|%-10s\n", id,name,age,gender);
             System.out.println("+-------------+----------------------+----------+----------+");
         }
     }
     catch (SQLException e ){
         System.out.println("EXCEPTION FROM VIEW PATIENT");
     }
    }
    public boolean getPatientById(int Id){
        String query = "SELECT * FROM Patients WHERE Id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, Id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                return true;
            }
            else {
                return false;
            }
        }
        catch (SQLException e){
            System.out.println("EXCEPTION FROM GET PATIENT");
        }
        return false;
    }
}

