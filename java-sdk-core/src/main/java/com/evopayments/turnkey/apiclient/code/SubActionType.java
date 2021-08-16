package com.evopayments.turnkey.apiclient.code;

/**
 * Relevant for card on file, recurring payments etc.
 * 
 * @author erbalazs
 */
public class SubActionType {
	
	private SubActionType() {
		// constants only => private constructor
	}
	
	/**
	 * CARD ON FILE
	 */
	public static final String SUB_ACTION_COF_FIRST = "SUB_ACTION_COF_FIRST";
	
	/**
	 * SUB_ACTION_COF_RECURRING
	 */
	public static final String SUB_ACTION_COF_RECURRING = "SUB_ACTION_COF_RECURRING";
	
	/**
	 * MMRP
	 */
	public static final String SUB_ACTION_MMRP_FIRST = "SUB_ACTION_MMRP_FIRST";
	
	/**
	 * MMRP
	 */
	public static final String SUB_ACTION_MMRP_RECURRING = "SUB_ACTION_MMRP_RECURRING";
	
}
