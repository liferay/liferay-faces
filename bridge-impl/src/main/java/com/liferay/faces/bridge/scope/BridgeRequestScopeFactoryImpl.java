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
package com.liferay.faces.bridge.scope;

import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletRequest;

import com.liferay.faces.util.product.ProductConstants;
import com.liferay.faces.util.product.ProductMap;


/**
 * @author  Neil Griffin
 */
public class BridgeRequestScopeFactoryImpl extends BridgeRequestScopeFactory {

	// Private Constants
	private static final boolean LIFERAY_PORTAL_DETECTED = ProductMap.getInstance().get(ProductConstants.LIFERAY_PORTAL)
		.isDetected();

	@Override
	public BridgeRequestScope getBridgeRequestScope(PortletConfig portletConfig, PortletContext portletContext,
		PortletRequest portletRequest) {

		if (LIFERAY_PORTAL_DETECTED) {
			return new BridgeRequestScopeLiferayImpl(portletConfig, portletContext, portletRequest);
		}
		else {
			return new BridgeRequestScopeImpl(portletConfig, portletContext, portletRequest);
		}
	}

	public BridgeRequestScopeFactory getWrapped() {

		// Since this is the factory instance provided by the bridge, it will never wrap another factory.
		return null;
	}
}
