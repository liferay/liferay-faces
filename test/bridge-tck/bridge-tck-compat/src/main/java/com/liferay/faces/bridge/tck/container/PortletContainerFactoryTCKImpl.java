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
package com.liferay.faces.bridge.tck.container;

import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import com.liferay.faces.bridge.container.PortletContainer;
import com.liferay.faces.bridge.container.PortletContainerFactory;
import com.liferay.faces.bridge.container.internal.PortletContainerDetector;


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
		PortletContext portletContext, PortletConfig portletConfig) {

		PortletContainer portletContainer = null;

		if (PortletContainerDetector.isPlutoPortletRequest(portletRequest)) {
			portletContainer = new PortletContainerPlutoTCKImpl();
		}
		else {

			if (wrappedPortletContainerFactory != null) {
				portletContainer = wrappedPortletContainerFactory.getPortletContainer(portletRequest, portletResponse,
						portletContext, portletConfig);
			}
		}

		return portletContainer;
	}

	public PortletContainerFactory getWrapped() {
		return wrappedPortletContainerFactory;
	}

}
