package com.evopayments.turnkey.apiclient.exception;

/**
 * Failure during the main (the action) call.
 * 
 * @author erbalazs
 */

@SuppressWarnings("serial")
public class ActionCallException extends SDKException {

	/**
	 * constructor of current class.
	 */
	public ActionCallException() {
		super();
	}

	/**
	 * constructor of current class.
	 *
	 * @param msg
	 */
	public ActionCallException(final String msg) {
		super(msg);
	}
}
