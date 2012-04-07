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

import java.io.Writer;
import java.util.Map;

import javax.portlet.BaseURL;
import javax.portlet.PortletRequest;

import com.liferay.faces.bridge.BridgeConstants;


/**
 * This class represents a simple "direct" {@link BaseURL}, meaning an implementation that wraps a context-relative path
 * and constructs an absolute path URL based on the scheme, server name, and port found in the {@link PortletRequest}.
 * The only methods that are meant to be called is {@link BaseURLDirectStringImpl#toString()} and {@link
 * BaseURLDirectStringImpl#write(Writer, boolean)}. All other methods throw an {@link UnsupportedOperationException}.
 *
 * @author  Neil Griffin
 */
public class BaseURLDirectStringImpl extends BaseURLNonEncodedStringImpl {

	// Private Data Members
	private String contextRelativePath;
	private PortletRequest portletRequest;

	public BaseURLDirectStringImpl(String url, Map<String, String[]> parameterMap, String contextRelativePath,
		PortletRequest portletRequest) {
		super(url, parameterMap);
		this.contextRelativePath = contextRelativePath;
		this.portletRequest = portletRequest;
	}

	@Override
	public String toString() {

		StringBuilder buf = new StringBuilder();
		buf.append(portletRequest.getScheme());
		buf.append(BridgeConstants.CHAR_COLON);
		buf.append(BridgeConstants.CHAR_FORWARD_SLASH);
		buf.append(BridgeConstants.CHAR_FORWARD_SLASH);
		buf.append(portletRequest.getServerName());
		buf.append(BridgeConstants.CHAR_COLON);
		buf.append(portletRequest.getServerPort());
		buf.append(contextRelativePath);

		String queryString = getQuery();

		if (queryString.length() > 0) {
			buf.append(BridgeConstants.CHAR_QUESTION_MARK);
			buf.append(queryString);
		}

		return buf.toString();
	}

}
