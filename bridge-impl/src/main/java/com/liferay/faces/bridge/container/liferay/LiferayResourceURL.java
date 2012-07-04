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
package com.liferay.faces.bridge.container.liferay;

import javax.portlet.PortletMode;
import javax.portlet.ResourceURL;
import javax.portlet.WindowState;

import com.liferay.faces.bridge.BridgeConstants;


/**
 * @author  Neil Griffin
 */
public class LiferayResourceURL extends LiferayBaseURL implements ResourceURL {

	// Private Constants

	// Private Data Members
	private String cacheLevel;
	private String resourceId;
	private String toStringValue;

	public LiferayResourceURL(ParsedBaseURL parsedLiferayURL, String responseNamespace, int liferayBuildNumber) {
		super(parsedLiferayURL, responseNamespace);
	}

	@Override
	public String toString() {

		if (toStringValue == null) {

			String superToString = super.toString();
			StringBuilder url = new StringBuilder(superToString);

			if (resourceId != null) {
				appendParameterToURL(LiferayConstants.P_P_RESOURCE_ID, resourceId, url);
			}
			else if (superToString.startsWith(BridgeConstants.WSRP)) {
				appendParameterToURL(LiferayConstants.P_P_RESOURCE_ID, BridgeConstants.WSRP, url);
			}

			toStringValue = url.toString();
		}

		return toStringValue;
	}

	public String getCacheability() {
		return cacheLevel;
	}

	public void setCacheability(String cacheLevel) {
		this.cacheLevel = cacheLevel;
	}

	@Override
	public boolean isPortletModeRequired() {
		return true;
	}

	@Override
	public boolean isWindowStateRequired() {
		return true;
	}

	@Override
	public String getPortletLifecycleId() {
		return LiferayConstants.LIFECYCLE_RESOURCE_PHASE_ID;
	}

	@Override
	public PortletMode getPortletMode() {
		return PortletMode.VIEW;
	}

	public void setResourceID(String resourceId) {
		this.resourceId = resourceId;
	}

	@Override
	public WindowState getWindowState() {
		return WindowState.NORMAL;
	}

}
