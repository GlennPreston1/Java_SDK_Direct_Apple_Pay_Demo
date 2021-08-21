package com.evopayments.turnkey.apiclient;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import com.evopayments.turnkey.apiclient.exception.ErrorType;
import com.evopayments.turnkey.apiclient.exception.TurnkeyValidationException;

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

		final GetAvailablePaymentSolutionsCall call = new GetAvailablePaymentSolutionsCall(config, inputParams, null);
		final JSONObject result = call.execute();

		Assert.assertNotNull(result);
		Assert.assertEquals("success", result.get("result"));
		Assert.assertTrue(result.toString().contains("\"CreditDebitCards\""));

	}

	/**
	 * {@link TurnkeyValidationException} test (intentionally left out param)
	 */
	@Test(expected = TurnkeyValidationException.class)
	public void missingParameterTest() {

		try {

			final Map<String, String> inputParams = new HashMap<>();
			inputParams.put("country", "FR");
			// inputParams.put("currency", "EUR"); // intentionally left out

			final GetAvailablePaymentSolutionsCall call = new GetAvailablePaymentSolutionsCall(config, inputParams, null);
			call.execute();

		} catch (final TurnkeyValidationException e) {
			Assert.assertEquals(ErrorType.VALIDATION_ERROR.getDescription() + ": " + Arrays.asList("currency").toString(), e.getMessage());
			throw e;
		}

	}

}
