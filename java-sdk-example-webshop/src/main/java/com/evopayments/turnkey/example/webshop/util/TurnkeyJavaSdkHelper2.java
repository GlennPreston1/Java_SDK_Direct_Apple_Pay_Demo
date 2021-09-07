package com.evopayments.turnkey.example.webshop.util;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.util.StringUtils;

import com.evopayments.turnkey.apiclient.AbstractApiCall;
import com.evopayments.turnkey.apiclient.PurchaseCall;
import com.evopayments.turnkey.apiclient.TokenizeCall;
import com.evopayments.turnkey.config.TestConfig;
import com.evopayments.turnkey.example.webshop.data.OrderEntity;
import com.evopayments.turnkey.example.webshop.data.OrderSubmitRequestDto;

/**
 * Helper methods for advanced payments (for PCI compliant merchants)
 * 
 * @author erbalazs
 *
 */
public class TurnkeyJavaSdkHelper2 {

	public static String executeAdvancedPayment(OrderSubmitRequestDto orderSubmitRequestDto, OrderEntity orderEntity) {

		TestConfig testConfig = TestConfig.getInstance();

		// ---

		Map<String, String> tokenizeInputParams = new HashMap<>();
		tokenizeInputParams.put("number", orderSubmitRequestDto.getCardNumber());
		tokenizeInputParams.put("nameOnCard", orderSubmitRequestDto.getNameOnCard());
		tokenizeInputParams.put("expiryMonth", orderSubmitRequestDto.getExpiryMonth());
		tokenizeInputParams.put("expiryYear", orderSubmitRequestDto.getExpiryYear());

		// tokenizeInputParams: customerId is not mandatory here, however value has to
		// be the same as purchaseInputParams below
		// this example webshop does not manage customers, there is no customerId
		// we use the orderId as customerId

		String customerId = orderEntity.getId();
		tokenizeInputParams.put("customerId", customerId);

		JSONObject joTokenizeCallResponse = new TokenizeCall(testConfig, tokenizeInputParams).execute();
		String cardToken = joTokenizeCallResponse.getString("cardToken");

		// ---

		Map<String, String> purchaseInputParams = new HashMap<>();
		purchaseInputParams.put("amount", orderEntity.getAmount().toString());
		// purchaseInputParams.put("currency", orderEntity.getCurrency().toUpperCase()); // in this example it is fixed, see currency setting in turnkey-sdk-test.properties
		purchaseInputParams.put("paymentSolutionId", "500"); // 500 = credit/debit cards

		purchaseInputParams.put("merchantTxId", orderEntity.getId());
		purchaseInputParams.put("customerId", customerId);

		purchaseInputParams.put("specinCreditCardToken", cardToken);
		purchaseInputParams.put("specinCreditCardCVV", orderSubmitRequestDto.getCardCvv());

		// note: instead of PurchaseCall it is also possible to use:
		// VerifyCall
		// AuthCall

		JSONObject joPurchaseCallResponse = new PurchaseCall(testConfig, purchaseInputParams).execute();

		String redirectionUrl = joPurchaseCallResponse.getString("redirectionUrl");
		if (StringUtils.hasLength(redirectionUrl)) {
			return redirectionUrl;
		}

		return testConfig.getProperty(AbstractApiCall.MERCHANT_LANDING_PAGE_URL_PROP_KEY);
	}

	public static String executeAdvancedPaymentApplePay(OrderSubmitRequestDto orderSubmitRequestDto,
			OrderEntity orderEntity) {

		TestConfig testConfig = TestConfig.getInstance();

		// this example webshop does not manage customers, there is no customerId
		// we use the orderId as customerId

		String customerId = orderEntity.getId();

		// ---

		Map<String, String> purchaseInputParams = new HashMap<>();
		purchaseInputParams.put("amount", orderEntity.getAmount().toString());
		// purchaseInputParams.put("currency", orderEntity.getCurrency().toUpperCase()); // in this example it is fixed, see currency setting in turnkey-sdk-test.properties
		purchaseInputParams.put("paymentSolutionId", "500"); // 500 = credit/debit cards, Apple Pay counts as a subtype (see below)
		purchaseInputParams.put("merchantTxId", orderEntity.getId());
		purchaseInputParams.put("customerId", customerId);

		purchaseInputParams.put("specinCCWalletId", "504"); // 504 = Apple Pay
		purchaseInputParams.put("specinCCWalletToken", orderSubmitRequestDto.getApplePayPayload()); 
		
		// ---
		
		// note: instead of PurchaseCall it is also possible to use:
		// VerifyCall
		// AuthCall

		JSONObject joPurchaseCallResponse = new PurchaseCall(testConfig, purchaseInputParams).execute();

		String redirectionUrl = joPurchaseCallResponse.getString("redirectionUrl");
		if (StringUtils.hasLength(redirectionUrl)) {
			return redirectionUrl;
		}

		return testConfig.getProperty(AbstractApiCall.MERCHANT_LANDING_PAGE_URL_PROP_KEY);
	}

	private TurnkeyJavaSdkHelper2() {
		// util class, with static methods
	}

}