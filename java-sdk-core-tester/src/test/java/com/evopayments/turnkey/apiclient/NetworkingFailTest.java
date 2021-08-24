package com.evopayments.turnkey.apiclient;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.evopayments.turnkey.apiclient.exception.TurnkeyCommunicationException;
import com.evopayments.turnkey.config.NetworkFailConfig;

public class NetworkingFailTest extends BaseTest {

	@Test(expected = TurnkeyCommunicationException.class)
	public void networkErrorTestCall() {

		final Map<String, String> inputParams = new HashMap<>();
		inputParams.put("country", "FR");
		inputParams.put("currency", "EUR");

		final GetAvailablePaymentSolutionsCall call = new GetAvailablePaymentSolutionsCall(NetworkFailConfig.getInstance(), inputParams);
		call.execute();

	}

}
