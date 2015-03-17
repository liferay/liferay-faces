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

import com.liferay.faces.bridge.application.internal.ResourceHandlerInnerImpl;
import com.liferay.faces.bridge.context.BridgeContext;
import com.liferay.faces.bridge.context.url.BridgeResourceURL;
import com.liferay.faces.bridge.context.url.BridgeURI;


/**
 * This class provides a compatibility layer that isolates differences between JSF1 and JSF2.
 *
 * @author  Neil Griffin
 */
public abstract class BridgeResourceURLCompatImpl extends BridgeURLBase implements BridgeResourceURL {

	public BridgeResourceURLCompatImpl(BridgeContext bridgeContext, BridgeURI bridgeURI, String viewId) {
		super(bridgeContext, bridgeURI, viewId);
	}

	public boolean isEncodedFaces2ResourceURL() {
		return ResourceHandlerInnerImpl.isEncodedFacesResourceURL(bridgeURI.toString());
	}

	public boolean isFaces2ResourceURL() {
		return ResourceHandlerInnerImpl.isFacesResourceURL(bridgeURI.toString());
	}

}
