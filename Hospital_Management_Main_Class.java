package HospitalManagementSystem;

import java.sql.*;
import java.util.Scanner;

public class Hospital_Management_Driver_Class {
    private static final String url = "jdbc:mysql://localhost:3306/hospital";
    private static final String username = "root";
    private static final String password = "21802510";

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

        } catch (ClassNotFoundException e) {
            System.out.println("CLASS NOT FOUND EXCEPTION ❌");
        }
        Scanner sc = new Scanner(System.in);
        try {
            Connection con = DriverManager.getConnection(url, username, password);
            Patient patient = new Patient(con, sc);
            Doctor doctor = new Doctor(con);
            while (true) {
                System.out.println(" <----- HOSPITAL MANAGEMENT SYSTEM ----->");
                System.out.println("1. ADD PATIENTS. ");
                System.out.println("2. VIEW PATIENTS. ");
                System.out.println("3. VIEW DOCTORS. ");
                System.out.println("4. BOOK APPOINTMENT. ");
                System.out.println("5. EXIT ❌");
                System.out.println("ENTER YOUR CHOICE : ");
                int choice = sc.nextInt();
                switch (choice) {
                    case 1:
                        sc.nextLine();
                        patient.addPatient();
                        System.out.println();
                        break;
                    case 2:
                        patient.viewPatient();
                        System.out.println();
                        break;
                    case 3:
                        doctor.viewDoctors();
                        System.out.println();
                        break;
                    case 4:
                        bookAppointment(patient, doctor, con, sc);
                        System.out.println();
                        break;
                    case 5:
                        System.out.println("THANK YOU FOR USING HOSPITAL MANAGEMENT SYSTEM \uD83D\uDE0A"); //  THIS CODE SMILY EMOJI
                        return;
                    default:
                        System.out.println("ENTER A VALID CHOICE ❌");
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void bookAppointment(Patient patient, Doctor doctor, Connection connection, Scanner sc) {
        System.out.println("ENTER PATIENT ID : ");
        int patientId = sc.nextInt();
        System.out.println("ENTER DOCTOR ID : ");
        int doctorId = sc.nextInt();
        System.out.println("ENTER APPOINTMENT DATE (YYYY-MM-DD) : ");
        String appointmentDate = sc.next();

        if (patient.getPatientById(patientId) && doctor.getDoctorById(doctorId)) {
            if (checkDoctorAvaliability(doctorId, appointmentDate, connection)) {
                String appoinmentQuery = "INSERT INTO appoinment(Patient_Id, Doctor_id,Appoinment_Date) VALUES (?,?,?)";
                try {
                    PreparedStatement ps = connection.prepareStatement(appoinmentQuery);
                    ps.setInt(1, patientId);
                    ps.setInt(2, doctorId);
                    ps.setString(3, appointmentDate);
                    int rowAffected = ps.executeUpdate();
                    if (rowAffected > 0) {
                        System.out.println("APPOINTMENT BOOKED SUCCESSFULLY✅");
                    } else {
                        System.out.println("FAILED TO BOOK APPOINTMENT ❌ ");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("DOCTOR NOT AVAILABLE ON THIS DATE ❌");
            }

        }

    }
    public static boolean checkDoctorAvaliability (int DoctorId, String appoinmentDate, Connection connection){
        String query = "SELECT COUNT(*) FROM Appoinment WHERE Doctor_Id = ?  AND Appoinment_Date =?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, DoctorId);
            ps.setString(2, appoinmentDate);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                int count = rs.getInt(1);
                if (count==0){
                    return true;
                }
                else {
                    return false;
                }
            }

        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}


