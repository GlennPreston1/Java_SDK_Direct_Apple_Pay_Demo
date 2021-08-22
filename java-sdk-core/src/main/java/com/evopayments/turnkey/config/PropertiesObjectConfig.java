package com.evopayments.turnkey.config;

import java.util.Properties;

/**
 * Config loaded from a {@link Properties} object
 * 
 * @author erbalazs
 */
public class PropertiesObjectConfig extends ApplicationConfig {
	
	private boolean isPrevalidationEnabled;

	public PropertiesObjectConfig(final Properties properties, boolean isPrevalidationEnabled) {
		super(properties);
		this.isPrevalidationEnabled = isPrevalidationEnabled;
	}
	
	public boolean isPrevalidationEnabled() {
		return isPrevalidationEnabled;
	}
	
}
