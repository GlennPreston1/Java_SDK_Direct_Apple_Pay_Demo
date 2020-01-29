package com.evopayments.turnkey.web.servlet.sample.b2s;

import com.evopayments.turnkey.apiclient.PurchaseTokenCall;
import com.evopayments.turnkey.apiclient.exception.ActionCallException;
import com.evopayments.turnkey.apiclient.exception.GeneralException;
import com.evopayments.turnkey.apiclient.exception.PostToApiException;
import com.evopayments.turnkey.apiclient.exception.RequiredParamException;
import com.evopayments.turnkey.apiclient.exception.TokenAcquirationException;
import com.evopayments.turnkey.config.ApplicationConfig;
import com.evopayments.turnkey.web.servlet.sample.s2s.AbstractServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

/**
 * Token acquiration used in Browser-to-Server mode.
 * 
 * @author erbalazs
 *
 */
@WebServlet(name = "PurchaseToken",
		description = "Token acquiration used in Browser-to-Server mode",
		urlPatterns = "/tokenforpurchase")
@SuppressWarnings("serial")
public class PurchaseTokenServlet extends HttpServlet {

	private static final Logger logger = Logger.getLogger(PurchaseTokenServlet.class.getName());

	protected final ApplicationConfig config;

	/**
	 * constructor of current  class.
	 */
	public PurchaseTokenServlet() {
		super();
		config = ApplicationConfig.getInstanceBasedOnSysProp();
	}

	@Override
	protected void doPost(final HttpServletRequest req, final HttpServletResponse resp)
			throws ServletException, IOException {

		try {
			
			final Map<String, String> inputParams = AbstractServlet.extractParams(req);
			
			final JSONObject jsonObject = new PurchaseTokenCall(config, inputParams,
					new PrintWriter(System.out, true)).execute();
			jsonObject.put("merchantId", inputParams.get("merchantId"));
			
			resp.getWriter().print(jsonObject.toString());
			
		} catch (RequiredParamException e) {
			resp.setStatus(422);
			logger.log(Level.INFO, "missing parameters", e);
		} catch (TokenAcquirationException e) {
			resp.setStatus(500);
			logger.log(Level.WARNING, "could not acquire token", e);
		} catch (ActionCallException e) {
			resp.setStatus(500);
			logger.log(Level.WARNING, "error during the action call", e);
		} catch (PostToApiException e) {
			resp.setStatus(500);
			logger.log(Level.SEVERE, "outgoing POST failed", e);
		} catch (GeneralException e) {
			resp.setStatus(500);
			logger.log(Level.SEVERE, "general SDK error", e);
		}
	}

}
