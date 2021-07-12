package com.evopayments.turnkey.apiclient;


import com.evopayments.turnkey.apiclient.code.ActionType;
import com.evopayments.turnkey.apiclient.exception.RequiredParamException;
import com.evopayments.turnkey.config.ApplicationConfig;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Queries the list of the possible payment solutions (ie. credit card) ,
 * (based on the country/currency).
 * 
 * @author erbalazs
 *
 */
public class GetAvailablePaymentSolutionsCall extends ApiCall {

	/**
	 * get available payment solution.
	 *
	 * @param config
	 *
	 * @param inputParams
	 *
	 * @param outputWriter
	 *
	 */
	public GetAvailablePaymentSolutionsCall(final ApplicationConfig config,
											final Map<String, String> inputParams,
											final PrintWriter outputWriter) {
		super(config, inputParams, outputWriter);
	}

	@Override
	protected ActionType getActionType() {
		return ActionType.GET_AVAILABLE_PAYSOLS;
	}

	@Override
	protected void preValidateParams(final Map<String, String> inputParams)
			throws RequiredParamException {

		final Set<String> requiredParams = new HashSet<>(Arrays.asList("country", "currency"));

		for (final Entry<String, String> entry : inputParams.entrySet()) {

			if (entry.getValue() != null && !entry.getValue().trim().isEmpty()) {
				requiredParams.remove(entry.getKey());
			}

		}

		if (!requiredParams.isEmpty()) {
			throw new RequiredParamException(requiredParams);
		}

	}

	@Override
	protected Map<String, String> getTokenParams(final Map<String, String> inputParams) {

		/**
		 * all of the input params plus the ones below
		 */
		final Map<String, String> tokenParams = new HashMap<>(inputParams);

		MerchantManager.putMerchantCredentials(inputParams, tokenParams, config);
		tokenParams.put("action", getActionType().getCode());
		tokenParams.put("timestamp", String.valueOf(System.currentTimeMillis()));
		tokenParams.put("allowOriginUrl", config.getProperty(ALLOW_ORIGIN_URL_PROP_KEY));

		return tokenParams;
	}

	@Override
	protected Map<String, String> getActionParams(final Map<String, String> inputParams, final String token) {

		final Map<String, String> actionParams = new HashMap<>();

		actionParams.put("merchantId", inputParams.get("merchantId"));
		actionParams.put("token", token);

		return actionParams;
	}

}
