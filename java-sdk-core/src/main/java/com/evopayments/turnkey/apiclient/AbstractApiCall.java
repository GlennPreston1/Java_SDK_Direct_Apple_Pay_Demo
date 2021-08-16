package com.evopayments.turnkey.apiclient;

import java.io.IOException;
import java.io.OutputStream;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.evopayments.turnkey.apiclient.code.ActionType;
import com.evopayments.turnkey.apiclient.exception.ActionCallException;
import com.evopayments.turnkey.apiclient.exception.GeneralException;
import com.evopayments.turnkey.apiclient.exception.PostToApiException;
import com.evopayments.turnkey.apiclient.exception.RequiredParamException;
import com.evopayments.turnkey.apiclient.exception.SDKException;
import com.evopayments.turnkey.apiclient.exception.TokenAcquirationException;
import com.evopayments.turnkey.apiclient.exception.TurnkeyCommunicationException;
import com.evopayments.turnkey.apiclient.exception.TurnkeyGenericException;
import com.evopayments.turnkey.apiclient.exception.TurnkeyInternalException;
import com.evopayments.turnkey.apiclient.exception.TurnkeyTokenException;
import com.evopayments.turnkey.apiclient.exception.TurnkeyValidationException;
import com.evopayments.turnkey.config.ApplicationConfig;

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

	private static final String TOKEN_URL_PROP_KEY = "application.sessionTokenRequestUrl";
	private static final String OPERATION_ACTION_URL_PROP_KEY = "application.paymentOperationActionUrl";
	private static final String MOBILE_CASHIER_URL_PROP_KEY = "application.mobile.cashierUrl";
	protected static final String ALLOW_ORIGIN_URL_PROP_KEY = "application.allowOriginUrl";
	protected static final String MERCHANT_NOTIFICATION_URL_PROP_KEY = "application.merchantNotificationUrl";
	protected static final String MERCHANT_LANDING_PAGE_URL_PROP_KEY = "application.merchantLandingPageUrl";

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
			throw new RequiredParamException(requiredParams2);
		}

	}

	/**
	 * This method extracts "merchantId" and "password" from inputParams and adds it to tokenParams. 
	 * If there is no parameter named "merchantId" in inputParams then it's get it from the {@link ApplicationConfig} object
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

			tokenParams.put("merchantId", config.getProperty("application.merchantId"));
			tokenParams.put("password", config.getProperty("application.password"));

		} else {

			/*
			 * Option - 2: supplying "merchantId" and "password" in inputParams Map
			 */

			tokenParams.put("merchantId", merchantId);
			tokenParams.put("password", inputParams.get("password"));

		}
	}

	/**
	 * String {@link Map} into {@link Form} conversion.
	 *
	 * @param params
	 *
	 * @return form for HTTPClient
	 */
	private static Form getForm(final Map<String, String> params) {

		final Form form = Form.form();

		final Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();
		while (iterator.hasNext()) {
			final Map.Entry<String, String> entry = iterator.next();
			form.add(entry.getKey(), entry.getValue());
		}

		return form;
	}

	/**
	 * Initiates HTTP POST toward the API (via {@link HttpClient}) (outgoing request).
	 *
	 * @param url
	 * @param paramMap
	 * 
	 * @return the response as a parsed JSONObject
	 * 
	 * @throws PostToApiException
	 */
	private static JSONObject postToApi(final String url, final Map<String, String> paramMap)
			throws PostToApiException {

		List<NameValuePair> paramList;

		try {
			paramList = getForm(paramMap).build();
		} catch (final Exception e) {
			logger.error("PostToApiException: ", e);
			throw new PostToApiException("cannot build bodyForm for the HTTP request", e);
		}

		String apiResponseStr;
		try {
			final HttpResponse apiResponse = Request.Post(url).bodyForm(paramList, Consts.UTF_8).execute()
					.returnResponse();
			apiResponseStr = new BasicResponseHandler().handleResponse(apiResponse);
		} catch (final Exception e) {
			logger.error("TurnkeyCommunicationException: ", e);
			throw new TurnkeyCommunicationException();
		}

		try {
			return new JSONObject(apiResponseStr);
		} catch (final Exception e) {
			logger.error("PostToApiException: ", e);
			throw new PostToApiException("failed to parse API call response (not JSON?)", e);
		}

	}

	protected final ApplicationConfig config;

	private final Map<String, String> inputParams;

	private final PrintWriter outputWriter;

	/**
	 * prepares the call 
	 * (+ optionally executes a simple prevalidation of the API parameter values)
	 *
	 * @param config
	 * @param inputParams
	 * @param outputWriter
	 * 
	 * @throws RequiredParamException
	 */
	public AbstractApiCall(final ApplicationConfig config,
			final Map<String, String> inputParams, PrintWriter outputWriter)
			throws RequiredParamException {

		try {

			this.config = config;

			this.inputParams = inputParams;

			if (outputWriter == null) {
				outputWriter = new PrintWriter(new OutputStream() {

					@Override
					public void write(final int b) throws IOException {
						// discard every write
					}
				});
			}

			outputWriter.println("params: " + inputParams);
			outputWriter.println("");

			this.outputWriter = outputWriter;

			if (config.isPrevalidationEnabled()) {
				this.preValidateParams(inputParams);
			}

		} catch (final RequiredParamException e) {

			logger.error("RequiredParamException: ", e);
			outputWriter.println("(error)");
			outputWriter.println("missing required params: " + e.getMissingFields());
			throw new TurnkeyValidationException(e.getMissingFields().toString());

		} catch (final TurnkeyInternalException e) {

			logger.error("TurnkeyInternalException: ", e);
			outputWriter.println("(error)");
			outputWriter.println("general SDK error (cause/class: " + e.getClass().getName()
					+ ", cause/msg: " + e.getMessage() + ")");
			throw new TurnkeyInternalException();

		} catch (final Exception e) {

			logger.error("TurnkeyInternalException: ", e);
			outputWriter.println("(error)");
			outputWriter.println("general SDK error (cause/class: " + e.getClass().getName()
					+ ", cause/msg: " + e.getMessage() + ")");
			throw new TurnkeyInternalException();

		} finally {
			if (outputWriter != null) {
				outputWriter.flush();
			}
		}

	}

	/**
	 * Executes the call.
	 *
	 * @return the JSON response of the action call
	 * @throws TokenAcquirationException
	 * @throws ActionCallException
	 * @throws PostToApiException
	 * @throws GeneralException
	 */
	public JSONObject execute() throws PostToApiException, TurnkeyTokenException,
			ActionCallException, GeneralException, TurnkeyInternalException {

		logger.info("API/SDK call: ", this.getActionType());

		try {
			final Map<String, String> tokenParams = this.getTokenParams(new HashMap<>(this.inputParams));
			final JSONObject tokenResponse = postToApi(this.config.getProperty(TOKEN_URL_PROP_KEY), tokenParams);

			if (!((String) tokenResponse.get("result")).equals("failure")) {

				final String token = tokenResponse.get("token").toString();

				this.outputWriter.println("received token: " + token);
				this.outputWriter.println("");

				final Map<String, String> actionParams = this.getActionParams(this.inputParams, token);

				if (actionParams == null) {
					return tokenResponse;
				}
				JSONObject actionResponse;
				if (actionParams.get("action") == ActionType.GET_MOBILE_CASHIER_URL.getCode()) {
					tokenResponse.put("mobileCashierUrl", this.config.getProperty(MOBILE_CASHIER_URL_PROP_KEY));
					tokenResponse.put("merchantId", tokenParams.get("merchantId"));
					actionResponse = tokenResponse;
				} else {
					actionResponse = postToApi(this.config.getProperty(OPERATION_ACTION_URL_PROP_KEY),
							actionParams);
				}

				if (((String) actionResponse.get("result")).equals("failure")) {

					this.outputWriter.println("(error)");
					this.outputWriter.println("error during the action call:");
					this.outputWriter.println(actionResponse.toString(4));

					throw new ActionCallException(actionResponse.toString());

				}

				this.outputWriter.println("result:");
				this.outputWriter.println(actionResponse.toString(4));

				return actionResponse;

			}

			this.outputWriter.println("(error)");
			this.outputWriter.println("could not acquire a token:");
			this.outputWriter.println("");
			this.outputWriter.println(tokenResponse.toString(4));

			throw new TurnkeyTokenException(tokenResponse.toMap().get("errors").toString());

		} catch (final PostToApiException e) {
			logger.error("PostToApiException: ", e);
			this.outputWriter.println("(error)");
			this.outputWriter.println("outgoing POST failed ("
					+ "cause/class: " + e.getCause().getClass().getName()
					+ ", cause/msg: " + e.getCause().getMessage() + ")");
			throw new TurnkeyInternalException();

		} catch (final TurnkeyGenericException e) {
			logger.error("TurnkeyGenericException: ", e);
			// It's OK to re-throw this exception, because it contains no sensitive information.
			throw e;
		} catch (final GeneralException e) {
			logger.error("GeneralException: ", e);
			this.outputWriter.println("(error)");
			this.outputWriter.println("general SDK error (" +
					"cause/class: " + e.getCause().getClass().getName()
					+ ", cause/msg: " + e.getCause().getMessage() + ")");
			throw new TurnkeyInternalException();

		} catch (final SDKException e) {
			logger.error("SDKException: ", e);
			throw new TurnkeyInternalException();

		} catch (final Exception e) {
			logger.error("Exception: ", e);
			this.outputWriter.println("(error)");
			this.outputWriter.println("general SDK error ("
					+ "cause/class: " + e.getClass().getName()
					+ ", cause/msg: " + e.getMessage() + ")");
			throw new TurnkeyInternalException();

		} finally {
			if (this.outputWriter != null) {
				this.outputWriter.flush();
			}
		}
	}

	/**
	 * Extracts the params needed for making the action request.
	 *
	 * @param inputParams 
	 * @param token 
	 * 		the received token for the operation
	 * 
	 * @return null if there is no action call (only the token is needed, there is no second call)
	 */
	protected abstract Map<String, String> getActionParams(Map<String, String> inputParams,
			String token);

	protected abstract ActionType getActionType();

	/**
	 * Extracts the params needed for making an authentication token request.
	 * 
	 * @param inputParams
	 * @return keys/values 
	 * 		for the token request
	 */
	protected abstract Map<String, String> getTokenParams(Map<String, String> inputParams);

	/**
	 * Limited validation (mandatory fields checked, no complex validation,
	 * the conditionally mandatory fields not check either).
	 *
	 * @param inputParams
	 * 
	 * @throws RequiredParamException
	 */
	protected abstract void preValidateParams(Map<String, String> inputParams)
			throws RequiredParamException;
}
