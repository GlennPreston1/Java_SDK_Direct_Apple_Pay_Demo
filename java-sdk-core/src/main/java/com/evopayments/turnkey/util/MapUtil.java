package com.evopayments.turnkey.util;

import java.util.Map;

public class MapUtil {

	private MapUtil() {
		// static util methods only
	}
	
	public static <U, V> void putIfNotNull(Map<U, V> map, U key, V value) {
		
		if (value != null) {
			map.put(key, value);
		}
		
	}
	
}
