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
package com.liferay.faces.bridge.container;

import javax.portlet.PortletContext;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import com.liferay.faces.bridge.config.BridgeConfig;
import com.liferay.faces.bridge.container.liferay.PortletContainerLiferayImpl;
import com.liferay.faces.bridge.container.pluto.PortletContainerPlutoImpl;
import com.liferay.faces.bridge.context.BridgeContext;


/**
 * @author  Neil Griffin
 */
public class PortletContainerFactoryImpl extends PortletContainerFactory {

	/**
	 * @see  {@link PortletContainerFactory#getPortletContainer(BridgeContext)}
	 */
	@Override
	public PortletContainer getPortletContainer(PortletRequest portletRequest, PortletResponse portletResponse,
		PortletContext portletContext, BridgeConfig bridgeConfig) {

		PortletContainer portletContainer = null;

		if (portletContainer == null) {

			if (PortletContainerDetector.isLiferayPortletRequest(portletRequest)) {
				portletContainer = new PortletContainerLiferayImpl(portletRequest, portletResponse, portletContext,
						bridgeConfig);
			}
			else if (PortletContainerDetector.isPlutoPortletRequest(portletRequest)) {
				portletContainer = new PortletContainerPlutoImpl(portletRequest, bridgeConfig);
			}
			else {
				portletContainer = new PortletContainerImpl(portletRequest, bridgeConfig);
			}
		}

		return portletContainer;
	}

	public PortletContainerFactory getWrapped() {

		// Since this is the factory instance provided by the bridge, it will never wrap another factory.
		return null;
	}
}
