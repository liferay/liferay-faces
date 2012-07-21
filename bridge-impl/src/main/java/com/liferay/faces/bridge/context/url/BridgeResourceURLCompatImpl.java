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
package com.liferay.faces.bridge.context.url;

import com.liferay.faces.bridge.application.ResourceHandlerInnerImpl;
import com.liferay.faces.bridge.context.BridgeContext;


/**
 * @author  Neil Griffin
 */
public abstract class BridgeResourceURLCompatImpl extends BridgeURLBaseImpl implements BridgeResourceURL {

	public BridgeResourceURLCompatImpl(String url, String currentFacesViewId, BridgeContext bridgeContext) {
		super(url, currentFacesViewId, bridgeContext);
	}

	public boolean isEncodedFaces2ResourceURL() {
		return ResourceHandlerInnerImpl.isEncodedFacesResourceURL(url);
	}

	public boolean isFaces2ResourceURL() {
		return ResourceHandlerInnerImpl.isFacesResourceURL(url);
	}

}
