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
package com.liferay.faces.bridge.config.internal;

import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;


/**
 * This class is part of a workaround in the bridge for LPS-3311 and LPS-8355 (both fixed in Liferay Portal 6.0).
 *
 * @author  Neil Griffin
 */
public class PortletConfigBridgeLiferayImpl extends PortletConfigWrapper {

	// Private Data Members
	private PortletConfig wrappedPortletConfig;
	private PortletContext portletContext;

	public PortletConfigBridgeLiferayImpl(PortletConfig portletConfig) {
		this.wrappedPortletConfig = portletConfig;
		this.portletContext = new PortletContextBridgeLiferayImpl(portletConfig.getPortletContext());
	}

	@Override
	public PortletContext getPortletContext() {
		return portletContext;
	}

	@Override
	public PortletConfig getWrapped() {
		return wrappedPortletConfig;
	}
}
