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
package com.liferay.faces.util.client;

import java.lang.reflect.Method;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;
import com.liferay.faces.util.product.Product;
import com.liferay.faces.util.product.ProductConstants;
import com.liferay.faces.util.product.ProductMap;

import com.liferay.portal.kernel.servlet.taglib.aui.ScriptData;


/**
 * @author  Neil Griffin
 */
public class ClientScriptLiferayImpl implements ClientScript {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(ClientScriptLiferayImpl.class);

	// Private Constants
	private static final String APPEND = "append";
	private static final Method APPEND_METHOD_LEGACY;
	private static final Method APPEND_METHOD_CURRENT;
	private static final boolean LIFERAY_PORTAL_DETECTED;

	static {

		Product liferayPortal = ProductMap.getInstance().get(ProductConstants.LIFERAY_PORTAL);

		LIFERAY_PORTAL_DETECTED = liferayPortal.isDetected();

		if (LIFERAY_PORTAL_DETECTED) {

			int buildNumber = liferayPortal.getBuildId();

			if ((buildNumber < 6102) || ((buildNumber > 6102) && (buildNumber < 6130))) {

				Method appendMethodLegacy = null;

				try {
					appendMethodLegacy = ScriptData.class.getMethod(APPEND, new Class[] { String.class, String.class });
				}
				catch (Exception e) {
					logger.error(e);
				}

				APPEND_METHOD_LEGACY = appendMethodLegacy;
				APPEND_METHOD_CURRENT = null;
			}
			else {
				Method appendMethodCurrent = null;

				try {
					appendMethodCurrent = ScriptData.class.getMethod(APPEND,
							new Class[] { String.class, String.class, String.class });
				}
				catch (Exception e) {
					logger.error(e);
				}

				APPEND_METHOD_LEGACY = null;
				APPEND_METHOD_CURRENT = appendMethodCurrent;
			}
		}
		else {
			APPEND_METHOD_LEGACY = null;
			APPEND_METHOD_CURRENT = null;
		}
	}

	// Private Data Members
	private Object scriptData;

	public ClientScriptLiferayImpl(Object scriptData) {
		this.scriptData = scriptData;
	}

	// Java 1.6+ @Override
	public void append(String portletId, String content, String use) {

		try {

			if (APPEND_METHOD_CURRENT != null) {
				APPEND_METHOD_CURRENT.invoke(scriptData, new Object[] { portletId, content, use });
			}
			else if (APPEND_METHOD_LEGACY != null) {
				APPEND_METHOD_LEGACY.invoke(scriptData, new Object[] { content, use });
			}
		}
		catch (Exception e) {
			logger.error(e);
		}
	}
}
