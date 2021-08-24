package com.evopayments.turnkey.apiclient;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.apache.http.impl.client.BasicResponseHandler;
import org.json.JSONObject;
import org.owasp.encoder.Encode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.evopayments.turnkey.apiclient.code.ActionType;
import com.evopayments.turnkey.apiclient.exception.TurnkeyCommunicationException;
import com.evopayments.turnkey.apiclient.exception.TurnkeyGenericException;
import com.evopayments.turnkey.apiclient.exception.TurnkeyInternalException;
import com.evopayments.turnkey.apiclient.exception.TurnkeyTokenException;
import com.evopayments.turnkey.apiclient.exception.TurnkeyValidationException;
import com.evopayments.turnkey.config.ApplicationConfig;
import com.evopayments.turnkey.util.crypto.TextEncryptionUtil;

/**
 * Intelligent Payments Turnkey API calls 
 * (abstract base class).
 *
 * @author erbalazs
 * 
 * @see AbstractApvCall
 */
public abstract class AbstractApiCall {

	private static final Logger logger = LoggerFactory.getLogger(AbstractApiCall.class);

	public static final String TOKEN_URL_PROP_KEY = "application.sessionTokenRequestUrl";
	public static final String OPERATION_ACTION_URL_PROP_KEY = "application.paymentOperationActionUrl";
	public static final String CASHIER_ROOT_URL_PROP_KEY = "application.cashierRootUrl";
	public static final String CASHIER_URL_PROP_KEY = "application.cashierUrl";
	public static final String CASHIER_JS_LIB_URL_PROP_KEY = "application.cashierJsLibUrl";
	public static final String MOBILE_CASHIER_URL_PROP_KEY = "application.mobile.cashierUrl";
	public static final String ALLOW_ORIGIN_URL_PROP_KEY = "application.allowOriginUrl";
	public static final String MERCHANT_NOTIFICATION_URL_PROP_KEY = "application.merchantNotificationUrl";
	public static final String MERCHANT_LANDING_PAGE_URL_PROP_KEY = "application.merchantLandingPageUrl";
	public static final String MERCHANT_ID_PROP_KEY = "application.merchantId";
	public static final String PASSWORD_PROP_KEY = "application.password";
	public static final String COUNTRY_PROP_KEY = "application.country";
	public static final String CURRENCY_PROP_KEY = "application.currency";
	
	/**
	 * @param inputParams
	 * @param requiredParams
	 */
	protected static void mandatoryValidation(final Map<String, String> inputParams,
			final Set<String> requiredParams) {

		final Set<String> requiredParams2 = new HashSet<>(requiredParams);

		for (final Map.Entry<String, String> entry : inputParams.entrySet()) {

			if (entry.getValue() != null && !entry.getValue().trim().isEmpty()) {
				requiredParams2.remove(entry.getKey());
			}

		}

		if (!requiredParams2.isEmpty()) {
			throw new TurnkeyValidationException(Encode.forJava(requiredParams2.toString()));
		}

	}

	/**
	 * This method extracts "merchantId" and "password" from inputParams and adds it to tokenParams. 
	 * If there is no parameter named "merchantId" in inputParams then gets both from the {@link ApplicationConfig} object.
	 * 
	 * @param inputParams
	 * @param tokenParams
	 * @param config
	 */
	protected static void putMerchantCredentials(
			final Map<String, String> inputParams,
			final Map<String, String> tokenParams,
			final ApplicationConfig config) {

		final String merchantId = inputParams.get("merchantId");

		if (merchantId == null || merchantId.isEmpty()) {

			/*
			 * Option - 1: ApplicationConfig instance for storing "merchantId" and "password"
			 */

			tokenParams.put("merchantId", config.getProperty(MERCHANT_ID_PROP_KEY));
			tokenParams.put("password", config.getProperty(PASSWORD_PROP_KEY));

		} else {

			/*
			 * Option - 2: supplying "merchantId" and "password" in inputParams Map
			 */

			tokenParams.put("merchantId", merchantId);
			tokenParams.put("password", inputParams.get("password"));

		}
	}
	
	/**
	 * This method extracts "merchantId" from inputParams and adds it to actionParams. 
	 * If there is no parameter named "merchantId" in inputParams then gets it from the {@link ApplicationConfig} object.
	 * 
	 * @param inputParams
	 * @param actionParams
	 * @param config
	 */
	protected static void putMerchantId(
			final Map<String, String> inputParams,
			final Map<String, String> actionParams,
			final ApplicationConfig config) {

		final String merchantId = inputParams.get("merchantId");

		if (merchantId == null || merchantId.isEmpty()) {

			/*
			 * Option - 1: ApplicationConfig instance for storing "merchantId" and "password"
			 */

			actionParams.put("merchantId", config.getProperty(MERCHANT_ID_PROP_KEY));

		} else {

			/*
			 * Option - 2: supplying "merchantId" and "password" in inputParams Map
			 */

			actionParams.put("merchantId", merchantId);

		}
	}
	
