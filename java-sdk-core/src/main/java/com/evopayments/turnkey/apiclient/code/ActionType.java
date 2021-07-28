package com.evopayments.turnkey.apiclient.code;

/**
 * The possible API actions.
 */
public enum ActionType {
	
	GET_AVAILABLE_PAYSOLS("GET_AVAILABLE_PAYSOLS"),
	
	/**
	 * for card payments, tokenizes the card number 
	 * (subsequent operations are conducted with this so called card token, 
	 * not with the original card number)
	 */
    TOKENIZE("TOKENIZE"),
    
    /**
     * two step payment (needs CAPTURE later); 
     * additionally this ActionType can be used to initiate hosted payment pages (standalone, iframe etc.) payments too
     */
    AUTH("AUTH"),
    
    /**
     * one step payment (no CAPTURE needed); 
     * additionally this ActionType can be used to initiate hosted payment pages (standalone, iframe etc.) payments too
     */
    PURCHASE("PURCHASE"),
    
    /**
     * eg. for card number checks; 
     * additionally this ActionType can be used to initiate hosted payment pages (standalone, iframe etc.) payments too
     */
    VERIFY("VERIFY"),
    
    /**
     * after AUTH to finish the payment (this is the second step)
     */
    CAPTURE("CAPTURE"),
    
    /**
     * after AUTH to cancel the payment
     */
    VOID("VOID"),
    
    /**
     * to refund a PURCHASE or an already CAPTURE-d AUTH
     * (a linked, but distinct transaction)
     */
    REFUND("REFUND"),
    
    STATUS_CHECK("STATUS_CHECK"),
    
    GET_MOBILE_CASHIER_URL("getMobileCashierUrl");

    private final String code;

    ActionType(final String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
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
