package org.example.Controller;

import org.example.Connection.ConnectionFactory;
import org.example.View.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;


public class MainController {

	private UserInterface userInterface;
	private ClientInterface clientInterface;
	private ProductInterface productInterface;
	private OrderInterface orderInterface;
	private OrderDetailsInterface orderDetailsInterface;
		
	public void start()
	{
		
		Connection con = ConnectionFactory.getConnection();
		if(con!=null)
		{
			System.out.println("Connected !");
		}
		
		userInterface=new UserInterface();
		clientInterface = new ClientInterface(userInterface,con);
		productInterface=new ProductInterface(userInterface,con);
		orderInterface= new OrderInterface(userInterface,con,productInterface,clientInterface);
		orderDetailsInterface = new OrderDetailsInterface(userInterface,con);
		
		userInterface.setVisible(true);
		
		
		
		initializeUserInterfaceButtons();
	}
	
	public void initializeUserInterfaceButtons()
	{
		userInterface.addAccessClientTableButtonActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				userInterface.setVisible(false);
				clientInterface.setVisible(true);
			}
		});
		
		userInterface.addAccessProductTableButtonActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				userInterface.setVisible(false);
				productInterface.setVisible(true);
			}
		});
		
		userInterface.addAccessOrderTableButtonActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				userInterface.setVisible(false);
				orderInterface.setVisible(true);
			}
		});
		
		userInterface.addAccessOrderDetailsTableButtonActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				userInterface.setVisible(false);
				orderDetailsInterface.setVisible(true);
			}
		});
	}
}
