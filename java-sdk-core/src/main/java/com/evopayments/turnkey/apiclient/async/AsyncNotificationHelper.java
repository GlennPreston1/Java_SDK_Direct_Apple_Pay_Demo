package com.evopayments.turnkey.apiclient.async;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.owasp.encoder.Encode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AsyncNotificationHelper {
	
	private static final Logger logger = LoggerFactory.getLogger(AsyncNotificationHelper.class);
	
	private static Map<String, String> extractParams(final HttpServletRequest request) {

		final HashMap<String, String> requestMap = new HashMap<>();

		final Map<String, String[]> parameterMap = request.getParameterMap();

		final Iterator<Map.Entry<String, String[]>> iterator = parameterMap.entrySet().iterator();
		while (iterator.hasNext()) {
			final Map.Entry<String, String[]> entry = iterator.next();
			requestMap.put(entry.getKey(), entry.getValue()[0]);
		}

		return requestMap;
	}
	
	protected void handleIncomingNotification(final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse) {

		final Map<String, String> notificationParams = extractParams(httpServletRequest);

		logger.info("AsyncNotificationHelper notification: " + Encode.forJava(notificationParams.toString()));

	}

}
