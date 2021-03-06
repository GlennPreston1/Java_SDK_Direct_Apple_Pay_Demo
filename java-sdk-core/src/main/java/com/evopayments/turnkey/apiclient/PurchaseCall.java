package com.evopayments.turnkey.apiclient;

import java.io.PrintWriter;
import java.util.Map;

import com.evopayments.turnkey.apiclient.code.ActionType;
import com.evopayments.turnkey.config.ApplicationConfig;

/**
 * [only for PCI compliant merchants, needed for fully custom UI implementations] 
 * 
 * Essentially means an authorize (AUTH) and capture (CAPTURE) operation at once (cannot be voided). 
 * In other words it is a one-step payment. 
 * 
 * (note: after PURCHASE refund is possible, but VOID is not) 
 * 
 * @author erbalazs
 * 
 * @see AuthCall
 * @see CaptureCall
 * @see VoidCall
 * 
 * @see PurchaseCall
 */
public class PurchaseCall extends AbstractApvCall {

	/**
	 * @deprecated
	 * 
	 * @param config
	 * @param inputParams
	 * @param outputWriter
	 * 		deprecated parameter, outputWriter is not utilized anymore, 
	 * 		instead use and adjust logging
	 */
	public PurchaseCall(final ApplicationConfig config, final Map<String, String> inputParams,
			final PrintWriter outputWriter) {
		super(config, inputParams, outputWriter);
	}

	/**
	 * @deprecated
	 * 
	 * @param config
	 * @param inputParams
	 * @param outputWriter
	 * 		deprecated parameter, outputWriter is not utilized anymore, 
	 * 		instead use and adjust logging
	 * @param subActionType
	 */
	public PurchaseCall(final ApplicationConfig config, final Map<String, String> inputParams,
			final PrintWriter outputWriter, final String subActionType) {
		super(config, inputParams, outputWriter, subActionType);
	}
	
	public PurchaseCall(final ApplicationConfig config, final Map<String, String> inputParams) {
		super(config, inputParams);
	}

	public PurchaseCall(final ApplicationConfig config, final Map<String, String> inputParams, final String subActionType) {
		super(config, inputParams, subActionType);
	}

	@Override
	protected ActionType getActionType() {
		return ActionType.PURCHASE;
	}

}
