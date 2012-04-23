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

import javax.portlet.PortletContext;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.faces.Bridge;

import com.liferay.faces.bridge.container.liferay.PortletContainerLiferayImpl;
import com.liferay.faces.bridge.container.pluto.PortletContainerPlutoImpl;
import com.liferay.faces.bridge.context.BridgeContext;


/**
 * @author  Neil Griffin
 */
public class PortletContainerFactoryImpl extends PortletContainerFactory {

	// Private Data Members
	private PortletContainerFactory wrappedFactory;

	public PortletContainerFactoryImpl(PortletContainerFactory portletContainerFactory) {
		this.wrappedFactory = portletContainerFactory;
	}

	/**
	 * @see  {@link PortletContainerFactory#getPortletContainer(BridgeContext)}
	 */
	@Override
	public PortletContainer getPortletContainer(PortletContext portletContext, PortletRequest portletRequest,
		PortletResponse portletResponse, Bridge.PortletPhase portletPhase) {

		PortletContainer portletContainer = null;

		if (wrappedFactory != null) {
			portletContainer = wrappedFactory.getPortletContainer(portletContext, portletRequest, portletResponse,
					portletPhase);
		}

		if (portletContainer == null) {

			if (PortletContainerDetector.isLiferayObject(portletRequest)) {
				portletContainer = new PortletContainerLiferayImpl(portletContext, portletRequest, portletResponse,
						portletPhase, portletResponse.getNamespace());
			}
			else if (PortletContainerDetector.isPlutoObject(portletRequest)) {
				portletContainer = new PortletContainerPlutoImpl(portletRequest, portletResponse, portletPhase,
						portletResponse.getNamespace());
			}
			else {
				portletContainer = new PortletContainerImpl(portletRequest, portletResponse, portletPhase,
						portletResponse.getNamespace());
			}
		}

		portletContainer.setPortletRequest(portletRequest);
		portletContainer.setPortletResponse(portletResponse);

		return portletContainer;
	}

	public PortletContainerFactory getWrapped() {
		return wrappedFactory;
	}
}
