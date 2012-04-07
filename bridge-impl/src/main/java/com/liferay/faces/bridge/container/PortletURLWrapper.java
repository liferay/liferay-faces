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
package com.liferay.faces.bridge.container;

import javax.portlet.PortletMode;
import javax.portlet.PortletModeException;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;
import javax.portlet.WindowStateException;


/**
 * @author  Neil Griffin
 */
public abstract class PortletURLWrapper extends BaseURLWrapper implements PortletURL {

	public void removePublicRenderParameter(String name) {
		PortletURL wrappedPortletURL = (PortletURL) getWrapped();
		wrappedPortletURL.removePublicRenderParameter(name);
	}

	public PortletMode getPortletMode() {
		PortletURL wrappedPortletURL = (PortletURL) getWrapped();

		return wrappedPortletURL.getPortletMode();
	}

	public void setPortletMode(PortletMode portletMode) throws PortletModeException {
		PortletURL wrappedPortletURL = (PortletURL) getWrapped();
		wrappedPortletURL.setPortletMode(portletMode);
	}

	public WindowState getWindowState() {
		PortletURL wrappedPortletURL = (PortletURL) getWrapped();

		return wrappedPortletURL.getWindowState();
	}

	public void setWindowState(WindowState windowState) throws WindowStateException {
		PortletURL wrappedPortletURL = (PortletURL) getWrapped();
		wrappedPortletURL.setWindowState(windowState);
	}

	public abstract PortletURL getWrapped();

}
