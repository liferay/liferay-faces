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
package com.liferay.faces.bridge.scope;

import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletRequest;

import com.liferay.faces.bridge.BridgeConstants;
import com.liferay.faces.bridge.config.ProductMap;


/**
 * @author  Neil Griffin
 */
public class BridgeRequestScopeFactoryImpl extends BridgeRequestScopeFactory {

	// Private Constants
	private static final boolean LIFERAY_PORTAL_DETECTED = ProductMap.getInstance().get(BridgeConstants.LIFERAY_PORTAL)
		.isDetected();

	// Private Data Members
	private BridgeRequestScopeFactory wrappedFactory;

	public BridgeRequestScopeFactoryImpl() {
	}

	public BridgeRequestScopeFactoryImpl(BridgeRequestScopeFactory bridgeRequestScopeFactory) {
		wrappedFactory = bridgeRequestScopeFactory;
	}

	@Override
	public BridgeRequestScope getBridgeRequestScope(PortletConfig portletConfig, PortletContext portletContext,
		PortletRequest portletRequest, String idPrefix) {

		BridgeRequestScope bridgeRequestScope = null;

		if (wrappedFactory != null) {
			bridgeRequestScope = wrappedFactory.getBridgeRequestScope(portletConfig, portletContext, portletRequest,
					idPrefix);
		}

		if (bridgeRequestScope == null) {

			if (LIFERAY_PORTAL_DETECTED) {
				bridgeRequestScope = new BridgeRequestScopeLiferayImpl(portletConfig, portletContext, portletRequest,
						idPrefix);
			}
			else {
				bridgeRequestScope = new BridgeRequestScopeImpl(portletConfig, portletContext, portletRequest,
						idPrefix);
			}
		}

		return bridgeRequestScope;
	}

	public BridgeRequestScopeFactory getWrapped() {
		return wrappedFactory;
	}
}
