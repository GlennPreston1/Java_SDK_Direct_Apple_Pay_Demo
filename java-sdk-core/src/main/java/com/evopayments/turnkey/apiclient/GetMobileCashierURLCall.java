package com.evopayments.turnkey.apiclient;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.evopayments.turnkey.apiclient.code.ActionType;
import com.evopayments.turnkey.config.ApplicationConfig;

/**
 * Queries the mobile cashier URL.
 *
 * @author shiying
 */
public class GetMobileCashierURLCall extends AbstractApiCall {

	private static final Set<String> requiredParams = Collections.unmodifiableSet(new HashSet<>(Arrays.asList("action", "amount", "channel", "country", "currency")));

	public GetMobileCashierURLCall(
			final ApplicationConfig config,
			final Map<String, String> inputParams,
			final PrintWriter outputWriter) {
		super(config, inputParams, outputWriter);
	}

	@Override
	protected void preValidateParams(final Map<String, String> inputParams) {

		mandatoryValidation(inputParams, requiredParams);

	}

	@Override
	protected Map<String, String> getTokenParams(final Map<String, String> inputParams) {

		final Map<String, String> tokenParams = new HashMap<>(inputParams);

		/* all of the input params plus the ones below. */

		putMerchantCredentials(inputParams, tokenParams, this.config);

		tokenParams.put("timestamp", String.valueOf(System.currentTimeMillis()));
		tokenParams.put("allowOriginUrl", this.config.getProperty(ALLOW_ORIGIN_URL_PROP_KEY));
		tokenParams.put(
				"merchantNotificationUrl", this.config.getProperty(MERCHANT_NOTIFICATION_URL_PROP_KEY));
		tokenParams.put("action", inputParams.get("action"));
		tokenParams.put("channel", inputParams.get("channel"));
		tokenParams.put("amount", inputParams.get("amount"));
		tokenParams.put("currency", inputParams.get("currency"));
		tokenParams.put("country", inputParams.get("country"));
		tokenParams.put("paymentSolutionId", inputParams.get("paymentSolutionId"));
		tokenParams.put("customerId", inputParams.get("customerId"));

		for (int counter = 1; counter < 20; counter++) {
			tokenParams.put(String.format("customParameter%dOr", counter),
					inputParams.get(String.format("customParameter%dOr", counter)));
		}

		return tokenParams;
	}

	@Override
	protected Map<String, String> getActionParams(
			final Map<String, String> inputParams, final String token) {

		final Map<String, String> actionParams = new HashMap<>();

		putMerchantId(inputParams, actionParams, this.config);
		
		actionParams.put("token", token);
		actionParams.put("action", String.valueOf(ActionType.GET_MOBILE_CASHIER_URL.getCode()));
		
		return actionParams;
	}

	@Override
	protected ActionType getActionType() {
		return ActionType.GET_MOBILE_CASHIER_URL;
	}
}
