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

import javax.portlet.ActionRequest;
import javax.portlet.PortalContext;
import javax.portlet.filter.ActionRequestWrapper;

import com.liferay.faces.bridge.context.BridgePortalContext;


/**
 * @author  Neil Griffin
 */
public class ActionRequestBridgeImpl extends ActionRequestWrapper {

	// Private Data Members
	private BridgePortalContext bridgePortalContext;

	public ActionRequestBridgeImpl(ActionRequest actionRequest, BridgePortalContext bridgePortalContext) {
		super(actionRequest);
		this.bridgePortalContext = bridgePortalContext;
	}

	@Override
	public PortalContext getPortalContext() {
		return bridgePortalContext;
	}
}
