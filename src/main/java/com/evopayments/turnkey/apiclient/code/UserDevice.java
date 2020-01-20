package com.evopayments.turnkey.apiclient.code;

/**
 * Type of user devices.
 */
public enum UserDevice {
    MOBILE("MOBILE"),
    DESKTOP("DESKTOP"),
    UNKNOWN("UNKNOWN");

    /**
     * define user device code.
     */
    private String code;

    UserDevice(final String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
