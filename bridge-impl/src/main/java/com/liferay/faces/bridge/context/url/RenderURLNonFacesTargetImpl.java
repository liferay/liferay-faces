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

import java.net.MalformedURLException;

import javax.portlet.faces.Bridge;

import com.liferay.faces.bridge.context.BridgeContext;


/**
 * @author  Neil Griffin
 */
public class RenderURLNonFacesTargetImpl extends RenderURLFacesTargetImpl {

	public RenderURLNonFacesTargetImpl(String url, String contextRelativePath, String portletMode, String windowState,
		boolean secure, BridgeContext bridgeContext) throws MalformedURLException {
		super(url, portletMode, windowState, secure, bridgeContext);
		getWrapped().setParameter(Bridge.NONFACES_TARGET_PATH_PARAMETER, contextRelativePath);
	}

}
