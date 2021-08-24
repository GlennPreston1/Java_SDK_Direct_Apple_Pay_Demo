package com.evopayments.turnkey.apiclient;

import java.util.Map;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import com.evopayments.turnkey.apiclient.code.SubActionType;

/**
 * CardOnFile / Recurring test cases
 */
public class PurchaseCallTest2 extends BaseTest {

	@Test
	public void cardOnFileFirstAndRecurringTestCall() {

		final String merchantTxId;
		final String redirectionUrl;

		{

			// PURCHASE (CardOnFile), First

			final Map<String, String> purchaseParams = super.prepareApiCall();

			final PurchaseCall call = new PurchaseCall(config, purchaseParams, null, SubActionType.SUB_ACTION_COF_FIRST);
			final JSONObject result = call.execute();

			Assert.assertNotNull(result);

			merchantTxId = result.getString("merchantTxId");
			redirectionUrl = result.getString("redirectionUrl");
		}
		
		Assert.assertNotNull(merchantTxId);
		Assert.assertNotNull(redirectionUrl);

		// TODO: cannot be tested fully this way... the redirection has to be handled from the first part of this test... plus probably takes some time to process etc.
		
		// {
		//
		// // ---
		//
		// // PURCHASE (CardOnFile), Recurring
		//
		// final Map<String, String> purchaseParams = super.prepareApiCall();
		// purchaseParams.put("cardOnFileInitialTransactionId", merchantTxId);
		//
		// final PurchaseCall call = new PurchaseCall(config, purchaseParams, null, SubActionType.SUB_ACTION_COF_RECURRING);
		// final JSONObject result = call.execute();
		//
		// Assert.assertNotNull(result);
		// Assert.assertEquals("redirection", result.getString("result"));
		// }

	}

	@Test
	public void mmrpFirstAndRecuringTestCall() {

		final String merchantTxId;
		final String redirectionUrl;

		{

			// First

			final Map<String, String> purchaseParams = super.prepareApiCall();

			final PurchaseCall call = new PurchaseCall(config, purchaseParams, null, SubActionType.SUB_ACTION_MMRP_FIRST);
			final JSONObject result = call.execute();

			Assert.assertNotNull(result);

			merchantTxId = result.getString("merchantTxId");
			redirectionUrl = result.getString("redirectionUrl");

		}
		
		Assert.assertNotNull(merchantTxId);
		Assert.assertNotNull(redirectionUrl);

		// TODO: cannot be tested fully this way... the redirection has to be handled from the first part of this test... plus probably takes some time to process etc.
		
		// {
		//
		// // Recurring
		//
		// final Map<String, String> purchaseParams = super.prepareApiCall();
		// purchaseParams.put("cardOnFileInitialTransactionId", merchantTxId);
		// purchaseParams.put("mmrpOriginalMerchantTransactionId", merchantTxId);
		//
		// final PurchaseCall call = new PurchaseCall(config, purchaseParams, null, SubActionType.SUB_ACTION_MMRP_RECURRING);
		// final JSONObject result = call.execute();
		//
		// Assert.assertNotNull(result);
		// Assert.assertEquals("success", result.getString("result"));
		//
		// }

	}

	@Test
	public void mmrpFirstAndRecurringMxTestCall() {

		final String merchantTxId;
		final String redirectionUrl;

		{

			// First

			final Map<String, String> purchaseParams = super.prepareApiCall();
			purchaseParams.put("mmrpContractNumber", "1234");
			purchaseParams.put("mmrpExistingDebt", "NotExistingDebt");
			purchaseParams.put("mmrpCurrentInstallmentNumber", "1");

			final PurchaseCall call = new PurchaseCall(config, purchaseParams, null, SubActionType.SUB_ACTION_MMRP_FIRST);
			final JSONObject result = call.execute();

			Assert.assertNotNull(result);

			merchantTxId = result.getString("merchantTxId");
			redirectionUrl = result.getString("redirectionUrl");
			
		}
		
		Assert.assertNotNull(merchantTxId);
		Assert.assertNotNull(redirectionUrl);
		
		// TODO: cannot be tested fully this way... the redirection has to be handled from the first part of this test... plus probably takes some time to process etc.

		// {
		//
		// // Recurring
		//
		// final Map<String, String> purchaseParams = super.prepareApiCall();
		// purchaseParams.put("cardOnFileInitialTransactionId", merchantTxId);
		//
		// purchaseParams.put("mmrpContractNumber", "1234");
		// purchaseParams.put("mmrpOriginalMerchantTransactionId", merchantTxId);
		//
		// final PurchaseCall call = new PurchaseCall(config, purchaseParams, null, SubActionType.SUB_ACTION_MMRP_RECURRING);
		// final JSONObject result = call.execute();
		//
		// Assert.assertNotNull(result);
		// Assert.assertEquals("success", result.getString("result"));
		//
		// }

	}

	/**
	 * Test for payment with 3DSV2 (External Auth)
	 */
	@Test
	public void testThreeDSecureV2Parameters() {

		// PURCHASE
		final Map<String, String> purchaseParams = super.prepareApiCall();

		final PurchaseCall call = new PurchaseCall(config, this.add3DSV2Parameters(purchaseParams), null, null);
		final JSONObject result = call.execute();

		Assert.assertNotNull(result);
		Assert.assertEquals("success", result.get("result"));
		Assert.assertEquals("CAPTURED", result.getString("status"));

	}

	/**
	 * Test for payment with 3DSV2 (Challenge Flow)
	 */
	@Test
	public void testThreeDSecureV2ParametersWithCR() {

		// PURCHASE
		final Map<String, String> purchaseParams = super.prepareApiCall();

		final PurchaseCall call = new PurchaseCall(config, this.add3DSV2ParametersNoExt(purchaseParams), null, null);
		final JSONObject result = call.execute();

		Assert.assertNotNull(result);
		Assert.assertEquals("INCOMPLETE", result.getString("status"));
		Assert.assertEquals("redirection", result.get("result"));
		Assert.assertNotNull(result.get("redirectionUrl"));
		Assert.assertEquals(config.getProperty("application.merchantId"), result.get("merchantId"));

	}

}
