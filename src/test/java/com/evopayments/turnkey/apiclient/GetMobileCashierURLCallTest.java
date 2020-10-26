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

        // 3DSV2 parameters
        tokenizeParams.put("merchantReference", "myFavourite45thPresident");
        tokenizeParams.put("customerRegistrationDate", "12/10/2020");
        tokenizeParams.put("merchantChallengeInd", "01");
        tokenizeParams.put("merchantDecReqInd", "N");
        tokenizeParams.put("merchantDecMaxTime", "10080");
        tokenizeParams.put("userDevice", "MOBILE");
        tokenizeParams.put("userAgent", "okhttp/4.3.1");
        tokenizeParams.put("customerIPAddress", "31.60.88.55");
        tokenizeParams.put("customerAccountInfo", "customerAccountInfo JsonObject");
        tokenizeParams.put("customerAddressHouseName", "floating bricks");
        tokenizeParams.put("customerAddressHouseNumber", "12");
        tokenizeParams.put("customerAddressFlat", "very");
        tokenizeParams.put("customerAddressStreet", "Legoland");
        tokenizeParams.put("customerAddressCity", "Aviles");
        tokenizeParams.put("customerAddressDistrict", "ninth");
        tokenizeParams.put("customerAddressPostalCode", "25-322");
        tokenizeParams.put("customerAddressCountry", "PL");
        tokenizeParams.put("customerAddressState", "Texas");
        tokenizeParams.put("customerAddressPhone", "call my dog");

        tokenizeParams.put("customerBillingAddressHouseName", "woo haa");
        tokenizeParams.put("customerBillingAddressHouseNumber", "3.1415");
        tokenizeParams.put("customerBillingAddressFlat", "less than the other");
        tokenizeParams.put("customerBillingAddressStreet", "Curvy");
        tokenizeParams.put("customerBillingAddressCity", "Billburg");
        tokenizeParams.put("customerBillingAddressDistrict", "tenth");
        tokenizeParams.put("customerBillingAddressPostalCode", "000000");
        tokenizeParams.put("customerBillingAddressCountry", "PL");
        tokenizeParams.put("customerBillingAddressState", "North Florida");
        tokenizeParams.put("customerBillingAddressPhone", "blue one");

        tokenizeParams.put("customerShippingAddressHouseName", "Swoo haa");
        tokenizeParams.put("customerShippingAddressHouseNumber", "S3.1415");
        tokenizeParams.put("customerShippingAddressFlat", "Sless than the other");
        tokenizeParams.put("customerShippingAddressStreet", "SCurvy");
        tokenizeParams.put("customerShippingAddressCity", "SBillburg");
        tokenizeParams.put("customerShippingAddressDistrict", "Stenth");
        tokenizeParams.put("customerShippingAddressPostalCode", "S000000");
        tokenizeParams.put("customerShippingAddressCountry", "PL");
        tokenizeParams.put("customerShippingAddressState", "SNorth Florida");
        tokenizeParams.put("customerShippingAddressPhone", "Sblue one");

        tokenizeParams.put("merchantAuthInfo", "merchantAuthInfo JSON Object");
        tokenizeParams.put("merchantPriorAuthInfo", "merchantPriorAuthInfo JSON Object");
        tokenizeParams.put("merchantRiskIndicator", "merchantRiskIndicator JSON Object");

        return tokenizeParams;
    }

    /**
     * successful case
     */
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

    /**
     *  Test for 3DSV2 parameters
     */
    @Test
    public void testThreeDSecureV2Parameters() {
        final Map<String, String> tokenizeParams = buildTokenizeParam();
        final GetMobileCashierURLCall call = new GetMobileCashierURLCall(config, tokenizeParams, null);
        Map<String, String> requestParams = call.getTokenParams(tokenizeParams);
        assertEquals("myFavourite45thPresident",  requestParams.get("merchantReference"));
        assertEquals("12/10/2020",  requestParams.get("customerRegistrationDate"));
        assertEquals("01",  requestParams.get("merchantChallengeInd"));
        assertEquals("N",  requestParams.get("merchantDecReqInd"));
        assertEquals("10080",  requestParams.get("merchantDecMaxTime"));
        assertEquals("MOBILE",  requestParams.get("userDevice"));
        assertEquals("okhttp/4.3.1",  requestParams.get("userAgent"));
        assertEquals("31.60.88.55",  requestParams.get("customerIPAddress"));
        assertEquals("floating bricks",  requestParams.get("customerAddressHouseName"));
        assertEquals("Curvy",  requestParams.get("customerBillingAddressStreet"));
        assertEquals("SBillburg",  requestParams.get("customerShippingAddressCity"));
        assertEquals("customerAccountInfo JsonObject",  requestParams.get("customerAccountInfo"));
        assertEquals("merchantAuthInfo JSON Object",  requestParams.get("merchantAuthInfo"));
        assertEquals("merchantPriorAuthInfo JSON Object",  requestParams.get("merchantPriorAuthInfo"));
        assertEquals("merchantRiskIndicator JSON Object",  requestParams.get("merchantRiskIndicator"));
    }
}
