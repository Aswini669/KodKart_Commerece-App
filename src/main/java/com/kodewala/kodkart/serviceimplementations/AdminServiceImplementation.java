package com.kodewala.kodkart.serviceimplementations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Scanner;

import com.kodewala.kodkart.entities.Products;
import com.kodewala.kodkart.services.AdminService;

public class AdminServiceImplementation implements AdminService {

	Scanner sc = new Scanner(System.in);

	@Override
	public int adminHome() {
		int ch = 0;
		while (true) {
			System.out.println("\n+-------------------------------------+");
			System.out.println("|             ADMIN MENU              |");
			System.out.println("+-------------------------------------+");
			System.out.println("| 1. Add Product                      |");
			System.out.println("| 2. Update Product                   |");
			System.out.println("| 3. Delete Product                   |");
			System.out.println("| 4. View All Products                |");
			System.out.println("| 5. View All Orders                  |");
			System.out.println("| 6. Logout                           |");
			System.out.println("+-------------------------------------+");
			System.out.print("Choose Option: ");

			ch = sc.nextInt();
			if (ch > 0 && ch < 7) {
				break;
			} else {
				System.out.println("Invalid Choice");
			}
		}
		return ch;
	}

	@Override
	public void addProduct(Connection con) {
		String sql = "insert into products(name,category,price,quantity,description) values(?,?,?,?,?)";
		try {
			sc.nextLine();
			System.out.println("Enter Product Name: ");
			String name = sc.nextLine();
			System.out.println("Enter Product Category: ");
			String category = sc.nextLine();
			System.out.println("Enter Product Price: ");
			int price = sc.nextInt();
			System.out.println("Enter Product Quantity: ");
			int quantity = sc.nextInt();
			sc.nextLine();
			System.out.println("Enter Product description: ");
			String description = sc.nextLine();

			Products product = new Products(name, category, price, quantity, description);

			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, product.getName());
			pstmt.setString(2, product.getCategory());
			pstmt.setInt(3, product.getPrice());
			pstmt.setInt(4, product.getQuantity());
			pstmt.setString(5, product.getDescription());
			pstmt.executeUpdate();
			System.out.println("Product Add Successfull");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void viewProduct(Connection con) {
		String sql = "select * from products";

		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				System.out.println("\n----------------------------------------");
				System.out.println("Product ID       : " + rs.getInt(1));
				System.out.println("Product Name     : " + rs.getString(2));
				System.out.println("Category         : " + rs.getString(3));
				System.out.println("Price            : " + rs.getInt(4));
				System.out.println("Quantity         : " + rs.getInt(5));
				System.out.println("Description      : " + rs.getString(6));
				System.out.println("----------------------------------------");;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void updateProduct(Connection con) {
		String sql = "select * from products";

		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				System.out.println("\n-------------------------");
				System.out.println("      PRODUCT DETAILS     ");
				System.out.println("-------------------------");
				System.out.println("Product ID       : " + rs.getInt(1));
				System.out.println("Name             : " + rs.getString(2));
				System.out.println("Category         : " + rs.getString(3));
				System.out.println("Price            : ₹" + rs.getInt(4));
				System.out.println("Available Qty    : " + rs.getInt(5));
				System.out.println("Description      : " + rs.getString(6));
				System.out.println("-------------------------\n");
			}
			String query1 = "update products set name = ? where id = ?";
			String query2 = "update products set category = ? where id = ?";
			String query3 = "update products set price = ? where id = ?";
			String query4 = "update products set quantity = ? where id = ?";
			String query5 = "update products set description = ? where id = ?";

			System.out.println(
					"1. for update name, 2. for update category, 3. for update price,4. for update quantity,5. for update description");
			int ch = sc.nextInt();

			System.out.println("Enter Product Id to Update");
			int productId = sc.nextInt();

			sc.nextLine();
			switch (ch) {
			case 1:
				System.out.println("enter name to update");
				String name = sc.nextLine();

				pstmt = con.prepareStatement(query1);
				pstmt.setString(1, name);
				pstmt.setInt(2, productId);
				pstmt.executeUpdate();
				System.out.println("Name update successfull");
				break;
			case 2:
				System.out.println("enter category to update");
				String category = sc.nextLine();

				pstmt = con.prepareStatement(query2);
				pstmt.setString(1, category);
				pstmt.setInt(2, productId);
				pstmt.executeUpdate();
				System.out.println("Category update successfull");
				break;
			case 3:
				System.out.println("enter price to update");
				int price = sc.nextInt();

				pstmt = con.prepareStatement(query3);
				pstmt.setInt(1, price);
				pstmt.setInt(2, productId);
				pstmt.executeUpdate();
				System.out.println("Price update successfull");
				break;
			case 4:
				System.out.println("enter quantity to update");
				int quantity = sc.nextInt();

				pstmt = con.prepareStatement(query4);
				pstmt.setInt(1, quantity);
				pstmt.setInt(2, productId);
				pstmt.executeUpdate();
				System.out.println("Quantity update successfull");
				break;
			case 5:
				System.out.println("enter description to update");
				String description = sc.nextLine();

				pstmt = con.prepareStatement(query5);
				pstmt.setString(1, description);
				pstmt.setInt(2, productId);
				pstmt.executeUpdate();
				System.out.println("Description update successfull");
				break;
			default:
				System.out.println("Invalid Choice");

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteProduct(Connection con) {
		String sql = "select * from products";
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				System.out.println("Product Id: " + rs.getInt(1) + "Product Name: " + rs.getString(2)
						+ "Product Category: " + rs.getString(3) + "Product Price: " + rs.getInt(4)
						+ "Product Quantity: " + rs.getInt(5) + "Product Description: " + rs.getString(6));
			}

			String query = "delete from products where id = ?";
			System.out.println("Enter Id to delete the Product");
			int prodId = sc.nextInt();
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, prodId);
			pstmt.executeUpdate();
			System.out.println("Delete Product Successfull");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void viewAllOrders(Connection con) {
		String orderQuery = "select * from orders";
		String orderItemQuery = "select * from order_items where order_id = ?";
		String productQuery = "select * from products where id = ?";
		
		try {
			PreparedStatement orderStmt = con.prepareStatement(orderQuery);
			ResultSet orderRes = orderStmt.executeQuery();
			System.out.println("-----------------------------------------");
			System.out.println("---------All Order History----------------");
			while(orderRes.next()) {
				int orderId = orderRes.getInt(1);
				int userId = orderRes.getInt(2);
				Date date = orderRes.getDate(3);
				int orderTotal = orderRes.getInt(4);
				
				PreparedStatement itemStmt = con.prepareStatement(orderItemQuery);
				itemStmt.setInt(1, orderId);
				ResultSet itemRes = itemStmt.executeQuery();
				while(itemRes.next()) {
					int prodId = itemRes.getInt(3);
					int qty = itemRes.getInt(4);
					
					PreparedStatement prodStmt = con.prepareStatement(productQuery);
					prodStmt.setInt(1, prodId);
					ResultSet prodRes = prodStmt.executeQuery();
					
					if(prodRes.next()) {
						String prodName = prodRes.getString(2);
						int price = prodRes.getInt(4);
						
						System.out.println("\n====================== ORDER DETAILS ======================");
						System.out.println("Order ID      : " + orderId);
						System.out.println("User ID       : " + userId);
						System.out.println("Product Name  : " + prodName);
						System.out.println("Order Date    : " + date);
						System.out.println("Quantity      : " + qty);
						System.out.println("Price         : " + price);
						System.out.println("Total Amount  : " + orderTotal);
						System.out.println("-----------------------------------------------------------\n");
						
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
