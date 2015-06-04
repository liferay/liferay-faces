/**
 * Copyright (c) 2000-2015 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.bridge.filter.internal;

import javax.portlet.PortletContext;

import com.liferay.faces.bridge.filter.BridgePortletContextFactory;
import com.liferay.faces.util.product.ProductConstants;
import com.liferay.faces.util.product.ProductMap;


/**
 * @author  Neil Griffin
 */
public class BridgePortletContextFactoryImpl extends BridgePortletContextFactory {

	// Private Constants
	private static final boolean LIFERAY_PORTAL_DETECTED = ProductMap.getInstance().get(ProductConstants.LIFERAY_PORTAL)
		.isDetected();

	// Private Data Members
	private BridgePortletContextFactory wrappedBridgePortletContextFactory;

	public BridgePortletContextFactoryImpl(BridgePortletContextFactory bridgePortletContextFactory) {
		this.wrappedBridgePortletContextFactory = bridgePortletContextFactory;
	}

	@Override
	public PortletContext getPortletContext(PortletContext portletContext) {

		if (LIFERAY_PORTAL_DETECTED) {
			return new PortletContextBridgeLiferayImpl(portletContext);
		}
		else {
			return portletContext;
		}
	}

	public BridgePortletContextFactory getWrapped() {
		return wrappedBridgePortletContextFactory;
	}
}
