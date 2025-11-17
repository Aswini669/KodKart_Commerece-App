package com.kodewala.kodkart.connections;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

	public Connection getConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try {
			return DriverManager.getConnection("jdbc:mysql://localhost:3306/kodkartnewdb", "root", "A9861@wini#");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
