package com.evopayments.turnkey.apiclient;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

public class GetAvailablePaymentSolutionsCallTest extends BaseTest {

	/**
	 * Successful case
	 */
	@Test
	public void noExTestCall() {

		final Map<String, String> inputParams = new HashMap<>();
		super.addCommonParams(inputParams);
		inputParams.put("country", "PL");
		inputParams.put("currency", "PLN");

		final GetAvailablePaymentSolutionsCall call = new GetAvailablePaymentSolutionsCall(config, inputParams);
		final JSONObject result = call.execute();

		Assert.assertNotNull(result);
		Assert.assertEquals("success", result.get("result"));
		Assert.assertTrue(result.toString().contains("\"CreditDebitCards\""));

	}

}
