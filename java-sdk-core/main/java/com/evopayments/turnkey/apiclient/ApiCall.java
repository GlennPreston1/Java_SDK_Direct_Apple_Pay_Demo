package com.evopayments.turnkey.apiclient;

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

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;


/**
 * Intelligent Paymanents API calls (for API version: 1.7) base class.
 *
 * @author erbalazs
 */
public abstract class ApiCall {

    private static final Logger logger = LogManager.getLogger();

    private static final String TOKEN_URL_PROP_KEY = "application.sessionTokenRequestUrl";
    private static final String OPERATION_ACTION_URL_PROP_KEY =
            "application.paymentOperationActionUrl";
    private static final String MOBILE_CASHIER_URL = "application.mobile.cashierUrl";
    protected static final String ALLOW_ORIGIN_URL_PROP_KEY = "application.allowOriginUrl";
    protected static final String MERCHANT_NOTIFICATION_URL_PROP_KEY =
            "application.merchantNotificationUrl";
    protected static final String MERCHANT_LANDING_PAGE_URL_PROP_KEY =
            "application.merchantLandingPageUrl";

    protected static final String MERCHANT_ID_PROP_KEY = "application.merchantId";
    protected static final String PASSWORD_PROP_KEY = "application.password";
    protected static final String PASSWORD = "password";
    protected static final String MERCHANT_ID = "merchantId";

    protected final ApplicationConfig config;

    private final Map<String, String> inputParams;
    private final PrintWriter outputWriter;

