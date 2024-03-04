package org.example.valid;


import org.example.View.ProductInterface;

public class ProductValidator {

	private ProductInterface productInterface;
	private String productID;
	private String productName;
	private String productPrice;
	private String productStock;

	public ProductValidator(ProductInterface productInterface) {
		this.productInterface = productInterface;
		this.productID = productInterface.getIdField();
		this.productName = productInterface.getNameField();
		this.productPrice = productInterface.getPriceField();
		this.productStock = productInterface.getStockField();
	}
	
	public boolean ValidateProduct()
	{
		if(ValidateName()==false) return false;
		if(ValidatePrice()==false) return false;
		if(ValidateStock()==false) return false;
		return true;
	}

	public boolean ValidateName() {
		if (productName == "")
			return false;
		if (productName.matches("[ a-zA-Z]+") == false)
			return false;
		return true;
	}

	public boolean ValidatePrice() {
		int price;
		try {
			price = Integer.parseInt(productPrice);
			if (price < 0)
				return false;
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	public boolean ValidateStock()
	{
		int stock;
		try {
			stock = Integer.parseInt(productStock);
			if (stock < 0)
				return false;
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
