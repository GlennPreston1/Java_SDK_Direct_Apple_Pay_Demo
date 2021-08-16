package com.evopayments.turnkey.apiclient;

import java.io.PrintWriter;
import java.util.Map;

import com.evopayments.turnkey.apiclient.code.ActionType;
import com.evopayments.turnkey.config.ApplicationConfig;

/**
 * [relevant for non-PCI compliant merchants]
 *  
 * Only token acquiration. The token aquired here can be used to 
 * initialize Turnkey Cashier UI (fully managed UI, works with redirection or in an iframe).
 * 
 * @author erbalazs
 *
 * @see AuthTokenCall
 */
public class AuthTokenCall extends AbstractApvTokenCall {

	public AuthTokenCall(final ApplicationConfig config, final Map<String, String> inputParams,
			final PrintWriter outputWriter) {
		super(config, inputParams, outputWriter);
	}

	@Override
	protected ActionType getActionType() {
		return ActionType.AUTH;
	}
}
