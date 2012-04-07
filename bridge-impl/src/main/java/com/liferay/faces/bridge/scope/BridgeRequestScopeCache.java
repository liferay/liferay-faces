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
package com.liferay.faces.bridge.scope;

import java.util.Collections;
import java.util.LinkedHashMap;


/**
 * This class provides a {@link java.util.Map} style interface for managing a Least Recently Used (LRU) cache of {@link
 * BridgeRequestScope}. Note that it extends {@link LinkedHashMap} which is not synchronized. Therefore instances of
 * this class should be wrapped by calling {@link Collections#synchronizedMap(java.util.Map)}.
 *
 * @author  Neil Griffin
 */
public class BridgeRequestScopeCache extends LinkedHashMap<String, BridgeRequestScope> {

	// serialVersionUID
	private static final long serialVersionUID = 4546189667853367660L;

	// Private Data Members
	private int maxSize;

	public BridgeRequestScopeCache(int maxSize) {
		super(maxSize, 1.0f, true);
		this.maxSize = maxSize;
	}

	@Override
	protected boolean removeEldestEntry(java.util.Map.Entry<String, BridgeRequestScope> eldest) {
		return (size() > maxSize);
	}

}
