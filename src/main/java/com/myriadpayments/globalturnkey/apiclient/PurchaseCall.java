package com.myriadpayments.globalturnkey.apiclient;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.myriadpayments.globalturnkey.apiclient.code.ActionType;
import com.myriadpayments.globalturnkey.apiclient.exception.RequiredParamException;
import com.myriadpayments.globalturnkey.config.ApplicationConfig;

/**
 * Does an authorize and capture operations at once (and cannot be voided)
 * 
 * @author erbalazs
 *
 */
public class PurchaseCall extends ApiCall {

	public PurchaseCall(ApplicationConfig config, Map<String, String> inputParams, PrintWriter outputWriter) {
		super(config, inputParams, outputWriter);
	}

	@Override
	protected ActionType getActionType() {
		return ActionType.PURCHASE;
	}

	@Override
	protected void preValidateParams(final Map<String, String> inputParams) throws RequiredParamException {

		final Set<String> requiredParams = new HashSet<>(Arrays.asList("amount", "channel", "country", "currency", "paymentSolutionId"));

		for (final Map.Entry<String, String> entry : inputParams.entrySet()) {

			if ((entry.getValue() != null) && !entry.getValue().trim().isEmpty()) {
				requiredParams.remove(entry.getKey());
			}

		}

		if (!requiredParams.isEmpty()) {
			throw new RequiredParamException(requiredParams);
		}

	}

	@Override
	protected Map<String, String> getTokenParams(final Map<String, String> inputParams) {

		final Map<String, String> tokenParams = new HashMap<>();

		tokenParams.put("merchantId", config.getProperty(MERCHANT_ID_PROP_KEY));
		tokenParams.put("password", config.getProperty(PASSWORD_PROP_KEY));
		tokenParams.put("action", getActionType().getCode());
		tokenParams.put("timestamp", String.valueOf(System.currentTimeMillis()));
		tokenParams.put("allowOriginUrl", config.getProperty(ALLOW_ORIGIN_URL_PROP_KEY));
		tokenParams.put("channel", inputParams.get("channel"));
		tokenParams.put("amount", inputParams.get("amount"));
		tokenParams.put("currency", inputParams.get("currency"));
		tokenParams.put("country", inputParams.get("country"));
		tokenParams.put("paymentSolutionId", inputParams.get("paymentSolutionId"));
		tokenParams.put("merchantNotificationUrl", config.getProperty(MERCHANT_NOTIFICATION_URL_PROP_KEY));

		return tokenParams;
	}

	@Override
	protected Map<String, String> getActionParams(final Map<String, String> inputParams, final String token) {

		final Map<String, String> actionParams = new HashMap<>();

		actionParams.put("merchantId", config.getProperty(MERCHANT_ID_PROP_KEY));
		actionParams.put("token", token);
		actionParams.put("customerId", inputParams.get("customerId"));
		actionParams.put("specinCreditCardToken", inputParams.get("specinCreditCardToken"));
		actionParams.put("specinCreditCardCVV", inputParams.get("specinCreditCardCVV"));

		return actionParams;
	}
}
