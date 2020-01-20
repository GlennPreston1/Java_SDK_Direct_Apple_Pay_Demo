package com.evopayments.turnkey.apiclient.exception;

import java.util.Set;

/**
 * One or more required parameter were left out.
 * 
 * @author erbalazs
 */
@SuppressWarnings("serial")
public class RequiredParamException extends SDKException {

	/**
	 * define variable missingFields.
	 */
	private final Set<String> missingFields;

	/**
	 * constructor of current class.
	 *
	 * @param missingFields
	 */
	public RequiredParamException(final Set<String> missingFields) {
		super(missingFields.toString());
		this.missingFields = missingFields;
	}

	/**
	 * constructor of current class.
	 *
	 * @return missingFields
	 */
	public Set<String> getMissingFields() {
		return missingFields;
	}

}
