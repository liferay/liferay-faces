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
package com.liferay.faces.bridge.context.url;

import java.util.Map;
import java.util.Set;

import javax.portlet.faces.Bridge.PortletPhase;


/**
 * @author  Neil Griffin
 */
public abstract class BridgeURLWrapper implements BridgeURL {

	public String removeParameter(String name) {
		return getWrapped().removeParameter(name);
	}

	public String getContextRelativePath() {
		return getWrapped().getContextRelativePath();
	}

	public boolean isEscaped() {
		return getWrapped().isEscaped();
	}

	public boolean isAbsolute() {
		return getWrapped().isAbsolute();
	}

	public boolean isOpaque() {
		return getWrapped().isOpaque();
	}

	public boolean isPathRelative() {
		return getWrapped().isPathRelative();
	}

	public boolean isPortletScheme() {
		return getWrapped().isPortletScheme();
	}

	public boolean isSecure() {
		return getWrapped().isSecure();
	}

	public boolean isSelfReferencing() {
		return getWrapped().isSelfReferencing();
	}

	public boolean isExternal() {
		return getWrapped().isExternal();
	}

	public boolean isHierarchical() {
		return getWrapped().isHierarchical();
	}

	public String getParameter(String name) {
		return getWrapped().getParameter(name);
	}

	public void setParameter(String name, String value) {
		getWrapped().setParameter(name, value);
	}

	public void setParameter(String name, String[] value) {
		getWrapped().setParameter(name, value);
	}

	public Map<String, String[]> getParameterMap() {
		return getWrapped().getParameterMap();
	}

	public Set<String> getParameterNames() {
		return getWrapped().getParameterNames();
	}

	public PortletPhase getPortletPhase() {
		return getWrapped().getPortletPhase();
	}

	public void setSecure(boolean secure) {
		getWrapped().setSecure(secure);
	}

	public void setSelfReferencing(boolean selfReferencing) {
		getWrapped().setSelfReferencing(selfReferencing);
	}

	public boolean isFacesViewTarget() {
		return getWrapped().isFacesViewTarget();
	}

	public abstract BridgeURL getWrapped();

}
