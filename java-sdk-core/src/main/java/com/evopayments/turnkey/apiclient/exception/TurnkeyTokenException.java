package com.evopayments.turnkey.apiclient.exception;


/**
 * TurnkeyToken Exception.
 * @version $Id: TurnkeyTokenException.java $
 */

@SuppressWarnings("serial")
public class TurnkeyTokenException extends TurnkeyGenericException {

    /**
     * define the error type is TOKEN_ERROR for current class.
     */
    private static final ErrorType ERROR_TYPE = ErrorType.TOKEN_ERROR;

    /**
     * Creates new instance.
     *
     * @param message
     *            error message
     */
    public TurnkeyTokenException(final String message) {
        super(ERROR_TYPE, ERROR_TYPE.getDescription() + ":" + message);
    }

    /**
     * Creates new instance.
     *
     * @param message
     *            error message
     * @param cause
     *            cause
     */
    public TurnkeyTokenException(final String message, final Throwable cause) {
        super(ERROR_TYPE, message, cause);
    }
}

