package org.example.DAO;

import org.example.model.OrderDetails;
import org.example.View.OrderDetailsInterface;
import org.example.valid.OrderDetailsValidator;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;




public class OrderDetailsDAO extends GenericDAO<OrderDetails> {

	private static Connection orderConnection;
	private OrderDetailsInterface orderDetailsInterface;
	private Statement orderDetailsStatement;

	public OrderDetailsDAO(Connection con, OrderDetailsInterface orderDetailsInterface) {
		this.orderConnection = con;
		this.orderDetailsInterface = orderDetailsInterface;
		try {
			this.orderDetailsStatement = con.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<OrderDetails> getOrderDetailsList() throws SQLException {
		ResultSet rs = orderDetailsStatement.executeQuery("SELECT * FROM schooldb.orderdetails");

		ArrayList<OrderDetails> orderDetailsList = new ArrayList<OrderDetails>();
		while (rs.next()) {
			OrderDetails myNewOrderDetails = new OrderDetails(rs.getInt("id"), rs.getInt("orderID"), rs.getString("deliveryCity"),
					rs.getString("deliveryStreet"));
			orderDetailsList.add(myNewOrderDetails);
		}
		return orderDetailsList;
	}

	public int[] getOrderIDs() throws SQLException {
		ResultSet rs = orderDetailsStatement.executeQuery("SELECT orderID FROM schooldb.order");
		ArrayList<Integer> orderIDs = new ArrayList<>();

		while (rs.next()) {
			int orderID = rs.getInt("orderID");
			orderIDs.add(orderID);
		}

		int[] result = new int[orderIDs.size()];
		for (int i = 0; i < orderIDs.size(); i++) {
			result[i] = orderIDs.get(i);
		}

		return result;
	}

	public int getLastID() throws SQLException {
		ResultSet rs = orderDetailsStatement.executeQuery("SELECT max(id) FROM schooldb.orderdetails");
		int lastID = 0;
		while (rs.next()) {
			lastID = rs.getInt(1);
		}
		int newID = lastID + 1;
		return newID;
	}

	public OrderDetails searchID(int id) throws SQLException {
		OrderDetails myOrderDetails = null;
		ArrayList<OrderDetails> orderDetailsList = getOrderDetailsList();
		Iterator<OrderDetails> myIterator = orderDetailsList.iterator();
		while (myIterator.hasNext()) {
			OrderDetails currentOrderDetails = myIterator.next();
			if (currentOrderDetails.getId()== id) {
				myOrderDetails = currentOrderDetails;
				break;
			}
		}
		return myOrderDetails;
	}
	
	public void displayAllOrderDetails() throws SQLException {
		ArrayList<OrderDetails> orderDetailsList = getOrderDetailsList();

		JScrollPane myScrollPane = new JScrollPane();
		myScrollPane.setBounds(250, 70, 600, 400);

		JTable orderDetailsTable = new JTable();
		orderDetailsTable = createTable(orderDetailsList);
		orderDetailsTable.setEnabled(true);
		orderDetailsTable.setVisible(true);

		myScrollPane.setViewportView(orderDetailsTable);
		orderDetailsInterface.getContentPane().add(myScrollPane);
	}
	
	public void InsertOrderDetails() throws SQLException
	{
		String odID = orderDetailsInterface.getID();
		int orderID = orderDetailsInterface.getOrderID();
		String city = orderDetailsInterface.getCity();
		String street = orderDetailsInterface.getStreet();
		int orderDetailID=0;
		boolean validID = true;
		if (odID.equals(""))
			orderDetailID = getLastID();
		else {
			try {
				orderDetailID = Integer.parseInt(odID);
			} catch (Exception e) {
				validID = false;
				JOptionPane.showMessageDialog(null, "Bad ID!");
			}
		}
		if(validID==true)
		{
			OrderDetailsValidator ODV = new OrderDetailsValidator();
			if(ODV.ValidateOrderDetais(city, street)== true)
			{
				OrderDetails myDetails = new OrderDetails(orderDetailID, orderID, city, street);
				Insert(myDetails);
			}
			else JOptionPane.showMessageDialog(null,"BAD input!");
		}
	}
	
	public void DeleteOrderDetails() {
		String odID = orderDetailsInterface.getID();
		int orderDetailsID = 0;
		boolean validID = true;
		try {
			orderDetailsID = Integer.parseInt(odID);
		} catch (Exception e) {
			validID = false;
			JOptionPane.showMessageDialog(null, "Bad ID!");
		}
		if (validID == true) {
			try {
				OrderDetails myDetails = searchID(orderDetailsID);
				if (myDetails != null)
					delete(myDetails); // GENERIC METHOD
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Error at deleting element");
			}
		}
		else JOptionPane.showMessageDialog(null, "Bad Input");
	}
	
	public void UpdateOrderDetails()  throws SQLException
	{
		String odID = orderDetailsInterface.getID();
		int orderID = orderDetailsInterface.getOrderID();
		String city = orderDetailsInterface.getCity();
		String street = orderDetailsInterface.getStreet();
		int orderDetailID=0;
		boolean validID = true;
		if (odID.equals(""))
			orderDetailID = getLastID();
		else {
			try {
				orderDetailID = Integer.parseInt(odID);
			} catch (Exception e) {
				validID = false;
				JOptionPane.showMessageDialog(null, "Bad ID!");
			}
		}
		if(validID==true)
		{
			OrderDetailsValidator ODV = new OrderDetailsValidator();
			if(ODV.ValidateOrderDetais(city, street)== true)
			{
				OrderDetails myDetails = new OrderDetails(orderDetailID, orderID, city, street);
				Update(myDetails);
			}
			else JOptionPane.showMessageDialog(null,"BAD input!");
		}
	}
}
