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
package com.liferay.faces.bridge.container.liferay.internal;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.portlet.MimeResponse;
import javax.portlet.PortletContext;
import javax.portlet.PortletMode;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.ResourceURL;
import javax.portlet.WindowState;

import com.liferay.faces.bridge.BridgeFactoryFinder;
import com.liferay.faces.bridge.context.BridgeContext;
import com.liferay.faces.bridge.internal.BridgeConstants;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;

import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;


/**
 * @author  Neil Griffin
 */
public class PortletContainerLiferayImpl extends PortletContainerLiferayCompatImpl {

	// serialVersionUID
	private static final long serialVersionUID = 4751433245905676075L;

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(PortletContainerLiferayImpl.class);

	// Private Pseudo-Constants Initialized at Construction-Time
	private String NAMESPACED_P_P_COL_ID;
	private String NAMESPACED_P_P_COL_POS;
	private String NAMESPACED_P_P_COL_COUNT;
	private String NAMESPACED_P_P_MODE;
	private String NAMESPACED_P_P_STATE;

	// Private Data Members
	private boolean friendlyURLMapperEnabled;
	private LiferayURLFactory liferayURLFactory;
	private LiferayPortletRequest liferayPortletRequest;
	private String portletResponseNamespace;
	private String requestURL;
	private String responseNamespace;

	public PortletContainerLiferayImpl(PortletRequest portletRequest, PortletResponse portletResponse,
		PortletContext portletContext) {

		try {

			// Initialize the private data members.
			this.portletResponseNamespace = portletResponse.getNamespace();

			LiferayPortletRequest liferayPortletRequest = new LiferayPortletRequest(portletRequest);
			ThemeDisplay themeDisplay = liferayPortletRequest.getThemeDisplay();
			this.liferayPortletRequest = liferayPortletRequest;

			// Initialize the pseudo-constants.
			NAMESPACED_P_P_COL_ID = portletResponseNamespace + LiferayConstants.P_P_COL_ID;
			NAMESPACED_P_P_COL_POS = portletResponseNamespace + LiferayConstants.P_P_COL_POS;
			NAMESPACED_P_P_COL_COUNT = portletResponseNamespace + LiferayConstants.P_P_COL_COUNT;
			NAMESPACED_P_P_MODE = portletResponseNamespace + LiferayConstants.P_P_MODE;
			NAMESPACED_P_P_STATE = portletResponseNamespace + LiferayConstants.P_P_STATE;

			// Save the render attributes.
			if (portletRequest instanceof RenderRequest) {
				PortletMode portletMode = portletRequest.getPortletMode();
				WindowState windowState = portletRequest.getWindowState();
				saveRenderAttributes(portletMode, windowState, themeDisplay.getPortletDisplay(), portletContext);
			}

			this.friendlyURLMapperEnabled = (liferayPortletRequest.getPortlet().getFriendlyURLMapperInstance() != null);
			this.liferayURLFactory = (LiferayURLFactory) BridgeFactoryFinder.getFactory(LiferayURLFactory.class);
			logger.debug("User-Agent requested URL=[{0}]", getRequestURL());
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	@Override
	public PortletURL createRedirectURL(String fromURL, Map<String, List<String>> parameters)
		throws MalformedURLException {

		BridgeContext bridgeContext = BridgeContext.getCurrentInstance();
		LiferayPortletResponse liferayPortletResponse = new LiferayPortletResponse(bridgeContext.getPortletResponse());

		PortletURL redirectURL = liferayPortletResponse.createRenderURL();

		copyRequestParameters(fromURL, redirectURL);

		if (parameters != null) {
			Set<String> parameterNames = parameters.keySet();

			for (String parameterName : parameterNames) {
				List<String> parameterValues = parameters.get(parameterName);
				String[] parameterValuesArray = parameterValues.toArray(new String[parameterValues.size()]);
				redirectURL.setParameter(parameterName, parameterValuesArray);
			}
		}

		return redirectURL;
	}

	@Override
	protected PortletURL createActionURL(MimeResponse mimeResponse) {

		BridgeContext bridgeContext = BridgeContext.getCurrentInstance();

		return liferayURLFactory.getLiferayActionURL(bridgeContext, mimeResponse, portletResponseNamespace,
				friendlyURLMapperEnabled);
	}

	@Override
	protected PortletURL createRenderURL(MimeResponse mimeResponse) {

		BridgeContext bridgeContext = BridgeContext.getCurrentInstance();

		return liferayURLFactory.getLiferayRenderURL(bridgeContext, mimeResponse, portletResponseNamespace,
				friendlyURLMapperEnabled);
	}

	@Override
	protected ResourceURL createResourceURL(MimeResponse mimeResponse) {

		BridgeContext bridgeContext = BridgeContext.getCurrentInstance();

		return liferayURLFactory.getLiferayResourceURL(bridgeContext, mimeResponse, portletResponseNamespace,
				friendlyURLMapperEnabled);
	}

	/**
	 * Liferay Hack: Need to save some stuff that's only available at RenderRequest time in order to have
	 * getResourceURL() work properly later.
	 */
	protected void saveRenderAttributes(PortletMode portletMode, WindowState windowState, PortletDisplay portletDisplay,
		PortletContext portletContext) {

		try {

			// Get the p_p_col_id and save it.
			portletContext.setAttribute(NAMESPACED_P_P_COL_ID, portletDisplay.getColumnId());

			// Get the p_p_col_pos and save it.
			portletContext.setAttribute(NAMESPACED_P_P_COL_POS, Integer.toString(portletDisplay.getColumnPos()));

			// Get the p_p_col_count and save it.
			portletContext.setAttribute(NAMESPACED_P_P_COL_COUNT, Integer.toString(portletDisplay.getColumnCount()));

			// Get the p_p_mode and save it.
			if (portletMode != null) {
				portletContext.setAttribute(NAMESPACED_P_P_MODE, portletMode.toString());
			}

			// Get the p_p_state and save it.
			if (windowState != null) {
				portletContext.setAttribute(NAMESPACED_P_P_STATE, windowState.toString());
			}
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	@Override
	public String[] getHeader(String name) {
		return liferayPortletRequest.getHeader(name);
	}

	@Override
	public long getHttpServletRequestDateHeader(String name) {
		return liferayPortletRequest.getDateHeader(name);
	}

	@Override
	public String getRequestURL() {

		if (requestURL == null) {
			StringBuilder buf = new StringBuilder();
			ThemeDisplay themeDisplay = liferayPortletRequest.getThemeDisplay();
			buf.append(themeDisplay.getURLPortal());
			buf.append(themeDisplay.getURLCurrent());
			requestURL = buf.toString();
		}

		return requestURL;
	}

	@Override
	public String getResponseNamespace() {

		if (responseNamespace == null) {

			BridgeContext bridgeContext = BridgeContext.getCurrentInstance();
			responseNamespace = bridgeContext.getPortletResponse().getNamespace();

			if (responseNamespace.startsWith(BridgeConstants.WSRP_REWRITE)) {
				responseNamespace = LiferayPortalUtil.getPortletId(bridgeContext.getPortletRequest());
			}
		}

		return responseNamespace;
	}
}
