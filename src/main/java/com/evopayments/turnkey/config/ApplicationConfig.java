package com.evopayments.turnkey.config;

import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Application properties (request URLs etc.)
 */
public abstract class ApplicationConfig {

	private static final  Logger logger = Logger.getLogger(ApplicationConfig.class.getName());

	private final Properties properties = new Properties();

	static {

		// detailed logging for the API HTTP requests/responses (optional)
		
		// USE
		// -Devopayments-turnkey-sdk-http-log=none
		// OR
		// -Devopayments-turnkey-sdk-http-log=verbose
		// (none is the default)

		if (System.getProperty("evopayments-turnkey-sdk-http-log", "none")
				.equals("verbose")) {
			System.setProperty("org.apache.commons.logging.Log",
					"org.apache.commons.logging.impl.SimpleLog");
			System.setProperty("org.apache.commons.logging.simplelog.showdatetime", "true");
			System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http.wire",
					"DEBUG");
		}

	}

	/**
	 * get production/test instance which represents the production/test property file.
	 *
	 * @return production/test instance
	 *
	 */
	public static ApplicationConfig getInstanceBasedOnSysProp() {

		ApplicationConfig config;

		config = ProductionConfig.getInstance();

		logger.log(Level.INFO, "active config: " + config.getFilename());

		return config;

	}

	/**
	 * constructor of current class.
	 */
	public ApplicationConfig() {

		final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

		try (InputStream input = classLoader.getResourceAsStream(getFilename())) {
			properties.load(input);
		} catch (final Exception e) {
			logger.log(Level.SEVERE, "could not load config", e);
			throw new RuntimeException("could not load config", e);
		}
	}

	/**
	 * Read values from properties.
	 *
	 * @param key
	 * @return a value from properties
	 */
	public String getProperty(final String key) {
		return properties.getProperty(key);
	}

	/**
	 * get config property file name
	 * @return
	 */
	protected abstract String getFilename();
}
