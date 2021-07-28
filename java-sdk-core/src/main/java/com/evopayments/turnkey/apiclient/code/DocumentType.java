package com.evopayments.turnkey.apiclient.code;

/**
 * Personal identification document types.
 */
public enum DocumentType {
	
    PASSPORT("PASSPORT"),
    NATIONAL_ID("NATIONAL_ID"),
    DRIVING_LICENSE("DRIVING_LICENSE"),
    UNIQUE_TAXPAYER_REFERENCE("UNIQUE_TAXPAYER_REFERENCE"),
    OTHER("OTHER");

    protected String code;

    DocumentType(final String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
