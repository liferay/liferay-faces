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
package com.liferay.faces.bridge.context.url;

import java.util.List;
import java.util.Map;

import com.liferay.faces.bridge.context.BridgeContext;
import com.liferay.faces.util.factory.FactoryExtension;


/**
 * @author  Neil Griffin
 */
public abstract class BridgeURLFactory implements FactoryExtension<BridgeURLFactory> {

	public abstract BridgeActionURL getBridgeActionURL(String url, String currentFacesViewId,
		BridgeContext bridgeContext);

	public abstract BridgeRedirectURL getBridgeRedirectURL(String url, Map<String, List<String>> parameters,
		String currentFacesViewId, BridgeContext bridgeContext);

	public abstract BridgeResourceURL getBridgeResourceURL(String url, String currentFacesViewId,
		BridgeContext bridgeContext);
}
