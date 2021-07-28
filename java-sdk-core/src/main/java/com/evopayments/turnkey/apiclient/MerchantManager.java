package com.evopayments.turnkey.apiclient;

import java.util.Map;
import com.evopayments.turnkey.config.ApplicationConfig;

/**
 * Helper class to manage Merchant credentials
 */
public class MerchantManager {

    /**
     * This method get merchantId and password from inputParams and add it to tokenParams.
     * If param merchantId don't exist in inputParams then it's get from config file
     * @param inputParams parameters passed from the Merchant.
     * @param tokenParams parameters for the session token.
     * @param config the SDK configuration file contents, which should never be used. It's there for backwards compatibility.
     */
    @Deprecated
    public static void putMerchantCredentials(
            final Map<String, String> inputParams,
            final Map<String, String> tokenParams,
            final ApplicationConfig config) {

        String merchantID = inputParams.get(ApiCall.MERCHANT_ID);
        if (merchantID == null || merchantID.isEmpty()) {
			/*  This is a temporary bug fix for legacy code.
			Here we query the config file if we lack a merchantID. However this hopefully never happens.
			Turnkey SDK Config for storing Merchant ID is probably not used by Merchants (and shouldn't be),
			but just in case, we're preserving the code here, in this deprecated block of code.
			*/
            tokenParams.put(ApiCall.MERCHANT_ID, config.getProperty(ApiCall.MERCHANT_ID_PROP_KEY));
            tokenParams.put(ApiCall.PASSWORD, config.getProperty(ApiCall.PASSWORD_PROP_KEY));
        } else {
            // This is the correct path. Here the Merchant will have defined the Merchant ID in input parameters to the SDK.
            tokenParams.put(ApiCall.MERCHANT_ID, merchantID);
            tokenParams.put(ApiCall.PASSWORD, inputParams.get(ApiCall.PASSWORD));
        }
    }

}