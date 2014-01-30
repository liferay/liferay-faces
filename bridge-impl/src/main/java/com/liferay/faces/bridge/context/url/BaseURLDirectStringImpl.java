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

import java.io.Writer;
import java.util.Map;

import javax.portlet.BaseURL;
import javax.portlet.PortletRequest;

import com.liferay.faces.util.lang.StringPool;


/**
 * This class represents a simple "direct" {@link BaseURL}, meaning an implementation that wraps a path and constructs
 * an absolute path URL based on the scheme, server name, and port found in the {@link PortletRequest}. The only methods
 * that are meant to be called is {@link BaseURLDirectStringImpl#toString()} and {@link
 * BaseURLDirectStringImpl#write(Writer, boolean)}. All other methods throw an {@link UnsupportedOperationException}.
 *
 * @author  Neil Griffin
 */
public class BaseURLDirectStringImpl extends BaseURLNonEncodedStringImpl {

	// Private Data Members
	private String path;
	private PortletRequest portletRequest;

	public BaseURLDirectStringImpl(String url, Map<String, String[]> parameterMap, String path,
		PortletRequest portletRequest) {
		super(url, parameterMap);
		this.path = path;
		this.portletRequest = portletRequest;
	}

	@Override
	public String toString() {

		StringBuilder buf = new StringBuilder();
		buf.append(portletRequest.getScheme());
		buf.append(StringPool.COLON);
		buf.append(StringPool.FORWARD_SLASH);
		buf.append(StringPool.FORWARD_SLASH);
		buf.append(portletRequest.getServerName());
		buf.append(StringPool.COLON);
		buf.append(portletRequest.getServerPort());
		buf.append(path);

		String queryString = getQuery();

		if (queryString.length() > 0) {
			buf.append(StringPool.QUESTION);
			buf.append(queryString);
		}

		return buf.toString();
	}

}
