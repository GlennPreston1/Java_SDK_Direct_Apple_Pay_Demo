package com.evopayments.example.webshop.util;

import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

import com.evopayments.example.webshop.data.OrderEntity;
import com.evopayments.turnkey.apiclient.PurchaseTokenCall;
import com.evopayments.turnkey.config.TestConfig;

public class TurnkeyJavaSdkHelper1 {

	private static PurchaseTokenCall buildCall(OrderEntity orderEntity) {
		
		Map<String, String> inputParams = new HashMap<>();
		inputParams.put("country", "PL");
		inputParams.put("currency", orderEntity.getCurrency().toUpperCase());
		inputParams.put("amount", orderEntity.getAmount().setScale(2, RoundingMode.HALF_UP).toPlainString());
		inputParams.put("channel", "ECOM");
		inputParams.put("merchantTxId", orderEntity.getId());
		// inputParams.put("paymentSolutionId", "500");
		
		// note: instead of PurchaseTokenCall it is also possible to use:
		// VerifyTokenCall
		// AuthTokenCall

		return new PurchaseTokenCall(TestConfig.getInstance(), inputParams);
	}

	public static String startIframeModeCashierPayment(OrderEntity orderEntity) {
		PurchaseTokenCall purchaseTokenCall = buildCall(orderEntity);
		return purchaseTokenCall.executeAndBuildCashierIframeUrl();
	}

	public static String startStandaloneModeCashierPayment(OrderEntity orderEntity) {
		PurchaseTokenCall purchaseTokenCall = buildCall(orderEntity);
		return purchaseTokenCall.executeAndBuildCashierStandaloneUrl();
	}

	public static String startHppModeCashierPayment(OrderEntity orderEntity) {
		PurchaseTokenCall purchaseTokenCall = buildCall(orderEntity);
		return purchaseTokenCall.executeAndBuildCashierHppUrl();
	}
	
	private TurnkeyJavaSdkHelper1() {
		// util class, with static methods
	}

}