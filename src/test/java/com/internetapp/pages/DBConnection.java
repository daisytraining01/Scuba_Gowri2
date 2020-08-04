package com.internetapp.pages;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Scanner;

public class DBConnection {
	final String URL = "jdbc:mysql://localhost:3306/parabankdb?allowPublicKeyRetrieval=true&useSSL=false";
	final String user = "root";
	final String password = "Gowri@1423";

	Connection dbcon;
	Statement statement;
	ResultSet rs;
	ResultSetMetaData rsmd;
	PreparedStatement pre;
	HashMap<String, String> Result = new HashMap<String, String>();

	public HashMap<String, String> getrow(String Query) throws SQLException {
		try {
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			dbcon = DriverManager.getConnection(URL, user, password);
			statement = dbcon.createStatement();
			rs = statement.executeQuery(Query);
			rsmd = rs.getMetaData();
			int columncount = rsmd.getColumnCount();
			while (rs.next()) {
				for (int i = 1; i <= columncount; i++) {
					String header = rsmd.getColumnName(i);
					String value = rs.getString(i);
					Result.put(header, value);
				}
			}
			System.out.println(Result);
			return Result;
		} catch (Exception e) {
			e.printStackTrace();
			return Result;
		} finally {
			dbcon.close();
			rs.close();
			statement.close();
		}
	}

	public void InsertUpdateDeleteWithPrepared(String query) throws SQLException {
		try {
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			dbcon = DriverManager.getConnection(URL, user, password);
			statement = dbcon.createStatement();
			int data = statement.executeUpdate(query);
			if (data == 1) {
				System.out.println("ROW Successfully!" + data);
			} else {
				System.out.println("ROW Exisits!");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			dbcon.close();
			// rs.close();
			statement.close();
		}
	}

	public void InsertUpdateDeleteWithoutPrepared(String query) throws SQLException {
		try {
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			dbcon = DriverManager.getConnection(URL, user, password);
			pre = dbcon.prepareStatement(query);
			statement = dbcon.createStatement();

			Scanner input = new Scanner(System.in);

			
			System.out.print("Enter the CustomerFirstname:  ");
			String CustomerFirstname = input.next();
			pre.setString(1,CustomerFirstname);
			System.out.print("Enter the Customerlastname:  ");
			String Customerlastname = input.next();
			pre.setString(2,Customerlastname);
			System.out.print("Enter the Customeraddress:  ");
			String Customeraddress = input.next();
			pre.setString(3,Customeraddress);		
			System.out.print("Enter the Customercity:  ");
			String Customercity = input.next();
			pre.setString(4,Customercity);			
			System.out.print("Enter the CustomerState:  ");
			String CustomerState = input.next();
			pre.setString(5,CustomerState);		
			System.out.print("Enter the CustomerZipcode:  ");
			String CustomerZipcode = input.next();
			pre.setString(6,CustomerZipcode);	
			System.out.print("Enter the Customermobnumber:  ");
			String Customermobnumber = input.next();
			pre.setString(7,Customermobnumber);	
			System.out.print("Enter the Customerssn:  ");
			String Customerssn = input.next();
			pre.setString(8,Customerssn);			
			System.out.print("Enter the Cusomerusername:  ");
			String Cusomerusername = input.next();
			pre.setString(9,Cusomerusername);	
			System.out.print("Enter the Customerpassword:  ");
			String Customerpassword = input.next();
			pre.setString(10,Customerpassword);	
			
			
			int data = pre.executeUpdate();
			if (data == 1) {
				System.out.println("ROW Successfully!" + data);
			} else {
				System.out.println("ROW Exisits!");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			dbcon.close();
			// rs.close();
			statement.close();
		}
	}

}
