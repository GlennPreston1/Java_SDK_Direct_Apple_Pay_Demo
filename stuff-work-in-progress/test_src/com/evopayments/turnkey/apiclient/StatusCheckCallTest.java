package com.evopayments.turnkey.apiclient;

import com.evopayments.turnkey.apiclient.exception.TurnkeyInternalException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class StatusCheckCallTest extends BaseTest {

	/**
	 * successful case
	 */
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

		final Map<String, String> inputParams = new HashMap<>();
		super.addCommonParams(inputParams);
		inputParams.put("txId", authCall.getString("txId"));
		inputParams.put("merchantTxId", authCall.getString("merchantTxId"));

		final StatusCheckCall call = new StatusCheckCall(config, inputParams, null);
		JSONObject result = call.execute();

		// note that any error will cause the throwing of some kind of SDKException (which extends RuntimeException)
		// still we make an assertNotNull

		Assert.assertNotNull(result);

	}

	/**
	 * ActionCallException test
	 */
	@Test(expected = TurnkeyInternalException.class)
	public void reqParExExpTestCall() {

		final Map<String, String> inputParams = new HashMap<>();
		inputParams.put("merchantId", config.getProperty("application.merchantId"));
		inputParams.put("password", config.getProperty("application.password"));
		// inputParams.put("txId", "11387591"); // intentionally left out
		// inputParams.put("merchantTxId", "11282092"); // intentionally left out

		final StatusCheckCall call = new StatusCheckCall(config, inputParams, null);
		call.execute();

	}
}
