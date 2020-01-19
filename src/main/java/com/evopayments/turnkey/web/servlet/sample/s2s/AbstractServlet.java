package com.evopayments.turnkey.web.servlet.sample.s2s;

import com.evopayments.turnkey.apiclient.exception.ActionCallException;
import com.evopayments.turnkey.apiclient.exception.GeneralException;
import com.evopayments.turnkey.apiclient.exception.PostToApiException;
import com.evopayments.turnkey.apiclient.exception.RequiredParamException;
import com.evopayments.turnkey.apiclient.exception.TokenAcquirationException;
import com.evopayments.turnkey.config.ApplicationConfig;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Sample servlet base class.
 * 
 * @author erbalazs
 *
 */
@SuppressWarnings("serial")
public abstract class AbstractServlet extends HttpServlet {

	private static final Logger logger = Logger.getLogger(AbstractServlet.class.getName());
	protected final ApplicationConfig config;

	/**
	 * extract params from http request.
	 *
	 * @param request
	 *
	 * @return
	 *
	 */
	public static Map<String, String> extractParams(final HttpServletRequest request) {

		final HashMap<String, String> requestMap = new HashMap<>();

		final Map<String, String[]> parameterMap = request.getParameterMap();

		final Iterator<Map.Entry<String, String[]>> iterator = parameterMap.entrySet().iterator();
		while (iterator.hasNext()) {
			final Map.Entry<String, String[]> entry = iterator.next();
			requestMap.put(entry.getKey(), entry.getValue()[0]);
		}

		return requestMap;
	}

	/**
	 * constructor of current class.
	 */
	public AbstractServlet() {
		super();
		config = ApplicationConfig.getInstanceBasedOnSysProp();
	}

	@Override
	protected final void doGet(final HttpServletRequest req, final HttpServletResponse resp)
			throws IOException {
		try {
			process(req, resp);
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
		} catch (Exception e) {
			resp.setStatus(500);
			logger.log(Level.SEVERE, "other error", e);
		}
	}

	/**
	 * process
	 * @param req
	 * @param resp
	 * @throws RequiredParamException
	 * @throws ActionCallException
	 * @throws TokenAcquirationException
	 * @throws GeneralException
	 * @throws IOException
	 */
	protected abstract void process(final HttpServletRequest req, final HttpServletResponse resp)
			throws RequiredParamException, ActionCallException, TokenAcquirationException,
			GeneralException, IOException;
}
