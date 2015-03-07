/**
 * Copyright (c) 2000-2015 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.util.client.internal;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.liferay.faces.util.client.ClientScript;
import com.liferay.faces.util.jsp.PageContextAdapter;
import com.liferay.faces.util.jsp.StringJspWriter;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;
import com.liferay.faces.util.portal.ScriptData;
import com.liferay.faces.util.portal.ScriptTagUtil;
import com.liferay.faces.util.product.Product;
import com.liferay.faces.util.product.ProductConstants;
import com.liferay.faces.util.product.ProductMap;
import com.liferay.portal.util.PortalUtil;


/**
 * @author  Neil Griffin
 */
public abstract class ClientScriptLiferayCompatImpl implements ClientScript {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(ClientScriptLiferayCompatImpl.class);

	// Private Constants
	private static final String APPEND = "append";
	private static final Method APPEND_METHOD_LEGACY;
	private static final Method APPEND_METHOD_CURRENT;

	static {

		Product liferayPortal = ProductMap.getInstance().get(ProductConstants.LIFERAY_PORTAL);
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

	protected void appendScriptData(ScriptData scriptData, String portletId, String content, String use) {

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

	protected String getScriptText(FacesContext facesContext, ScriptData scriptData) {

		ClientScriptWriter clientScriptWriter = new ClientScriptWriter();

		if (scriptData != null) {
			ExternalContext externalContext = facesContext.getExternalContext();
			PortletRequest portletRequest = (PortletRequest) externalContext.getRequest();
			PortletResponse portletResponse = (PortletResponse) externalContext.getResponse();
			HttpServletRequest httpServletRequest = PortalUtil.getHttpServletRequest(portletRequest);
			HttpServletResponse httpServletResponse = PortalUtil.getHttpServletResponse(portletResponse);
			PageContextAdapter pageContextAdapter = new PageContextAdapter(httpServletRequest, httpServletResponse,
					facesContext.getELContext(), (StringJspWriter) clientScriptWriter);
			ScriptTagUtil.flushScriptData(pageContextAdapter);
		}

		return clientScriptWriter.toString();
	}

	private class ClientScriptWriter extends StringJspWriter {

		@Override
		public void write(String string) throws IOException {

			if (!(string.startsWith("<script") || string.endsWith("script>"))) {
				super.write(string);
			}
		}
	}
}
