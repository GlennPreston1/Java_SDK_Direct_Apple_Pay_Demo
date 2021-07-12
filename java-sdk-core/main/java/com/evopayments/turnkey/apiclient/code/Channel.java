package com.evopayments.turnkey.apiclient.code;

/**
 * Type of channels.
 */
public enum Channel {
    ECOM("ECOM"),
    MOTO("MOTO");

    /**
     * define Channel code.
     */
    private final String code;

    Channel(final String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
