package edu.jsp.model;

public class Product {
	//to want multiple copies i make attributes as non-static
		//attributes accessible only for this class encapsulation is a option for that private access specifire is used and 
		//to give access getter setter methods are created
	private int id;
	private String productName;
	private double price;
	private int quantity;
	private boolean availability;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String name) {
		this.productName = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public boolean isAvailability() {
		return availability;
	}
	public void setAvailability(boolean availability) {
		this.availability = availability;
	}
	
}
