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

/**
 * This class provides a compatibility layer that isolates differences for JSF 2.0.
 *
 * @author  Neil Griffin
 */
public abstract class SAXHandlerFacesConfigPostImpl_2_0 extends SAXHandlerFacesConfigPostImpl_1_2 {

	public SAXHandlerFacesConfigPostImpl_2_0(boolean resolveEntities,
		BridgeConfigAttributeMap bridgeConfigAttributeMap) {
		super(resolveEntities, bridgeConfigAttributeMap);
	}
}
