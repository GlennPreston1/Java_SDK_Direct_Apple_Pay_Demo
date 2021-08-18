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
 * Refunds a previously finished payment partially or in full. 
 * 
 * Finished (successful) PURCHASE operations 
 * or already CAPTURED AUTH operations can be refunded here 
 * (note: for AUTH payments were CAPTURE was not yet executed VOID can be used). 
 * 
 * REFUND operations count as separate transactions (linked, but separate with new transaction id etc.) . 
 * 
 * @author erbalazs
 * 
 * @see AuthCall
 * @see CaptureCall
 * @see VoidCall
 * 
 * @see PurchaseCall
 */
public class RefundCall extends AbstractApiCall {

	private static final Set<String> requiredParams = Collections.unmodifiableSet(new HashSet<>(Arrays.asList("amount", "country",
			"currency", "originalMerchantTxId")));
	
	public RefundCall(final ApplicationConfig config, final Map<String, String> inputParams,
			final PrintWriter outputWriter) {
		super(config, inputParams, outputWriter);
	}

	@Override
	protected ActionType getActionType() {
		return ActionType.REFUND;
	}

	@Override
	protected void preValidateParams(final Map<String, String> inputParams) {

		mandatoryValidation(inputParams, requiredParams);
		
	}

	@Override
	protected Map<String, String> getTokenParams(final Map<String, String> inputParams) {

		final Map<String, String> tokenParams = new HashMap<>();

		putMerchantCredentials(inputParams, tokenParams, this.config);
		
		tokenParams.put("originalMerchantTxId", inputParams.get("originalMerchantTxId"));
		tokenParams.put("action", this.getActionType().getCode());
		tokenParams.put("timestamp", String.valueOf(System.currentTimeMillis()));
		tokenParams.put("allowOriginUrl", this.config.getProperty(ALLOW_ORIGIN_URL_PROP_KEY));
		tokenParams.put("amount", inputParams.get("amount"));

		return tokenParams;
	}

	@Override
	protected Map<String, String> getActionParams(final Map<String, String> inputParams,
			final String token) {

		final Map<String, String> actionParams = new HashMap<>();

		actionParams.put("merchantId", inputParams.get("merchantId"));
		actionParams.put("token", token);

		return actionParams;
	}
}
