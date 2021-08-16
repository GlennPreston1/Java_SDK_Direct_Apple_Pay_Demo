package com.evopayments.turnkey.apiclient;

import java.io.PrintWriter;
import java.util.Map;

import com.evopayments.turnkey.apiclient.code.ActionType;
import com.evopayments.turnkey.config.ApplicationConfig;

/**
 * [only for PCI compliant merchants, needed for fully custom UI implementations] 
 * 
 * It can be used to check if a credit or debit card is indeed valid (the actual card can be used for future payments or not). 
 * In some countries, with some acquirers this check actually means a small (1 USD or other currency) transaction, which will get refunded (or voided). 
 * 
 * Usually relevant when the customer wants to start some kind of subscripition (with monthly fees), has to pay installments etc.
 * 
 * @author erbalazs
 *
 */
public class VerifyCall extends AbstractApvCall {

	public VerifyCall(final ApplicationConfig config, final Map<String, String> inputParams,
			final PrintWriter outputWriter) {
		super(config, inputParams, outputWriter);
	}

	public VerifyCall(final ApplicationConfig config, final Map<String, String> inputParams,
			final PrintWriter outputWriter, final String subActionType) {
		super(config, inputParams, outputWriter, subActionType);
	}

	@Override
	protected ActionType getActionType() {
		return ActionType.VERIFY;
	}

}
