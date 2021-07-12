package com.evopayments.turnkey.apiclient.exception;
/**
 * RuntimeException is to ensure exception text
 * that gets sent to the external merchant is safe .
 * Generic Exception
 *
 * @version $Id: GenericException.java $
 */

@SuppressWarnings("serial")
public abstract class TurnkeyGenericException extends RuntimeException {

    /**
     * specify the errorType.
     */
    private final ErrorType errorType;

    /**
     * Creates new instance.
     *
     * @param errorType
     *            error type
     * @param message
     *            error message
     */
    protected TurnkeyGenericException(final ErrorType errorType, final String message) {
        super(message);
        this.errorType = errorType;
    }

    /**
     * Creates new instance.
     * @param errorType
     * @param message
     * @param cause
     */
    protected TurnkeyGenericException(final ErrorType errorType, final String message, final Throwable cause) {
        super(message, cause);
        this.errorType = errorType;
    }

    /**
     * Gets error type.
     *
     * @return error type
     */
    protected ErrorType getErrorType() {
        return errorType;
    }

    /**
     * Gets error type code.
     * @return
     */
    public int getErrorCode() {
        return errorType.getCode();
    }

    /**
     * Gets error type description.
     * @return
     */
    public String getErrorDescription() {
        return errorType.getDescription();
    }
}