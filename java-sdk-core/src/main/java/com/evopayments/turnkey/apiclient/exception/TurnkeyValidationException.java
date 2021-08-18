package com.evopayments.turnkey.apiclient.exception;

public class TurnkeyValidationException extends TurnkeyGenericException {
		
	public TurnkeyValidationException() {
		super(ErrorType.VALIDATION_ERROR);
	}

	public TurnkeyValidationException(final String message) {
		super(ErrorType.VALIDATION_ERROR, message);
	}
	
	public TurnkeyValidationException(final String message, final Throwable cause) {
		super(ErrorType.VALIDATION_ERROR, message, cause);
	}

}
