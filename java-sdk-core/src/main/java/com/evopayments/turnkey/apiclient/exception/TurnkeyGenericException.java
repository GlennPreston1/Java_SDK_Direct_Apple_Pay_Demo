package com.evopayments.turnkey.apiclient.exception;

import org.owasp.encoder.Encode;

public abstract class TurnkeyGenericException extends RuntimeException {

	private final ErrorType errorType;
	
	protected TurnkeyGenericException(final ErrorType errorType, final String message) {
		super(Encode.forJava(errorType.getDescription() + ": "+ message));
		this.errorType = errorType;
	}

	protected ErrorType getErrorType() {
		return this.errorType;
	}

	public int getErrorCode() {
		return this.errorType.getCode();
	}

	public String getErrorDescription() {
		return this.errorType.getDescription();
	}
}