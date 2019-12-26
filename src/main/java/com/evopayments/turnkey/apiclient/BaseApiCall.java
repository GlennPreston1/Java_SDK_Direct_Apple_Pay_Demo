package com.evopayments.turnkey.apiclient;

import com.evopayments.turnkey.apiclient.exception.RequiredParamException;
import com.evopayments.turnkey.config.ApplicationConfig;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;


/**
 * Base class for Auth/Purchase/Verify
 * 
 * @author erbalazs
 *
 */
public abstract class BaseApiCall extends ApiCall {

	/**
	 * CARD ON FILE
	 */
	public static String SUB_ACTION_COF_FIRST = "SUB_ACTION_COF_FIRST";
	public static String SUB_ACTION_COF_RECURRING = "SUB_ACTION_COF_RECURRING";
	/**
	 * MMRP
	 */
	public static String SUB_ACTION_MMRP_FIRST = "SUB_ACTION_MMRP_FIRST";
	public static String SUB_ACTION_MMRP_RECURRING = "SUB_ACTION_MMRP_RECURRING";
	/**
	 * MMRP for Mexico merchant
	 */
	public static String SUB_ACTION_MMRP_MX_FIRST = "SUB_ACTION_MMRP_MX_FIRST";
	public static String SUB_ACTION_MMRP_MX_RECURRING = "SUB_ACTION_MMRP_MX_RECURRING";

	private String subActionType = null;

	public BaseApiCall(ApplicationConfig config, Map<String, String> inputParams, PrintWriter outputWriter) {
		super(config, inputParams, outputWriter);
	}

	public BaseApiCall(ApplicationConfig config, Map<String, String> inputParams, PrintWriter outputWriter, String subActionType) {
		super(config, inputParams, outputWriter);
		this.subActionType = subActionType;
	}

	@Override
	protected void preValidateParams(final Map<String, String> inputParams) throws RequiredParamException {

		final Set<String> requiredParams = new HashSet<>(Arrays.asList("amount", "channel", "country", "currency", "paymentSolutionId"));
		mandatoryValidation(inputParams,requiredParams);
	}

	@Override
	protected Map<String, String> getTokenParams(final Map<String, String> inputParams) {

		final Map<String, String> tokenParams = new HashMap<>();

		tokenParams.put("merchantId", config.getProperty(MERCHANT_ID_PROP_KEY));
		tokenParams.put("password", config.getProperty(PASSWORD_PROP_KEY));
		tokenParams.put("action", getActionType().getCode());
		tokenParams.put("timestamp", String.valueOf(System.currentTimeMillis()));
		tokenParams.put("allowOriginUrl", config.getProperty(ALLOW_ORIGIN_URL_PROP_KEY));
		tokenParams.put("channel", inputParams.get("channel"));
		tokenParams.put("amount", inputParams.get("amount"));
		tokenParams.put("currency", inputParams.get("currency"));
		tokenParams.put("country", inputParams.get("country"));
		tokenParams.put("paymentSolutionId", inputParams.get("paymentSolutionId"));
		tokenParams.put("merchantNotificationUrl", config.getProperty(MERCHANT_NOTIFICATION_URL_PROP_KEY));
		tokenParams.put("merchantLandingPageUrl", config.getProperty(MERCHANT_LANDING_PAGE_URL_PROP_KEY));


		if(SUB_ACTION_COF_FIRST.equals(this.subActionType)){
			tokenParams.put("cardOnFileType", "First");
		} else if(SUB_ACTION_COF_RECURRING.equals(this.subActionType)) {
			tokenParams.put("cardOnFileType", "Repeat");
			tokenParams.put("cardOnFileInitiator", "Merchant");
			tokenParams.put("cardOnFileInitialTransactionId",inputParams.get("cardOnFileInitialTransactionId"));
		} else if (SUB_ACTION_MMRP_FIRST.equals(this.subActionType)) {
			tokenParams.put("cardOnFileType", "First");
			tokenParams.put("mmrpBillPayment", "Recurring");
			tokenParams.put("mmrpCustomerPresent", "BillPayment");
		}else if (SUB_ACTION_MMRP_RECURRING.equals(this.subActionType)) {
			populateParamsForRecurring(tokenParams,inputParams);
			/**
			 * MX merchant only?
			 */
			tokenParams.put("mmrpOriginalMerchantTransactionId",inputParams.get("mmrpOriginalMerchantTransactionId"));
		} else if (SUB_ACTION_MMRP_MX_FIRST.equals(this.subActionType)) { // the first payment transaction for MX merchants
			tokenParams.put("cardOnFileType", "First");
			tokenParams.put("mmrpBillPayment", "Recurring");
			tokenParams.put("mmrpCustomerPresent", "BillPayment");
			tokenParams.put("mmrpContractNumber",inputParams.get("mmrpContractNumber"));
		}else if (SUB_ACTION_MMRP_MX_RECURRING.equals(this.subActionType)) { // Recurring payment transaction for MX merchants
			populateParamsForRecurring(tokenParams,inputParams);
			tokenParams.put("mmrpContractNumber",inputParams.get("mmrpContractNumber"));
			tokenParams.put("mmrpExistingDebt","NotExistingDebt");
			tokenParams.put("mmrpCurrentInstallmentNumber","1");
			tokenParams.put("mmrpOriginalMerchantTransactionId",inputParams.get("mmrpOriginalMerchantTransactionId"));
		}

		return tokenParams;
	}

	/**
	 * Common params required for MMRP
	 *
	 * @param tokenParams
	 * @param inputParams
	 */
	private void populateParamsForRecurring(Map<String, String> tokenParams, Map<String, String> inputParams) {
		tokenParams.put("cardOnFileType", "Repeat");
		tokenParams.put("cardOnFileInitiator", "Merchant");
		tokenParams.put("cardOnFileInitialTransactionId",inputParams.get("cardOnFileInitialTransactionId"));
		tokenParams.put("mmrpBillPayment", "Recurring");
		tokenParams.put("mmrpCustomerPresent", "BillPayment");
	}

	@Override
	protected Map<String, String> getActionParams(final Map<String, String> inputParams, final String token) {

		final Map<String, String> actionParams = new HashMap<>();

		actionParams.put("merchantId", config.getProperty(MERCHANT_ID_PROP_KEY));
		actionParams.put("token", token);
		actionParams.put("customerId", inputParams.get("customerId"));
		actionParams.put("specinCreditCardToken", inputParams.get("specinCreditCardToken"));
		actionParams.put("specinCreditCardCVV", inputParams.get("specinCreditCardCVV"));

		return actionParams;
	}
}
