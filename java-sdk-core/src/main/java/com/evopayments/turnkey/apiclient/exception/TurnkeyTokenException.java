package com.evopayments.turnkey.apiclient.exception;

public class TurnkeyTokenException extends TurnkeyGenericException {

	public TurnkeyTokenException(final String message) {
		super(ErrorType.TOKEN_ERROR, message);
	}

	public TurnkeyTokenException(final String message, final Throwable cause) {
		super(ErrorType.TOKEN_ERROR, message, cause);
	}
}
