package com.evopayments.turnkey.apiclient.exception;

public class TurnkeyInternalException extends TurnkeyGenericException {

	public TurnkeyInternalException(final String message) {
		super(ErrorType.INTERNAL_ERROR, message);
	}
	
}
