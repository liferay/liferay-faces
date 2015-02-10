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
package com.liferay.faces.bridge.context.url.internal;

import com.liferay.faces.bridge.application.internal.ResourceHandlerInnerImpl;
import com.liferay.faces.bridge.context.BridgeContext;
import com.liferay.faces.bridge.context.url.BridgeResourceURL;


/**
 * This class provides a compatibility layer that isolates differences between JSF1 and JSF2.
 *
 * @author  Neil Griffin
 */
public abstract class BridgeResourceURLCompatImpl extends BridgeURLBaseImpl implements BridgeResourceURL {

	public BridgeResourceURLCompatImpl(BridgeContext bridgeContext, String url, String viewId) {
		super(bridgeContext, url, viewId);
	}

	public boolean isEncodedFaces2ResourceURL() {
		return ResourceHandlerInnerImpl.isEncodedFacesResourceURL(url);
	}

	public boolean isFaces2ResourceURL() {
		return ResourceHandlerInnerImpl.isFacesResourceURL(url);
	}

}
