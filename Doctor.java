package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Doctor {
    private Connection connection;

    public Doctor(Connection connection){
        this.connection = connection;
    }

    public void viewDoctors(){
        String query = "SELECT * FROM Doctors";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet resultSet = ps.executeQuery();
            System.out.println("DOCTORS : ");
            System.out.println("+─────────────+──────────────────────+────────────────────+");
            System.out.println("│ Doctor Id   │         Name         │    Specialization  │");
            System.out.println("+─────────────+──────────────────────+────────────────────+");
            while (resultSet.next()){
                int id = resultSet.getInt("Id");
                String name = resultSet.getString("Name");
                String specialization = resultSet.getString("Specialization");
                System.out.printf("│%-13s│%-25s│%-17s│\n",id,name,specialization);
                System.out.println("+-------------+----------------------+--------------------+");
            }
        }
        catch (Exception e ){
            System.out.println("EXCEPTION FROM VIEW PATIENT");
        }
    }
    public boolean getDoctorById(int Id){
        String query = "SELECT * FROM Doctors WHERE Id = ?";
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
        catch (Exception e){
            System.out.println("EXCEPTION FROM GET PATIENT");
        }
        return false;
    }
}
