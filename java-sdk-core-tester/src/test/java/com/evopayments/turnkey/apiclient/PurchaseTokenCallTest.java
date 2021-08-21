package com.evopayments.turnkey.apiclient;

import java.util.Map;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

public class PurchaseTokenCallTest extends BaseTest {

	/**
	 * Test for token response with 3DSV2
	 */
	@Test
	public void testThreeDSecureV2Parameters() {

		// PURCHASE
		final Map<String, String> purchaseParams = super.prepareApiCall();

		final PurchaseTokenCall call = new PurchaseTokenCall(config, this.add3DSV2Parameters(purchaseParams), null);
		final JSONObject result = call.execute();

		Assert.assertNotNull(result);
		Assert.assertEquals("success", result.getString("result"));
		Assert.assertNotNull(result.getString("token"));
	}
	
}
