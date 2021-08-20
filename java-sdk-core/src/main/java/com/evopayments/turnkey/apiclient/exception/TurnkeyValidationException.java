package com.evopayments.turnkey.apiclient.exception;

public class TurnkeyValidationException extends TurnkeyGenericException {
		
	public TurnkeyValidationException(final String message) {
		super(ErrorType.VALIDATION_ERROR, message);
	}
	
}
