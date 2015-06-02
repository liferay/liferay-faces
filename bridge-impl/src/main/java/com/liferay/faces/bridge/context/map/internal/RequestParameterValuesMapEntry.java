/**
 * Copyright (c) 2000-2015 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.bridge.context.map.internal;

import com.liferay.faces.util.map.AbstractPropertyMapEntry;


/**
 * @author  Neil Griffin
 */
public class RequestParameterValuesMapEntry extends AbstractPropertyMapEntry<String[]> {

	// Private Data Members
	RequestParameterValuesMap requestParameterValuesMap;

	public RequestParameterValuesMapEntry(String key, RequestParameterValuesMap requestParameterValuesMap) {
		super(key);
		this.requestParameterValuesMap = requestParameterValuesMap;
	}

	public String[] getValue() {
		return requestParameterValuesMap.getProperty(getKey());
	}

	public String[] setValue(String[] value) {
		throw new UnsupportedOperationException();
	}
}
