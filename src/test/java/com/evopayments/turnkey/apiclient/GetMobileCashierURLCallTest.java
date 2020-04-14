package com.evopayments.turnkey.apiclient;

import com.evopayments.turnkey.apiclient.code.Channel;
import com.evopayments.turnkey.apiclient.code.CountryCode;
import com.evopayments.turnkey.apiclient.code.CurrencyCode;
import com.evopayments.turnkey.apiclient.exception.TurnkeyValidationException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class GetMobileCashierURLCallTest extends BaseTest {

  protected Map<String, String> buildTokenizeParam() {
    Map<String, String> tokenizeParams = super.buildTokenizeParam();
    tokenizeParams.put("action", "AUTH");
    tokenizeParams.put("amount", "20.0");
    tokenizeParams.put("channel", Channel.ECOM.getCode());
    tokenizeParams.put("country", CountryCode.PL.getCode());
    tokenizeParams.put("currency", CurrencyCode.PLN.getCode());
    tokenizeParams.put("paymentSolutionId", "500");
    tokenizeParams.put("customerId", "8Gii57iYNVSd27xnFZzR");
    tokenizeParams.put("expiryYear", "21");
    for (int counter = 1; counter < 20; counter++) {
      tokenizeParams.put(String.format("customParameter%dOr", counter),
              String.format("example custom param %d", counter));
    }

    return tokenizeParams;
  }
  /** successful case */
  @Test
  public void noExTestCall() {
    final Map<String, String> tokenizeParams = buildTokenizeParam();
    final GetMobileCashierURLCall call = new GetMobileCashierURLCall(config, tokenizeParams, null);
    JSONObject result = call.execute();

    // note that any error will cause the throwing of some kind of SDKException (which extends
    // RuntimeException)
    // still we make an assertNotNull

    Assert.assertNotNull(result);
  }

  /** RequiredParamException test (intentionally left out param) */
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

      assertEquals(
          new TurnkeyValidationException().getTurnkeyValidationErrorDescription()
              + ":"
              + Arrays.asList("country", "currency").toString(),
          e.getMessage());
      throw e;
    }
  }

  @Test
  public void testCustomParameters() {
    final Map<String, String> tokenizeParams = buildTokenizeParam();
    final GetMobileCashierURLCall call = new GetMobileCashierURLCall(config, tokenizeParams, null);
    Map<String, String> requestParams = call.getTokenParams(tokenizeParams);
    for (int counter = 1; counter < 20; counter++) {
      assertEquals(String.format("example custom param %d", counter),
              requestParams.get(String.format("customParameter%dOr", counter)));
    }
  }
}
