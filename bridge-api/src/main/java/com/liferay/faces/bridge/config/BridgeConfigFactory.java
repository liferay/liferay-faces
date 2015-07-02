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
package com.liferay.faces.bridge.config;

import javax.faces.FacesWrapper;
import javax.portlet.PortletConfig;


/**
 * This abstract class provides a contract for defining a factory that knows how to create instances of type {@link
 * BridgeConfig} and {@link PortletConfig}.
 *
 * @author  Neil Griffin
 */
public abstract class BridgeConfigFactory implements FacesWrapper<BridgeConfigFactory> {

	public abstract BridgeConfig getBridgeConfig(PortletConfig portletConfig);

	public abstract PortletConfig getPortletConfig(PortletConfig portletConfig);
}
