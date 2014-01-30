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

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.portlet.faces.Bridge;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * This class provides a {@link java.util.Map} style interface for managing cache of {@link BridgeRequestScope}.
 *
 * @author  Neil Griffin
 */
public class BridgeRequestScopeCacheImpl extends ConcurrentHashMap<String, BridgeRequestScope>
	implements BridgeRequestScopeCache {

	// serialVersionUID
	private static final long serialVersionUID = 4546189667853367660L;

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(BridgeRequestScopeCacheImpl.class);

	// Private Data Members
	private int maxSize;

	public BridgeRequestScopeCacheImpl(int maxSize) {
		super();
		this.maxSize = maxSize;
	}

	@Override
	public BridgeRequestScope put(String bridgeRequestScopeId, BridgeRequestScope bridgeRequestScope) {

		// If there is a maximum size threshold for the cache, then
		if (maxSize != -1) {

			// If the threshold has been exceeded, then remove the eldest entry.
			if (super.size() > maxSize) {

				// Note: Iterating through the entire map is not the most performant algorithm for determining the
				// eldest entry, but there don't seem to be any good options for implementing an LRU feature for
				// ConcurrentHashMap without an external dependency. See:
				// http://stackoverflow.com/questions/221525/how-would-you-implement-an-lru-cache-in-java-6
				BridgeRequestScope eldestBridgeRequestScope = null;
				long oldestDate = bridgeRequestScope.getDateCreated();
				Set<Map.Entry<String, BridgeRequestScope>> entrySet = super.entrySet();

				for (Map.Entry<String, BridgeRequestScope> mapEntry : entrySet) {
					BridgeRequestScope currentBridgeRequestScope = mapEntry.getValue();

					if (currentBridgeRequestScope.getDateCreated() < oldestDate) {
						eldestBridgeRequestScope = currentBridgeRequestScope;
					}
				}

				if (eldestBridgeRequestScope != null) {
					String eldestBridgeRequestScopeId = eldestBridgeRequestScope.getId();
					super.remove(eldestBridgeRequestScopeId);
					logger.debug("Exceeded threshold of [{0}] for [{1}], removed eldest bridgeRequestScope id=[{2}]",
						maxSize, Bridge.MAX_MANAGED_REQUEST_SCOPES, eldestBridgeRequestScopeId);
				}
			}

		}

		return super.put(bridgeRequestScopeId, bridgeRequestScope);
	}
}
