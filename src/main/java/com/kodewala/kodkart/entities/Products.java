package com.kodewala.kodkart.entities;

public class Products {

	private String name;
	private String category;
	private int price;
	private int quantity;
	private String description;
	
	public Products(String name, String category, int price, int quantity, String description) {
		super();
		this.name = name;
		this.category = category;
		this.price = price;
		this.quantity = quantity;
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
	
}
