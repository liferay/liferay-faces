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

import javax.portlet.PortletRequest;

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
	public PortletContainer getPortletContainer(BridgeContext bridgeContext) {

		PortletContainer portletContainer = null;

		if (wrappedFactory != null) {
			portletContainer = wrappedFactory.getPortletContainer(bridgeContext);
		}

		if (portletContainer == null) {

			PortletRequest portletRequest = bridgeContext.getPortletRequest();

			if (PortletContainerDetector.isLiferayObject(portletRequest)) {
				portletContainer = new PortletContainerLiferayImpl(bridgeContext);
			}
			else if (PortletContainerDetector.isPlutoObject(portletRequest)) {
				portletContainer = new PortletContainerPlutoImpl(bridgeContext);
			}
			else {
				portletContainer = new PortletContainerImpl(bridgeContext);
			}
		}

		return portletContainer;
	}

	public PortletContainerFactory getWrapped() {
		return wrappedFactory;
	}
}
