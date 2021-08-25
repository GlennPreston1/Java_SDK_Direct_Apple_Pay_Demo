package com.evopayments.example.webshop.util;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.evopayments.example.webshop.data.OrderEntity;
import com.evopayments.example.webshop.data.OrderSubmitRequestDto;
import com.evopayments.turnkey.apiclient.AbstractApiCall;
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
		
		// tokenizeInputParams: customerId is not mandatory here, however value has to be the same as purchaseInputParams below
		// TODO: unfinished, temporarily we use the orderId here too
		tokenizeInputParams.put("customerId", orderEntity.getId());  

		JSONObject joTokenizeCallResponse = new TokenizeCall(TestConfig.getInstance(), tokenizeInputParams).execute();
		String cardToken = joTokenizeCallResponse.getString("cardToken");
		
		// ---
		
		Map<String, String> purchaseInputParams = new HashMap<>();
		purchaseInputParams.put("amount", orderEntity.getAmount().toString());
		// purchaseInputParams.put("currency", orderEntity.getCurrency().toUpperCase()); // in this example it is fixed, see turnkey-sdk-test.properties
		purchaseInputParams.put("paymentSolutionId", "500"); // 500 = credit/debit cards
		
		purchaseInputParams.put("merchantTxId", orderEntity.getId());
		purchaseInputParams.put("customerId", orderEntity.getId()); // TODO: unfinished, temporarily we use the orderId here too
		
		purchaseInputParams.put("specinCreditCardToken", cardToken);
		purchaseInputParams.put("specinCreditCardCVV", orderSubmitRequestDto.getCardCvv());
		
		// note: instead of PurchaseCall it is also possible to use:
		// VerifyCall
		// AuthCall
		
		TestConfig testConfig = TestConfig.getInstance();
		
		JSONObject joPurchaseCallResponse = new PurchaseCall(testConfig, purchaseInputParams).execute();
		
		// joPurchaseCallResponse.get("");
		// TODO: extraxt 3ds redirect... (if there is one)
		
		return testConfig.getProperty(AbstractApiCall.MERCHANT_LANDING_PAGE_URL_PROP_KEY);
	}
	
	private TurnkeyJavaSdkHelper2() {
		// util class, with static methods
	}

}