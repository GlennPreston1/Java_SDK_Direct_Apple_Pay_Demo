package com.evopayments.example.webshop.util;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.evopayments.example.webshop.data.OrderEntity;
import com.evopayments.example.webshop.data.OrderSubmitRequestDto;
import com.evopayments.turnkey.apiclient.PurchaseCall;
import com.evopayments.turnkey.apiclient.TokenizeCall;
import com.evopayments.turnkey.config.TestConfig;

public class TurnkeyJavaSdkHelper2 {

	public static String executeAdvancedPayment(OrderSubmitRequestDto orderSubmitRequestDto, OrderEntity orderEntity) {
		
		Map<String, String> tokenizeInputParams = new HashMap<>();
		tokenizeInputParams.put("number", orderSubmitRequestDto.getCardNumber());
		tokenizeInputParams.put("nameOnCard", orderSubmitRequestDto.getNameOnCard());
		tokenizeInputParams.put("expiryMonth", orderSubmitRequestDto.getExpiryMonth());
		tokenizeInputParams.put("expiryYear", orderSubmitRequestDto.getExpiryYear());

		JSONObject joTokenizeCallResponse = new TokenizeCall(TestConfig.getInstance(), tokenizeInputParams).execute();
		String cardToken = joTokenizeCallResponse.getString("cardToken");
		
		Map<String, String> purchaseInputParams = new HashMap<>();
		purchaseInputParams.put("amount", orderEntity.getAmount().toString());
		purchaseInputParams.put("channel", "ECOM");
		purchaseInputParams.put("country", "PL");
		purchaseInputParams.put("currency", orderEntity.getCurrency().toUpperCase());
		// purchaseInputParams.put("paymentSolutionId", "500");
		// purchaseInputParams.put("customerId", "");
		purchaseInputParams.put("specinCreditCardToken", cardToken);
		purchaseInputParams.put("specinCreditCardCVV", orderSubmitRequestDto.getCardCvv());
		
		JSONObject joPurchaseCallResponse = new PurchaseCall(TestConfig.getInstance(), tokenizeInputParams).execute();
		// joPurchaseCallResponse.get("");
		
		return "http://example.com";
	}

}