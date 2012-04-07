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

import com.liferay.faces.bridge.context.BridgeContext;


/**
 * This class represents a simple "encoded" {@link BaseURL}, meaning an implementation that encodes a String based URL
 * that is "external" to the application. Since this class doesn't override any methods in the superclass it is
 * essentially a marker-class for code readability. The only methods that are meant to be called is {@link
 * BaseURLEncodedExternalStringImpl#toString()} and {@link BaseURLEncodedExternalStringImpl#write(Writer, boolean)}. All
 * other methods throw an {@link UnsupportedOperationException}.
 *
 * @author  Neil Griffin
 */
public class BaseURLEncodedExternalStringImpl extends BaseURLEncodedStringImpl {

	/**
	 * Constructs a new instance of this class.
	 *
	 * @param  externalURL    This is a URL "external" to this application, like http://www.foo.bar/foo.png
	 * @param  parameterMap   The current map of URL parameters.
	 * @param  bridgeContext  The current {@link BridgeContext}.
	 */
	public BaseURLEncodedExternalStringImpl(String externalURL, Map<String, String[]> parameterMap,
		BridgeContext bridgeContext) {

		super(externalURL, parameterMap, bridgeContext);
	}

}
