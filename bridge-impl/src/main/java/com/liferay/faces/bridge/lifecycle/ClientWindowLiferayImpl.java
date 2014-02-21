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
package com.liferay.faces.bridge.lifecycle;

import java.util.Map;

import javax.faces.component.UINamingContainer;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.lifecycle.ClientWindow;
import javax.faces.lifecycle.ClientWindowWrapper;
import javax.faces.render.ResponseStateManager;

import com.liferay.faces.util.lang.StringPool;
import com.liferay.faces.util.portal.WebKeys;

import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;


/**
 * This class serves as a wrapper around the {@link ClientWindow} implementation provided by the JSF runtime
 * (Mojarra/MyFaces). Liferay Portal provides the ability to have multiple portlets on the same portal page, each with
 * its own unique instance ID that is the suffix of the portlet namespace. The {@link #getId()} method ensures that the
 * client window ID takes the portlet instance into account.
 *
 * @author  Neil Griffin
 */
public class ClientWindowLiferayImpl extends ClientWindowWrapper {

	// Private Constants
	private static final String CLIENT_WINDOW_COUNTER_KEY =
		"com.liferay.faces.bridge.lifecycle.CLIENT_WINDOW_COUNTER_KEY";

	// Private Data Members
	private String id;
	private ClientWindow wrappedClientWindow;

	public ClientWindowLiferayImpl(ClientWindow clientWindow) {
		wrappedClientWindow = clientWindow;
	}

	@Override
	public void decode(FacesContext facesContext) {

		ExternalContext externalContext = facesContext.getExternalContext();
		Map<String, String> requestParameterMap = externalContext.getRequestParameterMap();

		if (isClientWindowRenderModeEnabled(facesContext)) {
			id = requestParameterMap.get(ResponseStateManager.CLIENT_WINDOW_URL_PARAM);
		}

		if (requestParameterMap.containsKey(ResponseStateManager.CLIENT_WINDOW_PARAM)) {
			id = requestParameterMap.get(ResponseStateManager.CLIENT_WINDOW_PARAM);
		}
	}

	@Override
	public String getId() {

		if (id == null) {

			FacesContext facesContext = FacesContext.getCurrentInstance();
			ExternalContext externalContext = facesContext.getExternalContext();
			String portletNamespace = StringPool.BLANK;
			ThemeDisplay themeDisplay = (ThemeDisplay) externalContext.getRequestMap().get(WebKeys.THEME_DISPLAY);

			if (themeDisplay != null) {
				PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();
				portletNamespace = portletDisplay.getNamespace();
			}

			char separatorChar = UINamingContainer.getSeparatorChar(facesContext);
			String clientWindowCounterKey = CLIENT_WINDOW_COUNTER_KEY + separatorChar + portletNamespace;
			Object session = externalContext.getSession(true);
			String sessionId = externalContext.getSessionId(true);
			Map<String, Object> sessionMap = externalContext.getSessionMap();

			synchronized (session) {
				Integer clientWindowCounter = (Integer) sessionMap.get(clientWindowCounterKey);

				if (clientWindowCounter == null) {
					clientWindowCounter = new Integer(0);
				}

				id = sessionId + separatorChar + portletNamespace + separatorChar + clientWindowCounter;
				sessionMap.put(clientWindowCounterKey, ++clientWindowCounter);
			}
		}

		return id;
	}

	@Override
	public Map<String, String> getQueryURLParameters(FacesContext facesContext) {
		return wrappedClientWindow.getQueryURLParameters(facesContext);
	}

	@Override
	public ClientWindow getWrapped() {
		return wrappedClientWindow;
	}
}
