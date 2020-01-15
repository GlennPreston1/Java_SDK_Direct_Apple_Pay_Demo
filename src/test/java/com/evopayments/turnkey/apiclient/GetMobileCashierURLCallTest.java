package com.evopayments.turnkey.apiclient;

import com.evopayments.turnkey.apiclient.code.Channel;
import com.evopayments.turnkey.apiclient.code.CountryCode;
import com.evopayments.turnkey.apiclient.code.CurrencyCode;
import com.evopayments.turnkey.apiclient.exception.TurnkeyValidationException;
import com.evopayments.turnkey.config.ApplicationConfig;
import com.evopayments.turnkey.config.TestConfig;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class GetMobileCashierURLCallTest extends  BaseTest{

	private static ApplicationConfig config;

	protected Map<String, String> buildTokenizeParam(){
		Map<String, String> tokenizeParams = new HashMap<>();
		tokenizeParams.put("action", "AUTH");
		tokenizeParams.put("amount", "20.0");
		tokenizeParams.put("channel", Channel.ECOM.getCode());
		tokenizeParams.put("country", CountryCode.PL.getCode());
		tokenizeParams.put("currency", CurrencyCode.PLN.getCode());
		tokenizeParams.put("paymentSolutionId", "500");
		tokenizeParams.put("customerId", "8Gii57iYNVSd27xnFZzR");


		return tokenizeParams;
	}

	@BeforeClass
	public static void setUp() {
		config = TestConfig.getInstance();
	}

	/**
	 * successful case
	 */
	@Test
	public void noExTestCall() {
		final Map<String, String> tokenizeParams = buildTokenizeParam();
		final GetMobileCashierURLCall call = new GetMobileCashierURLCall(config, tokenizeParams, null);
		JSONObject result = call.execute();

		// note that any error will cause the throwing of some kind of SDKException (which extends RuntimeException)
		// still we make an assertNotNull

		Assert.assertNotNull(result);

	}

	/**
	 * RequiredParamException test (intentionally left out param)
	 */
	@Test(expected = TurnkeyValidationException.class)
	public void reqParExExpTestCall() {
		try {

			final Map<String, String> inputParams = new HashMap<>();
			inputParams.put("action", "AUTH");
			inputParams.put("amount", "20.0");
			inputParams.put("channel", Channel.ECOM.getCode());
//			inputParams.put("country", CountryCode.PL.getCode()); // left out field
//			inputParams.put("currency", CurrencyCode.PLN.getCode()); // left out field
			inputParams.put("paymentSolutionId", "500");

			final GetMobileCashierURLCall call = new GetMobileCashierURLCall(config, inputParams, null);
			call.execute();

		} catch (TurnkeyValidationException e) {

			Assert.assertEquals(new TurnkeyValidationException().getTurnkeyValidationErrorDescription() + ":" + Arrays.asList("country", "currency").toString(),e.getMessage());
			throw e;

		}
	}

}
