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
				Arrays.asList("action", "amount", "channel", "country", "currency",
						"paymentSolutionId"));
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


		return tokenParams;
	}

	@Override
	protected Map<String, String> getActionParams(
			final Map<String, String> inputParams, final String token) {

		final Map<String, String> actionParams = new HashMap<>();

		actionParams.put("merchantId", inputParams.get("merchantId"));
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
