package com.evopayments.turnkey.apiclient.exception;

/**
 * Error Type
 *
 * @version $Id: ErrorType.java 17118 2014-03-18 14:09:33Z semysm $
 */
public enum ErrorType {

    COMMUNICATION_ERROR(-1998, "Timeout Exception"),

    INTERNAL_ERROR(-1999, "Internal error"),

    TOKEN_ERROR(-2000, "Got Token error"),

    VALIDATION_ERROR(-10000, "Requested parameter missed");



    private final int code;

    private final String description;

    private ErrorType(int code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * @return the code
     */
    public int getCode() {
        return code;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets enumerator value from error code
     *
     * @param errorCode
     *            error code
     * @return enumerator value or {@code null} when not found
     */
    public static ErrorType fromValue(int errorCode) {
        ErrorType value = null;

        for (ErrorType t : values()) {
            if (t.getCode() == errorCode) {
                value = t;
                break;
            }
        }

        return value;
    }

}