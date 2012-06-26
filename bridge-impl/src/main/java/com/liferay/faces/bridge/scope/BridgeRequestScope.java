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
package com.liferay.faces.bridge.scope;

import java.util.Map;
import java.util.concurrent.ConcurrentMap;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.portlet.ActionRequest;
import javax.portlet.PortletMode;
import javax.portlet.RenderRequest;


/**
 * Section 5.1.2 of the JSR 329 Spec describes a concept called the "bridge request scope," the purpose of which is to
 * bridge the gap between the portlet lifecycle's ACTION_PHASE, EVENT_PHASE, and RENDER_PHASE. When the user invokes an
 * HTTP POST operation by clicking on a Submit button, the portlet lifecycle's ACTION_PHASE will be invoked, and the
 * bridge will execute all the phases of the JSF lifecycle except for RENDER_RESPONSE -- which is to be done later in
 * the portlet lifecycle's RENDER_PHASE. The problem is that there might be things stored as request attributes like
 * FacesMessage(s), and JSF Managed-Bean(s) during in the ACTION_PHASE, but also need to be referenced in the
 * RENDER_PHASE. Technically, the {@link ActionRequest} created for the ACTION_PHASE and the {@link RenderRequest}
 * created for the RENDER_PHASE are two different objects, and so the BridgeRequestScope exists to maintain request
 * attribute values between the two phases.
 *
 * @author  Neil Griffin
 */
public interface BridgeRequestScope extends ConcurrentMap<String, Object> {

	/**
	 * This method preserves the scoped data (as defined in Section 5.1.2 of the Bridge Spec). It should only be
	 * called during the {@link javax.portlet.PortletRequest#ACTION_PHASE} and {@link
	 * javax.portlet.PortletRequest#EVENT_PHASE} of the portlet lifecycle.
	 *
	 * @param  facesContext  The current {@link FacesContext}.
	 */
	void preserve(FacesContext facesContext);

	/**
	 * This method restores the scoped data that was preserved by the call to {@link
	 * #preserve(FacesContext)} method as required by section 5.1.2 of the Bridge Spec. This method is
	 * designed to be called during the EVENT_PHASE and RENDER_PHASE of the portlet lifecycle.
	 *
	 * @param  facesContext  The current {@link FacesContext}.
	 */
	void restore(FacesContext facesContext);

	/**
	 * Returns the flag indicating whether or not the Faces Lifecycle was executed.
	 *
	 * @return  <code>true</code> if the Faces Lifecycle was executed, otherwise <code>false</code>.
	 */
	boolean isFacesLifecycleExecuted();

	/**
	 * Returns the flag indicating whether or not a navigation-rule fired.
	 *
	 * @return  <code>true</code> indicates that a navigation-rule fired, otherwise <code>false</code>.
	 */
	boolean isNavigationOccurred();

	/**
	 * Returns a flag indicating whether or not the PortletMode has changed.
	 *
	 * @return  <code>true</code> if the portlet mode has changed, otherwise <code>false</code>
	 */
	boolean isPortletModeChanged();

	/**
	 * Returns a flag indicating whether or not a <redirect/> was encountered in a navigation-rule.
	 *
	 * @return  <code>true</code> indicates that <redirect/> was encountered in a navigation-rule, otherwise <code>
	 *          false</code>.
	 */
	boolean isRedirectOccurred();

	/**
	 * Sets the flag indicating whether or not the Faces lifecycle was executed.
	 *
	 * @param  facesLifecycleExecuted  <code>true</code> indicates that the Faces lifecycle was executed, otherwise
	 *                                 <code>false</code>.
	 */
	void setFacesLifecycleExecuted(boolean facesLifecycleExecuted);

	// PROPOSED-FOR-BRIDGE3-API: https://issues.apache.org/jira/browse/PORTLETBRIDGE-201
	Flash getFlash();

	// PROPOSED-FOR-BRIDGE3-API: https://issues.apache.org/jira/browse/PORTLETBRIDGE-201
	void setFlash(Flash flash);

	/**
	 * Returns the unique identifier, which is prefixed with the value passed to {@link #setIdPrefix(String)}.
	 */
	String getId();

	/**
	 * Sets the prefix of the unique identifier returned by {@link #getId()}.
	 */
	public void setIdPrefix(String idPrefix);

	/**
	 * Sets the flag indicating whether or not a navigation-rule fired.
	 *
	 * @param  navigationOccurred  <code>true</code> indicates that a navigation-rule fired, otherwise <code>
	 *                             false</code>.
	 */
	void setNavigationOccurred(boolean navigationOccurred);

	// PROPOSED-FOR-STANDARD: https://issues.apache.org/jira/browse/PORTLETBRIDGE-202
	PortletMode getPortletMode();

	// PROPOSED-FOR-STANDARD: https://issues.apache.org/jira/browse/PORTLETBRIDGE-202
	void setPortletMode(PortletMode portletMode);

	/**
	 * Sets a flag indicating whether or not the PortletMode has changed. If <code>true</code> then request attributes
	 * will not be preserved when the {@link #preserve(FacesContext)} method is called.
	 */
	void setPortletModeChanged(boolean portletModeChanged);

	Map<String, String> getPreservedActionParameterMap();

	String getPreservedViewStateParam();

	/**
	 * Sets a flag indicating whether or not a <redirect/> was encountered in a navigation-rule.
	 *
	 * @param  redirectOccurred  <code>true</code> indicates that a <redirect/> was encountered in a navigation-rule,
	 *                           otherwise <code>false</code>.
	 */
	void setRedirectOccurred(boolean redirectOccurred);
}
