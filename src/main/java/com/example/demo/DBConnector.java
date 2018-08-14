package com.example.demo;
import java.sql.*; 
public class DBConnector {
	
	public static void main (String args []) {
		try {
			Connection myConn =DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?useSSL=false","root","Tatafifi1997");			
			Statement myStmt = myConn.createStatement();
			
			ResultSet myRs = myStmt.executeQuery("select * from Owner");
			
			while(myRs.next()) {
				System.out.println(myRs.getString("username")+", "+myRs.getString("Name")+","+myRs.getString("Email"));
			}
		}
		catch(Exception exc) {
			exc.printStackTrace();
		}
	}

}