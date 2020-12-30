package com.evopayments.turnkey.apiclient;

import com.evopayments.turnkey.apiclient.code.Channel;
import com.evopayments.turnkey.apiclient.exception.TurnkeyValidationException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class VerifyCallTest extends  BaseTest{

	/**
	 * successful case.
	 */
	@Test
	public void noExTestCall() {

		// TOKENIZE
		final Map<String, String> tokenizeParams = super.buildTokenizeParam();

		final TokenizeCall tokenize = new TokenizeCall(config, tokenizeParams, null);
		final JSONObject tokenizeCall = tokenize.execute();

		// PURCHASE
		final Map<String, String> purchaseParams = new HashMap<>();
		super.addCommonParams(purchaseParams);
		purchaseParams.put("amount", "0");
		purchaseParams.put("customerId", tokenizeCall.getString("customerId"));
		purchaseParams.put("specinCreditCardToken", tokenizeCall.getString("cardToken"));
		purchaseParams.put("specinCreditCardCVV", "111");

		final VerifyCall call = new VerifyCall(config, purchaseParams, null);
		JSONObject result = call.execute();

		// note that any error will cause the throwing of some kind of SDKException (which extends RuntimeException)
		// still we make an assertNotNull

        Assert.assertNotNull(result);
        Assert.assertEquals(JSONObject.NULL, result.get("errors"));

	}

	/**
	 * RequiredParamException test (intentionally left out param).
	 */
	@Test(expected = TurnkeyValidationException.class)
	public void reqParExExpTestCall() {

		try {

			final Map<String, String> inputParams = new HashMap<>();
			inputParams.put("amount", "20.0");
			inputParams.put("channel", Channel.ECOM.getCode());
			// inputParams.put("country", CountryCode.GB.getCode());
			// inputParams.put("currency", CurrencyCode.EUR.getCode());
			inputParams.put("paymentSolutionId", "500");
			inputParams.put("customerId", "8Gii57iYNVSd27xnFZzR");

			final VerifyCall call = new VerifyCall(config, inputParams, null);
			call.execute();

		} catch (TurnkeyValidationException e) {
			Assert.assertEquals(new TurnkeyValidationException().getTurnkeyValidationErrorDescription() + ":" + Arrays.asList("country","currency").toString(),e.getMessage());
			throw e;

		}
	}

    /**
     * Test for payment with 3DSV2 (External Auth)
     */
    @Test
    public void testThreeDSecureV2Parameters() {

        // TOKENIZE
        final Map<String, String> tokenizeParams = super.buildTokenizeParam();

        final TokenizeCall tokenize = new TokenizeCall(config, tokenizeParams, null);
        final JSONObject tokenizeCall = tokenize.execute();

        // PURCHASE
        final Map<String, String> verifyParams = new HashMap<>();
        super.addCommonParams(verifyParams);
        verifyParams.put("amount", "0");
        verifyParams.put("customerId", tokenizeCall.getString("customerId"));
        verifyParams.put("specinCreditCardToken", tokenizeCall.getString("cardToken"));
        verifyParams.put("specinCreditCardCVV", "111");

        final VerifyCall call = new VerifyCall(config, add3DSV2Parameters(verifyParams), null);
        JSONObject result = call.execute();

        Assert.assertNotNull(result);
        Assert.assertEquals("VERIFIED", result.getString("status"));

    }

    /**
     * Test for payment with 3DSV2 (Challenge Flow)
     */
    @Test
    public void testThreeDSecureV2ParametersWithCR() {

        // TOKENIZE
        final Map<String, String> tokenizeParams = super.buildTokenizeParam();

        final TokenizeCall tokenize = new TokenizeCall(config, tokenizeParams, null);
        final JSONObject tokenizeCall = tokenize.execute();

        // PURCHASE
        final Map<String, String> verifyParams = new HashMap<>();
        super.addCommonParams(verifyParams);
        verifyParams.put("amount", "0");
        verifyParams.put("customerId", tokenizeCall.getString("customerId"));
        verifyParams.put("specinCreditCardToken", tokenizeCall.getString("cardToken"));
        verifyParams.put("specinCreditCardCVV", "111");

        final VerifyCall call = new VerifyCall(config, add3DSV2ParametersNoExt(verifyParams), null);
        JSONObject result = call.execute();

        Assert.assertNotNull(result);
        Assert.assertEquals("INCOMPLETE", result.getString("status"));

    }

}
