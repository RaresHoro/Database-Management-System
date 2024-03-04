package org.example.DAO;
import org.example.Connection.ConnectionFactory;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author Horodinca
 * Generic class for CRUD operations
 * @param <T>
 *
 */
public class GenericDAO<T> {

	/**
	 *
	 * @param genericObject CREATE -> inserts a new entry into the database
	 *
	 */
	public void Insert(Object genericObject) {
		String tableName = genericObject.getClass().getSimpleName();
		StringBuilder query = new StringBuilder();
		StringBuilder values = new StringBuilder();
		query.append("Insert into schooldb." + tableName + " (");
		Field[] allFields = genericObject.getClass().getDeclaredFields();
		try {
			for (int i = 0; i < allFields.length - 1; i++) {
				allFields[i].setAccessible(true);
				query.append(allFields[i].getName());
				query.append(", ");
				Object value = allFields[i].get(genericObject);
				String fieldType = allFields[i].getType().getSimpleName();
				if (fieldType.equals("String"))
					values.append("\"" + value + "\"");
				else
					values.append(value);
				values.append(", ");
			}
			int lastFieldIndex = allFields.length - 1;
			allFields[lastFieldIndex].setAccessible(true);
			query.append(allFields[lastFieldIndex].getName());
			Object value = allFields[lastFieldIndex].get(genericObject);
			String fieldType = allFields[lastFieldIndex].getType().getSimpleName();
			if (fieldType.equals("String"))
				values.append("\"" + value + "\"");
			else
				values.append(value);
			query.append(") values (").append(values).append(" )");
			System.out.println(query);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error at inserting");
		}
		try {
			Connection con = ConnectionFactory.getConnection();
			PreparedStatement prepInsertStatement = con.prepareStatement(query.toString());
			prepInsertStatement.executeUpdate();
			con.close();
			prepInsertStatement.close();
			JOptionPane.showMessageDialog(null, "Record created succesfully!");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Exception when executing insert query");
		}
	}
	
	public static JTable createTable(ArrayList<?> myList) {
		if (myList.isEmpty()) {
			// Handle the case when the list is empty
			return null; // or throw an exception, display an error message, etc.
		}
		int tableSize = myList.get(0).getClass().getDeclaredFields().length;
		String columnNames[] = new String[tableSize];
		int columnIndex = 0;
		for (Field currentField : myList.get(0).getClass().getDeclaredFields()) {
			currentField.setAccessible(true);
			try {
				columnNames[columnIndex] = currentField.getName();
				columnIndex++;
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
		}
		DefaultTableModel myModel = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;// all cells false
			}
		};
		myModel.setColumnIdentifiers(columnNames);
		for (Object o : myList) {
			Object[] obj = new Object[tableSize];
			int col = 0;
			for (Field currentField : o.getClass().getDeclaredFields()) {
				currentField.setAccessible(true);
				try {
					obj[col] = currentField.get(o);
					col++;
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
			myModel.addRow(obj);
		}
		JTable myTable = new JTable(myModel);
		return myTable;
	}

	public void delete(Object genericObject) {
		String tableName = genericObject.getClass().getSimpleName();
		StringBuilder query = new StringBuilder();
		query.append("Delete from schooldb." + tableName + " where ");
		Field[] allFields = genericObject.getClass().getDeclaredFields();
		Field firstField = allFields[0];
		firstField.setAccessible(true);
		String fieldName = firstField.getName();
		query.append(fieldName).append(" = ");
		try {
			Object value = firstField.get(genericObject);
			query.append(value);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error at geting id value");
		}
		System.out.println(query);
		try {
			Connection con = ConnectionFactory.getConnection();
			PreparedStatement prepDeleteStatement = con.prepareStatement(query.toString());
			prepDeleteStatement.executeUpdate();
			con.close();
			prepDeleteStatement.close();
			JOptionPane.showMessageDialog(null, "Record deleted succesfully!");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Exception when executing delete query");
		}
	}

	public void Update(Object genericObject) {
		String tableName = genericObject.getClass().getSimpleName();
		StringBuilder query = new StringBuilder();
		query.append("update schooldb." + tableName + " set ");
		Field[] allFields = genericObject.getClass().getDeclaredFields();
		try {
			for (int i = 0; i < allFields.length - 1; i++) {
				allFields[i].setAccessible(true);
				query.append(allFields[i].getName());
				query.append(" = ");
				Object value = allFields[i].get(genericObject);
				String fieldType = allFields[i].getType().getSimpleName();
				if (fieldType.equals("String"))
					query.append("\"" + value + "\"");
				else
					query.append(value);
				query.append(", ");
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error at updating");
		}
		int lastFieldIndex = allFields.length - 1;
		allFields[lastFieldIndex].setAccessible(true);
		query.append(allFields[lastFieldIndex].getName());
		query.append(" = ");
		try {
			Object value = allFields[lastFieldIndex].get(genericObject);
			String fieldType = allFields[lastFieldIndex].getType().getSimpleName();
			if (fieldType.equals("String"))
				query.append("\"" + value + "\"");
			else
				query.append(value);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error at updating");
		}
		
		query.append(" where ");
		Field firstField = allFields[0];
		firstField.setAccessible(true);
		String fieldName = firstField.getName();

		query.append(fieldName).append(" = ");

		try {
			Object value = firstField.get(genericObject);
			query.append(value);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error at geting id value");
		}
		try {
			System.out.println(query);
			Connection con = ConnectionFactory.getConnection();
			PreparedStatement prepUpdateStatement = con.prepareStatement(query.toString());
			prepUpdateStatement.executeUpdate();
			con.close();
			prepUpdateStatement.close();
			JOptionPane.showMessageDialog(null, "Record updated succesfully!");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Exception when executing update query");
		}
	}

}
