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
package com.liferay.faces.bridge.context;

import javax.portlet.PortletConfig;

import com.liferay.faces.bridge.config.BridgeConfig;


/**
 * @author  Neil Griffin
 */
public abstract class BridgeContext {

	// Private Static Data Members
	private static ThreadLocal<BridgeContext> instance = new ThreadLocal<BridgeContext>();

	public static BridgeContext getCurrentInstance() {
		return instance.get();
	}

	public static void setCurrentInstance(BridgeContext bridgeContext) {

		if (bridgeContext == null) {
			instance.remove();
		}
		else {
			instance.set(bridgeContext);
		}
	}

	public abstract BridgeConfig getBridgeConfig();

	public abstract PortletConfig getPortletConfig();
}
