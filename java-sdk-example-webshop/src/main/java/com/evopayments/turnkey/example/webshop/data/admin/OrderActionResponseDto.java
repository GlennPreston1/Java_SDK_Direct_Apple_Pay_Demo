package com.evopayments.turnkey.example.webshop.data.admin;

import com.fasterxml.jackson.annotation.JsonRawValue;

public class OrderActionResponseDto {

	@JsonRawValue
	private String responseFromTurnkeyApi;

	public String getResponseFromTurnkeyApi() {
		return responseFromTurnkeyApi;
	}

	public void setResponseFromTurnkeyApi(String responseFromTurnkeyApi) {
		this.responseFromTurnkeyApi = responseFromTurnkeyApi;
	}

}