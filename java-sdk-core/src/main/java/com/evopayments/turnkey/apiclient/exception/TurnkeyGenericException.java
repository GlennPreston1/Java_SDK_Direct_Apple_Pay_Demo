package com.evopayments.turnkey.apiclient.exception;

public abstract class TurnkeyGenericException extends RuntimeException {

	private final ErrorType errorType;

	protected TurnkeyGenericException(final ErrorType errorType) {
		super(errorType.getDescription());
		this.errorType = errorType;
	}
	
	protected TurnkeyGenericException(final ErrorType errorType, final String message) {
		super(errorType.getDescription() + ": "+ message);
		this.errorType = errorType;
	}

	protected TurnkeyGenericException(final ErrorType errorType, final String message, final Throwable cause) {
		super(errorType.getDescription() + ": "+ message, cause);
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