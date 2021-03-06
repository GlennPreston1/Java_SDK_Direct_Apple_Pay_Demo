package com.evopayments.turnkey.apiclient;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.evopayments.turnkey.apiclient.code.SubActionType;
import com.evopayments.turnkey.config.ApplicationConfig;
import com.evopayments.turnkey.util.MapUtil;

/**
 * Abstract base class for Auth/Purchase/Verify.
 * 
 * @author erbalazs
 * 
 * @see AbstractApiCall
 */
public abstract class AbstractApvCall extends AbstractApiCall {

	private static final Set<String> requiredParams = Collections
			.unmodifiableSet(new HashSet<>(Arrays.asList("amount", "paymentSolutionId")));

	/**
	 * @see SubActionType
	 */
	private final String subActionType;

	public AbstractApvCall(final ApplicationConfig config, final Map<String, String> inputParams,
			final PrintWriter outputWriter) {
		this(config, inputParams, outputWriter, null);
	}

	public AbstractApvCall(final ApplicationConfig config, final Map<String, String> inputParams,
			final String subActionType) {
		this(config, inputParams, null, subActionType);
	}

	public AbstractApvCall(final ApplicationConfig config, final Map<String, String> inputParams) {
		this(config, inputParams, null, null);
	}

	/**
	 * @param config
	 * @param inputParams
	 * @param outputWriter
	 * 
	 * @param subActionType relevant for card on file, recurring payments etc.
	 * 
	 * @see SubActionType
	 */
	public AbstractApvCall(final ApplicationConfig config, final Map<String, String> inputParams,
			final PrintWriter outputWriter, final String subActionType) {

		super(config, inputParams, outputWriter);

		this.subActionType = subActionType;

	}

	@Override
	protected void preValidateParams(final Map<String, String> inputParams) {

		mandatoryValidation(inputParams, requiredParams);

	}

	@Override
	protected Map<String, String> getTokenParams(final Map<String, String> inputParams) {

		final Map<String, String> tokenParams = new HashMap<>(inputParams);

		// ---
		
		putMerchantCredentials(inputParams, tokenParams, this.config);
		
		// ---

		tokenParams.put("action", this.getActionType().getCode());
		tokenParams.put("timestamp", String.valueOf(System.currentTimeMillis()));
		tokenParams.put("allowOriginUrl", this.config.getProperty(ALLOW_ORIGIN_URL_PROP_KEY));

		final String channel = inputParams.get("channel");

		if (channel != null) {
			tokenParams.put("channel", channel);
		} else {
			tokenParams.put("channel", "ECOM");
		}
		
		// ---

		tokenParams.put("amount", inputParams.get("amount"));

		putCurrency(inputParams, tokenParams, this.config);
		putCountry(inputParams, tokenParams, this.config);

		tokenParams.put("merchantTxId", inputParams.get("merchantTxId"));
		
		MapUtil.putIfNotNull(tokenParams, "paymentSolutionId", inputParams.get("paymentSolutionId"));
		
		// ---

		tokenParams.put("merchantNotificationUrl", this.config.getProperty(MERCHANT_NOTIFICATION_URL_PROP_KEY));
		tokenParams.put("merchantLandingPageUrl", this.config.getProperty(MERCHANT_LANDING_PAGE_URL_PROP_KEY));

		// ---
		
		MapUtil.putIfNotNull(tokenParams, "mmrpContractNumber", inputParams.get("mmrpContractNumber"));
		MapUtil.putIfNotNull(tokenParams, "mmrpOriginalMerchantTransactionId", inputParams.get("mmrpOriginalMerchantTransactionId"));
		
		if (SubActionType.SUB_ACTION_COF_FIRST.equals(this.subActionType)) {

			tokenParams.put("cardOnFileType", "First");

		} else if (SubActionType.SUB_ACTION_COF_RECURRING.equals(this.subActionType)) {

			tokenParams.put("cardOnFileType", "Repeat");
			tokenParams.put("cardOnFileInitiator", "Merchant");
			tokenParams.put("cardOnFileInitialTransactionId", inputParams.get("cardOnFileInitialTransactionId"));

		} else if (SubActionType.SUB_ACTION_MMRP_FIRST.equals(this.subActionType)) {

			tokenParams.put("cardOnFileType", "First");
			tokenParams.put("mmrpBillPayment", "Recurring");
			tokenParams.put("mmrpCustomerPresent", "BillPayment");

		} else if (SubActionType.SUB_ACTION_MMRP_RECURRING.equals(this.subActionType)) {

			tokenParams.put("cardOnFileType", "Repeat");
			tokenParams.put("cardOnFileInitiator", "Merchant");
			tokenParams.put("cardOnFileInitialTransactionId", inputParams.get("cardOnFileInitialTransactionId"));
			tokenParams.put("mmrpBillPayment", "Recurring");
			tokenParams.put("mmrpCustomerPresent", "BillPayment");

		}
				
		// ---

		return tokenParams;
	}

	@Override
	protected Map<String, String> getActionParams(final Map<String, String> inputParams, final String token) {

		final Map<String, String> actionParams = new HashMap<>();

		putMerchantId(inputParams, actionParams, this.config);

		actionParams.put("token", token);
		actionParams.put("customerId", inputParams.get("customerId"));
		
		MapUtil.putIfNotNull(actionParams, "specinCreditCardToken", inputParams.get("specinCreditCardToken")); // needed for regular credit/debit card transactions (see TOKENIZE action too)
		MapUtil.putIfNotNull(actionParams, "specinCreditCardCVV", inputParams.get("specinCreditCardCVV")); // needed for regular credit/debit card transactions
		
		MapUtil.putIfNotNull(actionParams, "specinCCWalletId", inputParams.get("specinCCWalletId")); // needed for Apple Pay / Google Pay
		MapUtil.putIfNotNull(actionParams, "specinCCWalletToken", inputParams.get("specinCCWalletToken")); // needed for Apple Pay / Google Pay
		
		return actionParams;
	}

}
