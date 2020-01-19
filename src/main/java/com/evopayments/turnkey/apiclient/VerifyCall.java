package com.evopayments.turnkey.apiclient;

import com.evopayments.turnkey.apiclient.code.ActionType;
import com.evopayments.turnkey.config.ApplicationConfig;
import java.io.PrintWriter;
import java.util.Map;

/**
 * Does an authorize and capture operations at once (and cannot be voided).
 *
 * @author erbalazs
 *
 */
public class VerifyCall extends BaseApiCall {

    /**
     * constructor of current class.
     *
     * @param config
     *
     * @param inputParams
     *
     * @param outputWriter
     *
     */
    public VerifyCall(final ApplicationConfig config, final Map<String, String> inputParams,
                      final PrintWriter outputWriter) {
        super(config, inputParams, outputWriter);
    }

    /**
     * constructor of current class.
     *
     * @param config
     *
     * @param inputParams
     *
     * @param outputWriter
     *
     * @param subActionType
     *
     */
    public VerifyCall(final ApplicationConfig config, final Map<String, String> inputParams,
                      final PrintWriter outputWriter, final String subActionType) {
        super(config, inputParams, outputWriter, subActionType);
    }

    @Override
    protected ActionType getActionType() {
        return ActionType.VERIFY;
    }

}
