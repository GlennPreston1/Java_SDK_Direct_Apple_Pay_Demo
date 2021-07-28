package com.evopayments.turnkey.apiclient.code;

/**
 * User device types-
 */
public enum UserDevice {
	
    MOBILE("MOBILE"),
    DESKTOP("DESKTOP"),
    UNKNOWN("UNKNOWN");

    private String code;

    UserDevice(final String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
