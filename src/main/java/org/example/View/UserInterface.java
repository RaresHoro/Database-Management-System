package org.example.View;

import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class UserInterface extends JFrame {

	private JLabel titleLabel;
	
	private JButton accessClientTableButton;
	private JButton accessProductTableButton;
	private JButton accessOrderTableButton;
	private JButton accessOrderDetailsTableButton;
	
	public UserInterface() {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setBounds(500, 150, 900, 700);
		this.getContentPane().setLayout(null);

		// use a bigger font
		Font biggerFont = new Font("Times New Roman", Font.PLAIN, 18);
		Font hugeFont = new Font("Times New Roman",Font.PLAIN,32);
		
		
		titleLabel = new JLabel("Database Management Application");
		titleLabel.setFont(hugeFont);
		titleLabel.setBounds(200,100,450,50);
		getContentPane().add(titleLabel);
		
		
		accessClientTableButton = new JButton("Manage Client Table");
		accessClientTableButton.setFont(biggerFont);
		accessClientTableButton.setBounds(100,350,250,50);
		getContentPane().add(accessClientTableButton);
		
		accessProductTableButton = new JButton("Manage Product Table");
		accessProductTableButton.setFont(biggerFont);
		accessProductTableButton.setBounds(500,350,250,50);
		getContentPane().add(accessProductTableButton);
		
		accessOrderTableButton = new JButton("Manage Order Table");
		accessOrderTableButton.setFont(biggerFont);
		accessOrderTableButton.setBounds(100,450,250,50);
		getContentPane().add(accessOrderTableButton);
		
		accessOrderDetailsTableButton = new JButton("Manage OrderDetails Table");
		accessOrderDetailsTableButton.setFont(biggerFont);
		accessOrderDetailsTableButton.setBounds(500,450,250,50);
		getContentPane().add(accessOrderDetailsTableButton);
	}
	
	public void addAccessClientTableButtonActionListener(ActionListener actionListener)
	{
		accessClientTableButton.addActionListener(actionListener);
	}
	
	public void addAccessProductTableButtonActionListener(ActionListener actionListener)
	{
		accessProductTableButton.addActionListener(actionListener);
	}
	
	public void addAccessOrderTableButtonActionListener(ActionListener actionListener)
	{
		accessOrderTableButton.addActionListener(actionListener);
	}
	
	public void addAccessOrderDetailsTableButtonActionListener(ActionListener actionListener)
	{
		accessOrderDetailsTableButton.addActionListener(actionListener);
	}
}
