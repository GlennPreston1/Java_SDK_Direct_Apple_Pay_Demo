package com.evopayments.turnkey.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class HttpParamUtil {

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
	
	private HttpParamUtil() {
		// static util methods only
	}
	
}
