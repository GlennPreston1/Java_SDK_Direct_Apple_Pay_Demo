package com.evopayments.turnkey.apiclient.exception;

/**
 * Validation Exception.
 * @version $Id: TurnkeyValidationException.java $
 */

@SuppressWarnings("serial")
public class TurnkeyValidationException extends TurnkeyGenericException {

    /**
     * define the error type is VALIDATION_ERROR for current class.
     */
    private static final ErrorType ERROR_TYPE = ErrorType.VALIDATION_ERROR;

    /**
     * constructor of current class.
     */
    public TurnkeyValidationException() {
        super(ERROR_TYPE, ERROR_TYPE.getDescription());
    }

    /**
     * get turnkey validation error description.
     *
     * @return turnkey validation error description
     */
    public String getTurnkeyValidationErrorDescription() {
        return ERROR_TYPE.getDescription();
    }



    /**
     * Creates new instance.
     *
     * @param message
     *            error message
     */
    public TurnkeyValidationException(final String message) {
        super(ERROR_TYPE, ERROR_TYPE.getDescription() + ":" + message);
    }

    /**
     * Creates new instance.
     * @param message
     * @param cause
     */
    public TurnkeyValidationException(final String message, final Throwable cause) {
        super(ERROR_TYPE, message, cause);
    }

    /**
     * constructor of current class.
     * @param cause
     */
    public TurnkeyValidationException(final Throwable cause) {
        super(ERROR_TYPE, ERROR_TYPE.getDescription(), cause);
    }


    /**
     * get ValidationError Description.
     * @return error description
     */
    public static String getValidationErrorDescription() {
        return ERROR_TYPE.getDescription();
    }
}
