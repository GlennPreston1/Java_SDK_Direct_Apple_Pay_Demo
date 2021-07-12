package com.evopayments.turnkey.apiclient.code;

/**
 * Type of sex.
 */
public enum Sex {
    MALE("M"),
    FEMALE("F");

    /**
     * defind sex code
     */
    protected String code;

    Sex(final String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
