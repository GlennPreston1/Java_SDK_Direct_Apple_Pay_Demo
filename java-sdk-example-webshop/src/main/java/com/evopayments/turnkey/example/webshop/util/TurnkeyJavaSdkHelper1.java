package com.evopayments.turnkey.example.webshop.util;

import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

import com.evopayments.turnkey.apiclient.PurchaseTokenCall;
import com.evopayments.turnkey.config.TestConfig;
import com.evopayments.turnkey.example.webshop.data.OrderEntity;

/**
 * Basic payment helper methods
 * 
 * @author erbalazs
 *
 */
public class TurnkeyJavaSdkHelper1 {

	private static PurchaseTokenCall buildCall(OrderEntity orderEntity) {
		
		Map<String, String> inputParams = new HashMap<>();
		// inputParams.put("currency", orderEntity.getCurrency().toUpperCase()); // in this example it is fixed, see turnkey-sdk-test.properties
		inputParams.put("amount", orderEntity.getAmount().setScale(2, RoundingMode.HALF_UP).toPlainString());
		inputParams.put("merchantTxId", orderEntity.getId());
		// inputParams.put("paymentSolutionId", "500");
		
		// note: instead of PurchaseTokenCall it is also possible to use:
		// VerifyTokenCall
		// AuthTokenCall

		return new PurchaseTokenCall(TestConfig.getInstance(), inputParams);
	}

	/**
	 * @param orderEntity
	 * @return
	 * 		token to init the Javascript lib. 
	 */
	public static String startIframeModeCashierPayment(OrderEntity orderEntity) {
		PurchaseTokenCall purchaseTokenCall = buildCall(orderEntity);
		return purchaseTokenCall.execute().getString("token");
	}

	/**
	 * @param orderEntity
	 * @return
	 * 		URL string (for customer redirection)
	 */
	public static String startStandaloneModeCashierPayment(OrderEntity orderEntity) {
		PurchaseTokenCall purchaseTokenCall = buildCall(orderEntity);
		return purchaseTokenCall.executeAndBuildCashierStandaloneUrl();
	}

	/**
	 * @param orderEntity
	 * @return
	 * 		URL string (for customer redirection)
	 */
	public static String startHppModeCashierPayment(OrderEntity orderEntity) {
		PurchaseTokenCall purchaseTokenCall = buildCall(orderEntity);
		return purchaseTokenCall.executeAndBuildCashierHppUrl();
	}
	
	private TurnkeyJavaSdkHelper1() {
		// util class, with static methods
	}

}