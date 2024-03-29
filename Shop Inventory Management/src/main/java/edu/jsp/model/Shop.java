package edu.jsp.model;

import java.util.List;

public class Shop {
	//to want multiple copies i make attributes as non-static
	//attributes accessible only for this class encapsulation is a option for that private access specifire is used and 
	//to give access getter setter methods are created
	private int id;
	private String shopName;
	private String address;
	private String ownerName;
	private String gst;
	private long contact;
	
	private List<Product> products;
	public List<Product> getProducts() {
		return products;
	}
	public void setProducts(List<Product> products) {
		this.products = products;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String name) {
		this.shopName = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String owner) {
		this.ownerName = owner;
	}
	public String getGst() {
		return gst;
	}
	public void setGst(String gst) {
		this.gst = gst;
	}
	public long getContact() {
		return contact;
	}
	public void setContact(long contact) {
		this.contact = contact;
	}
	
	
	
}
