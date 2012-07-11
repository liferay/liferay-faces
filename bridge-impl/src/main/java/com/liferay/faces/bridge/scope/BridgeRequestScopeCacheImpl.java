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

import java.util.concurrent.ConcurrentHashMap;


/**
 * This class provides a {@link java.util.Map} style interface for managing cache of {@link BridgeRequestScope}.
 *
 * @author  Neil Griffin
 */
public class BridgeRequestScopeCacheImpl extends ConcurrentHashMap<String, BridgeRequestScope>
	implements BridgeRequestScopeCache {

	// serialVersionUID
	private static final long serialVersionUID = 4546189667853367660L;

	public BridgeRequestScopeCacheImpl(int maxSize) {
		super(maxSize, 1.0f);
	}

}
