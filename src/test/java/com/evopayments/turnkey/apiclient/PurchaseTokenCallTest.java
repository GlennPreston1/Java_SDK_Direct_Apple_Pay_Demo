package com.evopayments.turnkey.apiclient;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

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
        // PURCHASE
        final Map<String, String> purchaseParams = super.prepareAPICall();

        final PurchaseTokenCall call = new PurchaseTokenCall(config, add3DSV2Parameters(purchaseParams), null);
        JSONObject result = call.execute();

        Assert.assertNotNull(result);
        Assert.assertEquals("success", result.getString("result"));
        Assert.assertNotNull(result.getString("token"));
    }
}
