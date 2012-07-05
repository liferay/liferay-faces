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
package com.liferay.faces.bridge.tck.scope;

import javax.faces.FacesException;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletRequest;

import com.liferay.faces.bridge.scope.BridgeRequestScope;
import com.liferay.faces.bridge.scope.BridgeRequestScopeFactory;


/**
 * @author  Neil Griffin
 */
public class BridgeRequestScopeFactoryTCKImpl extends BridgeRequestScopeFactory {

	// Private Data Members
	private BridgeRequestScopeFactory wrappedBridgeRequestScopeFactory;

	public BridgeRequestScopeFactoryTCKImpl(BridgeRequestScopeFactory bridgeRequestScopeFactory) {
		this.wrappedBridgeRequestScopeFactory = bridgeRequestScopeFactory;
	}

	@Override
	public BridgeRequestScope getBridgeRequestScope(PortletConfig portletConfig, PortletContext portletContext,
		PortletRequest portletRequest, String idPrefix) throws FacesException {

		if (portletConfig.getClass().getName().startsWith("com.liferay")) {
			return new BridgeRequestScopeLiferayTCKImpl(portletConfig, portletContext, portletRequest, idPrefix);
		}
		else {
			return wrappedBridgeRequestScopeFactory.getBridgeRequestScope(portletConfig, portletContext, portletRequest,
					idPrefix);
		}
	}

	public BridgeRequestScopeFactory getWrapped() {
		return wrappedBridgeRequestScopeFactory;
	}

}
