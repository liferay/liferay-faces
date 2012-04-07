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

import com.liferay.faces.bridge.BridgeConstants;
import com.liferay.faces.bridge.context.BridgeContext;


/**
 * This class represents a simple "encoded" {@link BaseURL}, meaning an implementation that encodes a String based URL
 * that is "internal" to the application. The only methods that are meant to be called is {@link
 * BaseURLEncodedInternalStringImpl#toString()} and {@link BaseURLEncodedInternalStringImpl#write(Writer, boolean)}. All
 * other methods throw an {@link UnsupportedOperationException}.
 *
 * @author  Neil Griffin
 */
public class BaseURLEncodedInternalStringImpl extends BaseURLEncodedStringImpl {

	// Private Data Members
	private String contextPath;
	private String toStringValue;

	/**
	 * Constructs a new instance of this class.
	 *
	 * @param  internalURL    This is a URL "internalURL" to this application, like /resource/foo.png
	 * @param  parameterMap   The current map of URL parameters.
	 * @param  bridgeContext  The current {@link BridgeContext}.
	 */
	public BaseURLEncodedInternalStringImpl(String internalURL, Map<String, String[]> parameterMap,
		BridgeContext bridgeContext) {
		super(internalURL, parameterMap, bridgeContext);
		this.contextPath = bridgeContext.getPortletRequest().getContextPath();
	}

	@Override
	public String toString() {

		if (toStringValue == null) {
			toStringValue = super.toString();

			// If the "internal" URL specified in the constructor doesn't start with the context-path, then prepend
			// the context-path.
			if (toStringValue.startsWith(BridgeConstants.CHAR_FORWARD_SLASH) &&
					!toStringValue.startsWith(contextPath)) {
				toStringValue = contextPath + toStringValue;
			}
		}

		return toStringValue;
	}

}
