package com.evopayments.turnkey.apiclient;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.evopayments.turnkey.apiclient.code.ActionType;
import com.evopayments.turnkey.config.ApplicationConfig;

/**
 * [only for PCI compliant merchants, needed for fully custom UI implementations] 
 * 
 * Tokenizes the card for future use (for added security). 
 * The real card number is only used in this request, 
 * in subsequent requests ({@link AuthCall} or {@link PurchaseCall} or {@link VerifyCall}) 
 * the received (in the response here) card token has to be used.  
 * 
 * @author erbalazs
 *
 */
public class TokenizeCall extends AbstractApiCall {
	
	private static final Set<String> requiredParams = Collections.unmodifiableSet(new HashSet<>(Arrays.asList("number", "nameOnCard",
			"expiryMonth", "expiryYear")));

	/**
	 * @deprecated
	 * 
	 * @param config
	 * @param inputParams
	 * @param outputWriter
	 * 		deprecated parameter, outputWriter is not utilized anymore, 
	 * 		instead use and adjust logging
	 */
	public TokenizeCall(final ApplicationConfig config, final Map<String, String> inputParams,
			final PrintWriter outputWriter) {
		super(config, inputParams, outputWriter);
	}
	
	public TokenizeCall(final ApplicationConfig config, final Map<String, String> inputParams) {
		super(config, inputParams);
	}

	@Override
	protected ActionType getActionType() {
		return ActionType.TOKENIZE;
	}

	@Override
	protected void preValidateParams(final Map<String, String> inputParams) {

		mandatoryValidation(inputParams, requiredParams);

	}

	@Override
	protected Map<String, String> getTokenParams(final Map<String, String> inputParams) {

		final Map<String, String> tokenParams = new HashMap<>();

		putMerchantCredentials(inputParams, tokenParams, this.config);
		
		tokenParams.put("action", this.getActionType().getCode());
		tokenParams.put("timestamp", String.valueOf(System.currentTimeMillis()));
		tokenParams.put("allowOriginUrl", this.config.getProperty(ALLOW_ORIGIN_URL_PROP_KEY));
		tokenParams.put("customerId", inputParams.get("customerId"));

		return tokenParams;
	}

	@Override
	protected Map<String, String> getActionParams(final Map<String, String> inputParams,
			final String token) {

		final Map<String, String> actionParams = new HashMap<>(inputParams);

		putMerchantId(inputParams, actionParams, this.config);
				
		actionParams.put("token", token);
		actionParams.put("number", inputParams.get("number"));
		actionParams.put("nameOnCard", inputParams.get("nameOnCard"));
		actionParams.put("expiryMonth", inputParams.get("expiryMonth"));
		actionParams.put("expiryYear", inputParams.get("expiryYear"));
		
		return actionParams;
	}
}
