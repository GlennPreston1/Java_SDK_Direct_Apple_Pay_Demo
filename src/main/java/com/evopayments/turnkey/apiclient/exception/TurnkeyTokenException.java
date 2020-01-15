package com.evopayments.turnkey.apiclient.exception;

@SuppressWarnings("serial")
public class TurnkeyTokenException extends TurnkeyGenericException {

    private static final ErrorType ERROR_TYPE = ErrorType.TOKEN_ERROR;

    /**
     * Creates new instance
     *
     * @param message
     *            error message
     */
    public TurnkeyTokenException(String message) {
        super(ERROR_TYPE, ERROR_TYPE.getDescription() + ":" + message);
    }

    /**
     * Creates new instance
     *
     * @param message
     *            error message
     * @param cause
     *            cause
     */
    public TurnkeyTokenException(String message, Throwable cause) {
        super(ERROR_TYPE, message, cause);
    }
}

