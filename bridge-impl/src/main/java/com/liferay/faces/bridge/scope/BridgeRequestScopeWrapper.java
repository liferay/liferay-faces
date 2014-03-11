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
package com.liferay.faces.bridge.scope;

import java.util.Map;
import java.util.Set;

import javax.faces.context.FacesContext;
import javax.portlet.PortletMode;
import javax.portlet.RenderRequest;
import javax.portlet.faces.Bridge.PortletPhase;

import com.liferay.faces.util.helper.Wrapper;


/**
 * @author  Neil Griffin
 */
public abstract class BridgeRequestScopeWrapper implements BridgeRequestScope, Wrapper<BridgeRequestScope> {

	public void removeExcludedAttributes(RenderRequest renderRequest) {
		getWrapped().removeExcludedAttributes(renderRequest);
	}

	public void restoreState(FacesContext facesContext) {
		getWrapped().restoreState(facesContext);
	}

	public void saveState(FacesContext facesContext) {
		getWrapped().saveState(facesContext);
	}

	public PortletPhase getBeganInPhase() {
		return getWrapped().getBeganInPhase();
	}

	public boolean isFacesLifecycleExecuted() {
		return getWrapped().isFacesLifecycleExecuted();
	}

	public boolean isNavigationOccurred() {
		return getWrapped().isNavigationOccurred();
	}

	public boolean isPortletModeChanged() {
		return getWrapped().isPortletModeChanged();
	}

	public boolean isRedirectOccurred() {
		return getWrapped().isRedirectOccurred();
	}

	public long getDateCreated() {
		return getWrapped().getDateCreated();
	}

	public void setFacesLifecycleExecuted(boolean facesLifecycleExecuted) {
		getWrapped().setFacesLifecycleExecuted(facesLifecycleExecuted);
	}

	public String getId() {
		return getWrapped().getId();
	}

	public void setIdPrefix(String idPrefix) {
		getWrapped().setIdPrefix(idPrefix);
	}

	public void setNavigationOccurred(boolean navigationOccurred) {
		getWrapped().setNavigationOccurred(navigationOccurred);
	}

	public PortletMode getPortletMode() {
		return getWrapped().getPortletMode();
	}

	public void setPortletMode(PortletMode portletMode) {
		getWrapped().setPortletMode(portletMode);
	}

	public void setPortletModeChanged(boolean portletModeChanged) {
		getWrapped().setPortletModeChanged(portletModeChanged);
	}

	public Map<String, String> getPreservedActionParameterMap() {
		return getWrapped().getPreservedActionParameterMap();
	}

	public String getPreservedViewStateParam() {
		return getWrapped().getPreservedViewStateParam();
	}

	public void setRedirectOccurred(boolean redirectOccurred) {
		getWrapped().setRedirectOccurred(redirectOccurred);
	}

	public Set<String> getRemovedAttributeNames() {
		return getWrapped().getRemovedAttributeNames();
	}

	public abstract BridgeRequestScope getWrapped();
}
