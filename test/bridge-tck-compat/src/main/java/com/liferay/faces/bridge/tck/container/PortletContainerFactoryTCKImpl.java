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
package com.liferay.faces.bridge.tck.container;

import javax.portlet.PortletContext;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import com.liferay.faces.bridge.config.BridgeConfig;
import com.liferay.faces.bridge.container.PortletContainer;
import com.liferay.faces.bridge.container.PortletContainerDetector;
import com.liferay.faces.bridge.container.PortletContainerFactory;


/**
 * @author  Neil Griffin
 */
public class PortletContainerFactoryTCKImpl extends PortletContainerFactory {

	// Private Data Members
	private PortletContainerFactory wrappedPortletContainerFactory;

	public PortletContainerFactoryTCKImpl(PortletContainerFactory portletContainerFactory) {
		this.wrappedPortletContainerFactory = portletContainerFactory;
	}

	@Override
	public PortletContainer getPortletContainer(PortletRequest portletRequest, PortletResponse portletResponse,
		PortletContext portletContext, BridgeConfig bridgeConfig) {

		PortletContainer portletContainer = null;

		if (PortletContainerDetector.isPlutoObject(portletRequest)) {
			portletContainer = new PortletContainerPlutoTCKImpl(portletRequest, bridgeConfig);
		}
		else {

			if (wrappedPortletContainerFactory != null) {
				portletContainer = wrappedPortletContainerFactory.getPortletContainer(portletRequest, portletResponse,
						portletContext, bridgeConfig);
			}
		}

		return portletContainer;
	}

	public PortletContainerFactory getWrapped() {
		return wrappedPortletContainerFactory;
	}

}
