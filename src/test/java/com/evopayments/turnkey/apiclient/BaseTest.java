package com.evopayments.turnkey.apiclient;

import com.evopayments.turnkey.apiclient.code.Channel;
import com.evopayments.turnkey.apiclient.code.CountryCode;
import com.evopayments.turnkey.apiclient.code.CurrencyCode;
import com.evopayments.turnkey.apiclient.config.TestConfig;
import com.evopayments.turnkey.config.ApplicationConfig;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * Base class for test cases. Provide common helper methods for subclasses.
 *
 */
public class BaseTest {

    protected static ApplicationConfig config = TestConfig.getInstance();

    /**
     * build the params map for token request.
     * @return tokenizeParams map
     */
    protected Map<String, String> buildTokenizeParam(){
        Map<String, String> tokenizeParams = new HashMap<>();
        tokenizeParams.put("number", "5413330300002004");
        tokenizeParams.put("nameOnCard", "John Doe");
        tokenizeParams.put("expiryMonth", "12");
        tokenizeParams.put("expiryYear", "2028");
        tokenizeParams.put("merchantId", config.getProperty("application.merchantId"));
        tokenizeParams.put("password", config.getProperty("application.password"));

        return tokenizeParams;
    }

    /**
     * add common params for action.
     * @param authParams
     */
    protected  void addCommonParams(Map<String, String> authParams){
        authParams.put("channel", Channel.ECOM.getCode());
        authParams.put("country", CountryCode.PL.getCode());
        authParams.put("currency", CurrencyCode.PLN.getCode());
        authParams.put("paymentSolutionId", "500");
        authParams.put("merchantId", config.getProperty("application.merchantId"));
        authParams.put("password", config.getProperty("application.password"));
    }

    /**
     * Add params for turnkey API.
     * @return inputParams
     */
    protected Map<String, String> prepareAPICall(){

        // TOKENIZE
        final Map<String, String> tokenizeParams = this.buildTokenizeParam();

        final TokenizeCall tokenize = new TokenizeCall(config, tokenizeParams, null);
        final JSONObject tokenizeCall = tokenize.execute();

        final Map<String, String> inputParams = new HashMap<>();
        this.addCommonParams(inputParams);
        inputParams.put("amount", "20.0");
        inputParams.put("customerId", tokenizeCall.getString("customerId"));
        inputParams.put("specinCreditCardToken", tokenizeCall.getString("cardToken"));
        inputParams.put("specinCreditCardCVV", "111");

        return inputParams;
    }

