package com.evopayments.turnkey.apiclient;

import com.evopayments.turnkey.apiclient.config.NetworkFailConfig;
import com.evopayments.turnkey.apiclient.exception.TurnkeyInternalException;
import com.evopayments.turnkey.config.ApplicationConfig;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class NetworkingFailTest extends  BaseTest{

	protected static ApplicationConfig config = NetworkFailConfig.getInstance();
	/**
	 * PostToApiException test (intentional network error (wrong URLs)).
	 */
	@Test(expected = TurnkeyInternalException.class)
	public void networkingExExpTestCall() {

		final Map<String, String> inputParams = new HashMap<>();
		inputParams.put("merchantId", config.getProperty("application.merchantId"));
		inputParams.put("password", config.getProperty("application.password"));
		inputParams.put("country", "FR");
		inputParams.put("currency", "EUR");

		final GetAvailablePaymentSolutionsCall call = new GetAvailablePaymentSolutionsCall(config, inputParams, null);
		call.execute();
		
	}

}
