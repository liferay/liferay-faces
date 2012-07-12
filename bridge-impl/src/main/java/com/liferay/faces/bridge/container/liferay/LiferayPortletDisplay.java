/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.bridge.container.liferay;

import java.lang.reflect.Method;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * This class wraps an instance of com.liferay.portal.theme.PortletDisplay and provides decorator methods that access
 * the wrapped instance via reflection in order to avoid a compile-time dependency.
 */
public class LiferayPortletDisplay {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(LiferayPortletDisplay.class);

	// Private Constants
	private static final String METHOD_NAME_GET_COLUMN_COUNT = "getColumnCount";
	private static final String METHOD_NAME_GET_COLUMN_ID = "getColumnId";
	private static final String METHOD_NAME_GET_COLUMN_POS = "getColumnPos";
	private static final String METHOD_NAME_GET_INSTANCE_ID = "getInstanceId";

	// Private Data Members
	private Object wrappedPortletDisplay;

	public LiferayPortletDisplay(Object wrappedPortletDisplay) {
		this.wrappedPortletDisplay = wrappedPortletDisplay;
	}

	public String getColumnCount() {
		return getMethodReturnValue(wrappedPortletDisplay, METHOD_NAME_GET_COLUMN_COUNT);
	}

	public String getColumnId() {
		return getMethodReturnValue(wrappedPortletDisplay, METHOD_NAME_GET_COLUMN_ID);
	}

	public String getColumnPos() {
		return getMethodReturnValue(wrappedPortletDisplay, METHOD_NAME_GET_COLUMN_POS);
	}

	public String getInstanceId() {
		return getMethodReturnValue(wrappedPortletDisplay, METHOD_NAME_GET_INSTANCE_ID);
	}

	protected String getMethodReturnValue(Object object, String methodName) {
		Object methodReturnValue = null;

		try {
			Method method = object.getClass().getMethod(methodName);
			methodReturnValue = method.invoke(object, (Object[]) null);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		if (methodReturnValue == null) {
			return null;
		}
		else {
			return methodReturnValue.toString();
		}
	}

}
