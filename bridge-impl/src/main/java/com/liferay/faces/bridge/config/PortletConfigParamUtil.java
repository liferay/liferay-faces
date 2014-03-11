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
package com.liferay.faces.bridge.config;

import java.util.HashMap;
import java.util.Map;

import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;

import com.liferay.faces.util.helper.BooleanHelper;
import com.liferay.faces.util.helper.IntegerHelper;


/**
 * This is a utility class that provides static utility methods for getting values from {@link PortletConfig} init-param
 * values.
 *
 * @author  Neil Griffin
 */
public class PortletConfigParamUtil {

	// Note: Performance is faster with a synchronized block around HashMap.put(String, Object) rather than using a
	// ConcurrentHashMap.
	private static final Map<String, Object> configParamCache = new HashMap<String, Object>();

	public static boolean getBooleanValue(PortletConfig portletConfig, String name, String alternateName,
		boolean defaultBooleanValue) {

		boolean booleanValue = defaultBooleanValue;

		String portletName = portletConfig.getPortletName();

		if (portletName == null) {
			String configuredValue = getConfiguredValue(portletConfig, name, alternateName);

			if (configuredValue != null) {
				booleanValue = BooleanHelper.isTrueToken(configuredValue);
			}
		}
		else {
			String configParamName = portletName + name;
			Object cachedValue = configParamCache.get(configParamName);

			if ((cachedValue != null) && (cachedValue instanceof Boolean)) {
				booleanValue = (Boolean) cachedValue;
			}
			else {
				String configuredValue = getConfiguredValue(portletConfig, name, alternateName);

				if (configuredValue != null) {
					booleanValue = BooleanHelper.isTrueToken(configuredValue);
				}

				synchronized (configParamCache) {
					configParamCache.put(configParamName, Boolean.valueOf(booleanValue));
				}
			}
		}

		return booleanValue;
	}

	public static String getConfiguredValue(PortletConfig portletConfig, String name, String alternateName) {

		String configuredValue = portletConfig.getInitParameter(name);

		PortletContext portletContext = null;

		if (configuredValue == null) {
			portletContext = portletConfig.getPortletContext();
			configuredValue = portletContext.getInitParameter(name);
		}

		if ((configuredValue == null) && (alternateName != null)) {
			configuredValue = portletConfig.getInitParameter(alternateName);

			if (configuredValue == null) {
				configuredValue = portletContext.getInitParameter(alternateName);
			}
		}

		return configuredValue;
	}

	public static boolean isSpecified(PortletConfig portletConfig, String name, String alternateName) {
		return (getConfiguredValue(portletConfig, name, alternateName) != null);
	}

	public static int getIntegerValue(PortletConfig portletConfig, String name, String alternateName,
		int defaultIntegerValue) {

		int integerValue = defaultIntegerValue;

		String portletName = portletConfig.getPortletName();

		if (portletName == null) {
			String configuredValue = getConfiguredValue(portletConfig, name, alternateName);

			if (configuredValue != null) {
				integerValue = IntegerHelper.toInteger(configuredValue);
			}
		}
		else {
			String configParamName = portletName + name;
			Object cachedValue = configParamCache.get(configParamName);

			if ((cachedValue != null) && (cachedValue instanceof Integer)) {
				integerValue = (Integer) cachedValue;
			}
			else {
				String configuredValue = getConfiguredValue(portletConfig, name, alternateName);

				if (configuredValue != null) {
					integerValue = IntegerHelper.toInteger(configuredValue);
				}

				synchronized (configParamCache) {
					configParamCache.put(configParamName, Integer.valueOf(integerValue));
				}
			}
		}

		return integerValue;
	}

	public static String getStringValue(PortletConfig portletConfig, String name, String alternateName,
		String defaultStringValue) {

		String stringValue = defaultStringValue;

		String portletName = portletConfig.getPortletName();

		if (portletName == null) {
			String configuredValue = getConfiguredValue(portletConfig, name, alternateName);

			if (configuredValue != null) {
				stringValue = configuredValue;
			}
		}
		else {
			String configParamName = portletName + name;
			Object cachedValue = configParamCache.get(configParamName);

			if ((cachedValue != null) && (cachedValue instanceof String)) {
				stringValue = (String) cachedValue;
			}
			else {
				String configuredValue = getConfiguredValue(portletConfig, name, alternateName);

				if (configuredValue != null) {
					stringValue = configuredValue;
				}

				synchronized (configParamCache) {
					configParamCache.put(configParamName, stringValue);
				}
			}
		}

		return stringValue;
	}
}
