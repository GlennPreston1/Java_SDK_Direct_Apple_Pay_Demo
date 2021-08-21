package com.evopayments.turnkey.apiclient;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

public class StatusCheckCallTest extends BaseTest {

	/**
	 * Successful case
	 */
	@Test
	public void noExTestCall() {

		// TOKENIZE
		final Map<String, String> tokenizeParams = super.buildTokenizeParamMap();

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

		final Map<String, String> inputParams = new HashMap<>();
		super.addCommonParams(inputParams);
		inputParams.put("txId", authCall.getString("txId"));
		inputParams.put("merchantTxId", authCall.getString("merchantTxId"));

		final StatusCheckCall call = new StatusCheckCall(config, inputParams, null);
		final JSONObject result = call.execute();

		Assert.assertNotNull(result);

	}

}
