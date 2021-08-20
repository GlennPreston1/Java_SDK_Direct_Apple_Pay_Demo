package com.evopayments.turnkey.apiclient.exception;

public class TurnkeyCommunicationException extends TurnkeyGenericException {

	public TurnkeyCommunicationException(final String message) {
		super(ErrorType.COMMUNICATION_ERROR, message);
	}

}
