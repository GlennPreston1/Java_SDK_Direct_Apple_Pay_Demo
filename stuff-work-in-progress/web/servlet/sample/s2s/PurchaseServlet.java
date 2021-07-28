package com.evopayments.turnkey.web.servlet.sample.s2s;

import com.evopayments.turnkey.apiclient.PurchaseCall;
import com.evopayments.turnkey.apiclient.exception.ActionCallException;
import com.evopayments.turnkey.apiclient.exception.GeneralException;
import com.evopayments.turnkey.apiclient.exception.RequiredParamException;
import com.evopayments.turnkey.apiclient.exception.TokenAcquirationException;
import java.io.IOException;
import java.util.Map;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Sample
 * 
 * @author erbalazs
 *
 */
@WebServlet(name = "Purchase",
		description = "Purchase operation (= a combination of Auth + Capture)",
		urlPatterns = "/purchase")
@SuppressWarnings("serial")
public class PurchaseServlet extends AbstractServlet {

	@Override
	protected void process(final HttpServletRequest req, final HttpServletResponse resp)
			throws RequiredParamException, ActionCallException, TokenAcquirationException,
			GeneralException, IOException {

		final Map<String, String> inputParams = AbstractServlet.extractParams(req);
		new PurchaseCall(config, inputParams, resp.getWriter()).execute();

	}
}
