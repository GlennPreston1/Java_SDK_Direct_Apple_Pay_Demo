package com.evopayments.turnkey.apiclient.code;

/**
 * Type of actions.
 */
public enum ActionType {
    TOKENIZE("TOKENIZE"),
    AUTH("AUTH"),
    CAPTURE("CAPTURE"),
    VOID("VOID"),
    PURCHASE("PURCHASE"),
    REFUND("REFUND"),
    GET_AVAILABLE_PAYSOLS("GET_AVAILABLE_PAYSOLS"),
    STATUS_CHECK("STATUS_CHECK"),
    VERIFY("VERIFY"),
    GET_MOBILE_CASHIER_URL("getMobileCashierUrl");

    /**
     * error type code
     */
    private final String code;

    /**
     * constructor of current class.
     *
     * @param code
     */
    ActionType(final String code) {
        this.code = code;
    }

    /**
     * get error code.
     * @return code
     */
    public String getCode() {
        return code;
    }

    /**
     *  get actionType.
     * @param code
     * @return
     */
    public static ActionType valueOfCode(final String code) {
        for (final ActionType actionType : values()) {
            if (actionType.code.equals(code)) {
                return actionType;
            }
        }
        throw new IllegalArgumentException(code);
    }

}
