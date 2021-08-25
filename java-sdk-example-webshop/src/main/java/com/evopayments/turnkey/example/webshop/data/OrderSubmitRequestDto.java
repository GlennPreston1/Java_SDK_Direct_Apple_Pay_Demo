package com.evopayments.turnkey.example.webshop.data;

public class OrderSubmitRequestDto {

	private String product;
	private String mode;

	/**
	 * only in advanced (PCI compliant merchant) mode
	 */
	private String cardNumber;

	/**
	 * only in advanced (PCI compliant merchant) mode
	 */
	private String nameOnCard;

	/**
	 * only in advanced (PCI compliant merchant) mode
	 */
	private String expiryMonth;

	/**
	 * only in advanced (PCI compliant merchant) mode
	 */
	private String expiryYear;

	/**
	 * only in advanced (PCI compliant merchant) mode
	 */
	private String cardCvv;

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getNameOnCard() {
		return nameOnCard;
	}

	public void setNameOnCard(String nameOnCard) {
		this.nameOnCard = nameOnCard;
	}

	public String getExpiryMonth() {
		return expiryMonth;
	}

	public void setExpiryMonth(String expiryMonth) {
		this.expiryMonth = expiryMonth;
	}

	public String getExpiryYear() {
		return expiryYear;
	}

	public void setExpiryYear(String expiryYear) {
		this.expiryYear = expiryYear;
	}

	public String getCardCvv() {
		return cardCvv;
	}

	public void setCardCvv(String cardCvv) {
		this.cardCvv = cardCvv;
	}

}