package com.evopayments.turnkey.apiclient.code;

import com.evopayments.turnkey.apiclient.AbstractApiCall;

/**
 * The possible API action constants. 
 * Please see the various {@link AbstractApiCall} subclasses for the usage (and meaning) of these.
 */
public enum ActionType {
	
	// Note: some special (for internal use etc.) API calls (or special variants) are not included in this SDK code 

	GET_AVAILABLE_PAYSOLS("GET_AVAILABLE_PAYSOLS"),
	TOKENIZE("TOKENIZE"),
	AUTH("AUTH"),
	PURCHASE("PURCHASE"),
	VERIFY("VERIFY"),
	CAPTURE("CAPTURE"),
	VOID("VOID"),
	REFUND("REFUND"),
	STATUS_CHECK("STATUS_CHECK"),
	GET_MOBILE_CASHIER_URL("getMobileCashierUrl");

	private final String code;

	ActionType(final String code) {
		this.code = code;
	}

	public String getCode() {
		return this.code;
	}

	public static ActionType valueOfCode(final String code) {
		for (final ActionType actionType : values()) {
			if (actionType.code.equals(code)) {
				return actionType;
			}
		}
		throw new IllegalArgumentException(code);
	}

}
