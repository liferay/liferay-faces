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
import javax.portlet.PortletModeException;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;
import javax.portlet.WindowStateException;


/**
 * @author  Neil Griffin
 */
public abstract class LiferayPortletURL extends LiferayBaseURL implements PortletURL {

	// Private Data Members
	private final PortletURL portletUrl;

	public LiferayPortletURL(PortletURL portletURL, String responseNamespace) {
		super(portletURL, responseNamespace);
		this.portletUrl = portletURL;
	}

	public void removePublicRenderParameter(String name) {
		// Ignore
	}

	@Override
	public PortletMode getPortletMode() {
		return portletUrl.getPortletMode();
	}

	public void setPortletMode(PortletMode portletMode) throws PortletModeException {
		portletUrl.setPortletMode(portletMode);
		resetToString();
	}

	@Override
	public WindowState getWindowState() {
		return portletUrl.getWindowState();
	}

	public void setWindowState(WindowState windowState) throws WindowStateException {
		portletUrl.setWindowState(windowState);
		resetToString();
	}

}
