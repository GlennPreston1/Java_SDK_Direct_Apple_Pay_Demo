package com.evopayments.turnkey.apiclient.exception;
import com.evopayments.turnkey.apiclient.exception.ErrorType;

/**
 * Generic Exception
 *
 * @version $Id: GenericException.java 17118 2014-03-18 14:09:33Z semysm $
 */
@SuppressWarnings("serial")
public abstract class TurnkeyGenericException extends RuntimeException {


    private final ErrorType errorType;

    /**
     * Creates new instance
     *
     * @param errorType
     *            error type
     * @param message
     *            error message
     */
    protected TurnkeyGenericException(ErrorType errorType, String message) {
        super(message);
        this.errorType = errorType;
    }

    /**
     * Creates new instance
     *
     * @param errorType
     *            error type
     * @param message
     *            error message
     * @param cause
     *            cause
     */
    protected TurnkeyGenericException(ErrorType errorType, String message, Throwable cause) {
        super(message, cause);
        this.errorType = errorType;
    }

    /**
     * Gets error type
     *
     * @return error type
     */
    protected ErrorType getErrorType() {
        return errorType;
    }

    /**
     * Gets error type code
     * @return
     */
    public int getErrorCode() {
        return errorType.getCode();
    }

    /**
     * Gets error type description
     * @return
     */
    public String getErrorDescription() {
        return errorType.getDescription();
    }
}