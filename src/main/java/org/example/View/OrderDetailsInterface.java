package org.example.View;

import org.example.DAO.OrderDetailsDAO;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;


public class OrderDetailsInterface extends JFrame {

	private JButton backButton;
	private JButton refreshComboBoxes;
	private JButton showAllButton;
	private JButton insertButton;
	private JButton deleteButton;
	private JButton updateButton;

	private JLabel idLabel;
	private JTextField idField;

	private JLabel idOrderLabel;
	

	private JLabel deliveryCityLabel;
	private JTextField deliveryCityField;
	
	private JLabel deliveryStreetLabel;
	private JTextField deliveryStreetField;
	
	private JComboBox<Integer> myComboBox;
	
	public OrderDetailsInterface(UserInterface parentInterface, Connection con)
	{
		OrderDetailsInterface odInt = this;
		OrderDetailsDAO orderDetailsDAO = new OrderDetailsDAO(con, odInt);
		
		this.setTitle("OrderDetails");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setBounds(500, 150, 900, 700);
		this.getContentPane().setLayout(null);

		// use a bigger font
		Font biggerFont = new Font("Times New Roman", Font.PLAIN, 18);
		Font hugeFont = new Font("Times New Roman",Font.PLAIN,32);
	
		
		idLabel = new JLabel("ID : ");
		idLabel.setFont(biggerFont);
		idLabel.setBounds(20, 30, 100, 30);
		getContentPane().add(idLabel);

		idField = new JTextField();
		idField.setBounds(80, 30, 75, 30);
		getContentPane().add(idField);

		idOrderLabel = new JLabel("Order ID : ");
		idOrderLabel.setFont(biggerFont);
		idOrderLabel.setBounds(20, 70, 100, 30);
		getContentPane().add(idOrderLabel);

		deliveryCityLabel = new JLabel("Delivery City : ");
		deliveryCityLabel.setFont(biggerFont);
		deliveryCityLabel.setBounds(20, 110, 150, 30);
		getContentPane().add(deliveryCityLabel);
		
		deliveryCityField = new JTextField();
		deliveryCityField.setBounds(130,110,70,30);
		getContentPane().add(deliveryCityField);

		deliveryStreetLabel = new JLabel("Delivery Street : ");
		deliveryStreetLabel.setFont(biggerFont);
		deliveryStreetLabel.setBounds(20, 150, 150, 30);
		getContentPane().add(deliveryStreetLabel);

		deliveryStreetField = new JTextField();
		deliveryStreetField.setBounds(140, 150, 70, 30);
		getContentPane().add(deliveryStreetField);
		
		
		int[] items = null;
		try {
			items = orderDetailsDAO.getOrderIDs();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		myComboBox = new JComboBox<>();
		myComboBox.setBounds(100, 70, 125, 30);
		myComboBox.setFont(biggerFont);
		for (int i = 0; i <10; i++) {//change 10
			//myComboBox.addItem(items[i]);
		}
		getContentPane().add(myComboBox);
		
		
		refreshComboBoxes= new JButton("Refresh ComboBoxes");
		refreshComboBoxes.setFont(biggerFont);
		refreshComboBoxes.setBounds(650, 475, 200, 50);
		refreshComboBoxes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				try {	
					int[] items = orderDetailsDAO.getOrderIDs();	
					myComboBox.removeAllItems();					
					for (int i = 0; i < items.length; i++) {
						myComboBox.addItem(items[i]);
					}					
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, "Error at refreshing comboboxes!");
					e1.printStackTrace();
				}
			}
		});
		getContentPane().add(refreshComboBoxes);
			
		
		/**
		 * BACK ALL BUTTON + action listener
		 */
		backButton = new JButton("Back");
		backButton.setFont(biggerFont);
		backButton.setBounds(750,550,100,50);
		backButton.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				
				setVisible(false);
				parentInterface.setVisible(true);
				
			}
		});
		getContentPane().add(backButton);
		
		
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
					orderDetailsDAO.displayAllOrderDetails();
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
					orderDetailsDAO.InsertOrderDetails();
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
			public void actionPerformed(ActionEvent e)  {
				orderDetailsDAO.DeleteOrderDetails();
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
						orderDetailsDAO.UpdateOrderDetails();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			}
		});
	}
	
	public String getID()
	{
		return idField.getText();
	}
	
	public int getOrderID()
	{
		return (Integer) myComboBox.getSelectedItem();
	}
	
	public String getCity()
	{
		return deliveryCityField.getText();
	}
	public String getStreet()
	{
		return deliveryStreetField.getText();
	}
}
