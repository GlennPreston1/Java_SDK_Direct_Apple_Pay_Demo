package com.evopayments.example.webshop.util;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.evopayments.turnkey.apiclient.CaptureCall;
import com.evopayments.turnkey.apiclient.RefundCall;
import com.evopayments.turnkey.apiclient.StatusCheckCall;
import com.evopayments.turnkey.apiclient.VoidCall;
import com.evopayments.turnkey.config.TestConfig;

public class TurnkeyJavaSdkHelper3 {

	private TurnkeyJavaSdkHelper3() {
		// util class, with static methods
	}

	public static JSONObject statusCheck(String orderId) {
		
		Map<String, String> inputParams = new HashMap<>();
		// inputParams.put("txId", orderId); // it is possible to use the txId (the internal id from the Turnkey API)
		inputParams.put("merchantTxId", orderId); // merchantTxId means the merchant's own id for the order/transaction
		
		return new StatusCheckCall(TestConfig.getInstance(), inputParams).execute();
		
	}
	
	public static JSONObject executeCapture(String orderId) {
		
		Map<String, String> inputParams = new HashMap<>();
		inputParams.put("originalMerchantTxId", orderId); // merchantTxId means the merchant's own id for the order/transaction
		
		// inputParams.put("amount", ""); // in some cases it is possible to do a partial capture
		
		return new CaptureCall(TestConfig.getInstance(), inputParams).execute();
		
	}
	
	public static JSONObject executeVoid(String orderId) {
		
		Map<String, String> inputParams = new HashMap<>();
		inputParams.put("originalMerchantTxId", orderId); // merchantTxId means the merchant's own id for the order/transaction
		
		return new VoidCall(TestConfig.getInstance(), inputParams).execute();
		
	}
	
	public static JSONObject executeRefund(String orderId) {
		
		Map<String, String> inputParams = new HashMap<>();
		inputParams.put("originalMerchantTxId", orderId); // merchantTxId means the merchant's own id for the order/transaction
		
		// inputParams.put("amount", ""); // in some cases it is possible to do a partial refund
		
		return new RefundCall(TestConfig.getInstance(), inputParams).execute();
		
	}
	
}