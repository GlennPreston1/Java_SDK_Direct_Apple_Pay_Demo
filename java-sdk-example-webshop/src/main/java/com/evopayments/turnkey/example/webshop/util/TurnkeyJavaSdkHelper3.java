package com.evopayments.turnkey.example.webshop.util;

import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.evopayments.turnkey.apiclient.CaptureCall;
import com.evopayments.turnkey.apiclient.RefundCall;
import com.evopayments.turnkey.apiclient.StatusCheckCall;
import com.evopayments.turnkey.apiclient.VoidCall;
import com.evopayments.turnkey.config.TestConfig;
import com.evopayments.turnkey.example.webshop.data.OrderEntity;

/**
 * Helper methods, mainly (but not only) 
 * for the small backoffice like example UI (capture, refund etc.)
 * 
 * @author erbalazs
 *
 */
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
	
	public static JSONObject executeCapture(OrderEntity orderEntity) {
		
		Map<String, String> inputParams = new HashMap<>();
		inputParams.put("originalMerchantTxId", orderEntity.getId()); // merchantTxId means the merchant's own id for the order/transaction
		
		// in some cases it is possible to do a partial capture, but here we execute a full capture
		inputParams.put("amount", orderEntity.getAmount().setScale(2, RoundingMode.HALF_UP).toPlainString()); 
		
		return new CaptureCall(TestConfig.getInstance(), inputParams).execute();
		
	}
	
	public static JSONObject executeVoid(String orderId) {
		
		Map<String, String> inputParams = new HashMap<>();
		inputParams.put("originalMerchantTxId", orderId); // merchantTxId means the merchant's own id for the order/transaction
		
		return new VoidCall(TestConfig.getInstance(), inputParams).execute();
		
	}
	
	public static JSONObject executeRefund(OrderEntity orderEntity) {
		
		Map<String, String> inputParams = new HashMap<>();
		inputParams.put("originalMerchantTxId", orderEntity.getId()); // merchantTxId means the merchant's own id for the order/transaction
		
		inputParams.put("amount", orderEntity.getAmount().setScale(2, RoundingMode.HALF_UP).toPlainString()); // in some cases it is possible to do a partial refund, but here we execute a full refund
		
		return new RefundCall(TestConfig.getInstance(), inputParams).execute();
		
	}
	
}