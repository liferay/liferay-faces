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
package com.liferay.faces.portal.context;

import javax.portlet.ActionResponse;
import javax.portlet.PortalContext;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletMode;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletSession;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.WindowState;


/**
 * @author  Neil Griffin
 */
public interface PortletHelper {

	/**
	 * Retrieves a render URL for the current facesContext.
	 */
	public abstract PortletURL createActionURL();

	/**
	 * Retrieves a render URL for the current facesContext.
	 */
	public abstract PortletURL createRenderURL();

	/**
	 * Retrieves the <code>javax.portlet.ActionResponse</code> object associated with the current JSF FacesContext.
	 */
	public abstract ActionResponse getActionResponse();

	/**
	 * Returns TRUE if the Window state of the current portlet is set to MAXIMIZED
	 */
	public abstract boolean isWindowMaximized();

	/**
	 * Returns TRUE if the current user is associated with the specified role.
	 */
	public abstract boolean isUserInRole(String roleName);

	/**
	 * Returns TRUE if the Window state of the current portlet is set to MAXIMIZED
	 */
	public abstract boolean isWindowNormal();

	/**
	 * Retrieves the <code>javax.portlet.PortalContext</code> object associated with the current JSF FacesContext.
	 */
	public abstract PortalContext getPortalContext();

	/**
	 * Retrieves the <code>javax.portlet.PortletConfig</code> object associated with the current JSF FacesContext.
	 */
	public abstract PortletConfig getPortletConfig();

	/**
	 * Retrieves the <code>javax.portlet.PortletContext</code> object associated with the current JSF FacesContext.
	 */
	public abstract PortletContext getPortletContext();

	/**
	 * Retrieves the name of the portlet associated with the current JSF FacesContext (usually, its the name of the
	 * portlet .war)
	 */
	public abstract String getPortletContextName();

	/**
	 * Sets the portlet mode of the portlet associated with the current JSF FacesContext to the specified portlet mode.
	 */
	public abstract void setPortletMode(PortletMode portletMode);

	/**
	 * Retrieves the name of the portlet associated with the current JSF FacesContext (as defined in portlet.xml's
	 * &lt;portlet-name&gt; tag).
	 */
	public abstract String getPortletName();

	/**
	 * Returns String representation of the value of the portlet preference associated with the specified name in from
	 * the <code>javax.portlet.PortletPreferences</code> object associated with the current JSF FacesContext.
	 */
	public abstract Object getPortletPreference(String preferenceName, Object defaultValue);

	/**
	 * Returns boolean representation of the value of the portlet preference associated with the specified name in from
	 * the PortletPreferenceMap object associated with the current JSF FacesContext.
	 */
	public abstract boolean getPortletPreferenceAsBool(String preferenceName, boolean defaultValue);

	/**
	 * Returns integer representation of the value of the portlet preference associated with the specified name in from
	 * the PortletPreferenceMap object associated with the current JSF FacesContext.
	 */
	public abstract int getPortletPreferenceAsInt(String preferenceName, int defaultValue);

	/**
	 * Returns short representation of the value of the portlet preference associated with the specified name in from
	 * the PortletPreferenceMap object associated with the current JSF FacesContext.
	 */
	public abstract short getPortletPreferenceAsShort(String preferenceName, short defaultValue);

	/**
	 * Returns short representation of the value of the portlet preference associated with the specified name in from
	 * the PortletPreferenceMap object associated with the current JSF FacesContext.
	 */
	public abstract String getPortletPreferenceAsString(String preferenceName, String defaultValue);

	/**
	 * Retrieves the <code>javax.portlet.PortletPreferences</code> object associated with the current JSF FacesContext.
	 */
	public abstract PortletPreferences getPortletPreferences();

	/**
	 * Retrieves the <code>javax.portlet.RenderRequest</code> object associated with the current JSF FacesContext.
	 */
	public abstract RenderRequest getPortletRenderRequest();

	/**
	 * Retrieves the <code>javax.portlet.RenderResponse</code> object associated with the current JSF FacesContext.
	 */
	public abstract RenderResponse getPortletRenderResponse();

	/**
	 * Retrieves the <code>javax.portlet.PortletRequest</code> object associated with the current JSF FacesContext.
	 */
	public abstract PortletRequest getPortletRequest();

	/**
	 * Retrieves the <code>javax.portlet.PortletResponse</code> object associated with the current JSF FacesContext.
	 */
	public abstract PortletResponse getPortletResponse();

	/**
	 * Retrieves the <code>javax.portlet.PortletSession</code> object associated with the current JSF FacesContext.
	 */
	public abstract PortletSession getPortletSession();

	/**
	 * Returns the user name/id of the user associated with the current JSF FacesContext
	 */
	public abstract String getRemoteUser();

	/**
	 * Returns the value of the session attribute associated with the specified name from
	 * PortletSession.APPLICATION_SCOPE
	 */
	public abstract Object getSessionSharedAttribute(String name);

	/**
	 * Sets the value of the a session attribute using the specified name and value within
	 * PortletSession.APPLICATION_SCOPE
	 */
	public abstract void setSessionSharedAttribute(String name, Object value);

	/**
	 * Returns TRUE if the underlying request/response is from a portlet environment. Otherwise, must be running in a
	 * servlet environment.
	 */
	public abstract boolean isPortletEnvironment();

	/**
	 * Returns the window state of the portlet associated with the current JSF FacesContext
	 */
	public abstract WindowState getWindowState();

	/**
	 * Sets the window state of the portlet associated with the current JSF FacesContext to the specified window state.
	 */
	public abstract void setWindowState(WindowState windowState);

	/**
	 * A short-cut method for calling setWindowState(WindowState.MAXIMIZED)
	 */
	public abstract void setWindowStateToMaximized();

	/**
	 * A short-cut method for calling setWindowState(WindowState.NORMAL)
	 */
	public abstract void setWindowStateToNormal();

}
