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
package com.liferay.faces.bridge.context.url;

import java.net.MalformedURLException;

import javax.portlet.PortletMode;
import javax.portlet.PortletModeException;
import javax.portlet.PortletSecurityException;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;
import javax.portlet.WindowStateException;

import com.liferay.faces.bridge.container.PortletURLWrapper;
import com.liferay.faces.bridge.context.BridgeContext;
import com.liferay.faces.bridge.logging.Logger;
import com.liferay.faces.bridge.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
public abstract class PortletURLFacesTarget extends PortletURLWrapper {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(PortletURLFacesTarget.class);

	// Private Data Members
	private PortletURL wrappedPortletURL;

	public PortletURLFacesTarget(BridgeContext bridgeContext, String url, String portletMode, String windowState,
		boolean secure) throws MalformedURLException {

		this.wrappedPortletURL = createPortletURL(bridgeContext, url);

		if (portletMode != null) {

			try {
				this.wrappedPortletURL.setPortletMode(new PortletMode(portletMode));
			}
			catch (PortletModeException e) {
				logger.error(e.getMessage());
			}
		}

		if (windowState != null) {

			try {
				this.wrappedPortletURL.setWindowState(new WindowState(windowState));
			}
			catch (WindowStateException e) {
				logger.error(e.getMessage());
			}
		}

		try {
			this.wrappedPortletURL.setSecure(secure);
		}
		catch (PortletSecurityException e) {
			logger.error(e.getMessage());
		}

	}

	public abstract PortletURL createPortletURL(BridgeContext bridgeContext, String url) throws MalformedURLException;

	@Override
	public PortletURL getWrapped() {
		return wrappedPortletURL;
	}
}
