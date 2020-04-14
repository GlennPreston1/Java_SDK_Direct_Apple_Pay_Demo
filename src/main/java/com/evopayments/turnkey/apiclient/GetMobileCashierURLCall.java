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
