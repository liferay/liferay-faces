/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.portal.context;

import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.portlet.ActionResponse;
import javax.portlet.PortalContext;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletMode;
import javax.portlet.PortletModeException;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletSession;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.WindowState;
import javax.portlet.WindowStateException;

import com.liferay.faces.util.helper.BooleanHelper;
import com.liferay.faces.util.helper.IntegerHelper;
import com.liferay.faces.util.helper.ShortHelper;


/**
 * @author  Neil Griffin
 */
public class PortletHelperImpl implements PortletHelper, Serializable {

	// serialVersionUID
	private static final long serialVersionUID = 840141805125109913L;

	/** JSR-168/286 request attribute that contains an instance of javax.portlet.PortletConfig */
	private static final String REQUEST_ATTR_PORTLET_CONFIG = "javax.portlet.config";

	public PortletURL createActionURL() {
		return getPortletRenderResponse().createActionURL();
	}

	public PortletURL createRenderURL() {
		return getPortletRenderResponse().createRenderURL();
	}

	public ActionResponse getActionResponse() {
		return (ActionResponse) getPortletResponse();
	}

	public boolean isWindowMaximized() {
		return (getWindowState() == WindowState.MAXIMIZED);
	}

	public boolean isUserInRole(String roleName) {
		return getPortletRequest().isUserInRole(roleName);
	}

	public boolean isWindowNormal() {
		return (getWindowState() == WindowState.NORMAL);
	}

	public PortalContext getPortalContext() {
		return getPortletRequest().getPortalContext();
	}

	public PortletConfig getPortletConfig() {
		return (PortletConfig) getPortletRequest().getAttribute(REQUEST_ATTR_PORTLET_CONFIG);
	}

	public PortletContext getPortletContext() {
		return getPortletSession().getPortletContext();
	}

	public String getPortletContextName() {
		return getPortletContext().getPortletContextName();
	}

	public void setPortletMode(PortletMode portletMode) {
		ActionResponse response = getActionResponse();

		try {
			response.setPortletMode(portletMode);
		}
		catch (PortletModeException e) {
			throw new UnsupportedOperationException("Invalid portlet mode", e);
		}
	}

	public String getPortletName() {
		return getPortletConfig().getPortletName();
	}

	public Object getPortletPreference(String preferenceName, Object defaultValue) {

		String defaultValueAsString = null;

		if (defaultValue != null) {
			defaultValueAsString = defaultValue.toString();
		}

		Object value = getPortletPreferences().getValue(preferenceName, defaultValueAsString);

		return value;
	}

	public boolean getPortletPreferenceAsBool(String preferenceName, boolean defaultValue) {
		Object value = getPortletPreference(preferenceName, defaultValue);

		if (value != null) {
			return BooleanHelper.toBoolean(value.toString(), defaultValue);
		}

		return defaultValue;
	}

	public int getPortletPreferenceAsInt(String preferenceName, int defaultValue) {
		Object value = getPortletPreference(preferenceName, defaultValue);

		if (value != null) {
			return IntegerHelper.toInteger(value.toString(), defaultValue);
		}

		return defaultValue;
	}

	public short getPortletPreferenceAsShort(String preferenceName, short defaultValue) {
		Object value = getPortletPreference(preferenceName, defaultValue);

		if (value != null) {
			return ShortHelper.toShort(value.toString(), defaultValue);
		}

		return defaultValue;
	}

	public String getPortletPreferenceAsString(String preferenceName, String defaultValue) {
		Object value = getPortletPreference(preferenceName, defaultValue);

		if (value != null) {
			return value.toString();
		}

		return null;
	}

	public PortletPreferences getPortletPreferences() {
		return getPortletRequest().getPreferences();
	}

	public RenderRequest getPortletRenderRequest() {
		return (RenderRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}

	public RenderResponse getPortletRenderResponse() {
		return (RenderResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
	}

	public PortletRequest getPortletRequest() {
		return (PortletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}

	public PortletResponse getPortletResponse() {
		return (PortletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
	}

	public PortletSession getPortletSession() {
		return getPortletRequest().getPortletSession();
	}

	public String getRemoteUser() {
		return getPortletRequest().getRemoteUser();
	}

	public Object getSessionSharedAttribute(String name) {
		Object value = null;
		PortletSession portletSession = (PortletSession) FacesContext.getCurrentInstance().getExternalContext()
			.getSession(false);

		if (portletSession != null) {
			value = portletSession.getAttribute(name, PortletSession.APPLICATION_SCOPE);
		}

		return value;
	}

	public void setSessionSharedAttribute(String name, Object value) {
		PortletSession portletSession = (PortletSession) FacesContext.getCurrentInstance().getExternalContext()
			.getSession(true);

		if (portletSession != null) {
			portletSession.setAttribute(name, value, PortletSession.APPLICATION_SCOPE);
		}
	}

	public boolean isPortletEnvironment() {

		boolean portletEnvironment = false;
		Object request = FacesContext.getCurrentInstance().getExternalContext().getRequest();

		if (request != null) {

			if (request instanceof PortletRequest) {
				portletEnvironment = true;
			}
		}

		return portletEnvironment;
	}

	public WindowState getWindowState() {
		return getPortletRequest().getWindowState();
	}

	public void setWindowState(WindowState windowState) {
		ActionResponse response = getActionResponse();

		try {
			response.setWindowState(windowState);
		}
		catch (WindowStateException e) {
			throw new UnsupportedOperationException("Invalid window state", e);
		}
	}

	public void setWindowStateToMaximized() {
		setWindowState(WindowState.MAXIMIZED);
	}

	public void setWindowStateToNormal() {
		setWindowState(WindowState.NORMAL);
	}

}
