package com.evopayments.turnkey.apiclient.exception;

public class TurnkeyCommunicationException extends TurnkeyGenericException {

	public TurnkeyCommunicationException() {
		super(ErrorType.COMMUNICATION_ERROR, ErrorType.COMMUNICATION_ERROR.getDescription());
	}

	public TurnkeyCommunicationException(final String message) {
		super(ErrorType.COMMUNICATION_ERROR, ErrorType.COMMUNICATION_ERROR.getDescription() + ":" + message);
	}

	public TurnkeyCommunicationException(final String message, final Throwable cause) {
		super(ErrorType.COMMUNICATION_ERROR, ErrorType.COMMUNICATION_ERROR.getDescription() + ":" + message, cause);
	}
}
