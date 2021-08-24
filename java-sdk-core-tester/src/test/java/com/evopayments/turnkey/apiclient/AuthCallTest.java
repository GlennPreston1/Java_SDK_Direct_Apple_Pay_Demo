package com.evopayments.turnkey.apiclient;

import java.util.Arrays;
import java.util.Map;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import com.evopayments.turnkey.apiclient.exception.ErrorType;
import com.evopayments.turnkey.apiclient.exception.TurnkeyValidationException;

public class AuthCallTest extends BaseTest {

	/**
	 * A successful case
	 */
	@Test
	public void noExTestCall() {

		final Map<String, String> authParams = super.prepareApiCall();

		final AuthCall call = new AuthCall(config, authParams, null);
		final JSONObject result = call.execute();

		Assert.assertNotNull(result);
		
	}

	/**
	 * Test for payment with 3DSV2 (External Auth)
	 */
	@Test
	public void testThreeDSecureV2Parameters() {

		final Map<String, String> authParams = super.prepareApiCall();

		final AuthCall call = new AuthCall(config, this.add3DSV2Parameters(authParams), null);
		final JSONObject result = call.execute();

		Assert.assertNotNull(result);
		Assert.assertEquals("success", result.get("result"));
		Assert.assertEquals("NOT_SET_FOR_CAPTURE", result.getString("status"));

	}

	/**
	 * Test for payment with 3DSV2 (Challenge Flow)
	 */
	@Test
	public void testThreeDSecureV2ParametersWithCR() {

		final Map<String, String> authParams = super.prepareApiCall();

		final AuthCall call = new AuthCall(config, this.add3DSV2ParametersNoExt(authParams), null);
		final JSONObject result = call.execute();

		Assert.assertNotNull(result);
		Assert.assertEquals("INCOMPLETE", result.getString("status"));
		Assert.assertEquals("redirection", result.get("result"));
		Assert.assertNotNull(result.get("redirectionUrl"));
		Assert.assertEquals(config.getProperty("application.merchantId"), result.get("merchantId"));

	}

	@Test
	public void redirectionResponseTestCall() {

		final Map<String, String> authParams = super.prepareApiCall();

		final AuthCall call = new AuthCall(config, authParams, null);

		final JSONObject result = call.execute();
		Assert.assertEquals(result.get("result"), "redirection");

	}

	/**
	 * {@link TurnkeyValidationException} test (intentionally left out param)
	 */
	@Test(expected = TurnkeyValidationException.class)
	public void missingParameterTest() {

		try {

			final Map<String, String> inputParams = super.prepareApiCall();
			inputParams.remove("amount");

			final AuthCall call = new AuthCall(config, inputParams, null);
			call.execute();

		} catch (final TurnkeyValidationException e) {

			Assert.assertEquals(ErrorType.VALIDATION_ERROR.getDescription() + ": " + Arrays.asList("amount").toString(), e.getMessage());
			throw e;

		}

	}

}
