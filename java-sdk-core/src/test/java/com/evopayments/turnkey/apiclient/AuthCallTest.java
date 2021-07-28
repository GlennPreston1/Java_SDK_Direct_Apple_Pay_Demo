package com.evopayments.turnkey.apiclient;

import com.evopayments.turnkey.apiclient.exception.TurnkeyValidationException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Map;


public class AuthCallTest extends BaseTest {

	/**
	 * successful case.
	 */
	@Test
	public void noExTestCall() {

        final Map<String, String> authParams = super.prepareAPICall();

		final AuthCall call = new AuthCall(config, authParams, null);
		JSONObject result = call.execute();

		// note that any error will cause the throwing of some kind of SDKException (which extends RuntimeException)
		// still we make an assertNotNull

		Assert.assertNotNull(result);

	}

    /**
     * Test for payment with 3DSV2 (External Auth)
     */
    @Test
    public void testThreeDSecureV2Parameters() {

        final Map<String, String> authParams = super.prepareAPICall();

        final AuthCall call = new AuthCall(config, add3DSV2Parameters(authParams), null);
        JSONObject result = call.execute();

        Assert.assertNotNull(result);
        Assert.assertEquals("success", result.get("result"));
        Assert.assertEquals("NOT_SET_FOR_CAPTURE", result.getString("status"));
    }

    /**
     * Test for payment with 3DSV2 (Challenge Flow)
     */
    @Test
    public void testThreeDSecureV2ParametersWithCR() {

        final Map<String, String> authParams = super.prepareAPICall();

        final AuthCall call = new AuthCall(config, add3DSV2ParametersNoExt(authParams), null);
        JSONObject result = call.execute();

        Assert.assertNotNull(result);
        Assert.assertEquals("INCOMPLETE", result.getString("status"));
        Assert.assertEquals("redirection", result.get("result"));
        Assert.assertNotNull(result.get("redirectionUrl"));
        Assert.assertEquals(config.getProperty("application.merchantId"), result.get("merchantId"));
    }

	/**
	 *  able to get the token.
	 */
	@Test
	public void redirectionResponseTestCall() {

        final Map<String, String> authParams = super.prepareAPICall();

		final AuthCall call = new AuthCall(config, authParams, null);

		JSONObject result = call.execute();
		Assert.assertEquals(result.get("result"), "redirection");

	}

	/**
	 * RequiredParamException test (intentionally left out param).
	 */
	@Test(expected = TurnkeyValidationException.class)
	public void reqParExExpTestCall() {

        try {
            final Map<String, String> inputParams = super.prepareAPICall();
            inputParams.remove("currency");

            final AuthCall call = new AuthCall(config, inputParams, null);
            call.execute();

        } catch (TurnkeyValidationException e) {
            Assert.assertEquals(new TurnkeyValidationException().getTurnkeyValidationErrorDescription() + ":" + Arrays.asList("currency").toString(), e.getMessage());
            throw e;

        }
	}
}
