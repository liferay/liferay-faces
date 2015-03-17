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
package com.liferay.faces.bridge.context.internal;

import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletMode;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.RenderRequest;
import javax.portlet.WindowState;
import javax.portlet.faces.Bridge;

import com.liferay.faces.bridge.config.BridgeConfig;
import com.liferay.faces.bridge.context.IncongruityContext;
import com.liferay.faces.bridge.scope.BridgeRequestScope;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;

import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;


/**
 * @author  Neil Griffin
 */
public class BridgeContextLiferayImpl extends BridgeContextImpl {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(BridgeContextLiferayImpl.class);

	// Private Pseudo-Constants Initialized at Construction-Time
	private String NAMESPACED_P_P_COL_ID;
	private String NAMESPACED_P_P_COL_POS;
	private String NAMESPACED_P_P_COL_COUNT;
	private String NAMESPACED_P_P_MODE;
	private String NAMESPACED_P_P_STATE;

	// Private Data Members
	private String requestURL;

	public BridgeContextLiferayImpl(BridgeConfig bridgeConfig, BridgeRequestScope bridgeRequestScope,
		PortletConfig portletConfig, PortletContext portletContext, PortletRequest portletRequest,
		PortletResponse portletResponse, Bridge.PortletPhase portletPhase, IncongruityContext incongruityContext) {

		super(bridgeConfig, bridgeRequestScope, portletConfig, portletContext, portletRequest, portletResponse,
			portletPhase, incongruityContext);

		String namespace = portletResponse.getNamespace();

		// Initialize the pseudo-constants.
		NAMESPACED_P_P_COL_ID = namespace.concat("p_p_col_id");
		NAMESPACED_P_P_COL_POS = namespace.concat("p_p_col_pos");
		NAMESPACED_P_P_COL_COUNT = namespace.concat("p_p_col_count");
		NAMESPACED_P_P_MODE = namespace.concat("p_p_mode");
		NAMESPACED_P_P_STATE = namespace.concat("p_p_state");

		// Save the render attributes.
		if (portletRequest instanceof RenderRequest) {
			PortletMode portletMode = portletRequest.getPortletMode();
			WindowState windowState = portletRequest.getWindowState();
			ThemeDisplay themeDisplay = (ThemeDisplay) portletRequest.getAttribute(WebKeys.THEME_DISPLAY);
			PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();
			saveRenderAttributes(portletMode, windowState, portletDisplay, portletContext);
		}

		setCurrentInstance(this);
	}

	@Override
	public void release() {
		super.release();
		this.NAMESPACED_P_P_COL_COUNT = null;
		this.NAMESPACED_P_P_COL_ID = null;
		this.NAMESPACED_P_P_COL_POS = null;
		this.NAMESPACED_P_P_MODE = null;
		this.NAMESPACED_P_P_STATE = null;
		this.requestURL = null;
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
	protected String getRequestURL() {

		if (requestURL == null) {
			StringBuilder buf = new StringBuilder();
			ThemeDisplay themeDisplay = (ThemeDisplay) getPortletRequest().getAttribute(WebKeys.THEME_DISPLAY);
			buf.append(themeDisplay.getURLPortal());
			buf.append(themeDisplay.getURLCurrent());
			requestURL = buf.toString();
		}

		return requestURL;
	}
}