	/**
	 * This method extracts "country" from inputParams and adds it to tokenParams or actionParams . 
	 * If there is no parameter named "country" in inputParams then gets it from the {@link ApplicationConfig} object.
	 * 
	 * @param inputParams
	 * @param tokenOrActionParams
	 * @param config
	 */
	protected static void putCountry(
			final Map<String, String> inputParams,
			final Map<String, String> tokenOrActionParams,
			final ApplicationConfig config) {

		final String country = inputParams.get("country");

		if (country == null || country.isEmpty()) {

			/*
			 * Option - 1: ApplicationConfig instance for storing "merchantId" and "password"
			 */

			tokenOrActionParams.put("country", config.getProperty(COUNTRY_PROP_KEY));

		} else {

			/*
			 * Option - 2: supplying "merchantId" and "password" in inputParams Map
			 */

			tokenOrActionParams.put("country", country);

		}
	}

	/**
	 * This method extracts "currency" from inputParams and adds it to tokenParams or actionParams . 
	 * If there is no parameter named "country" in inputParams then gets it from the {@link ApplicationConfig} object.
	 * 
	 * @param inputParams
	 * @param tokenOrActionParams
	 * @param config
	 */
	protected static void putCurrency(
			final Map<String, String> inputParams,
			final Map<String, String> tokenOrActionParams,
			final ApplicationConfig config) {

		final String country = inputParams.get("currency");

		if (country == null || country.isEmpty()) {

			/*
			 * Option - 1: ApplicationConfig instance for storing "merchantId" and "password"
			 */

			tokenOrActionParams.put("currency", config.getProperty(CURRENCY_PROP_KEY));

		} else {

			/*
			 * Option - 2: supplying "merchantId" and "password" in inputParams Map
			 */

			tokenOrActionParams.put("currency", country);

		}
	}

	/**
	 * String {@link Map} into {@link Form} conversion.
	 *
	 * @param params
	 *
	 * @return form object for {@link HttpClient}
	 */
	protected static Form getForm(final Map<String, String> params) {

		final Form form = Form.form();

		final Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();
		while (iterator.hasNext()) {
			final Map.Entry<String, String> entry = iterator.next();
			form.add(entry.getKey(), entry.getValue());
		}

		return form;
	}

	/**
	 * Initiates HTTP POST toward the API (via {@link HttpClient}) (outgoing request) and parses the response body into a {@link JSONObject}
	 *
	 * @param url
	 * @param paramMap
	 * 
	 * @return the response body as a parsed {@link JSONObject}
	 */
	private static JSONObject postToApi(final String url, final Map<String, String> paramMap) {

		List<NameValuePair> paramList;

		try {
			paramList = getForm(paramMap).build();
		} catch (final Exception e) {
			throw new TurnkeyInternalException("Failed to build bodyForm for the HTTP request.");
		}

		String apiResponseStr;
		try {
			final HttpResponse apiResponse = Request.Post(url).bodyForm(paramList, Consts.UTF_8).execute()
					.returnResponse();
			apiResponseStr = new BasicResponseHandler().handleResponse(apiResponse);
		} catch (final Exception e) {
			throw new TurnkeyCommunicationException("Failed to execute HTTP POST.");
		}

		try {
			return new JSONObject(apiResponseStr);
		} catch (final Exception e) {
			throw new TurnkeyInternalException("Failed to parse API call response (not JSON?).");
		}

	}

	protected final ApplicationConfig config;

	protected final Map<String, String> inputParams;

	/**
	 * @deprecated
	 * 
	 * @param config
	 * @param inputParams
	 * @param outputWriter
	 * 		deprecated parameter, outputWriter is not utilized anymore, 
	 * 		instead use and adjust logging
	 */
	@Deprecated
	public AbstractApiCall(final ApplicationConfig config,
			final Map<String, String> inputParams,
			final PrintWriter outputWriter) {
		this(config, inputParams);
	}

