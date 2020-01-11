package com.evopayments.turnkey.apiclient.exception;

/**
 * Communication Exception
 *
 * @version $Id: TurnkeyCommunicationException.java 17118 2014-03-18 14:09:33Z semysm $
 */
@SuppressWarnings("serial")
public class TurnkeyCommunicationException extends TurnkeyGenericException {

    private static final ErrorType ERROR_TYPE = ErrorType.COMMUNICATION_ERROR;

    public TurnkeyCommunicationException() {
        super(ERROR_TYPE, ERROR_TYPE.getDescription());
    }

    /**
     * Creates new instance
     *
     * @param message
     *            error message
     */
    public TurnkeyCommunicationException(String message) {
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
    public TurnkeyCommunicationException(String message, Throwable cause) {
        super(ERROR_TYPE, message, cause);
    }
}