    /**
     * prepares the call (+ calls a simple prevalidation).
     *
     * @param config
     * @param inputParams
     * @param outputWriter
     * @throws RequiredParamException
     */
    public ApiCall(final ApplicationConfig config,
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

            preValidateParams(inputParams);

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
     * String Map into {@link Form} conversion.
     *
     * @param params
     * @return form for HTTPClient
     */
    public static Form getForm(final Map<String, String> params) {

        final Form form = Form.form();

        final Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();
        while (iterator.hasNext()) {
            final Map.Entry<String, String> entry = iterator.next();
            form.add(entry.getKey(), entry.getValue());
        }

        return form;
    }

    /**
     * initiates HTTP POST toward the API (via {@link HttpClient}) (outgoing request).
     *
     * @param url
     * @param paramMap
     * @return the response as a parsed JSONObject
     * @throws PostToApiException
     */
    public static JSONObject postToApi(final String url, final Map<String, String> paramMap)
            throws PostToApiException {

        List<NameValuePair> paramList;

        try {
            paramList = getForm(paramMap).build();
        } catch (Exception e) {
            logger.error("PostToApiException: ", e);
            throw new PostToApiException("cannot build bodyForm for the HTTP request", e);
        }

        String apiResponseStr;
        try {
            final HttpResponse apiResponse = Request.Post(url).bodyForm(paramList, Consts.UTF_8).execute()
                    .returnResponse();
            apiResponseStr = new BasicResponseHandler().handleResponse(apiResponse);
        } catch (Exception e) {
            logger.error("TurnkeyCommunicationException: ", e);
            throw new TurnkeyCommunicationException();
        }

        try {
            return new JSONObject(apiResponseStr);
        } catch (Exception e) {
            logger.error("PostToApiException: ", e);
            throw new PostToApiException("failed to parse API call response (not JSON?)", e);
        }

    }

    protected abstract ActionType getActionType();

    /**
     * @param inputParams, user params (from Console, Servlet etc.)
     * @return keys/values for the token request
     */
    protected abstract Map<String, String> getTokenParams(Map<String, String> inputParams);

    /**
     * get action params
     *
     * @param inputParams original user params (from Console, Servlet etc).
     * @param token       the received token for the operation
     * @return null if no action call (only the token is needed)
     */
    protected abstract Map<String, String> getActionParams(Map<String, String> inputParams,
                                                           String token);

    /**
     * not everything is validated (mandatory fields checked, no complex validation,
     * the conditionally mandatory fields not check either).
     *
     * @param inputParams original user params (from Console, Servlet etc.)
     * @throws RequiredParamException
     */
    protected abstract void preValidateParams(Map<String, String> inputParams)
            throws RequiredParamException;

    /**
     * executes the call.
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
            Map<String, String> tokenParams = getTokenParams(new HashMap<>(inputParams));
            final JSONObject tokenResponse = postToApi(config.getProperty(TOKEN_URL_PROP_KEY), tokenParams);

            if (!((String) tokenResponse.get("result")).equals("failure")) {

                final String token = tokenResponse.get("token").toString();

                outputWriter.println("received token: " + token);
                outputWriter.println("");

                final Map<String, String> actionParams = getActionParams(inputParams, token);

                if (actionParams == null) {
                    return tokenResponse;
                }
                JSONObject actionResponse;
                if (actionParams.get("action") == ActionType.GET_MOBILE_CASHIER_URL.getCode()) {
                    tokenResponse.put("mobileCashierUrl", config.getProperty(MOBILE_CASHIER_URL));
                    tokenResponse.put("merchantId", tokenParams.get(MERCHANT_ID));
                    actionResponse = tokenResponse;
                } else {
                    actionResponse = postToApi(config.getProperty(OPERATION_ACTION_URL_PROP_KEY),
                            actionParams);
                }

                if (((String) actionResponse.get("result")).equals("failure")) {

                    outputWriter.println("(error)");
                    outputWriter.println("error during the action call:");
                    outputWriter.println(actionResponse.toString(4));

                    throw new ActionCallException(actionResponse.toString());

                }

                outputWriter.println("result:");
                outputWriter.println(actionResponse.toString(4));

                return actionResponse;

            }

            outputWriter.println("(error)");
            outputWriter.println("could not acquire a token:");
            outputWriter.println("");
            outputWriter.println(tokenResponse.toString(4));

            throw new TurnkeyTokenException(tokenResponse.toMap().get("errors").toString());

        } catch (final PostToApiException e) {
            logger.error("PostToApiException: ", e);
            outputWriter.println("(error)");
            outputWriter.println("outgoing POST failed ("
                    + "cause/class: " + e.getCause().getClass().getName()
                    + ", cause/msg: " + e.getCause().getMessage() + ")");
            throw new TurnkeyInternalException();

        } catch (TurnkeyGenericException e) {
            logger.error("TurnkeyGenericException: ", e);
            // It's OK to re-throw this exception, because it contains no sensitive information.
            throw e;
        } catch (final GeneralException e) {
            logger.error("GeneralException: ", e);
            outputWriter.println("(error)");
            outputWriter.println("general SDK error (" +
                    "cause/class: " + e.getCause().getClass().getName()
                    + ", cause/msg: " + e.getCause().getMessage() + ")");
            throw new TurnkeyInternalException();

        } catch (final SDKException e) {
            logger.error("SDKException: ", e);
            throw new TurnkeyInternalException();

        } catch (final Exception e) {
            logger.error("Exception: ", e);
            outputWriter.println("(error)");
            outputWriter.println("general SDK error ("
                    + "cause/class: " + e.getClass().getName()
                    + ", cause/msg: " + e.getMessage() + ")");
            throw new TurnkeyInternalException();

        } finally {
            if (outputWriter != null) {
                outputWriter.flush();
            }
        }
    }

    /**
     * @param inputParams
     * @param requiredParams
     */
    protected void mandatoryValidation(final Map<String, String> inputParams,
                                       final Set<String> requiredParams) {
        for (final Map.Entry<String, String> entry : inputParams.entrySet()) {

            if (entry.getValue() != null && !entry.getValue().trim().isEmpty()) {
                requiredParams.remove(entry.getKey());
            }

        }

        if (!requiredParams.isEmpty()) {
            throw new RequiredParamException(requiredParams);
        }

    }
}
