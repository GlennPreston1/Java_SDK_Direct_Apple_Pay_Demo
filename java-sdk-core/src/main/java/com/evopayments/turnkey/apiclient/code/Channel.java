package com.evopayments.turnkey.apiclient.code;

/**
 * Channels.
 */
public enum Channel {
	
	/**
	 * ECOM = Electronic Commerce
	 */
    ECOM("ECOM"),
    
    /**
     * MOTO = Mail Order Telephone Order
     */
    MOTO("MOTO");

    private final String code;

    Channel(final String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
