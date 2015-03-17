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

import com.liferay.faces.bridge.context.BridgeContext;
import com.liferay.faces.bridge.context.url.BridgeResourceURL;
import com.liferay.faces.bridge.context.url.BridgeURI;
import com.liferay.faces.bridge.context.url.BridgeURL;
import com.liferay.faces.bridge.context.url.BridgeURLFactory;
import com.liferay.faces.bridge.context.url.internal.liferay.BridgeRedirectURLLiferayImpl;
import com.liferay.faces.util.product.ProductConstants;
import com.liferay.faces.util.product.ProductMap;


/**
 * @author  Neil Griffin
 */
public class BridgeURLFactoryImpl extends BridgeURLFactory {

	// Private Constants
	private static final boolean LIFERAY_PORTAL_DETECTED = ProductMap.getInstance().get(ProductConstants.LIFERAY_PORTAL)
		.isDetected();

	@Override
	public BridgeURL getBridgeActionURL(BridgeContext bridgeContext, BridgeURI bridgeURI, String viewId) {
		return new BridgeActionURLImpl(bridgeContext, bridgeURI, viewId);
	}

	@Override
	public BridgeURL getBridgeBookmarkableURL(BridgeContext bridgeContext, BridgeURI bridgeURI,
		Map<String, List<String>> parameters, String viewId) {
		return new BridgeBookmarkableURLImpl(bridgeContext, bridgeURI, parameters, viewId);
	}

	@Override
	public BridgeURL getBridgePartialActionURL(BridgeContext bridgeContext, BridgeURI bridgeURI, String viewId) {
		return new BridgePartialActionURLImpl(bridgeContext, bridgeURI, viewId);
	}

	@Override
	public BridgeURL getBridgeRedirectURL(BridgeContext bridgeContext, BridgeURI bridgeURI,
		Map<String, List<String>> parameters, String viewId) {

		if (LIFERAY_PORTAL_DETECTED) {
			return new BridgeRedirectURLLiferayImpl(bridgeContext, bridgeURI, parameters, viewId);
		}
		else {
			return new BridgeRedirectURLImpl(bridgeContext, bridgeURI, parameters, viewId);
		}
	}

	@Override
	public BridgeResourceURL getBridgeResourceURL(BridgeContext bridgeContext, BridgeURI bridgeURI, String viewId) {
		return new BridgeResourceURLImpl(bridgeContext, bridgeURI, viewId);
	}

	public BridgeURLFactory getWrapped() {

		// Since this is the factory instance provided by the bridge, it will never wrap another factory.
		return null;
	}

}
