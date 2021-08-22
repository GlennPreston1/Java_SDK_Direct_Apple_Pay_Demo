package com.evopayments.turnkey.config;

import java.io.InputStream;
import java.util.Properties;

import org.owasp.encoder.Encode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.evopayments.turnkey.apiclient.exception.TurnkeyInternalException;

/**
 * Application properties (request URLs etc.)
 */
public abstract class ApplicationConfig {

	private static final Logger logger = LoggerFactory.getLogger(ApplicationConfig.class);

	private final Properties properties;

	private final String filename;

	/**
	 * Gets the classpath .properties file based (loaded from the file)
	 * production/test instance.
	 * 
	 * Use -Devopayments-turnkey-sdk-config=test or
	 * -Devopayments-turnkey-sdk-config=production
	 * 
	 * system property to influence which file (test or production variant) be used
	 * (for loading the props).
	 * 
	 * @return
	 *
	 */
	public static ApplicationConfig getInstanceBasedOnSysProp() {

		final String configParamStr = System.getProperty("evopayments-turnkey-sdk-config", "test");

		ApplicationConfig config;

		if ("production".equalsIgnoreCase(configParamStr)) {
			config = ProductionConfig.getInstance();
		} else {
			config = TestConfig.getInstance();
		}

		logger.info("active config: " + Encode.forJava(config.getFilename()));

		return config;

	}

	protected ApplicationConfig(final Properties properties) {

		this.filename = null;
		this.properties = properties;

	}

	/**
	 * Load config from a classpath .properties file
	 * 
	 * @param filename
	 */
	protected ApplicationConfig(final String filename) {

		this.filename = filename;
		this.properties = new Properties();

		// ---

		final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

		try (InputStream input = classLoader.getResourceAsStream(filename)) {
			this.properties.load(input);
		} catch (final Exception e) {
			logger.info("could not load config");
			throw new TurnkeyInternalException("could not load config");
		}

	}

	public String getProperty(final String key) {
		return this.properties.getProperty(key);
	}

	public String getFilename() {
		return this.filename;
	}

	public boolean isPrevalidationEnabled() {
		return true;
	}

}
