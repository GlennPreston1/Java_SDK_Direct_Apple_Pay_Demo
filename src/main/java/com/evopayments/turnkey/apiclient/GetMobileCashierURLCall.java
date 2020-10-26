package com.evopayments.turnkey.apiclient;

import com.evopayments.turnkey.apiclient.code.ActionType;
import com.evopayments.turnkey.config.ApplicationConfig;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Queries the cashier URL.
 *
 * @author shiying
 */
public class GetMobileCashierURLCall extends ApiCall {

  public static final String ACTION = "action";
  public static final String AMOUNT = "amount";
  public static final String CHANNEL = "channel";
  public static final String COUNTRY = "country";
  public static final String CURRENCY = "currency";
  public static final String PAYMENT_SOLUTION_ID = "paymentSolutionId";
  public static final String CUSTOMER_ID = "customerId";
  public static final String TIMESTAMP = "timestamp";
  public static final String ALLOW_ORIGIN_URL = "allowOriginUrl";
  public static final String MERCHANT_NOTIFICATION_URL = "merchantNotificationUrl";
  public static final String CUSTOM_PARAMETER_D_OR = "customParameter%dOr";
  public static final String TOKEN = "token";
  public static final String MERCHANT_REFERENCE = "merchantReference";
  public static final String CUSTOMER_REGISTRATION_DATE = "customerRegistrationDate";
  public static final String MERCHANT_CHALLENGE_IND = "merchantChallengeInd";
  public static final String MERCHANT_DEC_REQ_IND = "merchantDecReqInd";
  public static final String MERCHANT_DEC_MAX_TIME = "merchantDecMaxTime";
  public static final String USER_DEVICE = "userDevice";
  public static final String USER_AGENT = "userAgent";
  public static final String CUSTOMER_IP_ADDRESS = "customerIPAddress";
  public static final String CUSTOMER_ACCOUNT_INFO = "customerAccountInfo";
  public static final String CUSTOMER_ADDRESS_HOUSE_NAME = "customerAddressHouseName";
  public static final String CUSTOMER_ADDRESS_HOUSE_NUMBER = "customerAddressHouseNumber";
  public static final String CUSTOMER_ADDRESS_FLAT = "customerAddressFlat";
  public static final String CUSTOMER_ADDRESS_STREET = "customerAddressStreet";
  public static final String CUSTOMER_ADDRESS_CITY = "customerAddressCity";
  public static final String CUSTOMER_ADDRESS_DISTRICT = "customerAddressDistrict";
  public static final String CUSTOMER_ADDRESS_POSTAL_CODE = "customerAddressPostalCode";
  public static final String CUSTOMER_ADDRESS_COUNTRY = "customerAddressCountry";
  public static final String CUSTOMER_ADDRESS_STATE = "customerAddressState";
  public static final String CUSTOMER_ADDRESS_PHONE = "customerAddressPhone";
  public static final String CUSTOMER_BILLING_ADDRESS_HOUSE_NAME = "customerBillingAddressHouseName";
  public static final String CUSTOMER_BILLING_ADDRESS_HOUSE_NUMBER = "customerBillingAddressHouseNumber";
  public static final String CUSTOMER_BILLING_ADDRESS_FLAT = "customerBillingAddressFlat";
  public static final String CUSTOMER_BILLING_ADDRESS_STREET = "customerBillingAddressStreet";
  public static final String CUSTOMER_BILLING_ADDRESS_CITY = "customerBillingAddressCity";
  public static final String CUSTOMER_BILLING_ADDRESS_DISTRICT = "customerBillingAddressDistrict";
  public static final String CUSTOMER_BILLING_ADDRESS_POSTAL_CODE = "customerBillingAddressPostalCode";
  public static final String CUSTOMER_BILLING_ADDRESS_COUNTRY = "customerBillingAddressCountry";
  public static final String CUSTOMER_BILLING_ADDRESS_STATE = "customerBillingAddressState";
  public static final String CUSTOMER_BILLING_ADDRESS_PHONE = "customerBillingAddressPhone";
  public static final String CUSTOMER_SHIPPING_ADDRESS_HOUSE_NAME = "customerShippingAddressHouseName";
  public static final String CUSTOMER_SHIPPING_ADDRESS_HOUSE_NUMBER = "customerShippingAddressHouseNumber";
  public static final String CUSTOMER_SHIPPING_ADDRESS_FLAT = "customerShippingAddressFlat";
  public static final String CUSTOMER_SHIPPING_ADDRESS_STREET = "customerShippingAddressStreet";
  public static final String CUSTOMER_SHIPPING_ADDRESS_CITY = "customerShippingAddressCity";
  public static final String CUSTOMER_SHIPPING_ADDRESS_DISTRICT = "customerShippingAddressDistrict";
  public static final String CUSTOMER_SHIPPING_ADDRESS_POSTAL_CODE = "customerShippingAddressPostalCode";
  public static final String CUSTOMER_SHIPPING_ADDRESS_COUNTRY = "customerShippingAddressCountry";
  public static final String CUSTOMER_SHIPPING_ADDRESS_STATE = "customerShippingAddressState";
  public static final String CUSTOMER_SHIPPING_ADDRESS_PHONE = "customerShippingAddressPhone";
  public static final String MERCHANT_AUTH_INFO = "merchantAuthInfo";
  public static final String MERCHANT_PRIOR_AUTH_INFO = "merchantPriorAuthInfo";
  public static final String MERCHANT_RISK_INDICATOR = "merchantRiskIndicator";

