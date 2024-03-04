package org.example.model;

import java.sql.Date;

public class OrderDetails {

	private int id;
	private int orderID;
	private String deliveryCity;
	private String deliveryStreet;

	public OrderDetails(int id, int orderID, String deliveryCity, String deliveryStreet) {
		this.id = id;
		this.orderID = orderID;
		this.deliveryCity = deliveryCity;
		this.deliveryStreet = deliveryStreet;
		
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getOrderID() {
		return orderID;
	}
	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}
	public String getDeliveryCity() {
		return deliveryCity;
	}
	public void setDeliveryCity(String deliveryCity) {
		this.deliveryCity = deliveryCity;
	}
	public String getDeliveryStreet() {
		return deliveryStreet;
	}
	public void setDeliveryStreet(String deliveryStreet) {
		this.deliveryStreet = deliveryStreet;
	}

	
	


}
