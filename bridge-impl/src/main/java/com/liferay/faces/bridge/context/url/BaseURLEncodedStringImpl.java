/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import java.io.Writer;
import java.util.Map;

import javax.portlet.BaseURL;
import javax.portlet.PortletResponse;

import com.liferay.faces.bridge.context.BridgeContext;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * This abstract class represents a simple "encoded" {@link BaseURL}, meaning an implementation that encodes a String
 * based URL. The only methods that are meant to be called is {@link BaseURLEncodedStringImpl#toString()} and {@link
 * BaseURLEncodedStringImpl#write(Writer, boolean)}. All other methods throw an {@link UnsupportedOperationException}.
 *
 * @author  Neil Griffin
 */
public abstract class BaseURLEncodedStringImpl extends BaseURLNonEncodedStringImpl {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(BaseURLEncodedStringImpl.class);

	// Private Data Members
	private PortletResponse portletResponse;

	public BaseURLEncodedStringImpl(String url, Map<String, String[]> parameterMap, BridgeContext bridgeContext) {
		super(url, parameterMap);
		this.portletResponse = bridgeContext.getPortletResponse();
	}

	@Override
	public String toString() {

		String stringValue = null;

		try {
			stringValue = super.toString();
			stringValue = portletResponse.encodeURL(stringValue);
		}
		catch (Exception e) {
			logger.error(e);
		}

		return stringValue;
	}

}
