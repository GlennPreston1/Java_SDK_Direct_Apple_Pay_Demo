package com.evopayments.turnkey.apiclient;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.evopayments.turnkey.apiclient.exception.ErrorType;
import com.evopayments.turnkey.apiclient.exception.TurnkeyValidationException;

public class RefundCallTest extends BaseTest {

	// Cannot be tested fully this way (a background job will change the status of the transaction, it can take a long time...).

	/**
	 * {@link TurnkeyValidationException} test (intentionally left out param)
	 */
	@Test(expected = TurnkeyValidationException.class)
	public void missingParameterTest() {

		try {

			final Map<String, String> inputParams = new HashMap<>();
			// inputParams.put("originalMerchantTxId", "xy01")); // left out
			// inputParams.put("amount", "20.0"); // left out
			inputParams.put("country", "FR");
			inputParams.put("currency", "EUR"); 

			final RefundCall call = new RefundCall(config, inputParams);
			call.execute();

		} catch (final TurnkeyValidationException e) {
			Assert.assertEquals(ErrorType.VALIDATION_ERROR.getDescription() + ": " + Arrays.asList("amount", "originalMerchantTxId").toString(), e.getMessage());
			throw e;
		}

	}

}
