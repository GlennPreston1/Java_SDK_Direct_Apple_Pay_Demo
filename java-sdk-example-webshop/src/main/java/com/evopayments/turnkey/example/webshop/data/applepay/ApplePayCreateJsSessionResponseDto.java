package com.evopayments.turnkey.example.webshop.data.applepay;

import com.fasterxml.jackson.annotation.JsonRawValue;

public class ApplePayCreateJsSessionResponseDto {

	@JsonRawValue
	private String responseFromApple;

	public String getResponseFromApple() {
		return responseFromApple;
	}

	public void setResponseFromApple(String responseFromApple) {
		this.responseFromApple = responseFromApple;
	}

}
