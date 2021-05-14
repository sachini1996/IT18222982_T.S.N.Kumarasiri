package com;

import java.sql.*;

public class Customer {
	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/test", "root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public String readCustomers() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<table border='1'><tr><th>Customer Name</th><th>Customer Address</th><th>Customer Credit Card ID</th>"
					+ "<th>Customer Country</th><th>Update</th><th>Remove</th></tr>";
			String query = "select * from customers";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()) {
				String CID = Integer.toString(rs.getInt("CID"));
				String Name = rs.getString("Name");
				String Address = rs.getString("Address");
				String CreditCardID = Integer.toString(rs.getInt("CreditCardID"));
				String Country = rs.getString("Country");
				// Add into the html table
				output += "<tr><td><input id='hidCIDUpdate'name='hidCIDUpdate'type='hidden' value='" + CID + "'>" + Name
						+ "</td>";
				output += "<td>" + Address + "</td>";
				output += "<td>" + CreditCardID + "</td>";
				output += "<td>" + Country + "</td>";
				// buttons
				output += "<td><input name='btnUpdate'type='button' value='Update'class='btnUpdate btn btn-secondary'></td>"
						+ "<td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-Cid='"
						+ CID + "'>" + "</td></tr>";
			}
			con.close();
			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the customers.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String insertCustomer(String name, String address, String creditcardid, String country) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			// create a prepared statement
			String query = " insert into customers(CID, Name, Address, CreditCardID, Country)"
					+ " values (?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, name);
			preparedStmt.setString(3, address);
			preparedStmt.setInt(4, Integer.parseInt(creditcardid));
			preparedStmt.setString(5, country);
			// execute the statement
			preparedStmt.execute();
			con.close();
			String newCustomers = readCustomers();
			output = "{\"status\":\"success\", \"data\": \"" + newCustomers + "\"}";
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\":\"Error while inserting the customer.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String updateCustomer(String CID, String name, String address, String creditcardid, String country) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for updating.";
			}
			// create a prepared statement
			String query = "UPDATE customers SET Name=?,Address=?,CreditCardID=?,Country=? WHERE CID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setString(1, name);
			preparedStmt.setString(2, address);
			preparedStmt.setInt(3, Integer.parseInt(creditcardid));
			preparedStmt.setString(4, country);
			preparedStmt.setInt(5, Integer.parseInt(CID));
//execute the statement
			preparedStmt.execute();
			con.close();
			String newCustomers = readCustomers();
			output = "{\"status\":\"success\", \"data\": \"" + newCustomers + "\"}";
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\":\"Error while updating the customer.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String deleteCustomer(String CID) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}
			// create a prepared statement
			String query = "delete from customers where CID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(CID));
			// execute the statement
			preparedStmt.execute();
			con.close();
			String newCustomers = readCustomers();
			output = "{\"status\":\"success\", \"data\": \"" + newCustomers + "\"}";
		} 
		catch (Exception e) 
		 { 
			 output = "{\"status\":\"error\", \"data\":\"Error while deleting the customer.\"}"; 
			 System.err.println(e.getMessage()); 
			 }
		return output;
	}
}
