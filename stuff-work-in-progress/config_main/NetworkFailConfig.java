package com.evopayments.turnkey.apiclient.config;

import com.evopayments.turnkey.config.ApplicationConfig;

/**
 * Intentionally not valid / wrong URLs, can be used for network exception unit tests.
 * 
 * @author erbalazs
 *
 */
public class NetworkFailConfig extends ApplicationConfig {
	
	private static NetworkFailConfig instance;

	/**
	 * Java singleton implementation.
	 *
	 * @return the instance from this object
	 */
	public static NetworkFailConfig getInstance() {
		if (instance == null) {
			instance = new NetworkFailConfig();
		}
		return instance;
	}

	/**
	 * constructor of current class.
	 */
	private NetworkFailConfig(){
		// use getInstance...
		super();
	}

	@Override
	protected String getFilename() {
		return "application-network-fail.properties";
	}
	
}
