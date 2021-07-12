package com.evopayments.turnkey.web.servlet.sample.s2s;

import com.evopayments.turnkey.apiclient.PurchaseCall;
import com.evopayments.turnkey.apiclient.exception.ActionCallException;
import com.evopayments.turnkey.apiclient.exception.GeneralException;
import com.evopayments.turnkey.apiclient.exception.RequiredParamException;
import com.evopayments.turnkey.apiclient.exception.TokenAcquirationException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Sample class showing how to make a MMRP Recurring transaction call for Mexico merchants
 * 
 * @author erbalazs
 *
 */
@WebServlet(name = "MmrpRecurringMxServlet",
		description = "Showing how to make a MMRP Recurring transaction call for Mexico merchants  ",
		urlPatterns = "/mmrpRecurringMxServlet")
@SuppressWarnings("serial")
public class MmrpRecurringMxServlet extends AbstractServlet {

	@Override
	protected void process(final HttpServletRequest req, final HttpServletResponse resp)
			throws RequiredParamException, ActionCallException, TokenAcquirationException,
			GeneralException, IOException {

		final Map<String, String> inputParams = AbstractServlet.extractParams(req);

		final PurchaseCall call = new PurchaseCall(config, inputParams, resp.getWriter(), PurchaseCall.SUB_ACTION_MMRP_RECURRING);
		call.execute();

	}
}
