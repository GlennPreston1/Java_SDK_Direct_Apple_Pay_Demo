package com.evopayments.turnkey.apiclient.config;


import com.evopayments.turnkey.config.ApplicationConfig;

public class TestConfig extends ApplicationConfig {
	
	private static TestConfig instance;

	/**
	 * Java singleton implementation.
	 *
	 * @return the instance from this object
	 */
	public static TestConfig getInstance() {
		if (instance == null) {
			instance = new TestConfig();
		}
		return instance;
	}

	/**
	 * constructor of current class.
	 */
	private TestConfig() {
		// use getInstance...
		super();
	}

	@Override
	protected String getFilename() {
		return "application-test.properties";
	}
	
}