    /**
     * Add params for 3DSV2.
     * @param apiParams
     * @return
     */
    protected Map<String, String> add3DSV2ParametersNoExt(Map<String, String> apiParams) {

        // 3DSV2 parameters
        apiParams.put("merchantChallengeInd", "01");
        apiParams.put("merchantDecReqInd", "N");
        apiParams.put("merchantDecMaxTime", "10080");

        // customer browser/device data
        apiParams.put("userDevice", "DESKTOP");
        apiParams.put("userAgent", "okhttp/4.3.1");
        apiParams.put("customerIPAddress", "31.60.88.55");
        apiParams.put("customerBrowser", "{\n" +
                "\"browserAcceptHeader\":\"application/json\",\n" +
                "\"browserJavaEnabled\":false,\n" +
                "\"browserIP\":\"10.53.0.23\",\n" +
                "\"browserLanguage\":\"en-US\",\n" +
                "\"browserColorDepth\":\"24\",\n" +
                "\"browserScreenHeight\":\"1080\",\n" +
                "\"browserScreenWidth\":\"1920\",\n" +
                "\"browserTZ\":\"-60\",\n" +
                "\"challengeWindowSize\":\"05\",\n" +
                "\"browserJavascriptEnabled\":true\n" +
                "}");
        // customer account data with the merchant
        apiParams.put("merchantReference", "myFavourite45thPresident");
        apiParams.put("customerRegistrationDate", "12/10/2020");
        apiParams.put("customerAccountInfo", "{\n" +
                "    \"custAccAgeInd\":\"01\",\n" +
                "    \"custAccChange\":\"20200715\",\n" +
                "    \"custAccChangeInd\":\"02\",\n" +
                "    \"custAccPwChange\":\"20200621\" ,\n" +
                "    \"custAccPwChangeInd\":\"05\" ,\n" +
                "    \"custPurchaseCount\":\"250\" ,\n" +
                "    \"custProvisionAttemptsPerDay\":\"5\",\n" +
                "    \"custTxnActivityDay\":\"8\",\n" +
                "    \"custTxnActivityYear\":\"721\",\n" +
                "    \"custPaymentAccAge\":\"20200523\",\n" +
                "    \"custPaymentAccInd\":\"03\",\n" +
                "    \"custShipAddressUsage\":\"20200915\",\n" +
                "    \"custShipAddressUsageInd\":\"01\",\n" +
                "    \"custShipNameIndicator\":\"01\",\n" +
                "    \"custSuspiciousAccActivity\":\"01\"\n" +
                "}");
        // customer address data
        apiParams.put("customerAddressHouseName", "floating bricks");
        apiParams.put("customerAddressHouseNumber", "12");
        apiParams.put("customerAddressFlat", "very");
        apiParams.put("customerAddressStreet", "Legoland");
        apiParams.put("customerAddressCity", "Aviles");
        apiParams.put("customerAddressDistrict", "ninth");
        apiParams.put("customerAddressPostalCode", "25-322");
        apiParams.put("customerAddressCountry", "PL");
        apiParams.put("customerAddressState", "Texas");
        apiParams.put("customerAddressPhone", "call my dog");
        apiParams.put("customerBillingAddressHouseName", "woo haa");
        apiParams.put("customerBillingAddressHouseNumber", "13");
        apiParams.put("customerBillingAddressFlat", "very2");
        apiParams.put("customerBillingAddressStreet", "Curvy");
        apiParams.put("customerBillingAddressCity", "Billburg");
        apiParams.put("customerBillingAddressDistrict", "tenth");
        apiParams.put("customerBillingAddressPostalCode", "000000");
        apiParams.put("customerBillingAddressCountry", "PL");
        apiParams.put("customerBillingAddressState", "North Florida");
        apiParams.put("customerBillingAddressPhone", "blue one");
        apiParams.put("customerShippingAddressHouseName", "Swoo haa");
        apiParams.put("customerShippingAddressHouseNumber", "14");
        apiParams.put("customerShippingAddressFlat", "very3");
        apiParams.put("customerShippingAddressStreet", "SCurvy");
        apiParams.put("customerShippingAddressCity", "SBillburg");
        apiParams.put("customerShippingAddressDistrict", "Stenth");
        apiParams.put("customerShippingAddressPostalCode", "S000000");
        apiParams.put("customerShippingAddressCountry", "PL");
        apiParams.put("customerShippingAddressState", "SNorth Florida");
        apiParams.put("customerShippingAddressPhone", "Sblue one");
        // additional authentication data
        apiParams.put("merchantAuthInfo", "{\n" +
                "    \"merchantAuthData\":\"test\",\n" +
                "    \"merchantAuthMethod\":\"03\",\n" +
                "    \"merchantAuthTimestamp\":\"202004190830\"\n" +
                "}");
        apiParams.put("merchantPriorAuthInfo", "{\n" +
                "    \"merchantPriorAuthData\":\"test\",\n" +
                "    \"merchantPriorAuthMethod\":\"02\",\n" +
                "    \"merchantPriorAuthTimestamp\":\"202003060830\",\n" +
                "    \"merchantPriorRef\":\"test\"\n" +
                "}");
        apiParams.put("merchantRiskIndicator", "{\n" +
                "    \"deliveryEmailAddress\":\"test@evo.com\",\n" +
                "    \"deliveryTimeframe\":\"01\",\n" +
                "    \"giftCardAmount\":\"15.6\",\n" +
                "    \"giftCardCount\":\"22\",\n" +
                "    \"giftCardCurr\":\"978\",\n" +
                "    \"preOrderDate\":\"20201212\",\n" +
                "    \"preOrderPurchaseInd\":\"02\",\n" +
                "    \"reorderItemsInd\":\"02\",\n" +
                "    \"shipIndicator\":\"07\"\n" +
                "}");

        return apiParams;
    }

    protected Map<String, String> add3DSV2Parameters(Map<String, String> apiParams) {
        add3DSV2ParametersNoExt(apiParams);
        addExtAuthParameters(apiParams);
        return apiParams;
    }

    private Map<String, String> addExtAuthParameters(Map<String, String> apiParams) {
        // external authentication
        apiParams.put("authenticationValue", "MTJlZnJzZWdmZGdkZmdkZ2Q=");
        apiParams.put("authenticationECI", "rA");
        apiParams.put("protocolVersion", "1.0");
        apiParams.put("threeDSServerTransId", "txnId123456");
        apiParams.put("dsTransID", "txnId123456");
        apiParams.put("acsTransId", "txnId123456");
        apiParams.put("authenticationType", "A");
        apiParams.put("authenticationFlow", "F");
        apiParams.put("transStatus", "Y");
        apiParams.put("authenticationDateTime", "202005120630");

        return apiParams;
    }
}


