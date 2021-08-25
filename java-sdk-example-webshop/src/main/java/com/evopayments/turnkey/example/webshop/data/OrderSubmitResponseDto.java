package com.evopayments.turnkey.example.webshop.data;

public class OrderSubmitResponseDto {

	/**
	 * Customer redirect URL (needed for HPP, standalone and advanced modes)
	 */
	private String url;

	/**
	 * Received authentication token (needed for iframe mode init)
	 */
	private String tokenToInitJsLib;

	/**
	 * This is just echoed back to the frontend (for debug etc.)
	 */
	private String orderId;

	/**
	 * This is just echoed back to the frontend (for debug etc.)
	 */
	private String mode;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTokenToInitJsLib() {
		return tokenToInitJsLib;
	}

	public void setTokenToInitJsLib(String tokenToInitJsLib) {
		this.tokenToInitJsLib = tokenToInitJsLib;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

}