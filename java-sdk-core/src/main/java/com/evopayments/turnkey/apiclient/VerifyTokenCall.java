package com.evopayments.turnkey.apiclient;

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
 * @see VerifyTokenCall
 */
public class VerifyTokenCall extends AbstractApvTokenCall {

	public VerifyTokenCall(final ApplicationConfig config, final Map<String, String> inputParams) {
		super(config, inputParams);
	}

	@Override
	protected ActionType getActionType() {
		return ActionType.VERIFY;
	}
}
