package com.evopayments.turnkey.web.servlet.sample.callback;


import com.evopayments.turnkey.web.servlet.sample.s2s.AbstractServlet;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * When an operation is completed (successfully or not), a notification is sent to inform the
 * merchant about the result and the current status of the payment. This notification is sent to the
 * url provided as merchantNotificationUrl at the session token request by the merchant...
 * 
 * @author erbalazs
 */
@SuppressWarnings("serial")
@WebServlet(name = "TransactionResultCallback", description = "TransactionResultCallback servlet",
		urlPatterns = "/transactionresultcallback")
public class TransactionResultCallbackServlet extends HttpServlet {

	private static final Logger logger = Logger.getLogger(
			TransactionResultCallbackServlet.class.getName());

	/**
	 * constructor of current  class.
	 */
	public TransactionResultCallbackServlet() {
		super();
	}

	@Override
	protected void doPost(final HttpServletRequest req, final HttpServletResponse resp)
			throws ServletException, IOException {

		final Map<String, String> notificationParams = AbstractServlet.extractParams(req);

		logger.info("transaction notification callback servlet: " + notificationParams);
		resp.getWriter().println("notificationParams: " + notificationParams);
	}

}
