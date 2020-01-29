package com.evopayments.turnkey.apiclient;

import com.evopayments.turnkey.apiclient.exception.TurnkeyInternalException;
import com.evopayments.turnkey.apiclient.exception.TurnkeyValidationException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TokenizeCallTest extends BaseTest{

	/**
	 * successful case
	 */
	@Test
	public void noExTestCall() {

		final Map<String, String> inputParams = super.buildTokenizeParam();

		final TokenizeCall call = new TokenizeCall(config, inputParams, null);
		JSONObject result = call.execute();

		// note that any error will cause the throwing of some kind of SDKException (which extends RuntimeException)
		// still we make an assertNotNull

		Assert.assertNotNull(result);
	}

	/**
	 * RequiredParamException test (intentionally left out param).
	 */
	@Test(expected = TurnkeyValidationException.class)
	public void reqParExExpTestCall() {

		try {

			final Map<String, String> inputParams = new HashMap<>();
			inputParams.put("number", "5454545454545454");
			inputParams.put("nameOnCard", "John Doe");
			inputParams.put("expiryMonth", "12");
			// inputParams.put("expiryYear", "2018"); // intentionally left out

			final TokenizeCall call = new TokenizeCall(config, inputParams, null);
			call.execute();

		} catch (TurnkeyValidationException e) {
			Assert.assertEquals(new TurnkeyValidationException().getTurnkeyValidationErrorDescription() + ":" + Arrays.asList("expiryYear").toString(),e.getMessage());
			throw e;

		}
	}

	/**
	 * ActionCallException test (via intentionally wrong expiryYear).
	 */
	@Test(expected = TurnkeyInternalException.class)
	public void actCallExExpTestCall() {

		final Map<String, String> inputParams = new HashMap<>();
		inputParams.put("merchantId", config.getProperty("application.merchantId"));
		inputParams.put("password", config.getProperty("application.password"));
		inputParams.put("number", "5454545454545454");
		inputParams.put("nameOnCard", "John Doe");
		inputParams.put("expiryMonth", "12");
		inputParams.put("expiryYear", "2010"); // past date

		final TokenizeCall call = new TokenizeCall(config, inputParams, null);
		call.execute();
	}
}
