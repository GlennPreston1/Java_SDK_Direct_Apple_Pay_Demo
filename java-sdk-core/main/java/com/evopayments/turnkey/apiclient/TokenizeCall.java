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
 * Tokenizes the card for future use.
 * 
 * @author erbalazs
 *
 */
public class TokenizeCall extends ApiCall {

	/**
	 * constructor of current class.
	 *
	 * @param config
	 *
	 * @param inputParams
	 *
	 * @param outputWriter
	 *
	 */
	public TokenizeCall(final ApplicationConfig config, final Map<String, String> inputParams,
						final PrintWriter outputWriter) {
		super(config, inputParams, outputWriter);
	}

	@Override
	protected ActionType getActionType() {
		return ActionType.TOKENIZE;
	}

	@Override
	protected void preValidateParams(final Map<String, String> inputParams)
			throws RequiredParamException {

		final Set<String> requiredParams = new HashSet<>(Arrays.asList("number", "nameOnCard",
				"expiryMonth", "expiryYear"));

		for (final Map.Entry<String, String> entry : inputParams.entrySet()) {

			if (entry.getValue() != null && !entry.getValue().trim().isEmpty()) {
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

		MerchantManager.putMerchantCredentials(inputParams, tokenParams, config);
		tokenParams.put("action", getActionType().getCode());
		tokenParams.put("timestamp", String.valueOf(System.currentTimeMillis()));
		tokenParams.put("allowOriginUrl", config.getProperty(ALLOW_ORIGIN_URL_PROP_KEY));

		return tokenParams;
	}

	@Override
	protected Map<String, String> getActionParams(final Map<String, String> inputParams,
												  final String token) {

		final Map<String, String> actionParams = new HashMap<>(inputParams);

		actionParams.put("merchantId", inputParams.get("merchantId"));
		actionParams.put("token", token);
		actionParams.put("number", inputParams.get("number"));
		actionParams.put("nameOnCard", inputParams.get("nameOnCard"));
		actionParams.put("expiryMonth", inputParams.get("expiryMonth"));
		actionParams.put("expiryYear", inputParams.get("expiryYear"));

		return actionParams;
	}
}
