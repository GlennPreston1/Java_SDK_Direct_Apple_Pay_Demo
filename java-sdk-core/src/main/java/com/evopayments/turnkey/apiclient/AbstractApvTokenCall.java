package com.evopayments.turnkey.apiclient;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.apache.http.client.utils.URLEncodedUtils;
import com.evopayments.turnkey.apiclient.code.ActionType;
import com.evopayments.turnkey.config.ApplicationConfig;

/**
 * [relevant for non-PCI compliant merchants]
 * 
 * Only token acquiration. The token aquired here can be used to initialize
 * Turnkey Cashier UI (fully managed UI, works with redirection or in an
 * iframe).
 * 
 * @author erbalazs
 */
public class AbstractApvTokenCall extends AbstractApvCall {

	private static final Set<String> requiredParams = Collections
			.unmodifiableSet(new HashSet<>(Arrays.asList("amount", "channel", "country", "currency")));

	/**
	 * @deprecated
	 * 
	 * @param config
	 * @param inputParams
	 * @param outputWriter deprecated parameter, outputWriter is not utilized
	 *                     anymore, instead use and adjust logging
	 */
	@Deprecated
	public AbstractApvTokenCall(final ApplicationConfig config, final Map<String, String> inputParams,
			final PrintWriter outputWriter) {
		super(config, inputParams, outputWriter);
	}

	public AbstractApvTokenCall(final ApplicationConfig config, final Map<String, String> inputParams) {
		super(config, inputParams);
	}

	@Override
	protected ActionType getActionType() {
		return ActionType.PURCHASE;
	}

	@Override
	protected void preValidateParams(final Map<String, String> inputParams) {

		mandatoryValidation(inputParams, requiredParams);

	}

	@Override
	protected Map<String, String> getActionParams(final Map<String, String> inputParams, final String token) {
		// there is no action call
		// (only the token is needed in this case, there is no second call)
		return null;
	}

	/**
	 * For iframe mode Cashier UI
	 * 
	 * @return URL string, use as iframe src property
	 */
	public final String executeAndBuildCashierIframeUrl() {

		String tokenStr = execute().getString("token");
		String cashierRootUrl = config.getProperty(CASHIER_ROOT_URL_PROP_KEY);

		// ---

		final Map<String, String> redirectParams = new LinkedHashMap<>();

		// TODO: maybe some of these are not mandatory (plus they are redundant, ie. we
		// send language in token request too) (for now I have followed the internally
		// used checkout-demo as an example)

		redirectParams.put("operation", "");

		redirectParams.put("token", tokenStr);
		redirectParams.put("language", "en");

		putMerchantId(inputParams, redirectParams, config);

		redirectParams.put("integrationMode", "iframe");

		redirectParams.put("styleSheetUrl", "/cashier/css/cashier.css");
		redirectParams.put("styleSuffix", "-evopl");

		redirectParams.put("redirectionTime", "");
		redirectParams.put("allowCardHolderSpecialChars", "");
		redirectParams.put("templateName", "");
		redirectParams.put("paymentSolutionId", "");
		redirectParams.put("numberOfInstallments", "");
		redirectParams.put("installmentsPlanId", "");

		redirectParams.put("baseUrl", cashierRootUrl);

		return cashierRootUrl + "?" + URLEncodedUtils.format(getForm(redirectParams).build(), "UTF-8");

	}

	/**
	 * For HPP (hosted payment pages) mode Cashier UI (note: it has a similar role
	 * to standalone mode, however the UI is somewhat different)
	 * 
	 * @return URL as a string, redirect the customer here
	 */
	public final String executeAndBuildCashierHppUrl() {

		String tokenStr = execute().getString("token");
		String cashierRootUrl = config.getProperty(CASHIER_ROOT_URL_PROP_KEY);

		// ---

		final Map<String, String> redirectParams = new LinkedHashMap<>();

		// TODO: maybe some of these are not mandatory (plus they are redundant, ie. we
		// send language in token request too) (for now I have followed the internally
		// used checkout-demo as an example)

		redirectParams.put("operation", "");

		redirectParams.put("token", tokenStr);
		redirectParams.put("language", "en");

		putMerchantId(inputParams, redirectParams, config);

		redirectParams.put("integrationMode", "hostedPayPage");

		redirectParams.put("styleSheetUrl", "/cashier/css/cashier.css");
		redirectParams.put("styleSuffix", "-evopl");

		redirectParams.put("redirectionTime", "");
		redirectParams.put("allowCardHolderSpecialChars", "");
		redirectParams.put("templateName", "");
		redirectParams.put("paymentSolutionId", "");
		redirectParams.put("numberOfInstallments", "");
		redirectParams.put("installmentsPlanId", "");

		redirectParams.put("baseUrl", cashierRootUrl);

		return cashierRootUrl + "?" + URLEncodedUtils.format(getForm(redirectParams).build(), "UTF-8");

	}

	/**
	 * For standalone mode Cashier UI (note: it has a similar role to HPP mode,
	 * however the UI is somewhat different)
	 * 
	 * @return URL as a string, redirect the customer here
	 */
	public final String executeAndBuildCashierStandaloneUrl() {

		String tokenStr = execute().getString("token");
		String cashierRootUrl = config.getProperty(CASHIER_ROOT_URL_PROP_KEY);

		// ---

		final Map<String, String> redirectParams = new LinkedHashMap<>();

		// TODO: maybe some of these are not mandatory (plus they are redundant, ie. we
		// send language in token request too) (for now I have followed the internally
		// used checkout-demo as an example)

		redirectParams.put("operation", "");

		redirectParams.put("token", tokenStr);
		redirectParams.put("language", "en");

		putMerchantId(inputParams, redirectParams, config);

		redirectParams.put("integrationMode", "standalone");
		// redirectParams.put("originalIntegrationMode", ""); // ?

		redirectParams.put("styleSheetUrl", "/cashier/css/cashier.css");
		redirectParams.put("styleSuffix", "-evopl");

		redirectParams.put("redirectionTime", "");
		redirectParams.put("allowCardHolderSpecialChars", "");
		redirectParams.put("templateName", "");
		redirectParams.put("paymentSolutionId", "");
		redirectParams.put("numberOfInstallments", "");
		redirectParams.put("installmentsPlanId", "");
		redirectParams.put("baseUrl", cashierRootUrl);
		redirectParams.put("showBanner", "");
		redirectParams.put("action", "");
		redirectParams.put("maxColNum", "");
		redirectParams.put("targetSelector", "");

		return cashierRootUrl + "?" + URLEncodedUtils.format(getForm(redirectParams).build(), "UTF-8");

	}
}
