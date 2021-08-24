package com.evopayments.turnkey.apiclient;

import java.io.PrintWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.evopayments.turnkey.apiclient.code.ActionType;
import com.evopayments.turnkey.config.ApplicationConfig;

/**
 * [only for PCI compliant merchants, needed for fully custom UI implementations] 
 * 
 * Queries the list of the available payment solutions 
 * (ie. credit card, various bank transfers)
 * (can depend on the selected country/currency).
 * 
 * @author erbalazs
 */
public class GetAvailablePaymentSolutionsCall extends AbstractApiCall {
	
	private static final Set<String> requiredParams = Collections.emptySet();

	/**
	 * @deprecated
	 * 
	 * @param config
	 * @param inputParams
	 * @param outputWriter
	 * 		deprecated parameter, outputWriter is not utilized anymore, 
	 * 		instead use and adjust logging
	 */
	public GetAvailablePaymentSolutionsCall(final ApplicationConfig config,
			final Map<String, String> inputParams,
			final PrintWriter outputWriter) {
		super(config, inputParams, outputWriter);
	}
	
	public GetAvailablePaymentSolutionsCall(final ApplicationConfig config,
			final Map<String, String> inputParams) {
		super(config, inputParams);
	}

	@Override
	protected ActionType getActionType() {
		return ActionType.GET_AVAILABLE_PAYSOLS;
	}

	@Override
	protected void preValidateParams(final Map<String, String> inputParams) {

		mandatoryValidation(inputParams, requiredParams);

	}

	@Override
	protected Map<String, String> getTokenParams(final Map<String, String> inputParams) {

		/*
		 * all of the input params plus the ones below
		 */
		
		final Map<String, String> tokenParams = new HashMap<>(inputParams);

		putMerchantCredentials(inputParams, tokenParams, this.config);
		
		tokenParams.put("action", this.getActionType().getCode());
		tokenParams.put("timestamp", String.valueOf(System.currentTimeMillis()));
		tokenParams.put("allowOriginUrl", this.config.getProperty(ALLOW_ORIGIN_URL_PROP_KEY));
		
		putCurrency(inputParams, tokenParams, this.config);
		if (tokenParams.get("currency") == null) {
			tokenParams.remove("currency");
		}
		
		putCountry(inputParams, tokenParams, this.config);
				
		return tokenParams;
	}

	@Override
	protected Map<String, String> getActionParams(final Map<String, String> inputParams, final String token) {

		final Map<String, String> actionParams = new HashMap<>();

		putMerchantId(inputParams, actionParams, this.config);
		
		actionParams.put("token", token);

		return actionParams;
	}

}
