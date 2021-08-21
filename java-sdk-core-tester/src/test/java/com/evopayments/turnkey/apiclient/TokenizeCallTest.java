package com.evopayments.turnkey.apiclient;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import com.evopayments.turnkey.apiclient.exception.ErrorType;
import com.evopayments.turnkey.apiclient.exception.TurnkeyInternalException;
import com.evopayments.turnkey.apiclient.exception.TurnkeyValidationException;

public class TokenizeCallTest extends BaseTest {

	/**
	 * Successful case
	 */
	@Test
	public void noExTestCall() {

		final Map<String, String> inputParams = super.buildTokenizeParamMap();

		final TokenizeCall call = new TokenizeCall(config, inputParams, null);
		final JSONObject result = call.execute();

		Assert.assertNotNull(result);
	}

	/**
	 * {@link TurnkeyValidationException} test (intentionally left out param)
	 */
	@Test(expected = TurnkeyValidationException.class)
	public void missingParameterTest() {

		try {

			final Map<String, String> inputParams = new HashMap<>();
			inputParams.put("number", "5454545454545454");
			inputParams.put("nameOnCard", "John Doe");
			inputParams.put("expiryMonth", "12");
			// inputParams.put("expiryYear", "2018"); // intentionally left out

			final TokenizeCall call = new TokenizeCall(config, inputParams, null);
			call.execute();

		} catch (final TurnkeyValidationException e) {
			Assert.assertEquals(ErrorType.VALIDATION_ERROR.getDescription() + ": " + Arrays.asList("expiryYear").toString(), e.getMessage());
			throw e;

		}
	}

	/**
	 * Intentionally wrong expiryYear test
	 */
	@Test(expected = TurnkeyInternalException.class)
	public void wrongExpYearTest() {

		final Map<String, String> inputParams = new HashMap<>();
		inputParams.put("number", "5454545454545454");
		inputParams.put("nameOnCard", "John Doe");
		inputParams.put("expiryMonth", "12");
		inputParams.put("expiryYear", "2010"); // past date

		final TokenizeCall call = new TokenizeCall(config, inputParams, null);
		call.execute();
		
	}
	
}
