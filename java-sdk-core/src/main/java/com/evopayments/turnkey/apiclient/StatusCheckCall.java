package com.evopayments.turnkey.apiclient;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import com.evopayments.turnkey.apiclient.code.ActionType;
import com.evopayments.turnkey.config.ApplicationConfig;

/**
 * [only for PCI compliant merchants, needed for fully custom UI implementations] 
 * 
 * Returns the status of an already issued payment transaction.
 * 
 * @author erbalazs
 *
 */
public class StatusCheckCall extends AbstractApiCall {
	
	/**
	 * @deprecated
	 * 
	 * @param config
	 * @param inputParams
	 * @param outputWriter
	 * 		deprecated parameter, outputWriter is not utilized anymore, 
	 * 		instead use and adjust logging
	 */
	public StatusCheckCall(final ApplicationConfig config, final Map<String, String> inputParams,
			final PrintWriter outputWriter) {
		super(config, inputParams, outputWriter);
	}
	
	public StatusCheckCall(final ApplicationConfig config, final Map<String, String> inputParams) {
		super(config, inputParams);
	}

	@Override
	protected ActionType getActionType() {
		return ActionType.STATUS_CHECK;
	}

	@Override
	protected void preValidateParams(final Map<String, String> inputParams) {

		// either txId or merchantTxId has to be supplied, but we don't pre-validate it here
		
	}

	@Override
	protected Map<String, String> getTokenParams(final Map<String, String> inputParams) {

		final Map<String, String> tokenParams = new HashMap<>();

		putMerchantCredentials(inputParams, tokenParams, this.config);
		
		tokenParams.put("action", this.getActionType().getCode());
		tokenParams.put("timestamp", String.valueOf(System.currentTimeMillis()));
		tokenParams.put("allowOriginUrl", this.config.getProperty(ALLOW_ORIGIN_URL_PROP_KEY));

		return tokenParams;
	}

	@Override
	protected Map<String, String> getActionParams(final Map<String, String> inputParams,
			final String token) {

		final Map<String, String> actionParams = new HashMap<>();

		putMerchantId(inputParams, actionParams, this.config);
		
		actionParams.put("token", token);
		actionParams.put("action", this.getActionType().getCode());
		
		String txId = inputParams.get("txId");
		String merchantTxId = inputParams.get("merchantTxId");
		
		if (txId != null) {
			actionParams.put("txId", txId);
		} else {
			actionParams.put("merchantTxId", merchantTxId);
		}
		
		return actionParams;
	}
}
