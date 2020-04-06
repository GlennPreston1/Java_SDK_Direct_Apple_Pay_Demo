package com.evopayments.turnkey.apiclient;

import com.evopayments.turnkey.apiclient.code.ActionType;
import com.evopayments.turnkey.apiclient.exception.RequiredParamException;
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
 *
 */
public class GetMobileCashierURLCall extends ApiCall {

	/**
	 * the constructor of class GetMobileCashierURLCall.
	 * @param config
	 * @param inputParams
	 * @param outputWriter
	 */
 	public GetMobileCashierURLCall(final ApplicationConfig config,
								   final Map<String, String> inputParams,
								   final PrintWriter outputWriter) {
		super(config, inputParams, outputWriter);
	}

	@Override
	protected void preValidateParams(final Map<String, String> inputParams)
			throws RequiredParamException {

		final Set<String> requiredParams = new HashSet<>(
				Arrays.asList("action", "amount", "channel", "country", "currency"));
		mandatoryValidation(inputParams,requiredParams);
	}

	@Override
	protected Map<String, String> getTokenParams(
			final Map<String, String> inputParams) {
		/**
		 *  all of the input params plus the ones below.
		 */
		final Map<String, String> tokenParams = new HashMap<>(inputParams);

		MerchantManager.putMerchantCredentials(inputParams, tokenParams, config);
		tokenParams.put("timestamp",
				String.valueOf(System.currentTimeMillis()));
		tokenParams.put("allowOriginUrl", config.getProperty(ALLOW_ORIGIN_URL_PROP_KEY));
		tokenParams.put("merchantNotificationUrl",
				config.getProperty(MERCHANT_NOTIFICATION_URL_PROP_KEY));
		tokenParams.put("action", inputParams.get("action"));
		tokenParams.put("channel", inputParams.get("channel"));
		tokenParams.put("amount", inputParams.get("amount"));
		tokenParams.put("currency", inputParams.get("currency"));
		tokenParams.put("country", inputParams.get("country"));
		tokenParams.put("paymentSolutionId", inputParams.get("paymentSolutionId"));
		tokenParams.put("customerId", inputParams.get("customerId"));
		tokenParams.put("CustomParameter1Or", inputParams.get("customParameter1Or"));
		tokenParams.put("CustomParameter2Or", inputParams.get("customParameter2Or"));
		tokenParams.put("CustomParameter3Or", inputParams.get("customParameter3Or"));
		tokenParams.put("CustomParameter4Or", inputParams.get("customParameter4Or"));
		tokenParams.put("CustomParameter5Or", inputParams.get("customParameter5Or"));
		tokenParams.put("CustomParameter6Or", inputParams.get("customParameter6Or"));
		tokenParams.put("CustomParameter7Or", inputParams.get("customParameter7Or"));
		tokenParams.put("CustomParameter8Or", inputParams.get("customParameter8Or"));
		tokenParams.put("CustomParameter9Or", inputParams.get("customParameter9Or"));
		tokenParams.put("CustomParameter10Or", inputParams.get("customParameter10Or"));
		tokenParams.put("CustomParameter11Or", inputParams.get("customParameter11Or"));
		tokenParams.put("CustomParameter12Or", inputParams.get("customParameter12Or"));
		tokenParams.put("CustomParameter13Or", inputParams.get("customParameter13Or"));
		tokenParams.put("CustomParameter14Or", inputParams.get("customParameter14Or"));
		tokenParams.put("CustomParameter15Or", inputParams.get("customParameter15Or"));
		tokenParams.put("CustomParameter16Or", inputParams.get("customParameter16Or"));
		tokenParams.put("CustomParameter17Or", inputParams.get("customParameter17Or"));
		tokenParams.put("CustomParameter18Or", inputParams.get("customParameter18Or"));
		tokenParams.put("CustomParameter19Or", inputParams.get("customParameter19Or"));
		tokenParams.put("CustomParameter20Or", inputParams.get("customParameter20Or"));

		return tokenParams;
	}

	@Override
	protected Map<String, String> getActionParams(
			final Map<String, String> inputParams, final String token) {

		final Map<String, String> actionParams = new HashMap<>();

		actionParams.put(ApiCall.MERCHANT_ID, inputParams.get(ApiCall.MERCHANT_ID));
		actionParams.put("token", token);
		actionParams.put("action",
				String.valueOf(ActionType.GET_MOBILE_CASHIER_URL.getCode()));
		return actionParams;
	}

	@Override
	protected ActionType getActionType() {
		return ActionType.GET_MOBILE_CASHIER_URL;
	}
}
