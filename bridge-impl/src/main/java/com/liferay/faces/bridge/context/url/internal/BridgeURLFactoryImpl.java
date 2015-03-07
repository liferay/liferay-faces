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
package com.liferay.faces.bridge.context.url.internal;

import java.util.List;
import java.util.Map;

import com.liferay.faces.bridge.filter.internal.PortletContainerDetector;
import com.liferay.faces.bridge.context.BridgeContext;
import com.liferay.faces.bridge.context.url.BridgeActionURL;
import com.liferay.faces.bridge.context.url.BridgePartialActionURL;
import com.liferay.faces.bridge.context.url.BridgeRedirectURL;
import com.liferay.faces.bridge.context.url.BridgeResourceURL;
import com.liferay.faces.bridge.context.url.BridgeURLFactory;
import com.liferay.faces.bridge.context.url.liferay.internal.BridgeResourceURLLiferayImpl;


/**
 * @author  Neil Griffin
 */
public class BridgeURLFactoryImpl extends BridgeURLFactory {

	@Override
	public BridgeActionURL getBridgeActionURL(BridgeContext bridgeContext, String url, String viewId) {
		return new BridgeActionURLImpl(bridgeContext, url, viewId);
	}

	@Override
	public BridgePartialActionURL getBridgePartialActionURL(BridgeContext bridgeContext, String url, String viewId) {
		return new BridgePartialActionURLImpl(bridgeContext, url, viewId);
	}

	@Override
	public BridgeRedirectURL getBridgeRedirectURL(BridgeContext bridgeContext, String url,
		Map<String, List<String>> parameters, String viewId) {
		return new BridgeRedirectURLImpl(bridgeContext, url, parameters, viewId);
	}

	@Override
	public BridgeResourceURL getBridgeResourceURL(BridgeContext bridgeContext, String url, String viewId) {

		if (PortletContainerDetector.isLiferayPortletRequest(bridgeContext.getPortletRequest())) {
			return new BridgeResourceURLLiferayImpl(bridgeContext, url, viewId);
		}
		else {
			return new BridgeResourceURLImpl(bridgeContext, url, viewId);
		}
	}

	public BridgeURLFactory getWrapped() {

		// Since this is the factory instance provided by the bridge, it will never wrap another factory.
		return null;
	}

}
