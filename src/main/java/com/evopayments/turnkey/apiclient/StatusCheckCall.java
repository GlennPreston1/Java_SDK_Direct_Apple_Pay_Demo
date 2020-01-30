package com.evopayments.turnkey.apiclient;

import com.evopayments.turnkey.apiclient.code.ActionType;
import com.evopayments.turnkey.apiclient.exception.RequiredParamException;
import com.evopayments.turnkey.config.ApplicationConfig;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Returns the status of an already issued payment transaction,
 * as such it doesn’t actually generate a new transaction.
 * 
 * @author erbalazs
 *
 */
public class StatusCheckCall extends ApiCall {

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
	public StatusCheckCall(final ApplicationConfig config, final Map<String, String> inputParams,
						   final PrintWriter outputWriter) {
		super(config, inputParams, outputWriter);
	}

	@Override
	protected ActionType getActionType() {
		return ActionType.STATUS_CHECK;
	}

	@Override
	protected void preValidateParams(final Map<String, String> inputParams)
			throws RequiredParamException {
		// 
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

		final Map<String, String> actionParams = new HashMap<>();

		actionParams.put("merchantId", inputParams.get("merchantId"));
		actionParams.put("token", token);
		actionParams.put("action", getActionType().getCode());
		actionParams.put("txId", inputParams.get("txId"));
		actionParams.put("merchantTxId", inputParams.get("merchantTxId"));

		return actionParams;
	}
}
