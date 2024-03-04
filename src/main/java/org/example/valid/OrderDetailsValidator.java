package org.example.valid;

import java.util.Date;


public class OrderDetailsValidator {

	public OrderDetailsValidator()
	{
		
	}
	public boolean ValidateOrderDetais(String city, String street)
	{
		if(ValidateCity(city)==false) return false;
		else if (ValidateStreet(street)==false) return false;
		else
		return true;
	}
	
	public boolean ValidateCity(String city)
	{
		if (city == "")
			return false;
		if (city.matches("[ a-zA-Z-]+") == false)
			return false;
		return true;
	}
	
	public boolean ValidateStreet(String street)
	{
		if (street == "")
			return false;
		if (street.matches("[ a-zA-Z]+") == false)
			return false;
		return true;
	}
	
	
}
