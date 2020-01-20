package com.evopayments.turnkey.apiclient.exception;

/**
 * Error in (around) the outgoing (toward the API server) HTTP client call
 * (most likely: failed HTTP request, failed response parsing, failed request body creation).
 * 
 * @author erbalazs
 *
 */
@SuppressWarnings("serial")
public class PostToApiException extends SDKException {

	/**
	 * constructor of current class.
	 */
	public PostToApiException() {
		super();
	}

	/**
	 * constructor of current class.
	 *
	 * @param message
	 *
	 * @param cause
	 */
	public PostToApiException(final String message, final Throwable cause) {
		super(message, cause);
	}

	/**
	 * constructor of current class.
	 *
	 * @param message
	 */
	public PostToApiException(final String message) {
		super(message);
	}

	/**
	 * constructor of current class.
	 *
	 * @param cause
	 */
	public PostToApiException(final Throwable cause) {
		super(cause);
	}

}
