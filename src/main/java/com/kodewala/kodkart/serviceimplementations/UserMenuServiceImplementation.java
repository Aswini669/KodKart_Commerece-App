package com.kodewala.kodkart.serviceimplementations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.kodewala.kodkart.entities.Cart;
import com.kodewala.kodkart.entities.OrderItems;
import com.kodewala.kodkart.entities.Orders;
import com.kodewala.kodkart.services.UserMenuService;


public class UserMenuServiceImplementation implements UserMenuService {

	Scanner sc = new Scanner(System.in);

	@Override
	public int userHome() {
		int ch = 0;
		while (true) {
			System.out.println("\n==================== USER MENU ====================");
			System.out.println("1. View Products");
			System.out.println("2. Search Product");
			System.out.println("3. Add to Cart");
			System.out.println("4. View Cart");
			System.out.println("5. View Order History");
			System.out.println("6. Logout");
			System.out.println("====================================================");
			System.out.print("Choose Option: ");

			ch = sc.nextInt();
			if (ch > 0 && ch < 8) {
				break;
			} else {
				System.out.println("Invalid Choice");
			}
		}
		return ch;
	}

	@Override
	public void viewProducts(Connection con) {
		String sql = "select * from products";

		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				System.out.println("\n---------------------- PRODUCT DETAILS ----------------------");
				System.out.println("Product ID       : " + rs.getInt(1));
				System.out.println("Product Name     : " + rs.getString(2));
				System.out.println("Category         : " + rs.getString(3));
				System.out.println("Price            : " + rs.getInt(4));
				System.out.println("Quantity         : " + rs.getInt(5));
				System.out.println("Description      : " + rs.getString(6));
				System.out.println("--------------------------------------------------------------\n");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void searchProduct(Connection con) {
		String query = "select * from products";

		System.out.println("Enter search name/category: ");
		sc.nextLine();
		String keyWord = sc.nextLine().toLowerCase();
		try {
			PreparedStatement pstmt = con.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				String name = rs.getString(2).toLowerCase();
				String category = rs.getString(3).toLowerCase();
				if (name.contains(keyWord) || category.contains(keyWord)) {
					System.out.println("\n---------------------- PRODUCT ----------------------");
					System.out.println("ID           : " + rs.getInt(1));
					System.out.println("Name         : " + rs.getString(2));
					System.out.println("Category     : " + rs.getString(3));
					System.out.println("Price        : ₹" + rs.getInt(4));
					System.out.println("Quantity     : " + rs.getInt(5));
					System.out.println("Description  : " + rs.getString(6));
					System.out.println("------------------------------------------------------\n");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void addToCart(Connection con,int userId) {
		
		System.out.println("Enter Product Id to add in Cart: ");
		int prodId = sc.nextInt();
		System.out.println("Enter the Quantity to add in Cart: ");
		int qty = sc.nextInt();
		
		String prodQuery = "select id from products where id = ?";
		
		try {
			PreparedStatement pstmt = con.prepareStatement(prodQuery);
			pstmt.setInt(1, prodId);
			ResultSet rs = pstmt.executeQuery();
			
			if(!rs.next()) {
				System.out.println("Oncorrect product id ");
			}else {
				Cart cart = new Cart(userId, prodId, qty);
				String sql = "insert into cart (user_id, product_id, quantity) values (?, ?, ?)";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, cart.getUser_id());
				pstmt.setInt(2, cart.getProduct_id());
				pstmt.setInt(3, cart.getQuantity());
				pstmt.executeUpdate();
				System.out.println("Cart Add Successfull");
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int viewCart(Connection con,int userId) {
		String cartQuery = "select * from cart where user_id = ?";
		String prodQuery = "select * from products where id = ?";
		
		try {
			PreparedStatement pstmt = con.prepareStatement(cartQuery);
			pstmt.setInt(1, userId);
			ResultSet cartResult = pstmt.executeQuery();
			int totalPrice = 0;
			int price = 0;
			int totalQuantity = 0;
			System.out.println("---------------------------");
			System.out.println("------ My Cart Items ------");
			
			if(!cartResult.isBeforeFirst()) {
				System.out.println("No record found");
			}else {
				while(cartResult.next()) {
					int prodId = cartResult.getInt(3);
					pstmt = con.prepareStatement(prodQuery);
					pstmt.setInt(1, prodId);
					ResultSet rs = pstmt.executeQuery();
					while(rs.next()) {
						System.out.println("\n----------------------------------------");
						System.out.println("             CART ITEM");
						System.out.println("----------------------------------------");
						System.out.println("Cart ID        : " + cartResult.getInt(1));
						System.out.println("Product Name   : " + rs.getString(2));
						System.out.println("Category       : " + rs.getString(3));
						System.out.println("Quantity       : " + cartResult.getInt(4));
						System.out.println("Price (Each)   : ₹" + rs.getInt(4));
						System.out.println("----------------------------------------\n");
						price = rs.getInt(4);
						totalQuantity = cartResult.getInt(4);
						totalPrice += (price * totalQuantity);
					}
				}
				System.out.println("--------------------------------");
				System.out.println("Total price: " + totalPrice);
				while(true) {
					System.out.println("\n╔════════════ CART MENU ════════════╗");
					System.out.println("║ 1. Place Order                    ║");
					System.out.println("║ 2. Remove Item From Cart          ║");
					System.out.println("║ 3. Back to Main Menu              ║");
					System.out.println("╚═══════════════════════════════════╝");
					System.out.print("➡ Choose Option: ");
					int ch = sc.nextInt();
					if(ch > 0 && ch < 4) {
						return ch;
					}else {
						System.out.println("Invalid Choice");
					}
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 3;
	}

	@Override
	public void placeOrder(Connection con, int userId) {
		String query = "select * from cart where user_id = ?";
		String prodQuery = "select * from products where id = ?";
		String orderQuery = "insert into orders(user_id,order_date,total_amount) values (?, ?, ?)";
		String updateQtyQuery = "update products set quantity = quantity - ? where id = ?";
		
		try {
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setInt(1, userId);
			ResultSet cartResult = pstmt.executeQuery();
			int totalPrice = 0;
			int price = 0;
			int totalQty = 0;
			
			List<OrderItems> itemList = new ArrayList<OrderItems>();
			System.out.println("----------------------------------");
			System.out.println("---------- Order Items ------------");
			while(cartResult.next()) {
				int prodId = cartResult.getInt(3);
				pstmt = con.prepareStatement(prodQuery);
				pstmt.setInt(1, prodId);
				ResultSet rs = pstmt.executeQuery();
				while(rs.next()) {
					System.out.println("Cart ID: " + cartResult.getInt(1) + "Product Name: " + rs.getString(2)
					+ "Product Category: " + rs.getString(3) + "Quantity: " + cartResult.getInt(4));
					price = rs.getInt(4);
					totalQty = cartResult.getInt(4);
					totalPrice += (price * totalQty);
					itemList.add(new OrderItems(0, rs.getInt(1), cartResult.getInt(4), totalPrice));
				}
			}
			System.out.println("-----------------------------------------");
			System.out.println("Total price: " + totalPrice);
			
			Orders orders = new Orders(userId, LocalDate.now(), totalPrice);
			pstmt = con.prepareStatement(orderQuery, Statement.RETURN_GENERATED_KEYS); // return the id of the order
			pstmt.setInt(1, orders.getUser_id());
			pstmt.setDate(2, java.sql.Date.valueOf(orders.getOrderDate()));
			pstmt.setDouble(3, orders.getTotalAmount());
			pstmt.executeUpdate();
			
			System.out.println("Order Placed Successfull....");
			
			ResultSet orderKeys = pstmt.getGeneratedKeys();
			int orderId = 0;
			while(orderKeys.next()) {
				orderId = orderKeys.getInt(1);
			}
			
			//updating the order items
			String orderItemQuery = "insert into order_items(order_id,product_id,quantity,price) values(?,?,?,?)";
			for(OrderItems items : itemList) {
				OrderItems orderItems = new OrderItems(orderId, items.getProduct_id(), items.getQuantity(), items.getPrice());
				pstmt = con.prepareStatement(orderItemQuery);
				pstmt.setInt(1, orderItems.getOrder_id());
				pstmt.setInt(2, orderItems.getProduct_id());
				pstmt.setInt(3, orderItems.getQuantity());
				pstmt.setInt(4, orderItems.getPrice());
				pstmt.executeUpdate();
				
				//updating the product qty
				pstmt = con.prepareStatement(updateQtyQuery);
				pstmt.setInt(1, items.getQuantity());
				pstmt.setInt(2, items.getProduct_id());
				pstmt.executeUpdate();
			}
			
			//delete items from cart once placed
			String delteQuery = "delete from cart where user_id = ?";
			pstmt = con.prepareStatement(delteQuery);
			pstmt.setInt(1, userId);
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void removeItemFromCart(Connection con, int userId) {
		String query = "delete from cart where id = ?";
		System.out.println("Enter cart id to remove product from cart: ");
		int cartId = sc.nextInt();
		while(true) {
			try {
				PreparedStatement pstmt = con.prepareStatement(query);
				pstmt.setInt(1, cartId);
				int count = pstmt.executeUpdate();
				if(count > 0) {
					System.out.println("Item removed from the cart...");
				}else {
					System.out.println("Invalid cart id. Try Again!");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void fetchMyOrders(Connection con, int userId) {
		String orderQuery = "select * from orders where user_id = ?";
		String orderItemQuery = "select * from order_items where order_id = ?";
		String productQuery = "select * from products where id = ?";
		
		try {
			PreparedStatement orderStmt = con.prepareStatement(orderQuery);
			orderStmt.setInt(1, userId);
			ResultSet orderRes = orderStmt.executeQuery();
			System.out.println("-------------------------");
			System.out.println("--------My Orders---------");
			while(orderRes.next()) {
				int orderId = orderRes.getInt(1);
				
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
						
						System.out.println("Order ID: " + orderId + "Product ID: " + prodId + "Product Name: " + prodName 
								+ "Quantity: " + qty + "Price: " + price + "Total: " + (qty * price));
						
					}
				}
			}
			System.out.println("<---------------------------------------------****------------------------------------------>");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

}
