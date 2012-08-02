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
package com.liferay.faces.bridge;

import java.io.IOException;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import com.icesoft.faces.webapp.http.portlet.MainPortlet;

import com.liferay.faces.bridge.context.BridgeContext;
import com.liferay.faces.bridge.context.BridgeContextImpl;


/**
 * @author  Neil Griffin
 */
public class GenericICEfacesPortlet extends MainPortlet {

	@Override
	protected void doDispatch(RenderRequest request, RenderResponse response) throws PortletException, IOException {

		BridgeContextImpl bridgeContext = new BridgeContextImpl(getPortletConfig());
		BridgeContext.setCurrentInstance(bridgeContext);
		super.doDispatch(request, response);
		BridgeContext.setCurrentInstance(null);
	}

}