  /**
   * the constructor of class GetMobileCashierURLCall.
   *
   * @param config SDK configuration file content
   * @param inputParams input params which comes from MSS
   * @param outputWriter stores execution result
   */
  public GetMobileCashierURLCall(
      final ApplicationConfig config,
      final Map<String, String> inputParams,
      final PrintWriter outputWriter) {
    super(config, inputParams, outputWriter);
  }

  @Override
  protected void preValidateParams(final Map<String, String> inputParams) {
    final Set<String> requiredParams =
        new HashSet<>(Arrays.asList(ACTION, AMOUNT, CHANNEL, COUNTRY, CURRENCY));
    mandatoryValidation(inputParams, requiredParams);
  }

  @Override
  protected Map<String, String> getTokenParams(final Map<String, String> inputParams) {
    /** all of the input params plus the ones below. */
    final Map<String, String> tokenParams = new HashMap<>(inputParams);

    MerchantManager.putMerchantCredentials(inputParams, tokenParams, config);
    tokenParams.put(TIMESTAMP, String.valueOf(System.currentTimeMillis()));
    tokenParams.put(ALLOW_ORIGIN_URL, config.getProperty(ALLOW_ORIGIN_URL_PROP_KEY));
    tokenParams.put(
        MERCHANT_NOTIFICATION_URL, config.getProperty(MERCHANT_NOTIFICATION_URL_PROP_KEY));
    tokenParams.put(ACTION, inputParams.get(ACTION));
    tokenParams.put(CHANNEL, inputParams.get(CHANNEL));
    tokenParams.put(AMOUNT, inputParams.get(AMOUNT));
    tokenParams.put(CURRENCY, inputParams.get(CURRENCY));
    tokenParams.put(COUNTRY, inputParams.get(COUNTRY));
    tokenParams.put(PAYMENT_SOLUTION_ID, inputParams.get(PAYMENT_SOLUTION_ID));
    tokenParams.put(CUSTOMER_ID, inputParams.get(CUSTOMER_ID));
    tokenParams.put(MERCHANT_REFERENCE, inputParams.get(MERCHANT_REFERENCE));
    tokenParams.put(CUSTOMER_REGISTRATION_DATE, inputParams.get(CUSTOMER_REGISTRATION_DATE));
    tokenParams.put(MERCHANT_CHALLENGE_IND, inputParams.get(MERCHANT_CHALLENGE_IND));
    tokenParams.put(MERCHANT_DEC_REQ_IND, inputParams.get(MERCHANT_DEC_REQ_IND));
    tokenParams.put(MERCHANT_DEC_MAX_TIME, inputParams.get(MERCHANT_DEC_MAX_TIME));
    tokenParams.put(USER_DEVICE, inputParams.get(USER_DEVICE));
    tokenParams.put(USER_AGENT, inputParams.get(USER_AGENT));
    tokenParams.put(CUSTOMER_IP_ADDRESS, inputParams.get(CUSTOMER_IP_ADDRESS));
    tokenParams.put(CUSTOMER_ACCOUNT_INFO, inputParams.get(CUSTOMER_ACCOUNT_INFO));
    tokenParams.put(CUSTOMER_ADDRESS_HOUSE_NAME, inputParams.get(CUSTOMER_ADDRESS_HOUSE_NAME));
    tokenParams.put(CUSTOMER_ADDRESS_HOUSE_NUMBER, inputParams.get(CUSTOMER_ADDRESS_HOUSE_NUMBER));
    tokenParams.put(CUSTOMER_ADDRESS_FLAT, inputParams.get(CUSTOMER_ADDRESS_FLAT));
    tokenParams.put(CUSTOMER_ADDRESS_STREET, inputParams.get(CUSTOMER_ADDRESS_STREET));
    tokenParams.put(CUSTOMER_ADDRESS_CITY, inputParams.get(CUSTOMER_ADDRESS_CITY));
    tokenParams.put(CUSTOMER_ADDRESS_DISTRICT, inputParams.get(CUSTOMER_ADDRESS_DISTRICT));
    tokenParams.put(CUSTOMER_ADDRESS_POSTAL_CODE, inputParams.get(CUSTOMER_ADDRESS_POSTAL_CODE));
    tokenParams.put(CUSTOMER_ADDRESS_COUNTRY, inputParams.get(CUSTOMER_ADDRESS_COUNTRY));
    tokenParams.put(CUSTOMER_ADDRESS_STATE, inputParams.get(CUSTOMER_ADDRESS_STATE));
    tokenParams.put(CUSTOMER_ADDRESS_PHONE, inputParams.get(CUSTOMER_ADDRESS_PHONE));
    tokenParams.put(CUSTOMER_BILLING_ADDRESS_HOUSE_NAME, inputParams.get(CUSTOMER_BILLING_ADDRESS_HOUSE_NAME));
    tokenParams.put(CUSTOMER_BILLING_ADDRESS_HOUSE_NUMBER, inputParams.get(CUSTOMER_BILLING_ADDRESS_HOUSE_NUMBER));
    tokenParams.put(CUSTOMER_BILLING_ADDRESS_FLAT, inputParams.get(CUSTOMER_BILLING_ADDRESS_FLAT));
    tokenParams.put(CUSTOMER_BILLING_ADDRESS_STREET, inputParams.get(CUSTOMER_BILLING_ADDRESS_STREET));
    tokenParams.put(CUSTOMER_BILLING_ADDRESS_CITY, inputParams.get(CUSTOMER_BILLING_ADDRESS_CITY));
    tokenParams.put(CUSTOMER_BILLING_ADDRESS_DISTRICT, inputParams.get(CUSTOMER_BILLING_ADDRESS_DISTRICT));
    tokenParams.put(CUSTOMER_BILLING_ADDRESS_POSTAL_CODE, inputParams.get(CUSTOMER_BILLING_ADDRESS_POSTAL_CODE));
    tokenParams.put(CUSTOMER_BILLING_ADDRESS_COUNTRY, inputParams.get(CUSTOMER_BILLING_ADDRESS_COUNTRY));
    tokenParams.put(CUSTOMER_BILLING_ADDRESS_STATE, inputParams.get(CUSTOMER_BILLING_ADDRESS_STATE));
    tokenParams.put(CUSTOMER_BILLING_ADDRESS_PHONE, inputParams.get(CUSTOMER_BILLING_ADDRESS_PHONE));
    tokenParams.put(CUSTOMER_SHIPPING_ADDRESS_HOUSE_NAME, inputParams.get(CUSTOMER_SHIPPING_ADDRESS_HOUSE_NAME));
    tokenParams.put(CUSTOMER_SHIPPING_ADDRESS_HOUSE_NUMBER, inputParams.get(CUSTOMER_SHIPPING_ADDRESS_HOUSE_NUMBER));
    tokenParams.put(CUSTOMER_SHIPPING_ADDRESS_FLAT, inputParams.get(CUSTOMER_SHIPPING_ADDRESS_FLAT));
    tokenParams.put(CUSTOMER_SHIPPING_ADDRESS_STREET, inputParams.get(CUSTOMER_SHIPPING_ADDRESS_STREET));
    tokenParams.put(CUSTOMER_SHIPPING_ADDRESS_CITY, inputParams.get(CUSTOMER_SHIPPING_ADDRESS_CITY));
    tokenParams.put(CUSTOMER_SHIPPING_ADDRESS_DISTRICT, inputParams.get(CUSTOMER_SHIPPING_ADDRESS_DISTRICT));
    tokenParams.put(CUSTOMER_SHIPPING_ADDRESS_POSTAL_CODE, inputParams.get(CUSTOMER_SHIPPING_ADDRESS_POSTAL_CODE));
    tokenParams.put(CUSTOMER_SHIPPING_ADDRESS_COUNTRY, inputParams.get(CUSTOMER_SHIPPING_ADDRESS_COUNTRY));
    tokenParams.put(CUSTOMER_SHIPPING_ADDRESS_STATE, inputParams.get(CUSTOMER_SHIPPING_ADDRESS_STATE));
    tokenParams.put(CUSTOMER_SHIPPING_ADDRESS_PHONE, inputParams.get(CUSTOMER_SHIPPING_ADDRESS_PHONE));
    tokenParams.put(CUSTOMER_SHIPPING_ADDRESS_PHONE, inputParams.get(CUSTOMER_SHIPPING_ADDRESS_PHONE));
    tokenParams.put(MERCHANT_AUTH_INFO, inputParams.get(MERCHANT_AUTH_INFO));
    tokenParams.put(MERCHANT_PRIOR_AUTH_INFO, inputParams.get(MERCHANT_PRIOR_AUTH_INFO));
    tokenParams.put(MERCHANT_RISK_INDICATOR, inputParams.get(MERCHANT_RISK_INDICATOR));

    for (int counter = 1; counter < 20; counter++) {
      tokenParams.put(String.format(CUSTOM_PARAMETER_D_OR, counter),
              inputParams.get(String.format(CUSTOM_PARAMETER_D_OR, counter)));
    }

    return tokenParams;
  }

  @Override
  protected Map<String, String> getActionParams(
      final Map<String, String> inputParams, final String token) {

    final Map<String, String> actionParams = new HashMap<>();

    actionParams.put(ApiCall.MERCHANT_ID, inputParams.get(ApiCall.MERCHANT_ID));
    actionParams.put(TOKEN, token);
    actionParams.put(ACTION, String.valueOf(ActionType.GET_MOBILE_CASHIER_URL.getCode()));
    return actionParams;
  }

  @Override
  protected ActionType getActionType() {
    return ActionType.GET_MOBILE_CASHIER_URL;
  }
}
