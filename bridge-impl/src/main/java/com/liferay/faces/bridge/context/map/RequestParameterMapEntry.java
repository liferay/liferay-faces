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
package com.liferay.faces.bridge.context.map;

import com.liferay.faces.util.map.AbstractPropertyMapEntry;


/**
 * @author  Neil Griffin
 */
public class RequestParameterMapEntry extends AbstractPropertyMapEntry<String> {

	// Private Data Members
	private RequestParameterMap requestParameterMap;

	public RequestParameterMapEntry(String key, RequestParameterMap requestParameterMap) {
		super(key);
		this.requestParameterMap = requestParameterMap;
	}

	public String getValue() {
		return requestParameterMap.getProperty(getKey());
	}

	public String setValue(String value) {
		throw new UnsupportedOperationException();
	}
}
