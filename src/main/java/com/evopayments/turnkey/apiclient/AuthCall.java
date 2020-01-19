package com.evopayments.turnkey.apiclient;

import com.evopayments.turnkey.apiclient.code.ActionType;
import com.evopayments.turnkey.config.ApplicationConfig;
import java.io.PrintWriter;
import java.util.Map;


/**
 * Requests authorisation for a payment.
 * 
 * @author erbalazs
 *
 */
public class AuthCall extends BaseApiCall {

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
	public AuthCall(final ApplicationConfig config, final Map<String, String> inputParams,
					final PrintWriter outputWriter) {
		super(config, inputParams, outputWriter);
	}

	@Override
	protected ActionType getActionType() {
		return ActionType.AUTH;
	}

}
