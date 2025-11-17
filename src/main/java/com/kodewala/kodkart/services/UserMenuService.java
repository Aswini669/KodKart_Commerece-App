package com.kodewala.kodkart.services;

import java.sql.Connection;

public interface UserMenuService {

	int userHome();

	void viewProducts(Connection con);

	void searchProduct(Connection con);

	void addToCart(Connection con, int userId);

	int viewCart(Connection con, int userId);

	void placeOrder(Connection con, int userId);

	void removeItemFromCart(Connection con, int userId);
	
	void fetchMyOrders(Connection con, int userId);

}
