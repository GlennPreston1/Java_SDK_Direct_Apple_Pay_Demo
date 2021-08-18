package com.evopayments.turnkey.apiclient.exception;

public enum ErrorType {

	COMMUNICATION_ERROR(-1998, "A communication error occurred"),
	INTERNAL_ERROR(-1999, "An internal error occurred"),
	TOKEN_ERROR(-2000, "A token error occurred"),
	VALIDATION_ERROR(-10000, "A request parameter was missing or invalid");

	private final int code;

	private final String description;

	private ErrorType(final int code, final String description) {
		this.code = code;
		this.description = description;
	}

	public int getCode() {
		return this.code;
	}

	public String getDescription() {
		return this.description;
	}

	public static ErrorType fromValue(final int errorCode) {
		ErrorType value = null;

		for (final ErrorType t : values()) {
			if (t.getCode() == errorCode) {
				value = t;
				break;
			}
		}

		return value;
	}

}