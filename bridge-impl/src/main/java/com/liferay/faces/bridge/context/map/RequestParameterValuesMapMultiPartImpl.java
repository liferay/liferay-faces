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
package com.liferay.faces.bridge.context.map;

import java.util.Enumeration;

import com.liferay.faces.util.map.AbstractPropertyMapEntry;


/**
 * @author  Neil Griffin
 */
public class RequestParameterValuesMapMultiPartImpl extends RequestParameterValuesMap {

	// Private Data Members
	private RequestParameterMapMultiPartImpl requestParameterMapMultiPartImpl;

	public RequestParameterValuesMapMultiPartImpl(RequestParameterMapMultiPartImpl requestParameterMapMultiPartImpl) {
		this.requestParameterMapMultiPartImpl = requestParameterMapMultiPartImpl;
	}

	@Override
	protected AbstractPropertyMapEntry<String[]> createPropertyMapEntry(String name) {
		return new RequestParameterValuesMapEntryMultiPart(requestParameterMapMultiPartImpl, name);
	}

	@Override
	protected void removeProperty(String name) {
		requestParameterMapMultiPartImpl.removeProperty(name);
	}

	@Override
	protected String[] getProperty(String name) {
		String propertySingleValue = requestParameterMapMultiPartImpl.getProperty(name);
		String[] propertyValueArray = new String[] { propertySingleValue };

		return propertyValueArray;
	}

	@Override
	protected void setProperty(String name, String[] value) {
		String propertySingleValue = null;

		if ((value != null) && (value.length > 0)) {
			propertySingleValue = value[0];
		}

		requestParameterMapMultiPartImpl.setProperty(name, propertySingleValue);
	}

	@Override
	protected Enumeration<String> getPropertyNames() {
		return requestParameterMapMultiPartImpl.getPropertyNames();
	}

}
