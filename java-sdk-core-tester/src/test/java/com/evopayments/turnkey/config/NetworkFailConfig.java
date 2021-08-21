package com.evopayments.turnkey.config;

/**
 * Config loaded from resources/application-network-fail.properties file. 
 * Intentionally bad config for tests.
 * 
 * @author erbalazs
 */
public class NetworkFailConfig extends ApplicationConfig {
	
	private static NetworkFailConfig instance;

	public static NetworkFailConfig getInstance() {
		if (instance == null) {
			instance = new NetworkFailConfig();
		}
		return instance;
	}

	private NetworkFailConfig(){
		// private constructor, use getInstance...
		super("application-network-fail.properties");
	}

	
}
