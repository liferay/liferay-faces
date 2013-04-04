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

import com.liferay.faces.bridge.context.BridgeContext;
import com.liferay.faces.util.lang.StringPool;


/**
 * This class represents a relative {@link BaseURL}, meaning an implementation that simply wraps a String based URL that
 * starts with "../" and does not require encoding. The only methods that are meant to be called is {@link
 * BaseURLRelativeStringImpl#toString()} and {@link BaseURLRelativeStringImpl#write(Writer, boolean)}. All other methods
 * throw an {@link UnsupportedOperationException}.
 *
 * @author  Neil Griffin
 */
public class BaseURLRelativeStringImpl extends BaseURLNonEncodedStringImpl {

	// Private Data Members
	private String contextPath;
	private String toStringValue;

	public BaseURLRelativeStringImpl(String url, Map<String, String[]> parameterMap, BridgeContext bridgeContext) {
		super(url, parameterMap);
		this.contextPath = bridgeContext.getPortletRequest().getContextPath();
	}

	@Override
	public String toString() {

		if (toStringValue == null) {
			toStringValue = super.toString();

			if (toStringValue.startsWith(BridgeURLBaseImpl.RELATIVE_PATH_PREFIX)) {
				StringBuilder buf = new StringBuilder();
				buf.append(contextPath);
				buf.append(StringPool.FORWARD_SLASH);
				buf.append(toStringValue.substring(BridgeURLBaseImpl.RELATIVE_PATH_PREFIX.length()));
				toStringValue = buf.toString();
			}
		}

		return toStringValue;
	}

}
