package com.evopayments.example.webshop.util;

import com.evopayments.example.webshop.data.OrderEntity;
import com.evopayments.example.webshop.data.OrderSubmitRequestDto;

public class TurnkeyJavaSdkHelper {

	private TurnkeyJavaSdkHelper() {
		// util class, with static methods, no need for constructor (hence private)
	}
	
	public static String startIframeModeCashierPayment(OrderEntity orderEntity) {
		return "http://example.com";
	}
	
	public static String startStandaloneModeCashierPayment(OrderEntity orderEntity) {
		return "http://example.com";
	}
	
	public static String startHppModeCashierPayment(OrderEntity orderEntity) {
		return "http://example.com";
	}
	
	public static String executeAdvancedPayment(OrderSubmitRequestDto orderSubmitRequestDto, OrderEntity orderEntity) {
		return "http://example.com";
	}

}