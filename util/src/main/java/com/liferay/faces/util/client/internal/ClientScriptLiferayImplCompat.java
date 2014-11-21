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
package com.liferay.faces.util.client.internal;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.liferay.faces.util.client.ClientScript;
import com.liferay.faces.util.context.ExtFacesContext;
import com.liferay.faces.util.jsp.PageContextAdapter;
import com.liferay.faces.util.jsp.StringJspWriter;
import com.liferay.faces.util.lang.StringPool;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;
import com.liferay.faces.util.portal.LiferayPortletUtil;
import com.liferay.faces.util.portal.ScriptTagUtil;
import com.liferay.faces.util.portal.WebKeys;
import com.liferay.faces.util.product.Product;
import com.liferay.faces.util.product.ProductConstants;
import com.liferay.faces.util.product.ProductMap;

import com.liferay.portal.kernel.servlet.taglib.aui.ScriptData;
import com.liferay.portal.util.PortalUtil;


/**
 * @author  Neil Griffin
 */
public class ClientScriptLiferayImplCompat implements ClientScript {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(ClientScriptLiferayImplCompat.class);

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

	// Private Data Members
	private ScriptData scriptData;

	@Override
	public void append(String content, String use) {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		Map<String, Object> requestMap = externalContext.getRequestMap();

		if (scriptData == null) {

			scriptData = new ScriptData();
			requestMap.put(WebKeys.AUI_SCRIPT_DATA, scriptData);
		}

		try {

			if (APPEND_METHOD_CURRENT != null) {

				String portletId = StringPool.BLANK;
				Object portlet = requestMap.get(WebKeys.RENDER_PORTLET);

				if (portlet != null) {
					portletId = LiferayPortletUtil.getPortletId(portlet);
				}

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

	@Override
	public void clear() {

		ExtFacesContext extFacesContext = ExtFacesContext.getInstance();
		Map<String, Object> requestMap = extFacesContext.getExternalContext().getRequestMap();
		requestMap.remove(WebKeys.AUI_SCRIPT_DATA);
		scriptData = null;
	}

	@Override
	public String toString() {

		ClientScriptLiferayWriter clientScriptLiferayWriter = new ClientScriptLiferayWriter();

		if (scriptData != null) {

			FacesContext facesContext = FacesContext.getCurrentInstance();
			ExternalContext externalContext = facesContext.getExternalContext();
			PortletRequest portletRequest = (PortletRequest) externalContext.getRequest();
			PortletResponse portletResponse = (PortletResponse) externalContext.getResponse();
			HttpServletRequest httpServletRequest = PortalUtil.getHttpServletRequest(portletRequest);
			HttpServletResponse httpServletResponse = PortalUtil.getHttpServletResponse(portletResponse);
			PageContextAdapter pageContextAdapter = new PageContextAdapter(httpServletRequest, httpServletResponse,
					facesContext.getELContext(), clientScriptLiferayWriter);
			ScriptTagUtil.flushScriptData(pageContextAdapter);
		}

		return clientScriptLiferayWriter.toString();
	}

	private class ClientScriptLiferayWriter extends StringJspWriter {

		@Override
		public void write(String string) throws IOException {

			if (!(string.startsWith("<script") || string.endsWith("script>"))) {
				super.write(string);
			}
		}
	}
}
