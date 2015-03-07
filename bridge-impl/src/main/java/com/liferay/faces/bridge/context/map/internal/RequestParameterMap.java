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

import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;

import com.liferay.faces.util.context.map.FacesRequestParameterMap;
import com.liferay.faces.util.map.AbstractPropertyMap;
import com.liferay.faces.util.map.AbstractPropertyMapEntry;


/**
 * @author  Neil Griffin
 */
public class RequestParameterMap extends AbstractPropertyMap<String> implements Map<String, String> {

	// Private Data Members
	private FacesRequestParameterMap facesRequestParameterMap;

	public RequestParameterMap(FacesRequestParameterMap facesRequestParameterMap) {
		this.facesRequestParameterMap = facesRequestParameterMap;
	}

	@Override
	public boolean containsKey(Object key) {
		return facesRequestParameterMap.containsKey(key);
	}

	@Override
	protected AbstractPropertyMapEntry<String> createPropertyMapEntry(String name) {
		return new RequestParameterMapEntry(name, this);
	}

	@Override
	protected void removeProperty(String name) {
		throw new UnsupportedOperationException();
	}

	@Override
	protected String getProperty(String name) {
		return facesRequestParameterMap.getFirst(name);
	}

	@Override
	protected void setProperty(String name, String value) {
		throw new UnsupportedOperationException();
	}

	@Override
	protected Enumeration<String> getPropertyNames() {
		return Collections.enumeration(facesRequestParameterMap.keySet());
	}
}
