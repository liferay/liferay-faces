/**
 * Copyright (c) 2000-2014 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
package com.liferay.faces.util.config;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import com.liferay.faces.util.helper.BooleanHelper;
import com.liferay.faces.util.helper.IntegerHelper;


/**
 * This is a utility class that provides static utility methods for getting values from {@link ServletContext}
 * init-param values.
 *
 * @author  Neil Griffin
 */
public class WebConfigParamUtil {

	// Note: Performance is faster with a synchronized block around HashMap.put(String, Object) rather than using a
	// ConcurrentHashMap.
	private static final Map<String, Object> configParamCache = new HashMap<String, Object>();

	public static boolean getBooleanValue(ServletContext servletContext, String name, String alternateName,
		boolean defaultBooleanValue) {

		boolean booleanValue = defaultBooleanValue;

		Object cachedValue = configParamCache.get(name);

		if ((cachedValue != null) && (cachedValue instanceof Boolean)) {
			booleanValue = (Boolean) cachedValue;
		}
		else {
			String configuredValue = getConfiguredValue(servletContext, name, alternateName);

			if (configuredValue != null) {
				booleanValue = BooleanHelper.isTrueToken(configuredValue);
			}

			synchronized (configParamCache) {
				configParamCache.put(name, Boolean.valueOf(booleanValue));
			}
		}

		return booleanValue;
	}

	public static String getConfiguredValue(ServletContext servletContext, String name, String alternateName) {

		String configuredValue = servletContext.getInitParameter(name);

		if ((configuredValue == null) && (alternateName != null)) {
			configuredValue = servletContext.getInitParameter(alternateName);
		}

		return configuredValue;
	}

	public static boolean isSpecified(ServletContext servletContext, String name, String alternateName) {
		return (getConfiguredValue(servletContext, name, alternateName) != null);
	}

	public static int getIntegerValue(ServletContext servletContext, String name, String alternateName,
		int defaultIntegerValue) {

		int integerValue = defaultIntegerValue;

		Object cachedValue = configParamCache.get(name);

		if ((cachedValue != null) && (cachedValue instanceof Integer)) {
			integerValue = (Integer) cachedValue;
		}
		else {
			String configuredValue = getConfiguredValue(servletContext, name, alternateName);

			if (configuredValue != null) {
				integerValue = IntegerHelper.toInteger(configuredValue);
			}

			synchronized (configParamCache) {
				configParamCache.put(name, Integer.valueOf(integerValue));
			}
		}

		return integerValue;
	}

	public static String getStringValue(ServletContext servletContext, String name, String alternateName,
		String defaultStringValue) {

		String stringValue = defaultStringValue;

		Object cachedValue = configParamCache.get(name);

		if ((cachedValue != null) && (cachedValue instanceof String)) {
			stringValue = (String) cachedValue;
		}
		else {
			String configuredValue = getConfiguredValue(servletContext, name, alternateName);

			if (configuredValue != null) {
				stringValue = configuredValue;
			}

			synchronized (configParamCache) {
				configParamCache.put(name, stringValue);
			}
		}

		return stringValue;
	}
}