	/**
	 * prepares the call 
	 * (+ optionally executes a simple prevalidation of the API parameter values)
	 *
	 * @param config
	 * @param inputParams
	 */
	public AbstractApiCall(final ApplicationConfig config,
			final Map<String, String> inputParams) {

		try {

			this.config = config;

			if (config.isPrevalidationEnabled()) {
				this.preValidateParams(inputParams);
			}

			this.inputParams = inputParams;

		} catch (final TurnkeyGenericException e) {

			final String errorMsgStr = "Constructor of " + this.getClass().getName() + " class failed because of subclass of TurnkeyGenericException exception: "
					+ e.getClass().getName() + ", message: " + e.getMessage();
			logger.error(Encode.forJava(errorMsgStr));

			throw e;

		} catch (final Exception e) {

			final String errorMsgStr = "Constructor of " + this.getClass().getName() + " class failed because of an otherwise uncaught exception: " + e.getClass().getName();
			logger.error(Encode.forJava(errorMsgStr));

			throw new TurnkeyInternalException(errorMsgStr);

		}

	}
	
	private static void decryptPasswordBasedOnSystemPropPass(final Map<String, String> tokenParams) {
		
		String p = tokenParams.get("password");
		
		if (p.startsWith("ENC-")) {
			p = p.substring(4);
			p = TextEncryptionUtil.decryptBasedOnSystemPropPass(p);
			tokenParams.put("password", p);
		}
		
	}
	
	/**
	 * Executes the call 
	 * (builds and executes the token request, handles its response, 
	 * after that does the same with the action request).
	 *
	 * @return the JSON response of the last response
	 */
	public final JSONObject execute() {

		try {
			
			final Map<String, String> tokenParams = this.getTokenParams(new HashMap<>(this.inputParams));
			
			decryptPasswordBasedOnSystemPropPass(tokenParams);
			
			final JSONObject tokenResponse = postToApi(this.config.getProperty(TOKEN_URL_PROP_KEY), tokenParams);

			if (((String) tokenResponse.get("result")).equals("failure")) {
				logger.debug("Error during the token request, tokenResponse: " + Encode.forJava(tokenResponse.toString()));
				throw new TurnkeyTokenException("Could not acquire authentication token (which is needed for the subsequent action call)!");
			}

			final String token = tokenResponse.get("token").toString();

			// ---

			final Map<String, String> actionParams = this.getActionParams(this.inputParams, token);

			if (actionParams == null) { // in case the call has no second (action) part
				return tokenResponse;
			}

			// ---

			JSONObject actionResponse;

			if (ActionType.GET_MOBILE_CASHIER_URL.getCode().equals(actionParams.get("action"))) {

				// GET_MOBILE_CASHIER_URL is a special case

				tokenResponse.put("mobileCashierUrl", this.config.getProperty(MOBILE_CASHIER_URL_PROP_KEY));
				tokenResponse.put("merchantId", tokenParams.get("merchantId"));

				actionResponse = tokenResponse;

			} else {
				actionResponse = postToApi(this.config.getProperty(OPERATION_ACTION_URL_PROP_KEY), actionParams);
			}

			if (((String) actionResponse.get("result")).equals("failure")) {
				logger.debug("Error during the action request, actionResponse: " + Encode.forJava(actionResponse.toString()));
				throw new TurnkeyInternalException("Error during the action request!");
			}

			return actionResponse;

		} catch (final TurnkeyGenericException e) {

			final String errorMsgStr = "Execute method of " + this.getClass().getName() + " class failed because of subclass of TurnkeyGenericException exception: "
					+ e.getClass().getName() + ", message: " + e.getMessage();
			logger.error(errorMsgStr);

			throw e;

		} catch (final Exception e) {

			final String errorMsgStr = "Execute method of " + this.getClass().getName() + " class failed because of an otherwise uncaught exception: " + e.getClass().getName();
			logger.error(errorMsgStr);

			throw new TurnkeyInternalException(errorMsgStr);

		}

	}

	/**
	 * Extracts the params needed for making the action request.
	 *
	 * @param inputParams 
	 * @param token 
	 * 		the previously received token for the operation
	 * 
	 * @return 
	 * 		keys/values for the action request, null if no 
	 * 		action request needed (meaning only the token is needed, 
	 * 		there is no second call)
	 */
	protected abstract Map<String, String> getActionParams(Map<String, String> inputParams,
			String token);

	protected abstract ActionType getActionType();

	/**
	 * Extracts the params needed for making the authentication token request.
	 * 
	 * @param inputParams
	 * @return keys/values for the token request
	 */
	protected abstract Map<String, String> getTokenParams(Map<String, String> inputParams);

	/**
	 * Limited validation (mandatory fields checked, no complex validation,
	 * the conditionally mandatory fields not check either).
	 *
	 * @param inputParams
	 * 
	 * @throws TurnkeyValidationException
	 */
	protected abstract void preValidateParams(Map<String, String> inputParams)
			throws TurnkeyValidationException;
}
