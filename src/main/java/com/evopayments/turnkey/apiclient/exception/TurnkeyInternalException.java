package com.evopayments.turnkey.apiclient.exception;

/**
 * Internal Exception
 *
 * @version $Id: TurnkeyInternalException.java 17118 2014-03-18 14:09:33Z semysm $
 */
@SuppressWarnings("serial")
public class TurnkeyInternalException extends TurnkeyGenericException {

    private static final ErrorType ERROR_TYPE = ErrorType.INTERNAL_ERROR;

    /**
     * Creates new instance
     *
     * @param message
     *            error message
     */
    public TurnkeyInternalException(String message) {
        super(ERROR_TYPE, message);
    }

    /**
     * Creates new instance
     *
     * @param message
     *            error message
     * @param cause
     *            cause
     */
    public TurnkeyInternalException(String message, Throwable cause) {
        super(ERROR_TYPE, message, cause);
    }
}
