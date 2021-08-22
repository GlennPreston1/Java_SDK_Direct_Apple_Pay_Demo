package com.evopayments.turnkey;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.owasp.encoder.Encode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.evopayments.turnkey.apiclient.AbstractApiCall;
import com.evopayments.turnkey.apiclient.AuthCall;
import com.evopayments.turnkey.apiclient.CaptureCall;
import com.evopayments.turnkey.apiclient.GetAvailablePaymentSolutionsCall;
import com.evopayments.turnkey.apiclient.GetMobileCashierURLCall;
import com.evopayments.turnkey.apiclient.PurchaseCall;
import com.evopayments.turnkey.apiclient.RefundCall;
import com.evopayments.turnkey.apiclient.StatusCheckCall;
import com.evopayments.turnkey.apiclient.TokenizeCall;
import com.evopayments.turnkey.apiclient.VoidCall;
import com.evopayments.turnkey.apiclient.code.ActionType;
import com.evopayments.turnkey.apiclient.exception.TurnkeyCommunicationException;
import com.evopayments.turnkey.apiclient.exception.TurnkeyGenericException;
import com.evopayments.turnkey.apiclient.exception.TurnkeyInternalException;
import com.evopayments.turnkey.apiclient.exception.TurnkeyTokenException;
import com.evopayments.turnkey.apiclient.exception.TurnkeyValidationException;
import com.evopayments.turnkey.config.ApplicationConfig;

/**
 * Command line API client.
 *
 * @author erbalazs
 */
public class Start {

	private static Logger logger;

	public static void main(final String[] args) {
		
		System.setProperty("org.slf4j.simpleLogger.logFile", "System.err");
		System.setProperty("org.slf4j.simpleLogger.showDateTime", "true");
		System.setProperty("org.slf4j.simpleLogger.dateTimeFormat", "yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
		System.setProperty("org.slf4j.simpleLogger.showThreadName", "true");
		System.setProperty("org.slf4j.simpleLogger.levelInBrackets", "true");
		
		logger = LoggerFactory.getLogger(Start.class);
		
		// ---

		// sample cmd params:
		// -action TOKENIZE -number 5454545454545454 -nameOnCard "John Doe" -expiryMonth 12 -expiryYear 2022

		final Map<String, String> paramMap = new HashMap<>();

		parseArgs(args, paramMap);

		ActionType action = null;
		try {
			action = ActionType.valueOfCode(paramMap.get("action"));
		} catch (final IllegalArgumentException ex) {
			logger.error("Illegal action parameter usage");
			System.exit(1);
		}

		try {

			final ApplicationConfig config = ApplicationConfig.getInstanceBasedOnSysProp();

			JSONObject result = null;
			
			switch (action) {
			case AUTH:
				result = new AuthCall(config, paramMap).execute();
				break;
			case CAPTURE:
				result = new CaptureCall(config, paramMap).execute();
				break;
			case GET_AVAILABLE_PAYSOLS:
				result = new GetAvailablePaymentSolutionsCall(config, paramMap).execute();
				break;
			case PURCHASE:
				result = new PurchaseCall(config, paramMap).execute();
				break;
			case REFUND:
				result = new RefundCall(config, paramMap).execute();
				break;
			case STATUS_CHECK:
				result = new StatusCheckCall(config, paramMap).execute();
				break;
			case TOKENIZE:
				result = new TokenizeCall(config, paramMap).execute();
				break;
			case VOID:
				result = new VoidCall(config, paramMap).execute();
				break;
			case GET_MOBILE_CASHIER_URL:
				result = new GetMobileCashierURLCall(config, paramMap).execute();
				break;
			default:
				logger.error("Illegal action parameter usage");
				System.exit(1);
				break;
			}
			
			System.out.println(result.toString(2));

		} catch (final TurnkeyValidationException e) {
			logger.error("missing parameters", e);
			System.exit(2);
		} catch (final TurnkeyTokenException e) {
			logger.error("could not acquire token", e);
			System.exit(3);
		} catch (final TurnkeyInternalException e) {
			logger.error("error during the action call", e);
			System.exit(4);
		} catch (final TurnkeyCommunicationException e) {
			logger.error("outgoing POST failed", e);
			System.exit(5);
		} catch (final TurnkeyGenericException e) {
			logger.error("general SDK error", e);
			System.exit(6);
		} catch (Exception e) {
			logger.error("other error", e);
			System.exit(7);
		}

	}

	private static void parseArgs(final String[] args, final Map<String, String> params) {
		String key = null;

		for (final String a : args) {

			if (a == null || a.trim().isEmpty()) {
				logger.error("Error at an argument");
				System.exit(1);
			}

			if (a.charAt(0) == '-') {
				if (a.length() < 2) {
					logger.error("Error at argument: " + Encode.forJava(a));
					System.exit(1);
				}

				key = a.substring(1);

			} else {

				if (key == null) {
					logger.error("Error at argument: " + Encode.forJava(a));
					System.exit(1);
				}

				params.put(key, a);
				key = null;

			}

		}
	}

}
