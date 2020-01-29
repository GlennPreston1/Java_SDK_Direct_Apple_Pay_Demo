package com.evopayments.turnkey.apiclient;

import com.evopayments.turnkey.apiclient.code.Channel;
import com.evopayments.turnkey.apiclient.exception.TurnkeyValidationException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class PurchaseCallTest extends  BaseTest{

	private String SAMPLE_TX_ID = "TX_ID_OF_THE_FIRST_TRANSACTOIN";

	/**
	 * successful case.
	 */
	// purchase cannot be tested this way (a background job will change the status of transaction, it can take a long time...)
	/*
	@Test
	public void noExTestCall() {

		// TOKENIZE
		final Map<String, String> tokenizeParams = super.buildTokenizeParam();

		final TokenizeCall tokenize = new TokenizeCall(config, tokenizeParams, null);
		final JSONObject tokenizeCall = tokenize.execute();

		// PURCHASE
		final Map<String, String> purchaseParams = new HashMap<>();
		super.addCommonParams(purchaseParams);
		purchaseParams.put("amount", "20.0");
		purchaseParams.put("customerId", tokenizeCall.getString("customerId"));
		purchaseParams.put("specinCreditCardToken", tokenizeCall.getString("cardToken"));
		purchaseParams.put("specinCreditCardCVV", "111");

		final PurchaseCall call = new PurchaseCall(config, purchaseParams, null);
		JSONObject result = call.execute();

		// note that any error will cause the throwing of some kind of SDKException (which extends RuntimeException)
		// still we make an assertNotNull

		Assert.assertNotNull(result);
	}*/

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

			final PurchaseCall call = new PurchaseCall(config, inputParams, null);
			call.execute();

		} catch (TurnkeyValidationException e) {
			Assert.assertEquals(new TurnkeyValidationException().getTurnkeyValidationErrorDescription() + ":" + Arrays.asList( "country", "currency").toString(),e.getMessage());
			throw e;

		}
	}

	@Test
	public void CardOnFileFirstTestCall(){
		// TOKENIZE
		final Map<String, String> tokenizeParams = super.buildTokenizeParam();


		final TokenizeCall tokenize = new TokenizeCall(config, tokenizeParams, null);
		final JSONObject tokenizeCall = tokenize.execute();

		// PURCHASE (CardOnFile)
		final Map<String, String> purchaseParams = new HashMap<>();
		super.addCommonParams(purchaseParams);
		purchaseParams.put("amount", "20.0");
		purchaseParams.put("customerId", tokenizeCall.getString("customerId"));
		purchaseParams.put("specinCreditCardToken", tokenizeCall.getString("cardToken"));
		purchaseParams.put("specinCreditCardCVV", "111");

		final PurchaseCall call = new PurchaseCall(config, purchaseParams, null, PurchaseCall.SUB_ACTION_COF_FIRST);
		JSONObject result = call.execute();

		// note that any error will cause the throwing of some kind of SDKException (which extends RuntimeException)
		// still we make an assertNotNull

		Assert.assertNotNull(result);
	}


	@Test
	public void CardOnFileRecurringTestCall(){

		/********** Replace the merchantTxId with the transaction id from the CardOnFileFirstTestCall test case ***/
		String merchantTxId = SAMPLE_TX_ID;
//		String merchantTxId = "u8ALJCxdtgpRuCAaOdjZ";
		if(SAMPLE_TX_ID.equals(merchantTxId)) {
			System.out.println("**** Warning: to run recurring payment, please assign the merchantTxId of the 'First' transaction to variable  [ merchantTxId ] ");
			return;
		}


		// TOKENIZE
		final Map<String, String> tokenizeParams = super.buildTokenizeParam();


		final TokenizeCall tokenize = new TokenizeCall(config, tokenizeParams, null);
		final JSONObject tokenizeCall = tokenize.execute();

		// PURCHASE (CardOnFile)
		final Map<String, String> purchaseParams = new HashMap<>();
		super.addCommonParams(purchaseParams);
		purchaseParams.put("amount", "20.0");
		purchaseParams.put("customerId", tokenizeCall.getString("customerId"));
		purchaseParams.put("specinCreditCardToken", tokenizeCall.getString("cardToken"));
		purchaseParams.put("specinCreditCardCVV", "111");
		purchaseParams.put("cardOnFileInitialTransactionId",merchantTxId);

		final PurchaseCall call = new PurchaseCall(config, purchaseParams, null,PurchaseCall.SUB_ACTION_COF_RECURRING);
		JSONObject result = call.execute();

		// note that any error will cause the throwing of some kind of SDKException (which extends RuntimeException)
		// still we make an assertNotNull

		Assert.assertNotNull(result);
		Assert.assertEquals("redirection", result.getString("result"));
	}

	@Test
	public void MmrpFirstTestCall(){
		// TOKENIZE
		final Map<String, String> tokenizeParams = super.buildTokenizeParam();


		final TokenizeCall tokenize = new TokenizeCall(config, tokenizeParams, null);
		final JSONObject tokenizeCall = tokenize.execute();

		// PURCHASE - MMRP
		final Map<String, String> purchaseParams = new HashMap<>();
		super.addCommonParams(purchaseParams);
		purchaseParams.put("amount", "99.0");
		purchaseParams.put("customerId", tokenizeCall.getString("customerId"));
		purchaseParams.put("specinCreditCardToken", tokenizeCall.getString("cardToken"));
		purchaseParams.put("specinCreditCardCVV", "111");

		final PurchaseCall call = new PurchaseCall(config, purchaseParams, null, PurchaseCall.SUB_ACTION_MMRP_FIRST);
		JSONObject result = call.execute();

		// note that any error will cause the throwing of some kind of SDKException (which extends RuntimeException)
		// still we make an assertNotNull

		Assert.assertNotNull(result);
	}



	@Test
	public void MmrpRecurringTestCall(){
/********** Replace the merchantTxId with the transaction id from the MmrpFirstTestCall test case ***/
		String merchantTxId = SAMPLE_TX_ID;
//		String merchantTxId = "bV1xrfmRbwkXQBYBeGQr";
		if(SAMPLE_TX_ID.equals(merchantTxId)) {
			System.out.println("**** Warning: to run recurring payment, please assign the merchantTxId of the 'First' transaction to variable  [ merchantTxId ] ");
			return;
		}

		// TOKENIZE
		final Map<String, String> tokenizeParams = super.buildTokenizeParam();

		final TokenizeCall tokenize = new TokenizeCall(config, tokenizeParams, null);
		final JSONObject tokenizeCall = tokenize.execute();

		// PURCHASE (CardOnFile)
		final Map<String, String> purchaseParams = new HashMap<>();
		super.addCommonParams(purchaseParams);
		purchaseParams.put("amount", "97.0");
		purchaseParams.put("customerId", tokenizeCall.getString("customerId"));
		purchaseParams.put("specinCreditCardToken", tokenizeCall.getString("cardToken"));
		purchaseParams.put("specinCreditCardCVV", "111");
		purchaseParams.put("cardOnFileInitialTransactionId",merchantTxId);
		purchaseParams.put("mmrpOriginalMerchantTransactionId",merchantTxId);

		final PurchaseCall call = new PurchaseCall(config, purchaseParams, null,PurchaseCall.SUB_ACTION_MMRP_RECURRING);
		JSONObject result = call.execute();

		// note that any error will cause the throwing of some kind of SDKException (which extends RuntimeException)
		// still we make an assertNotNull

		Assert.assertNotNull(result);
		Assert.assertEquals("success", result.getString("result"));
	}

	@Test
	public void MmrpFirstMxTestCall(){
		// TOKENIZE
		final Map<String, String> tokenizeParams = super.buildTokenizeParam();

		final TokenizeCall tokenize = new TokenizeCall(config, tokenizeParams, new PrintWriter(System.out));
		final JSONObject tokenizeCall = tokenize.execute();

		// PURCHASE - MMRP
		final Map<String, String> purchaseParams = new HashMap<>();
		super.addCommonParams(purchaseParams);
		purchaseParams.put("amount", "200.0");
		purchaseParams.put("customerId", tokenizeCall.getString("customerId"));
		purchaseParams.put("specinCreditCardToken", tokenizeCall.getString("cardToken"));
		purchaseParams.put("specinCreditCardCVV", "111");
		purchaseParams.put("mmrpContractNumber","1234");
		purchaseParams.put("mmrpExistingDebt","NotExistingDebt");
		purchaseParams.put("mmrpCurrentInstallmentNumber","1");

		final PurchaseCall call = new PurchaseCall(config, purchaseParams, null, PurchaseCall.SUB_ACTION_MMRP_FIRST);
		JSONObject result = call.execute();

		// note that any error will cause the throwing of some kind of SDKException (which extends RuntimeException)
		// still we make an assertNotNull
		Assert.assertNotNull(result);
	}



	/**
	 * MMRP for MX Merchant
	 */
	@Test
	public void MmrpRecurringMxTestCall(){
/********** Replace the merchantTxId with the transaction id from the MmrpFirstMxTestCall test case ***/
		String merchantTxId = SAMPLE_TX_ID;
//		String merchantTxId = "VjUgGXxA6aiUPU54tjPA";
		if(SAMPLE_TX_ID.equals(merchantTxId)) {
			System.out.println("**** Warning: to run recurring payment, please assign the merchantTxId of the 'First' transaction to variable  [ merchantTxId ] ");
			return;
		}


		// TOKENIZE
		final Map<String, String> tokenizeParams = super.buildTokenizeParam();


		final TokenizeCall tokenize = new TokenizeCall(config, tokenizeParams, new PrintWriter(System.out));
		final JSONObject tokenizeCall = tokenize.execute();

		// PURCHASE
		final Map<String, String> purchaseParams = new HashMap<>();
		super.addCommonParams(purchaseParams);
		purchaseParams.put("amount", "980.0");
		purchaseParams.put("customerId", tokenizeCall.getString("customerId"));
		purchaseParams.put("specinCreditCardToken", tokenizeCall.getString("cardToken"));
		purchaseParams.put("specinCreditCardCVV", "111");
		purchaseParams.put("cardOnFileInitialTransactionId",merchantTxId);

		purchaseParams.put("mmrpContractNumber","1234");
		purchaseParams.put("mmrpOriginalMerchantTransactionId",merchantTxId);


		final PurchaseCall call = new PurchaseCall(config, purchaseParams, null,PurchaseCall.SUB_ACTION_MMRP_RECURRING);
		JSONObject result = call.execute();

		// note that any error will cause the throwing of some kind of SDKException (which extends RuntimeException)
		// still we make an assertNotNull

		Assert.assertNotNull(result);
		Assert.assertEquals("success", result.getString("result"));
	}



}
