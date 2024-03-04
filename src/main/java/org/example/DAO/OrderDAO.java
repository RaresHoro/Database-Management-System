package org.example.DAO;

import org.example.View.OrderInterface;
import org.example.View.ProductInterface;
import org.example.model.Order;
import org.example.model.Product;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 *
 * @author Horodincaa
 *
 *	OrderDAO -> CRUD for order
 */
public class OrderDAO extends GenericDAO<Order> {

	private static Connection orderConnection;
	private OrderInterface orderInterface;
	private ProductInterface productInterface;
	private Statement orderStatement;

	public OrderDAO(Connection con, OrderInterface orderInterface, ProductInterface productInterface) {
		this.orderConnection = con;
		this.orderInterface = orderInterface;
		this.productInterface = productInterface;
		try {
			this.orderStatement = con.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Order> getOrderList() throws SQLException {
		ResultSet rs = orderStatement.executeQuery("SELECT * FROM schooldb.order");

		ArrayList<Order> ordersList = new ArrayList<Order>();
		while (rs.next()) {
			Order myNewOrder = new Order(rs.getInt("orderID"), rs.getInt("clientID"), rs.getInt("productID"),
					rs.getInt("quantity"));
			ordersList.add(myNewOrder);
		}
		return ordersList;
	}

	public int[] getClientsIDs() throws SQLException {
		ResultSet rs = orderStatement.executeQuery("SELECT clientID FROM client");
		ArrayList<Integer> clientIdList = new ArrayList<>();
		while (rs.next()) {
			clientIdList.add(rs.getInt("clientID"));
		}
		int[] result = new int[clientIdList.size()];
		for (int i = 0; i < clientIdList.size(); i++) {
			result[i] = clientIdList.get(i);
		}
		return result;
	}

	public int[] getProductsIDs() throws SQLException {
		ResultSet rs = orderStatement.executeQuery("SELECT productID FROM product");
		ArrayList<Integer> productIdList = new ArrayList<>();
		while (rs.next()) {
			productIdList.add(rs.getInt("productID"));
		}
		int[] result = new int[productIdList.size()];
		for (int i = 0; i < productIdList.size(); i++) {
			result[i] = productIdList.get(i);
		}
		return result;
	}

	public int getLastID() throws SQLException {
		ResultSet rs = orderStatement.executeQuery("SELECT max(orderID) FROM schooldb.order");
		int lastID = 0;
		while (rs.next()) {
			lastID = rs.getInt(1);
		}
		int newID = lastID + 1;
		return newID;
	}

	public Order searchID(int id) throws SQLException {
		Order myOrder = null;
		ArrayList<Order> orderList = getOrderList();
		Iterator<Order> myIterator = orderList.iterator();
		while (myIterator.hasNext()) {
			Order currentOrder = myIterator.next();
			if (currentOrder.getOrderID() == id) {
				myOrder = currentOrder;
				break;
			}
		}
		return myOrder;
	}

	public void displayAllOrders() throws SQLException {
		ArrayList<Order> orderList = getOrderList();

		JScrollPane myScrollPane = new JScrollPane();
		myScrollPane.setBounds(250, 70, 600, 400);

		JTable orderTable = new JTable();
		orderTable = createTable(orderList);
		orderTable.setEnabled(true);
		orderTable.setVisible(true);

		myScrollPane.setViewportView(orderTable);
		orderInterface.getContentPane().add(myScrollPane);
	}

	public void insertNewOrder() throws SQLException {
		int orderID = 0, quantity = 0;
		String oID = orderInterface.getIdField();
		String quan = orderInterface.getQuantity();
		try {
			quantity = Integer.parseInt(quan);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Bad QUANTITY!");
		}
		boolean validID = true;
		if (oID.equals(""))
			orderID = getLastID();
		else {
			try {
				orderID = Integer.parseInt(oID);
			} catch (Exception e) {
				validID = false;
				JOptionPane.showMessageDialog(null, "Bad ID!");
			}
		}
		if (validID == true) {
			int productID = orderInterface.getProductID();
			int clientID = orderInterface.getClientID();
			ProductDAO prDAO = new ProductDAO(orderConnection, productInterface);
			Product myProduct = prDAO.searchID(productID);
			System.out.println(myProduct.getProductID() + " " + myProduct.getStock());
			int productStock = myProduct.getStock();
			if (quantity <= productStock) {
				myProduct.setStock(productStock - quantity);
				Update(myProduct);

				Order myOrder = new Order(orderID, clientID, productID, quantity);
				Insert(myOrder);
			} else
				JOptionPane.showMessageDialog(null, "Quantity bigger than stock! -> ERROR ");
		}
	}

	public void DeleteOrder() throws SQLException {
		int orderID = 0;
		String oID = orderInterface.getIdField();
		boolean validID = true;
		try {
			orderID = Integer.parseInt(oID);
		} catch (Exception e) {
			validID = false;
			JOptionPane.showMessageDialog(null, "Bad ID!");
		}
		if (validID == true) {
			Order orderToBeDeleted = searchID(orderID);
			System.out.println(orderToBeDeleted);
			if (orderToBeDeleted != null) {
				int productID = orderToBeDeleted.getProductID();
				int quantity = orderToBeDeleted.getQuantity();
				ProductDAO prDAO = new ProductDAO(orderConnection, productInterface);
				Product myProduct = prDAO.searchID(productID);
				//System.out.println(myProduct.getProductID() + " " + myProduct.getStock());
				int productStock = myProduct.getStock();
				myProduct.setStock(productStock + quantity);
				Update(myProduct);
				delete(orderToBeDeleted);
			} else
				JOptionPane.showMessageDialog(null, "ERROR when deleting order. Order inexistent! ");
		} else
			JOptionPane.showMessageDialog(null, "ERROR when deleting order. ID inexistent! ");
	}

	public void UpdateOrder() throws SQLException {
		String oID = orderInterface.getIdField();
		String newQ = orderInterface.getQuantity();
		int quant=0;
		try {
			quant=Integer.parseInt(newQ);
		}catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Bad Quantity!");
		}
		int cID = orderInterface.getClientID();
		int pID = orderInterface.getProductID();
		int orderID = 0;
		boolean validID = true;
		try {
			orderID = Integer.parseInt(oID);
		} catch (Exception e) {
			validID = false;
			JOptionPane.showMessageDialog(null, "Bad ID!");
		}
		if (validID == true) {
			Order orderToBeUpdated = searchID(orderID);
			int productID = orderToBeUpdated.getProductID();
			int quantity = orderToBeUpdated.getQuantity();
			ProductDAO prDAO = new ProductDAO(orderConnection, productInterface);
			Product myProduct = prDAO.searchID(productID);
			int productStock = myProduct.getStock();
			myProduct.setStock(productStock + quantity-quant);
			Update(myProduct);
			Order myOrder = new Order(orderID, cID, pID, quant);
			Update(myOrder);

		} else
			JOptionPane.showMessageDialog(null, "Bad input!");
	}
}