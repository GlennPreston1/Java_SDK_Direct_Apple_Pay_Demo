package com.evopayments.turnkey.apiclient.exception;

/**
 * Validation Exception
 * @version $Id: TurnkeyValidationException.java 17118 2014-03-18 14:09:33Z semysm $
 */
@SuppressWarnings("serial")
public class TurnkeyValidationException extends TurnkeyGenericException {

    private static final ErrorType ERROR_TYPE = ErrorType.VALIDATION_ERROR;

    public TurnkeyValidationException() {
        super(ERROR_TYPE, ERROR_TYPE.getDescription());
    }

    /**
     * get turnkey validation error description
     *
     * @return turnkey validation error description
     */
    public String getTurnkeyValidationErrorDescription() {
        return ERROR_TYPE.getDescription();
    }



    /**
     * Creates new instance
     *
     * @param message
     *            error message
     */
    public TurnkeyValidationException(String message) {
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
    public TurnkeyValidationException(String message, Throwable cause) {
        super(ERROR_TYPE, message, cause);
    }

    public TurnkeyValidationException( Throwable cause) {
        super(ERROR_TYPE, ERROR_TYPE.getDescription(), cause);
    }


    public static String getValidationErrorDescription() {
        return ERROR_TYPE.getDescription();
    }
}
