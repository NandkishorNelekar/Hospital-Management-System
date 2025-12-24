package hospital_management_system;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class hospital_application {
	static Scanner sc = new Scanner(System.in);

	void patient_registration() throws ClassNotFoundException, SQLException {
		Connection con = DB.getConnection();
		
		PreparedStatement ps = con.prepareStatement("insert into patient values(?,?,?,?,?,?)");
		
		System.out.println("---- New Registration ------");
		
		System.out.print("Enter the patient name : ");
		String name = sc.nextLine();

		System.out.print("Enter the patient mobile number(10 digit) : ");
		Long contact = sc.nextLong();
		sc.nextLine();
		
		System.out.print("Enter the patient address : ");
		String address = sc.nextLine();
		
		System.out.print("Enter the patient disease : ");
		String disease = sc.nextLine();
		
		ps.setString(1, name);
		ps.setLong(2, contact);
		ps.setString(3, address);
		ps.setString(4, disease);
		ps.setString(5, null);
		ps.setLong(6, 0);
		
		ps.executeUpdate();
		
		System.out.println("Patient Details Register successfully!!!");

		DB.closeConnection(con);
	}
	
	void admitPatient() throws ClassNotFoundException, SQLException {
		Connection con = DB.getConnection();
		
		System.out.println("---- Admit Patient (Assign Ward/Bed) ----");
		
		System.out.print("Enter the patient name : ");
		String name = sc.nextLine();
		
		System.out.print("Enter Ward/Bed Number : ");
		String wardBed = sc.nextLine();

		PreparedStatement ps = con.prepareStatement("update Patient set ward_bed_number = ? where name = ?;");
		
		ps.setString(1, wardBed);
		ps.setString(2, name);
		
		int rowsUpdate = ps.executeUpdate();
		if(rowsUpdate > 0)
			System.out.println("Patient name " + name + " admitted to Ward/Bed number" + wardBed + " successfully!");
		else
			System.out.println("No patient found with name " + name);
		
		DB.closeConnection(con);
	}
	
	void dischargePatient() throws ClassNotFoundException,SQLException {
		Connection con = DB.getConnection();

		System.out.println("---- Discharge Patient ----");
		
		System.out.print("Enter the patient name : ");
		String name = sc.nextLine();
		
		System.out.print("Enter the ward/bed number : ");
		String wardbed = sc.nextLine();
		
		System.out.print("Enter final bill amount: ");
		long finalBill = sc.nextLong();
		sc.nextLine();

		PreparedStatement ps = con.prepareStatement("update Patient set final_bill = ? where name = ? and ward_bed_number=?");
		
		ps.setLong(1, finalBill);
		ps.setString(2, name);
		ps.setString(3, wardbed);
		
		int rowsUpdate = ps.executeUpdate();
		if(rowsUpdate > 0) {
			System.out.println("Patient name " + name + " discharged successfully! Final bill: " + finalBill+" ₹");
		} else {
			System.out.println("No patient found with name " + name);
		}
		
		DB.closeConnection(con);

	}
	
	void checkPatientDetails() throws ClassNotFoundException, SQLException {
		Connection con = DB.getConnection();
		
		System.out.println("---- Check Patient Details ----");
		
		System.out.print("enter the patient name : ");
		String name = sc.nextLine();
		
		String show = "select * from Patient where name = ?;";
		
		PreparedStatement ps = con.prepareStatement(show);
		
		ps.setString(1, name);
		ResultSet rs = ps.executeQuery();
		
		if(rs.next()) {
			System.out.println("\n--- PATIENT RECORD ---");
			System.out.println("Name: " + rs.getString("name"));
			System.out.println("Contact: " + rs.getString("contact"));
			System.out.println("Address: " + rs.getString("address"));
			System.out.println("Disease: " + rs.getString("disease"));
			System.out.println("Ward/Bed: " + rs.getString("ward_bed_number")); 
			System.out.println("Final Bill: " + rs.getLong("final_bill")+" ₹"); 
			
		} else {
			System.out.println("No patient record found with name " + name + ".");
		}
		
		DB.closeConnection(con);
	}
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		hospital_application h = new hospital_application();
		while(true) {
			System.out.println("\n-- Welcome to the Hospital Management System --");
			System.out.println("----- Choose an option between (1-5) :  ------------");
			System.out.println("1. New Patient Registration");
			System.out.println("2. Admit Patient (Assign Ward/Bed number)");
			System.out.println("3. Discharge Patient");
			System.out.println("4. Check Patient Details");
			System.out.println("5. Exit");
			
			System.out.print("Choice is: ");
			int ch = sc.nextInt();
			sc.nextLine();
			    
    		switch(ch) {
    			case 1:
    				h.patient_registration();
    				break; 
    			case 2:
    				h.admitPatient();
    				break; 
    			case 3:
    				h.dischargePatient();
    				break; 
    			case 4:
    				h.checkPatientDetails();
    				break; 
    			case 5: 
    				System.out.println("Thank you for using the Hospital Management System!");
    				sc.close();
    				System.exit(0);
    				break;
    			default:
    				System.out.println("Please enter a correct choice between (1-5)!");
    			}
			}
		}
	}
