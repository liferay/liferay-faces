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
import java.util.HashMap;

import javax.portlet.BaseURL;

import com.liferay.faces.bridge.context.BridgeContext;


/**
 * @author  Neil Griffin
 */
public class BridgeURLMockImpl extends BridgeURLBaseImpl {

	public BridgeURLMockImpl(String url, String currentViewId, BridgeContext bridgeContext) {
		super(url, currentViewId, bridgeContext);
	}

	@Override
	public BaseURL toBaseURL() throws MalformedURLException {
		return new BaseURLNonEncodedStringImpl(url, new HashMap<String, String[]>());
	}

}
