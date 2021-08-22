package com.evopayments.turnkey.apiclient;

import java.util.Arrays;
import java.util.Map;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import com.evopayments.turnkey.apiclient.exception.ErrorType;
import com.evopayments.turnkey.apiclient.exception.TurnkeyValidationException;

public class VerifyCallTest extends BaseTest {

	/**
	 * Successful case
	 */
	@Test
	public void noExTestCall() {

		// VERIFY
		final Map<String, String> verifyParams = super.prepareApiCall();
		verifyParams.put("amount", "0");

		final VerifyCall call = new VerifyCall(config, verifyParams);
		final JSONObject result = call.execute();

		Assert.assertNotNull(result);
		Assert.assertEquals(JSONObject.NULL, result.get("errors"));

	}

	/**
	 * {@link TurnkeyValidationException} test (intentionally left out param)
	 */
	@Test(expected = TurnkeyValidationException.class)
	public void missingParameterTest() {

		try {

			final Map<String, String> inputParams = super.prepareApiCall();
			inputParams.remove("currency");
			inputParams.remove("country");

			final VerifyCall call = new VerifyCall(config, inputParams);
			call.execute();

		} catch (final TurnkeyValidationException e) {
			Assert.assertEquals(ErrorType.VALIDATION_ERROR.getDescription() + ": " + Arrays.asList("country", "currency").toString(), e.getMessage());
			throw e;
		}
	}

	/**
	 * Test for payment with 3DSV2 (External Auth)
	 */
	@Ignore
	@Test
	public void testThreeDSecureV2Parameters() { // FIXME: fails...

		// VERIFY
		final Map<String, String> verifyParams = super.prepareApiCall();
		verifyParams.put("amount", "0");

		final VerifyCall call = new VerifyCall(config, this.add3DSV2Parameters(verifyParams));
		final JSONObject result = call.execute();

		Assert.assertNotNull(result);
		Assert.assertEquals("success", result.get("result"));
		Assert.assertEquals("VERIFIED", result.getString("status"));

	}

	/**
	 * Test for payment with 3DSV2 (Challenge Flow)
	 */
	@Ignore
	@Test
	public void testThreeDSecureV2ParametersWithCR() { // FIXME: fails

		// VERIFY
		final Map<String, String> verifyParams = super.prepareApiCall();
		verifyParams.put("amount", "0");

		final VerifyCall call = new VerifyCall(config, this.add3DSV2ParametersNoExt(verifyParams));
		final JSONObject result = call.execute();

		Assert.assertNotNull(result);
		Assert.assertEquals("INCOMPLETE", result.getString("status"));
		Assert.assertEquals("redirection", result.get("result"));
		Assert.assertNotNull(result.get("redirectionUrl"));

	}

}
