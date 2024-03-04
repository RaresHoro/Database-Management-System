package org.example.valid;

public class ClientValidator {

	public ClientValidator() {

	}

	public boolean ValidateClient(String name, String email, String phoneNumber) {
		if (ValidateName(name) == false)
			return false;
		else if (ValidateEmail(email) == false)
			return false;
		else if(ValidatePhone(phoneNumber)==false) 
			return false;
		return true;
	}

	public boolean ValidateName(String name) {

		if (name == "")
			return false;
		if (name.matches("[ a-zA-Z]+") == false)
			return false;
		return true;
	}

	public boolean ValidateEmail(String email) {
		if (email.contains("@") == false || email.contains(".") == false)
			return false;
		return true;
	}
	
	public boolean ValidatePhone(String phoneNumber)
	{
		if(phoneNumber.length()!=10) return false;
		if(phoneNumber.matches("[0-9]+")==false) return false;
		return true;
	}

}
