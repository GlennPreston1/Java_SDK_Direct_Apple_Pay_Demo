package com.evopayments.turnkey.apiclient.exception;

/**
 * Base class for the SDK exceptions.
 * 
 * @author erbalazs
 *
 */
@SuppressWarnings("serial")
public abstract class SDKException extends RuntimeException {

	public SDKException() {
		super();
	}

	public SDKException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public SDKException(final String message) {
		super(message);
	}

	public SDKException(final Throwable cause) {
		super(cause);
	}
	
}
