package org.example.View;

import org.example.DAO.ProductDAO;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;


public class ProductInterface extends JFrame {

	private JButton backButton;
	private JButton showAllButton;
	private JButton insertButton;
	private JButton deleteButton;
	private JButton updateButton;

	private JLabel idLabel;
	private JTextField idField;

	private JLabel nameLabel;
	private JTextField nameField;

	private JLabel priceLabel;
	private JTextField priceField;

	private JLabel stockLabel;
	private JTextField stockField;

	public ProductInterface(UserInterface parentInterface, Connection con) {
		ProductInterface pInt = this;
		ProductDAO productDAO = new ProductDAO(con, pInt);

		this.setTitle("Products");
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

		nameLabel = new JLabel("Name : ");
		nameLabel.setFont(biggerFont);
		nameLabel.setBounds(20, 70, 100, 30);
		getContentPane().add(nameLabel);

		nameField = new JTextField();
		nameField.setBounds(80, 70, 125, 30);
		getContentPane().add(nameField);

		priceLabel = new JLabel("Price : ");
		priceLabel.setFont(biggerFont);
		priceLabel.setBounds(20, 110, 100, 30);
		getContentPane().add(priceLabel);

		priceField = new JTextField();
		priceField.setBounds(80, 110, 125, 30);
		getContentPane().add(priceField);

		stockLabel = new JLabel("Stock : ");
		stockLabel.setFont(biggerFont);
		stockLabel.setBounds(20, 150, 100, 30);
		getContentPane().add(stockLabel);

		stockField = new JTextField();
		stockField.setBounds(80, 150, 125, 30);
		getContentPane().add(stockField);

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
					productDAO.displayAllProducts();
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
					productDAO.insertNewProduct();
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
				productDAO.DeleteProduct();;
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
					productDAO.UpdateProduct();
			}
		});

	}

	public String getIdField() {
		return idField.getText();
	}

	public String getNameField() {
		return nameField.getText();
	}

	public String getPriceField() {
		return priceField.getText();
	}

	public String getStockField() {
		return stockField.getText();
	}
}
