package org.example.DAO;

import org.example.model.Client;
import org.example.View.ClientInterface;
import org.example.valid.ClientValidator;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;



public class ClientDAO extends GenericDAO<Client> {

	private static Connection clientConnection;
	private ClientInterface clientInterface;
	private Statement clientStatement;

	public ClientDAO(Connection con, ClientInterface clientInterface) {
		this.clientConnection = con;
		this.clientInterface = clientInterface;
		try {
			this.clientStatement = con.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void viewAllClients() throws SQLException {

		ArrayList<Client> clientsList = getClientsList();

		JScrollPane myScrollPane = new JScrollPane();
		myScrollPane.setBounds(250, 70, 600, 400);

		JTable clientTable = new JTable();
		clientTable=createTable(clientsList);
		clientTable.setEnabled(true);
		clientTable.setVisible(true);

		myScrollPane.setViewportView(clientTable);
		clientInterface.getContentPane().add(myScrollPane);
	}

	public void insertNewClient() throws SQLException {
		String cID = clientInterface.getIdField();
		String cName = clientInterface.getNameField();
		String cEmail = clientInterface.getEmailField();
		String cPhone = clientInterface.getPhoneField();
		boolean validID = true;
		int clientID = 0;
		if (cID.equals(""))
			clientID = getLastID();
		else {
			try {
				clientID = Integer.parseInt(cID);
			} catch (Exception e) {
				validID = false;
				JOptionPane.showMessageDialog(null, "Bad ID!");
			}
		}
		if (validID == true) {
			ClientValidator CV = new ClientValidator();

			if (CV.ValidateClient(cName, cEmail, cPhone) == true) {
				Client myNewClient = new Client(clientID, cName, cEmail, cPhone);
				Insert(myNewClient);													// GENERIC METHOD
			} else
				JOptionPane.showMessageDialog(null, "Bad input!");
		}

	}
	
	public void DeleteClient()
	{
		String cID = clientInterface.getIdField();
		int clientID=0;
		boolean validID=true;
		try {
			clientID = Integer.parseInt(cID);
		} catch (Exception e) {
			validID = false;
			JOptionPane.showMessageDialog(null, "Bad ID!");
		}		
		if(validID==true)
		{
			try {
				Client myNewClient=searchID(clientID);
			if(myNewClient!=null) delete(myNewClient);									// GENERIC METHOD
			}catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Error at deleting element");
			}
			
		}
	}
	
	public void UpdateClient()
	{
		String cID = clientInterface.getIdField();
		String cName = clientInterface.getNameField();
		String cEmail = clientInterface.getEmailField();
		String cPhone = clientInterface.getPhoneField();
		int clientID=0;
		boolean validID=true;
		try {
			clientID = Integer.parseInt(cID);
		} catch (Exception e) {
			validID = false;
			JOptionPane.showMessageDialog(null, "Bad ID!");
		}
		
		if (validID == true) {
			ClientValidator CV = new ClientValidator();

			if (CV.ValidateClient(cName, cEmail, cPhone) == true) {
				try {
					Client myNewClient = searchID(clientID);
					myNewClient.setEmail(cEmail);
					myNewClient.setName(cName);
					myNewClient.setPhoneNumber(cPhone);
					Update(myNewClient);									// GENERIC METHOD
				}catch (Exception e) {
					System.out.println("Client not found");
				}		
			} else
				JOptionPane.showMessageDialog(null, "Bad input!");
		}
	}

	public int getLastID() throws SQLException {
		ResultSet rs = clientStatement.executeQuery("SELECT max(clientID) FROM client");

		int lastID = 0;
		while (rs.next()) {
			lastID = rs.getInt(1);
		}

		int newID = lastID + 1;
		return newID;
	}

	public ArrayList<Client> getClientsList() throws SQLException {
		ResultSet rs = clientStatement.executeQuery("SELECT * FROM schooldb.client");

		ArrayList<Client> clientsList = new ArrayList<Client>();
		while (rs.next()) {
			Client newClient = new Client(rs.getInt("clientID"), rs.getString("name"), rs.getString("email"),
					rs.getString("phoneNumber"));
			clientsList.add(newClient);
		}

		return clientsList;
	}

	public Client searchID(int id) throws SQLException {
		Client myClient = null;

		ArrayList<Client> clientsList = getClientsList();
		Iterator<Client> myIterator = clientsList.iterator();
		while (myIterator.hasNext()) {
			Client currentClient = myIterator.next();
			if (currentClient.getClientID() == id) {
				myClient = currentClient;
				break;
			}
		}
		return myClient;
	}
}
