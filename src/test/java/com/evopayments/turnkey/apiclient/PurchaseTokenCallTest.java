package com.evopayments.turnkey.apiclient;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Test for PurchaseTokenCall logic
 */
public class PurchaseTokenCallTest extends BaseTest {

    /**
     * Test for token response with 3DSV2
     */
    @Test
    public void testThreeDSecureV2Parameters() {
        // TOKENIZE
        final Map<String, String> tokenizeParams = super.buildTokenizeParam();

        final TokenizeCall tokenize = new TokenizeCall(config, tokenizeParams, null);
        final JSONObject tokenizeCall = tokenize.execute();

        // PURCHASE
        final Map<String, String> purchaseParams = new HashMap<>();
        super.addCommonParams(purchaseParams);
        purchaseParams.put("amount", "22.0");
        purchaseParams.put("customerId", tokenizeCall.getString("customerId"));
        purchaseParams.put("specinCreditCardToken", tokenizeCall.getString("cardToken"));
        purchaseParams.put("specinCreditCardCVV", "111");

        final PurchaseTokenCall call = new PurchaseTokenCall(config, add3DSV2Parameters(purchaseParams), null);
        JSONObject result = call.execute();

        Assert.assertNotNull(result);
        Assert.assertEquals("success", result.getString("result"));
        Assert.assertNotNull(result.getString("token"));
    }
}
