package com.evopayments.turnkey.config;


/**
 * Config loaded from resources/turnkey-sdk-production.properties file.
 * 
 * @author erbalazs
 */
public class ProductionConfig extends ApplicationConfig {
	
	private static ProductionConfig instance;

	public static ProductionConfig getInstance() {
		if (instance == null) {
			instance = new ProductionConfig();
		}
		return instance;
	}

	private ProductionConfig(){
		// private constructor, use getInstance...
		super("turnkey-sdk-production.properties");
	}

	
}
