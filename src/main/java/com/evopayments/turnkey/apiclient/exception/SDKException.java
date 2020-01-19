package com.evopayments.turnkey.apiclient.exception;

/**
 * Base class for the SDK exceptions.
 * 
 * @author erbalazs
 *
 */
@SuppressWarnings("serial")
public abstract class SDKException extends RuntimeException {

	/**
	 * constructor of current class.
	 */
	public SDKException() {
		super();
	}

	/**
	 * constructor of current class.
	 *
	 * @param message
	 *
	 * @param cause
	 */
	public SDKException(final String message, final Throwable cause) {
		super(message, cause);
	}

	/**
	 * constructor of current class.
	 *
	 * @param message
	 */
	public SDKException(final String message) {
		super(message);
	}

	/**
	 * constructor of current class.
	 *
	 * @param cause
	 */
	public SDKException(final Throwable cause) {
		super(cause);
	}
	
}
