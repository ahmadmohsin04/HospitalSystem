import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
public class Main {
  public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        while (true) {
            System.out.println("\n=== Hospital Management System ===");
            System.out.println("1. Add Patient");
            System.out.println("2. Update Patient");
            System.out.println("3. Delete Patient");
            System.out.println("4. Search Patient by ID");
            System.out.println("5. Show All Patients");
            System.out.println("6. Add Doctor");
            System.out.println("7. Update Doctor");
            System.out.println("8. Delete Doctor");
            System.out.println("9. Search Doctor by Name");
            System.out.println("10. Show All Doctors");
            System.out.println("11. Add Appointment");
            System.out.println("12. Delete Appointment");
            System.out.println("13. Add Bill");
            System.out.println("14. Show All Bills");
            System.out.println("0. Exit");

            System.out.print("\nEnter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addPatient();
                    break;
                case 2:
                    updatePatient();
                    break;
                case 3:
                    deletePatient();
                    break;
                case 4:
                    searchPatientById();
                    break;
                case 5:
                    showAllPatients();
                    break;
                case 6:
                    addDoctor();
                    break;
                case 7:
                    updateDoctor();
                    break;
                case 8:
                    deleteDoctor();
                    break;
                case 9:
                    searchDoctorByName();
                    break;
                case 10:
                    showAllDoctors();
                    break;
                case 11:
                    addAppointment();
                    break;
                case 12:
                    deleteAppointment();
                    break;
                case 13:
                    addBill();
                    break;
                case 14:
                    showAllBills();
                    break;
                case 0:
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    public static void addPatient() {
        try (Connection conn = DBConnection.getConnection();
             CallableStatement stmt = conn.prepareCall("{call AddPatient(?, ?, ?, ?)}")) {
             
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter Patient Name: ");
            String name = scanner.nextLine();
            System.out.print("Enter Age: ");
            int age = scanner.nextInt();
            scanner.nextLine();  
            System.out.print("Enter Disease: ");
            String disease = scanner.nextLine();
            System.out.print("Enter Phone Number: ");
            String phone = scanner.nextLine();

            stmt.setString(1, name);
            stmt.setInt(2, age);
            stmt.setString(3, disease);
            stmt.setString(4, phone);

   stmt.execute();
            System.out.println("Patient added successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updatePatient() {
        try (Connection conn = DBConnection.getConnection();
             CallableStatement stmt = conn.prepareCall("{call UpdatePatient(?, ?, ?, ?, ?)}")) {

            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter Patient ID: ");
            int p_id = scanner.nextInt();
            scanner.nextLine();  
            System.out.print("Enter New Name: ");
            String name = scanner.nextLine();
            System.out.print("Enter New Age: ");
            int age = scanner.nextInt();
            scanner.nextLine();  
            System.out.print("Enter New Disease: ");
            String disease = scanner.nextLine();
            System.out.print("Enter New Phone Number: ");
            String phone = scanner.nextLine();

            stmt.setInt(1, p_id);
            stmt.setString(2, name);
            stmt.setInt(3, age);
            stmt.setString(4, disease);
            stmt.setString(5, phone);

            stmt.executeUpdate();
            System.out.println("Patient updated successfully!");

        } catch (SQLException e) {
  e.printStackTrace();
        }
    }

    public static void deletePatient() {
        try (Connection conn = DBConnection.getConnection();
             CallableStatement stmt = conn.prepareCall("{call DeletePatient(?)}")) {

            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter Patient ID to delete: ");
            int p_id = scanner.nextInt();

            stmt.setInt(1, p_id);
            stmt.executeUpdate();
            System.out.println("Patient deleted successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void searchPatientById() {
        try (Connection conn = DBConnection.getConnection();
             CallableStatement stmt = conn.prepareCall("{call SearchPatientByID(?)}")) {

            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter Patient ID to search: ");
            int p_id = scanner.nextInt();

            stmt.setInt(1, p_id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                System.out.println("Patient ID: " + rs.getInt("p_id"));
                System.out.println("Name: " + rs.getString("name"));
     System.out.println("Age: " + rs.getInt("age"));
                System.out.println("Disease: " + rs.getString("disease"));
                System.out.println("Phone: " + rs.getString("ph_no"));
            } else {
                System.out.println("No patient found with ID: " + p_id);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void showAllPatients() {
        try (Connection conn = DBConnection.getConnection();
             CallableStatement stmt = conn.prepareCall("{call ShowAllPatients()}")) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                System.out.println("Patient ID: " + rs.getInt("p_id"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Age: " + rs.getInt("age"));
                System.out.println("Disease: " + rs.getString("disease"));
                System.out.println("Phone: " + rs.getString("ph_no"));
                System.out.println("---------------------------");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void doctorWisePatientReport() {
        try (Connection conn = DBConnection.getConnection();
             CallableStatement stmt = conn.prepareCall("{call DoctorWisePatientReport(?)}")) {

            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter Doctor ID: ");
            int doc_id = scanner.nextInt();

            stmt.setInt(1, doc_id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                System.out.println("Doctor Name: " + rs.getString("doctor_name"));
                System.out.println("Total Patients Checked: " + rs.getInt("total_patients"));
            } else {
                System.out.println("No records found for Doctor ID: " + doc_id);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void dateWiseBillRecord() {
        try (Connection conn = DBConnection.getConnection();
             CallableStatement stmt = conn.prepareCall("{call DateWiseBillRecord(?)}")) {

            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter Date (YYYY-MM-DD): ");
            String bill_date = scanner.next();

            stmt.setString(1, bill_date);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                System.out.println("Bill ID: " + rs.getInt("bill_id"));
                System.out.println("Date: " + rs.getString("bill_datetime"));
    System.out.println("Amount: " + rs.getDouble("payable_amount"));
                System.out.println("Payer: " + rs.getString("payer_name"));
                System.out.println("---------------------------");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void patientWiseBillRecord() {
        try (Connection conn = DBConnection.getConnection();
             CallableStatement stmt = conn.prepareCall("{call PatientWiseBillRecord(?)}")) {

            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter Patient Name: ");
            String patient_name = scanner.next();

            stmt.setString(1, patient_name);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                System.out.println("Patient Name: " + rs.getString("patient_name"));
                System.out.println("Date: " + rs.getString("bill_datetime"));
                System.out.println("Amount: " + rs.getDouble("payable_amount"));
                System.out.println("---------------------------");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void addDoctor() {
        try (Connection conn = DBConnection.getConnection();
             CallableStatement stmt = conn.prepareCall("{call AddDoctor(?, ?, ?, ?, ?)}")) {

            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter Doctor Name: ");
            String name = scanner.nextLine();
            System.out.print("Enter Qualification: ");
            String qualification = scanner.nextLine();
            System.out.print("Enter Designation: ");
            String designation = scanner.nextLine();
            System.out.print("Enter Salary: ");
            double salary = scanner.nextDouble();
            scanner.nextLine();  // consume newline
            System.out.print("Enter Department: ");
            String department = scanner.nextLine();

            stmt.setString(1, name);
            stmt.setString(2, qualification);
            stmt.setString(3, designation);
            stmt.setDouble(4, salary);
            stmt.setString(5, department);

            stmt.execute();
            System.out.println("Doctor added successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateDoctor() {
        try (Connection conn = DBConnection.getConnection();
             CallableStatement stmt = conn.prepareCall("{call UpdateDoctor(?, ?, ?, ?, ?, ?)}")) {

            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter Doctor ID: ");
            int doc_id = scanner.nextInt();
            scanner.nextLine();  // consume newline
            System.out.print("Enter New Name: ");
            String name = scanner.nextLine();
            System.out.print("Enter New Qualification: ");
            String qualification = scanner.nextLine();
            System.out.print("Enter New Designation: ");
            String designation = scanner.nextLine();
            System.out.print("Enter New Salary: ");
            double salary = scanner.nextDouble();
            scanner.nextLine();  // consume newline
            System.out.print("Enter New Department: ");
            String department = scanner.nextLine();

            stmt.setInt(1, doc_id);
            stmt.setString(2, name);
            stmt.setString(3, qualification);
            stmt.setString(4, designation);
            stmt.setDouble(5, salary);
            stmt.setString(6, department);

            stmt.executeUpdate();
            System.out.println("Doctor updated successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteDoctor() {
        try (Connection conn = DBConnection.getConnection();
             CallableStatement stmt = conn.prepareCall("{call DeleteDoctor(?)}")) {

            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter Doctor ID to delete: ");
            int doc_id = scanner.nextInt();

            stmt.setInt(1, doc_id);
            stmt.executeUpdate();
            System.out.println("Doctor deleted successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void searchDoctorByName() {
        try (Connection conn = DBConnection.getConnection();
             CallableStatement stmt = conn.prepareCall("{call SearchDoctorByName(?)}")) {

            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter Doctor Name to search: ");
            String name = scanner.nextLine();

            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                System.out.println("Doctor ID: " + rs.getInt("doc_id"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Qualification: " + rs.getString("qualification"));
                System.out.println("Designation: " + rs.getString("designation"));
                System.out.println("Salary: " + rs.getDouble("salary"));
                System.out.println("Department: " + rs.getString("department"));
                System.out.println("---------------------------");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void showAllDoctors() {
        try (Connection conn = DBConnection.getConnection();
             CallableStatement stmt = conn.prepareCall("{call ShowAllDoctors()}")) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                System.out.println("Doctor ID: " + rs.getInt("doc_id"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Qualification: " + rs.getString("qualification"));
                System.out.println("Designation: " + rs.getString("designation"));
                System.out.println("Salary: " + rs.getDouble("salary"));
                System.out.println("Department: " + rs.getString("department"));
                System.out.println("---------------------------");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

 
    public static void addAppointment() {
        try (Connection conn = DBConnection.getConnection();
             CallableStatement stmt = conn.prepareCall("{call AddAppointment(?, ?, ?, ?)}")) {

            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter Appointment Datetime (YYYY-MM-DD HH:MM:SS): ");
            String datetime = scanner.nextLine();
            System.out.print("Enter Patient ID: ");
            int patient_id = scanner.nextInt();
            System.out.print("Enter Doctor ID: ");
            int doc_id = scanner.nextInt();
            scanner.nextLine();  // consume newline
            System.out.print("Enter Appointment Type: ");
            String type = scanner.nextLine();

            stmt.setString(1, datetime);
            stmt.setInt(2, patient_id);
            stmt.setInt(3, doc_id);
            stmt.setString(4, type);

            stmt.execute();
            System.out.println("Appointment added successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteAppointment() {
        try (Connection conn = DBConnection.getConnection();
             CallableStatement stmt = conn.prepareCall("{call DeleteAppointment(?)}")) {

            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter Appointment ID to delete: ");
            int appoint_id = scanner.nextInt();

            stmt.setInt(1, appoint_id);
            stmt.executeUpdate();
            System.out.println("Appointment deleted successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addBill() {
        try (Connection conn = DBConnection.getConnection();
             CallableStatement stmt = conn.prepareCall("{call AddBill(?, ?, ?, ?)}")) {

            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter Bill Datetime (YYYY-MM-DD HH:MM:SS): ");
            String datetime = scanner.nextLine();
            System.out.print("Enter Payable Amount: ");
            double amount = scanner.nextDouble();
            scanner.nextLine();  // consume newline
            System.out.print("Enter Payer Name: ");
            String payer = scanner.nextLine();
            System.out.print("Enter Appointment ID: ");
            int appoint_id = scanner.nextInt();

            stmt.setString(1, datetime);
            stmt.setDouble(2, amount);
            stmt.setString(3, payer);
            stmt.setInt(4, appoint_id);

            stmt.execute();
            System.out.println("Bill added successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void showAllBills() {
        try (Connection conn = DBConnection.getConnection();
             CallableStatement stmt = conn.prepareCall("{call ShowBill()}")) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                System.out.println("Bill ID: " + rs.getInt("bill_id"));
                System.out.println("Date: " + rs.getString("bill_datetime"));
                System.out.println("Amount: " + rs.getDouble("payable_amount"));
                System.out.println("Payer: " + rs.getString("payer_name"));
                System.out.println("Appointment ID: " + rs.getInt("appoint_id"));
                System.out.println("---------------------------");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

