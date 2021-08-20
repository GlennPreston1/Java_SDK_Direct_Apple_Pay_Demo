package com.evopayments.turnkey.apiclient.exception;

public class TurnkeyTokenException extends TurnkeyGenericException {

	public TurnkeyTokenException(final String message) {
		super(ErrorType.TOKEN_ERROR, message);
	}

}
