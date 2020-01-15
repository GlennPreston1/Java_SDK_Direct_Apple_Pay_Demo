package com.evopayments.turnkey.apiclient.exception;

/**
 * Enum is to define the exception text that gets sent to the external merchant in a safe way
 * Error Type
 *
 * @version $Id: ErrorType.java 17118 2014-03-18 14:09:33Z semysm $
 */
public enum ErrorType {

    COMMUNICATION_ERROR(-1998, "A communication error occurred"),

    INTERNAL_ERROR(-1999, "An internal error occurred"),

    TOKEN_ERROR(-2000, "A token error occurred"),

    VALIDATION_ERROR(-10000, "A request parameter was missing or invalid");


    // code refers to the error type, like -1998 refers to communication_error, -1999 refers to internal_error
    private final int code;
    // description refers to the error message, like 'A communication error occurred','An internal error occurred'
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