package com.evopayments.turnkey.apiclient;

import java.io.PrintWriter;
import java.util.Map;

import com.evopayments.turnkey.apiclient.code.ActionType;
import com.evopayments.turnkey.config.ApplicationConfig;

/**
 * [only for PCI compliant merchants, needed for fully custom UI implementations] 
 * 
 * AUTH (authorize) operation (two phase payment's first phase). 
 * 
 * Call {@link CaptureCall} after this as the second phase, 
 * or {@link VoidCall} to cancel (void) the payment. 
 * 
 * Note: the word auth in this context does not mean API authentication! 
 * 
 * @author erbalazs
 */
public class AuthCall extends AbstractApvCall {

	/**
	 * @deprecated
	 * 
	 * @param config
	 * @param inputParams
	 * @param outputWriter
	 * 		deprecated parameter, outputWriter is not utilized anymore, 
	 * 		instead use and adjust logging
	 */
	public AuthCall(final ApplicationConfig config, final Map<String, String> inputParams,
			final PrintWriter outputWriter) {
		super(config, inputParams, outputWriter);
	}
	
	public AuthCall(final ApplicationConfig config, final Map<String, String> inputParams) {
		super(config, inputParams);
	}

	@Override
	protected ActionType getActionType() {
		return ActionType.AUTH;
	}

}
