package com.evopayments.turnkey.apiclient.exception;

public class TurnkeyInternalException extends TurnkeyGenericException {

	public TurnkeyInternalException() {
		super(ErrorType.INTERNAL_ERROR);
	}

	public TurnkeyInternalException(final String message) {
		super(ErrorType.INTERNAL_ERROR, message);
	}

	public TurnkeyInternalException(final String message, final Throwable cause) {
		super(ErrorType.INTERNAL_ERROR, message, cause);
	}
}
