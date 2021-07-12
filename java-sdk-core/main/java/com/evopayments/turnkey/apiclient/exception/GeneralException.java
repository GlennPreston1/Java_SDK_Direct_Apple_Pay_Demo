package com.evopayments.turnkey.apiclient.exception;

/**
 * Other, not specified error in the SDK code.
 * 
 * @author erbalazs
 *
 */
@SuppressWarnings("serial")
public class GeneralException extends SDKException {

	/**
	 * constructor of current class.
	 */
	public GeneralException() {
		super();
	}

	/**
	 * constructor of current class.
	 *
	 * @param message
	 *
	 * @param cause
	 */
	public GeneralException(final String message, final Throwable cause) {
		super(message, cause);
	}

	/**
	 * constructor of current class.
	 * @param message
	 */
	public GeneralException(final String message) {
		super(message);
	}

	/**
	 * constructor of current class.
	 * @param cause
	 */
	public GeneralException(final Throwable cause) {
		super(cause);
	}	 
	
}
