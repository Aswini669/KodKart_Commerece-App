package com.kodewala.kodkart.entities;

import java.time.LocalDate;

public class Orders {

	private int user_id;
	private LocalDate orderDate;
	private double totalAmount;
	
	public Orders(int user_id, LocalDate orderDate, double totalAmount) {
		super();
		this.user_id = user_id;
		this.orderDate = orderDate;
		this.totalAmount = totalAmount;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public LocalDate getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDate orderDate) {
		this.orderDate = orderDate;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	
}
