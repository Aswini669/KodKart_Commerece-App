package com.kodewala.kodkart.entities;

public class OrderItems {

	private int order_id;
	private int product_id;
	private int quantity;
	private int price;
	
	public OrderItems(int order_id, int product_id, int quantity, int price) {
		super();
		this.order_id = order_id;
		this.product_id = product_id;
		this.quantity = quantity;
		this.price = price;
	}
	
	public int getOrder_id() {
		return order_id;
	}
	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}
	public int getProduct_id() {
		return product_id;
	}
	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	
	
}
