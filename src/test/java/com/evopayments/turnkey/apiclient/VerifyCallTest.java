package com.evopayments.turnkey.apiclient;

import com.evopayments.turnkey.apiclient.exception.TurnkeyValidationException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Map;

public class VerifyCallTest extends  BaseTest{

	/**
	 * successful case.
	 */
	@Test
	public void noExTestCall() {

        // VERIFY
        final Map<String, String> verifyParams = super.prepareAPICall();
        verifyParams.put("amount", "0");

        final VerifyCall call = new VerifyCall(config, verifyParams, null);
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

            final Map<String, String> inputParams = super.prepareAPICall();
            inputParams.remove("currency");
            inputParams.remove("country");

            final VerifyCall call = new VerifyCall(config, inputParams, null);
            call.execute();

        } catch (TurnkeyValidationException e) {
            Assert.assertEquals(new TurnkeyValidationException().getTurnkeyValidationErrorDescription() + ":" + Arrays.asList("country", "currency").toString(), e.getMessage());
            throw e;

        }
	}

    /**
     * Test for payment with 3DSV2 (External Auth)
     */
    @Test
    public void testThreeDSecureV2Parameters() {

        // VERIFY
        final Map<String, String> verifyParams = super.prepareAPICall();
        verifyParams.put("amount", "0");

        final VerifyCall call = new VerifyCall(config, add3DSV2Parameters(verifyParams), null);
        JSONObject result = call.execute();

        Assert.assertNotNull(result);
        Assert.assertEquals("success", result.get("result"));
        Assert.assertEquals("VERIFIED", result.getString("status"));

    }

    /**
     * Test for payment with 3DSV2 (Challenge Flow)
     */
    @Test
    public void testThreeDSecureV2ParametersWithCR() {

        // VERIFY
        final Map<String, String> verifyParams = super.prepareAPICall();
        verifyParams.put("amount", "0");

        final VerifyCall call = new VerifyCall(config, add3DSV2ParametersNoExt(verifyParams), null);
        JSONObject result = call.execute();

        Assert.assertNotNull(result);
        Assert.assertEquals("INCOMPLETE", result.getString("status"));
        Assert.assertEquals("redirection", result.get("result"));
        Assert.assertNotNull(result.get("redirectionUrl"));

    }

}
