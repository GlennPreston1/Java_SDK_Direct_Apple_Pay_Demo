package com.evopayments.example.webshop.util;

import com.evopayments.example.webshop.data.OrderEntity;
import com.evopayments.example.webshop.data.OrderSubmitRequestDto;
import com.evopayments.turnkey.apiclient.PurchaseTokenCall;
import com.evopayments.turnkey.config.TestConfig;

public class TurnkeyJavaSdkHelper {

	private TurnkeyJavaSdkHelper() {
		// util class, with static methods, no need for constructor (hence private)
	}
	
	public static String startIframeModeCashierPayment(OrderEntity orderEntity) {
		return "http://example.com";
	}
	
	public static String startStandaloneModeCashierPayment(OrderEntity orderEntity) {
		
		resp.sendRedirect(config.getProperty(CASHIER_URL_PROP_KEY)+ "?"
				+ URLEncodedUtils.format(ApiCall.getForm(inputParams).build(),
				"UTF-8"));
		
		TestConfig.getInstance();
		
		new PurchaseTokenCall(null, null)
		
		return "http://example.com";
	}
	
	public static String startHppModeCashierPayment(OrderEntity orderEntity) {
		return "http://example.com";
	}
	
	public static String executeAdvancedPayment(OrderSubmitRequestDto orderSubmitRequestDto, OrderEntity orderEntity) {
		return "http://example.com";
	}

}