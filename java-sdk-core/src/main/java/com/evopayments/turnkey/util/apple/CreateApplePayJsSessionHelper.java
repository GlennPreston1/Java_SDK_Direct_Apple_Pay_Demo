package com.evopayments.turnkey.util.apple;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

import com.evopayments.turnkey.apiclient.exception.TurnkeyInternalException;
import com.evopayments.turnkey.config.ApplicationConfig;
import com.evopayments.turnkey.util.crypto.TextEncryptionUtil;

/**
 * [relevant for PCI compliant merchants]
 * 
 * Relevant for advanced/custom Apple Pay (Direct API) 
 * (for simpler cases instantiating a standalone/HPP mode cashier-ui is enough) 
 */
public class CreateApplePayJsSessionHelper {
	
	public static final String APPLE_PAY_MERCHANT_IDENTITY_JKS_KEYSTORE_CLASSPATH = "application.applepay.helper.merchantIdentityJksKeystoreClasspath"; // "/apple_pay_merchant_identity.jks"
	public static final String APPLE_PAY_MERCHANT_IDENTIFIER = "application.applepay.helper.merchantIdentifier"; // "merchant.com.ipg.applepay.test.app"
	public static final String APPLE_PAY_INITIATIVE_CONTEXT = "application.applepay.helper.initiativeContext"; // "ebtest.eu.ngrok.io"
	public static final String APPLE_PAY_DOMAIN_NAME = "application.applepay.helper.domainName"; // "ebtest.eu.ngrok.io"
	public static final String APPLE_PAY_DISPLAY_NAME = "application.applepay.helper.displayName"; // "Example webshop"
	public static final String APPLE_PAY_KEYSTORE_PASSWORD = "application.applepay.helper.keyStorePassword"; // ab"
	public static final String APPLE_PAY_KEY_PASSWORD = "application.applepay.helper.keyPassword"; // "ab"
	public static final String APPLE_PAY_IS_SANDBOX_MODE = "application.applepay.helper.isSandboxMode"; // true

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

	private byte[] merchantIdentityJksKeystore;
	private final Map<String, String> requestBodyMap;
	
	private final String keyStorePassword;
	private final String keyPassword;
	
	private final boolean isSandboxMode;
		
	public CreateApplePayJsSessionHelper(final ApplicationConfig config) {
		
		try (InputStream is = CreateApplePayJsSessionHelper.class.getResource(config.getProperty(APPLE_PAY_MERCHANT_IDENTITY_JKS_KEYSTORE_CLASSPATH))
				.openStream()) {
			merchantIdentityJksKeystore = IOUtils.toByteArray(is);
		} catch (final IOException e) {
			new RuntimeException("ApplePayHelperController init failed!");
		}
		
		requestBodyMap = new HashMap<>();
		requestBodyMap.put("merchantIdentifier", config.getProperty(APPLE_PAY_MERCHANT_IDENTIFIER));
		requestBodyMap.put("initiativeContext", config.getProperty(APPLE_PAY_INITIATIVE_CONTEXT));
		requestBodyMap.put("domainName", config.getProperty(APPLE_PAY_DOMAIN_NAME));
		requestBodyMap.put("initiative", "web");
		requestBodyMap.put("displayName", config.getProperty(APPLE_PAY_DISPLAY_NAME));

		keyStorePassword = TextEncryptionUtil.decryptBasedOnSystemPropPassIfEncrypted(config.getProperty(APPLE_PAY_KEYSTORE_PASSWORD));
		keyPassword = TextEncryptionUtil.decryptBasedOnSystemPropPassIfEncrypted(config.getProperty(APPLE_PAY_KEY_PASSWORD));
		isSandboxMode = Boolean.parseBoolean(config.getProperty(APPLE_PAY_IS_SANDBOX_MODE));
		
	}

	@SuppressWarnings("resource")
	private SSLContext buildSslContextForAppleApiCall() {

		try {

			final KeyStore clientStore = KeyStore.getInstance("JKS"); 
			
			// KeyStore object is not thread-safe, see https://community.oracle.com/tech/developers/discussion/2078874/is-keystore-thread-safe
			// so we just store merchantIdentityJksKeystore as a byte[]
			
			try (InputStream is = new ByteArrayInputStream(merchantIdentityJksKeystore)) {
				clientStore.load(is, keyStorePassword.toCharArray());
			}

			final KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
			kmf.init(clientStore, keyPassword.toCharArray());
			final KeyManager[] kms = kmf.getKeyManagers();

			SSLContext sslContext = null;
			sslContext = SSLContext.getInstance("TLSv1.2"); // use TLSv1.3 when we can switch to newer Java version
			sslContext.init(kms, null, new SecureRandom());

			return sslContext;

		} catch (final Exception e) {
			throw new TurnkeyInternalException("buildSslContextForAppleApiCall (merchantIdentityJksKeystore load) failed!");
		}
	}

	/**
	 * By Apple referred as "Merchant Validation" step. 
	 * Needed only in case of JavaScript based Apple Pay.
	 * 
	 * @return
	 */
	public String createApplePayJsSession(final String appleValidationUrlFromJs) {

		{

			// Guide from Apple:
			// "Ensure that your server accesses only the validation URLs provided by Apple in Setting Up Your Server, and fails for other URLs.
			// Using a strict whitelist for the validation URLs is recommended.
			// The payment session request must be sent from your server;
			// never request the session from the client"

			String s = StringUtils.removeEnd(appleValidationUrlFromJs, "/");
			s = StringUtils.removeEnd(s, "/paymentservices/startSession");

			if (!(validationUrlWhiteList.contains(s) || (isSandboxMode && validationSandboxUrlWhiteList.contains(s)))) {
				// do not log the bad URL itself (might be unsafe for logging)
				throw new TurnkeyInternalException("Invalid Apple Pay validationURL (session init)!");
			}

		}

		// ---

		final String validationURL;

		if (isSandboxMode) {
			// in dev/sandbox cases the sandbox URL has to be used always 
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

			final JSONObject requestBodyJsonObject = new JSONObject(requestBodyMap);
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
