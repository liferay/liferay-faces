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

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import javax.faces.FacesWrapper;


/**
 * @author  Neil Griffin
 */
public abstract class BridgeRequestScopeCacheWrapper implements BridgeRequestScopeCache,
	FacesWrapper<BridgeRequestScopeCache> {

	public void clear() {
		getWrapped().clear();
	}

	public boolean containsKey(Object key) {
		return getWrapped().containsKey(key);
	}

	public boolean containsValue(Object value) {
		return getWrapped().containsValue(value);
	}

	public Set<java.util.Map.Entry<String, BridgeRequestScope>> entrySet() {
		return getWrapped().entrySet();
	}

	public BridgeRequestScope get(Object key) {
		return getWrapped().get(key);
	}

	public Set<String> keySet() {
		return getWrapped().keySet();
	}

	public BridgeRequestScope put(String key, BridgeRequestScope value) {
		return getWrapped().put(key, value);
	}

	public void putAll(Map<? extends String, ? extends BridgeRequestScope> m) {
		getWrapped().putAll(m);
	}

	public BridgeRequestScope remove(Object key) {
		return getWrapped().remove(key);
	}

	public int size() {
		return getWrapped().size();
	}

	public Collection<BridgeRequestScope> values() {
		return getWrapped().values();
	}

	public abstract BridgeRequestScopeCache getWrapped();

	public boolean isEmpty() {
		return getWrapped().isEmpty();
	}
}
