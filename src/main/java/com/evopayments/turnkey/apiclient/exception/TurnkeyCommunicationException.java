package com.evopayments.turnkey.apiclient.exception;

/**
 * Communication Exception.
 *
 * @version $Id: TurnkeyCommunicationException.java 17118 $
 */

@SuppressWarnings("serial")
public class TurnkeyCommunicationException extends TurnkeyGenericException {

    /**
     * define the ErrorType is COMMUNICATION_ERROR for current class.
     */
    private static final ErrorType ERROR_TYPE = ErrorType.COMMUNICATION_ERROR;

    /**
     * the constructor of class TurnkeyCommunicationException.
     */
    public TurnkeyCommunicationException() {
        super(ERROR_TYPE, ERROR_TYPE.getDescription());
    }

    /**
     * Creates new instance.
     *
     * @param message
     *            error message
     */
    public TurnkeyCommunicationException(final String message) {
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
    public TurnkeyCommunicationException(final String message, final Throwable cause) {
        super(ERROR_TYPE, message, cause);
    }
}
