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
package com.liferay.faces.bridge.config;

import javax.faces.FacesException;
import javax.portlet.PortletContext;

import com.liferay.faces.bridge.FactoryWrapper;


/**
 * This abstract class provides a contract for defining a factory that knows how to create instances of type {@link
 * BridgeConfig}.
 *
 * @author  Neil Griffin
 */
@SuppressWarnings("deprecation")
public abstract class BridgeConfigFactory implements FactoryWrapper<BridgeConfigFactory> {

	public abstract BridgeConfig getBridgeConfig(PortletContext portletContext) throws FacesException;
}
