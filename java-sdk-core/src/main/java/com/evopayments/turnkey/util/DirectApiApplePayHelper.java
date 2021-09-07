package com.evopayments.turnkey.util;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

import com.evopayments.turnkey.apiclient.exception.TurnkeyInternalException;

/**
 * [relevant for PCI compliant merchants]
 * 
 * Relevant for advanced/custom Apple Pay (Direct API) 
 * (for simpler cases instantiating a standalone/HPP mode cashier-ui is enough) 
 */
public class DirectApiApplePayHelper {

	private static final Set<String> validationUrlWhiteList = Collections.unmodifiableSet(new HashSet<String>(Arrays.asList(
			"https://apple-pay-gateway.apple.com",
			"https://apple-pay-gateway-nc-pod1.apple.com",
			"https://apple-pay-gateway-nc-pod2.apple.com",
			"https://apple-pay-gateway-nc-pod3.apple.com",
			"https://apple-pay-gateway-nc-pod4.apple.com",
			"https://apple-pay-gateway-nc-pod5.apple.com",
			"https://apple-pay-gateway-pr-pod1.apple.com",
			"https://apple-pay-gateway-pr-pod2.apple.com",
			"https://apple-pay-gateway-pr-pod3.apple.com",
			"https://apple-pay-gateway-pr-pod4.apple.com",
			"https://apple-pay-gateway-pr-pod5.apple.com",
			"https://apple-pay-gateway-nc-pod1-dr.apple.com",
			"https://apple-pay-gateway-nc-pod2-dr.apple.com",
			"https://apple-pay-gateway-nc-pod3-dr.apple.com",
			"https://apple-pay-gateway-nc-pod4-dr.apple.com",
			"https://apple-pay-gateway-nc-pod5-dr.apple.com",
			"https://apple-pay-gateway-pr-pod1-dr.apple.com",
			"https://apple-pay-gateway-pr-pod2-dr.apple.com",
			"https://apple-pay-gateway-pr-pod3-dr.apple.com",
			"https://apple-pay-gateway-pr-pod4-dr.apple.com",
			"https://apple-pay-gateway-pr-pod5-dr.apple.com")));

	private static final Set<String> validationSandboxUrlWhiteList = Collections.unmodifiableSet(new HashSet<String>(Arrays.asList(
			"https://apple-pay-gateway-cert.apple.com")));

	private DirectApiApplePayHelper() {
		// static util methods only
	}

	@SuppressWarnings("resource")
	private static SSLContext buildSslContextForAppleApiCall() {

		try {

			// TODO: currently used merchant.com.ipg.applepay.test.app.jks (renamed file)
			
			final String keyStorePassword = "ab"; // TODO: ...
			final String keyPassword = "ab"; // TODO: ...

			final KeyStore clientStore = KeyStore.getInstance("JKS");
			
			try (InputStream is = DirectApiApplePayHelper.class.getResource("/apple_pay_merchant_identity.jks").openStream()) {
				clientStore.load(is, keyStorePassword.toCharArray());
			}

			final KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
			kmf.init(clientStore, keyPassword.toCharArray());
			final KeyManager[] kms = kmf.getKeyManagers();

			SSLContext sslContext = null;
			sslContext = SSLContext.getInstance("TLS");
			sslContext.init(kms, null, new SecureRandom());

			return sslContext;

		} catch (final Exception e) {
			throw new TurnkeyInternalException("apple_pay_merchant_identity.jks load failed!");
		}
	}

	/**
	 * by Apple referred as "Merchant Validation" step
	 * @return
	 */
	public static String createApplePayJsSession(final String appleValidationUrlFromJs) {

		final boolean isDevMode = true; // TODO: 

		{

			// Guide from Apple:
			// "Ensure that your server accesses only the validation URLs provided by Apple in Setting Up Your Server, and fails for other URLs.
			// Using a strict whitelist for the validation URLs is recommended.
			// The payment session request must be sent from your server;
			// never request the session from the client"

			String s = StringUtils.removeEnd(appleValidationUrlFromJs, "/");
			s = StringUtils.removeEnd(s, "/paymentservices/startSession");

			if (!(validationUrlWhiteList.contains(s) || (isDevMode && validationSandboxUrlWhiteList.contains(s)))) {
				// do not log the bad URL itself (might be unsafe for logging)
				throw new TurnkeyInternalException("Invalid Apple Pay validationURL (session init)!");
			}

		}

		// ---

		final String validationURL;

		if (isDevMode) { // forces sandbox
			validationURL = validationSandboxUrlWhiteList.iterator().next() + "/paymentservices/startSession";
		} else {
			validationURL = appleValidationUrlFromJs;
		}

		// ---

		try {

			final URL url = new URL(validationURL);
			final HttpsURLConnection urlConn = (HttpsURLConnection) url.openConnection();

			urlConn.setSSLSocketFactory(buildSslContextForAppleApiCall().getSocketFactory());
			urlConn.setDoOutput(true);
			urlConn.setDoInput(true);
			urlConn.setRequestProperty("Content-Type", "application/json");
			urlConn.setRequestProperty("Accept", "application/json");
			urlConn.setRequestMethod("POST");

			// https://developer.apple.com/documentation/apple_pay_on_the_web/apple_pay_js_api/requesting_an_apple_pay_payment_session

			final JSONObject requestBodyJsonObject = new JSONObject();
			requestBodyJsonObject.put("merchantIdentifier", "merchant.com.ipg.applepay.test.app");
			requestBodyJsonObject.put("initiativeContext", "ebtest.eu.ngrok.io"); // TODO ...
			requestBodyJsonObject.put("domainName", "ebtest.eu.ngrok.io"); // TODO ...
			requestBodyJsonObject.put("initiative", "web");
			requestBodyJsonObject.put("displayName", "IPG Test");

			final String requestBodyStr = requestBodyJsonObject.toString();

			try (OutputStream os = urlConn.getOutputStream()) {
				IOUtils.write(requestBodyStr, os, "UTF-8");
			}

			final int responseCode = urlConn.getResponseCode();

			String reponseBodyStr;

			try (InputStream is = (responseCode == 200 ? urlConn.getInputStream() : urlConn.getErrorStream())) {
				reponseBodyStr = IOUtils.toString(is, "UTF-8");
			}

			return reponseBodyStr;

		} catch (final Exception e) {
			throw new TurnkeyInternalException("createApplePayJsSession(): Apple API request failed!");
		}

	}
	
	
}
