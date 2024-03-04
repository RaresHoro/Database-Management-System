package org.example.View;

import org.example.DAO.ClientDAO;
import org.example.DAO.OrderDAO;
import org.example.DAO.ProductDAO;
import org.example.model.Client;
import org.example.model.Order;
import org.example.model.Product;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;



public class OrderInterface extends JFrame {

	private JButton backButton;
	private JButton refreshComboBoxes;
	private JButton showAllButton;
	private JButton insertButton;
	private JButton deleteButton;
	private JButton updateButton;

	private JButton GenerateBill;

	private JLabel idLabel;
	private JTextField idField;

	private JLabel idClientLabel;
	// private JTextField idClientField;

	private JLabel idProductLabel;
	// private JTextField idProductField;

	private JLabel quantityLabel;
	private JTextField quantityField;

	private JComboBox<Integer> myComboBox;
	private JComboBox<Integer> myComboBox2;

	public OrderInterface(UserInterface parentInterface, Connection con, ProductInterface productInterface,
						  ClientInterface clientInterface) {
		OrderInterface oInt = this;
		OrderDAO orderDAO = new OrderDAO(con, oInt, productInterface);

		this.setTitle("Orders");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setBounds(500, 150, 900, 700);
		this.getContentPane().setLayout(null);

		// use a bigger font
		Font biggerFont = new Font("Times New Roman", Font.PLAIN, 18);
		Font hugeFont = new Font("Times New Roman", Font.PLAIN, 32);

		idLabel = new JLabel("ID : ");
		idLabel.setFont(biggerFont);
		idLabel.setBounds(20, 30, 100, 30);
		getContentPane().add(idLabel);

		idField = new JTextField();
		idField.setBounds(80, 30, 75, 30);
		getContentPane().add(idField);

		idClientLabel = new JLabel("Client ID : ");
		idClientLabel.setFont(biggerFont);
		idClientLabel.setBounds(20, 70, 100, 30);
		getContentPane().add(idClientLabel);

		idProductLabel = new JLabel("Product ID : ");
		idProductLabel.setFont(biggerFont);
		idProductLabel.setBounds(20, 110, 100, 30);
		getContentPane().add(idProductLabel);

		quantityLabel = new JLabel("Quantity : ");
		quantityLabel.setFont(biggerFont);
		quantityLabel.setBounds(20, 150, 100, 30);
		getContentPane().add(quantityLabel);

		quantityField = new JTextField();
		quantityField.setBounds(100, 150, 125, 30);
		getContentPane().add(quantityField);

		int[] items = null;
		int[] items2 = null;
		try {
			items = orderDAO.getClientsIDs();
			items2 = orderDAO.getProductsIDs();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		myComboBox = new JComboBox<>();
		myComboBox.setBounds(100, 70, 125, 30);
		myComboBox.setFont(biggerFont);
		for (int i = 0; i < items.length; i++) {
			myComboBox.addItem(items[i]);
		}
		getContentPane().add(myComboBox);

		myComboBox2 = new JComboBox<>();
		myComboBox2.setBounds(110, 110, 125, 30);
		myComboBox2.setFont(biggerFont);
		for (int i = 0; i < items2.length; i++) {
			myComboBox2.addItem(items2[i]);
		}
		getContentPane().add(myComboBox2);

		backButton = new JButton("Back");
		backButton.setFont(biggerFont);
		backButton.setBounds(750, 550, 100, 50);
		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				parentInterface.setVisible(true);
			}
		});
		getContentPane().add(backButton);

		refreshComboBoxes = new JButton("Refresh ComboBoxes");
		refreshComboBoxes.setFont(biggerFont);
		refreshComboBoxes.setBounds(650, 475, 200, 50);
		refreshComboBoxes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					int[] items = orderDAO.getClientsIDs();
					int[] items2 = orderDAO.getProductsIDs();
					myComboBox.removeAllItems();
					myComboBox2.removeAllItems();
					for (int i = 0; i < items.length; i++) {
						myComboBox.addItem(items[i]);
					}
					for (int i = 0; i < items2.length; i++) {
						myComboBox2.addItem(items2[i]);
					}
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, "Error at refreshing comboboxes!");
					e1.printStackTrace();
				}

			}
		});
		getContentPane().add(refreshComboBoxes);

		/**
		 * SHOW ALL BUTTON + action listener
		 */
		showAllButton = new JButton("Show all");
		showAllButton.setFont(biggerFont);
		showAllButton.setBounds(70, 550, 100, 50);
		showAllButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					orderDAO.displayAllOrders();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		getContentPane().add(showAllButton);

		/**
		 * INSERT BUTTON + action listener
		 */
		insertButton = new JButton("Insert");
		insertButton.setFont(biggerFont);
		insertButton.setBounds(200, 550, 100, 50);
		getContentPane().add(insertButton);

		insertButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					orderDAO.insertNewOrder();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		/**
		 * DELETE BUTTON + action listener
		 */
		deleteButton = new JButton("Delete");
		deleteButton.setFont(biggerFont);
		deleteButton.setBounds(330, 550, 100, 50);
		getContentPane().add(deleteButton);
		deleteButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					orderDAO.DeleteOrder();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		/**
		 * UPDATE BUTTON + action listener
		 */
		updateButton = new JButton("Update");
		updateButton.setFont(biggerFont);
		updateButton.setBounds(460, 550, 100, 50);
		getContentPane().add(updateButton);
		updateButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					orderDAO.UpdateOrder();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		GenerateBill = new JButton("Generate bill !");
		GenerateBill.setFont(biggerFont);
		GenerateBill.setBounds(20, 350, 200, 50);
		getContentPane().add(GenerateBill);
		GenerateBill.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {


				try {
					//Whatever the file path is.
					File statText = new File("bills.txt");
					FileOutputStream is = new FileOutputStream(statText);
					OutputStreamWriter osw = new OutputStreamWriter(is);
					Writer w = new BufferedWriter(osw);



					StringBuilder builder = new StringBuilder();
					ArrayList<Order> orderList = null;
					try {
						orderList = orderDAO.getOrderList();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					Iterator<Order> myIterator = orderList.iterator();

					while (myIterator.hasNext()) {
						Order currentOrder = myIterator.next();
						builder.append("Order : " + currentOrder.getOrderID() + "\n");
						int cID = currentOrder.getClientID();
						int pID = currentOrder.getProductID();
						ClientDAO cDAO = new ClientDAO(con, clientInterface);
						Client curClient = null;
						try {
							curClient = cDAO.searchID(cID);
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
						if (curClient != null) {
							builder.append("Name : " + curClient.getName() + "\n");
						}
						ProductDAO pDAO = new ProductDAO(con, productInterface);
						Product curProduct = null;
						try {
							curProduct = pDAO.searchID(pID);
						} catch (SQLException e2) {
							e2.printStackTrace();
						}
						if (curProduct != null) {
							builder.append("Product : " + curProduct.getName() + "\n");
						}
						builder.append("Quantity : " + currentOrder.getQuantity() + "\n");
						builder.append("Final price : " + currentOrder.getQuantity()*curProduct.getPrice()+"\n\n");
						System.out.println(builder.toString());
						w.write(builder.toString());
						builder.delete(0, builder.length());
					}




					w.close();


				} catch (IOException e5) {
					System.err.println("Problem writing to the file statsTest.txt");
				}




			}
		});
	}

	public String getIdField() {
		return idField.getText();
	}

	public int getClientID() {
		return (Integer) myComboBox.getSelectedItem();
	}

	public int getProductID() {
		return (Integer) myComboBox2.getSelectedItem();
	}

	public String getQuantity() {
		return quantityField.getText();
	}
}