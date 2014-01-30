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
package com.liferay.faces.bridge.scope;

import java.util.HashMap;
import java.util.Map;


/**
 * @author  Neil Griffin
 */
public abstract class BridgeRequestScopeBaseImpl implements BridgeRequestScope {

	// Private Data Members
	private Map<String, Object> attributeMap;

	public BridgeRequestScopeBaseImpl() {
		this.attributeMap = new HashMap<String, Object>();
	}

	public Object getAttribute(String key) {
		return attributeMap.get(key);
	}

	public void setAttribute(String key, Object value) {
		attributeMap.put(key, value);
	}
}
