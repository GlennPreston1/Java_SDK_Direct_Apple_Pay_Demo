package com.evopayments.turnkey.apiclient;

import com.evopayments.turnkey.apiclient.exception.TurnkeyValidationException;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class VoidCallTest extends BaseTest{
	/**
	 * successful case.
	 */
	// void cannot be tested this way (a background job will change the status of transaction, it can take a long time...)
	/*
	@Test
	public void noExTestCall() {

		// TOKENIZE
		final Map<String, String> tokenizeParams = super.buildTokenizeParam();

		final TokenizeCall tokenize = new TokenizeCall(config, tokenizeParams, null);
		final JSONObject tokenizeCall = tokenize.execute();

		// AUTH
		final Map<String, String> authParams = new HashMap<>();
		super.addCommonParams(authParams);
		authParams.put("amount", "20.0");
		authParams.put("customerId", tokenizeCall.getString("customerId"));
		authParams.put("specinCreditCardToken", tokenizeCall.getString("cardToken"));
		authParams.put("specinCreditCardCVV", "111");

		final AuthCall auth = new AuthCall(config, authParams, null);
		final JSONObject authCall = auth.execute();

		// VOID
		final Map<String, String> inputParams = new HashMap<>();
		inputParams.put("originalMerchantTxId", authCall.getString("merchantTxId"));
		inputParams.put("country", "FR");
		inputParams.put("currency", "EUR");

		final VoidCall call = new VoidCall(config, inputParams, null);
		JSONObject result = call.execute();

		// note that any error will cause the throwing of some kind of SDKException (which extends RuntimeException)
		// still we make an assertNotNull

		Assert.assertNotNull(result);

	}*/

	/**
	 * RequiredParamException test.
	 */
	@Test(expected = TurnkeyValidationException.class)
	public void reqParExExpTestCall() {

		try {

			final Map<String, String> inputParams = new HashMap<>();
			inputParams.put("originalMerchantTxId", "8Gii57iYNVSd27xnFZzR");
			// inputParams.put("country", "FR"); // left out field
			// inputParams.put("currency", "EUR"); // left out field

			final VoidCall call = new VoidCall(config, inputParams, null);
			call.execute();

		} catch (TurnkeyValidationException e) {
			Assert.assertEquals(new TurnkeyValidationException().getTurnkeyValidationErrorDescription() + ":" + Arrays.asList("country","currency").toString(),e.getMessage());
			throw e;
			
		}
	}
}
