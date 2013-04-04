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
	private PortletMode portletMode;
	private WindowState windowState;

	public LiferayPortletURL(ParsedPortletURL parsedPortletURL, String responseNamespace) {
		super(parsedPortletURL, responseNamespace);
		portletMode = parsedPortletURL.getPortletMode();
		windowState = parsedPortletURL.getWindowState();
	}

	public void removePublicRenderParameter(String name) {
		// Ignore
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
	public PortletMode getPortletMode() {
		return portletMode;
	}

	public void setPortletMode(PortletMode portletMode) throws PortletModeException {
		this.portletMode = portletMode;
		resetToString();
	}

	@Override
	public WindowState getWindowState() {
		return windowState;
	}

	public void setWindowState(WindowState windowState) throws WindowStateException {
		this.windowState = windowState;
		resetToString();
	}

}
