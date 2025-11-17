package com.kodewala.kodkart.services;

import java.sql.Connection;

public interface AdminService {

	int adminHome();

	void addProduct(Connection con);

	void viewProduct(Connection con);

	void viewAllOrders(Connection con);
	
	void updateProduct(Connection con);

	void deleteProduct(Connection con);
}
