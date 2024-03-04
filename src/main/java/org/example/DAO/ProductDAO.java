package org.example.DAO;

import org.example.DAO.GenericDAO;
import org.example.View.ProductInterface;
import org.example.model.Product;
import org.example.valid.ProductValidator;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;



public class ProductDAO extends GenericDAO<Product> {

	private static Connection productConnection;
	private ProductInterface productInterface;
	private Statement productStatement;

	public ProductDAO(Connection con, ProductInterface productInterface) {
		this.productConnection = con;
		this.productInterface = productInterface;
		try {
			this.productStatement = con.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Product> getProductList() throws SQLException {
		ResultSet rs = productStatement.executeQuery("SELECT * FROM schooldb.product");

		ArrayList<Product> productsList = new ArrayList<Product>();
		while (rs.next()) {
			Product myNewProduct = new Product(rs.getInt("productID"), rs.getString("name"), rs.getInt("price"),
					rs.getInt("stock"));
			productsList.add(myNewProduct);
		}

		return productsList;
	}

	public int getLastID() throws SQLException {
		ResultSet rs = productStatement.executeQuery("SELECT max(productID) FROM product");
		int lastID = 0;
		while (rs.next()) {
			lastID = rs.getInt(1);
		}
		int newID = lastID + 1;
		return newID;
	}

	public Product searchID(int id) throws SQLException {
		Product myProduct = null;
		ArrayList<Product> productsList = getProductList();
		Iterator<Product> myIterator = productsList.iterator();
		while (myIterator.hasNext()) {
			Product currentProduct = myIterator.next();
			if (currentProduct.getProductID() == id) {
				myProduct = currentProduct;
				break;
			}
		}
		return myProduct;
	}

	public void displayAllProducts() throws SQLException {
		ArrayList<Product> productsList = getProductList();

		JScrollPane myScrollPane = new JScrollPane();
		myScrollPane.setBounds(250, 70, 600, 400);

		JTable productTable = new JTable();
		productTable = createTable(productsList);
		productTable.setEnabled(true);
		productTable.setVisible(true);

		myScrollPane.setViewportView(productTable);
		productInterface.getContentPane().add(myScrollPane);
	}

	public void insertNewProduct() throws SQLException {
		String pID = productInterface.getIdField();
		boolean validID = true;
		int productID = 0;
		if (pID.equals(""))
			productID = getLastID();
		else {
			try {
				productID = Integer.parseInt(pID);
			} catch (Exception e) {
				validID = false;
				JOptionPane.showMessageDialog(null, "Bad ID!");
			}
		}
		if (validID == true) {
			ProductValidator PV = new ProductValidator(productInterface);

			if (PV.ValidateProduct() == true) {
				Product myNewProduct = new Product(productID, productInterface.getNameField(),
						Integer.parseInt(productInterface.getPriceField()),
						Integer.parseInt(productInterface.getStockField()));
				Insert(myNewProduct); // GENERIC METHOD
			} else
				JOptionPane.showMessageDialog(null, "Bad input!");
		}
	}

	public void DeleteProduct() {
		String pID = productInterface.getIdField();
		int productID = 0;
		boolean validID = true;
		try {
			productID = Integer.parseInt(pID);
		} catch (Exception e) {
			validID = false;
			JOptionPane.showMessageDialog(null, "Bad ID!");
		}
		if (validID == true) {
			try {
				Product myNewProduct = searchID(productID);
				if (myNewProduct != null)
					delete(myNewProduct); // GENERIC METHOD
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Error at deleting element");
			}
		}
	}

	public void UpdateProduct() {
		String pID = productInterface.getIdField();
		int productID = 0;
		boolean validID = true;
		try {
			productID = Integer.parseInt(pID);
		} catch (Exception e) {
			validID = false;
			JOptionPane.showMessageDialog(null, "Bad ID!");
		}

		if (validID == true) {
			ProductValidator PV = new ProductValidator(productInterface);

			if (PV.ValidateProduct() == true) {
				Product myNewProduct = new Product(productID, productInterface.getNameField(),
						Integer.parseInt(productInterface.getPriceField()),
						Integer.parseInt(productInterface.getStockField()));
				Update(myNewProduct); // GENERIC METHOD
			}
		} else
			JOptionPane.showMessageDialog(null, "Bad input!");
	}
}
