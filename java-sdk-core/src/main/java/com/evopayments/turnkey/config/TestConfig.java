package com.evopayments.turnkey.config;


/**
 * Config loaded from resources/turnkey-sdk-test.properties file.
 * 
 * @author erbalazs
 */
public class TestConfig extends ApplicationConfig {
	
	private static TestConfig instance;

	public static TestConfig getInstance() {
		if (instance == null) {
			instance = new TestConfig();
		}
		return instance;
	}

	private TestConfig() {
		// private constructor, use getInstance...
		super("turnkey-sdk-test.properties");
	}
	
}
