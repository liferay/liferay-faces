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
package com.liferay.faces.bridge.context.map;

import com.liferay.faces.util.map.AbstractPropertyMapEntry;


/**
 * @author  Neil Griffin
 */
public class RequestParameterValuesMapEntryMultiPart extends AbstractPropertyMapEntry<String[]> {

	private RequestParameterMapMultiPartImpl requestParameterMapMultiPartImpl;

	public RequestParameterValuesMapEntryMultiPart(RequestParameterMapMultiPartImpl requestParameterMapMultiPartImpl,
		String key) {
		super(key);
		this.requestParameterMapMultiPartImpl = requestParameterMapMultiPartImpl;
	}

	public String[] getValue() {
		String singleValue = requestParameterMapMultiPartImpl.getProperty(getKey());
		String[] valueArray = new String[] { singleValue };

		return valueArray;
	}

	public String[] setValue(String[] value) {
		throw new UnsupportedOperationException();
	}
}
