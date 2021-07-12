package com.evopayments.turnkey.apiclient.exception;

/**
 * Internal Exception.
 *
 * @version $Id: TurnkeyInternalException.java 17118 2014-03-18 14:09:33Z semysm $
 */

@SuppressWarnings("serial")
public class TurnkeyInternalException extends TurnkeyGenericException {

    /**
     * define the error type is INTERNAL_ERROR for current class.
     */
    private static final ErrorType ERROR_TYPE = ErrorType.INTERNAL_ERROR;

    /**
     * the constructor of current class.
     */
    public TurnkeyInternalException() {
        super(ERROR_TYPE, ERROR_TYPE.getDescription());
    }

    /**
     * Creates new instance.
     *
     * @param message
     *            error message
     */
    public TurnkeyInternalException(final String message) {
        super(ERROR_TYPE, message);
    }

    /**
     * Creates new instance.
     *
     * @param message
     *            error message
     * @param cause
     *            cause
     */
    public TurnkeyInternalException(final String message, final Throwable cause) {
        super(ERROR_TYPE, message, cause);
    }
}
