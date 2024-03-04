package org.example.View;

import org.example.DAO.ClientDAO;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;



public class ClientInterface extends JFrame {

	private JButton backButton;
	private JButton showAllButton;
	private JButton insertButton;
	private JButton deleteButton;
	private JButton updateButton;

	private JLabel idLabel;
	private JTextField idField;

	private JLabel nameLabel;
	private JTextField nameField;

	private JLabel emailLabel;
	private JTextField emailField;

	private JLabel phoneLabel;
	private JTextField phoneField;

	public ClientInterface(UserInterface parentInterface, Connection con) {

		ClientInterface cInt = this;
		ClientDAO clientDAO = new ClientDAO(con, cInt);

		this.setTitle("Clients");
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

		emailLabel = new JLabel("Email : ");
		emailLabel.setFont(biggerFont);
		emailLabel.setBounds(20, 110, 100, 30);
		getContentPane().add(emailLabel);

		emailField = new JTextField();
		emailField.setBounds(80, 110, 125, 30);
		getContentPane().add(emailField);

		phoneLabel = new JLabel("Phone : ");
		phoneLabel.setFont(biggerFont);
		phoneLabel.setBounds(20, 150, 100, 30);
		getContentPane().add(phoneLabel);

		phoneField = new JTextField();
		phoneField.setBounds(80, 150, 125, 30);
		getContentPane().add(phoneField);

		// BUTTONS :

		/**
		 * BACK BUTTON + action listener
		 */
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
					clientDAO.viewAllClients();
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
					new ClientDAO(con, cInt).insertNewClient();
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

				new ClientDAO(con, cInt).DeleteClient();
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

				new ClientDAO(con, cInt).UpdateClient();
			}
		});

	}

	public String getIdField() {
		return idField.getText();
	}

	public String getNameField() {
		return nameField.getText();
	}

	public String getEmailField() {
		return emailField.getText();
	}

	public String getPhoneField() {
		return phoneField.getText();
	}

}
