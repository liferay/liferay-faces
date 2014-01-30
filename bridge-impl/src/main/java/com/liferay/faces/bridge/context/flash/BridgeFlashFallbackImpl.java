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
package com.liferay.faces.bridge.context.flash;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;


/**
 * This class is a "fallback" implementation of the JSF {@link Flash} scope that only implements enough of the scope
 * functionality to prevent errors from being thrown at runtime. In theory it should never be necessary, because the JSF
 * runtime (Mojarra or MyFaces) provide their own implementations. See the {@link BridgeFlashFactory} class for more
 * information.
 *
 * @author  Neil Griffin
 */
public class BridgeFlashFallbackImpl extends BridgeFlash {

	// Private Data Members
	private boolean keepMessages = false;
	private HashMap<String, Object> hashMap = new HashMap<String, Object>();
	private boolean redirect = false;

	public void clear() {
		hashMap.clear();
	}

	public boolean containsKey(Object key) {
		return hashMap.containsKey(key);
	}

	public boolean containsValue(Object value) {
		return hashMap.containsValue(value);
	}

	@Override
	public void doPostPhaseActions(FacesContext facesContext) {
		// no-op
	}

	@Override
	public void doPrePhaseActions(FacesContext facesContext) {
		// no-op
	}

	public Set<java.util.Map.Entry<String, Object>> entrySet() {
		return hashMap.entrySet();
	}

	public Object get(Object key) {
		return hashMap.get(key);
	}

	@Override
	public void keep(String key) {
		// TODO Auto-generated method stub
	}

	public Set<String> keySet() {
		return hashMap.keySet();
	}

	public Object put(String key, Object value) {
		return hashMap.put(key, value);
	}

	public void putAll(Map<? extends String, ? extends Object> t) {
		hashMap.putAll(t);
	}

	@Override
	public void putNow(String key, Object value) {
		// no-op
	}

	public Object remove(Object key) {
		return hashMap.remove(key);
	}

	public int size() {
		return hashMap.size();
	}

	public Collection<Object> values() {
		return hashMap.values();
	}

	@Override
	public boolean isServletResponseRequired() {
		return false;
	}

	@Override
	public void setKeepMessages(boolean newValue) {
		this.keepMessages = newValue;
	}

	@Override
	public void setRedirect(boolean newValue) {
		this.redirect = newValue;
	}

	@Override
	public boolean isKeepMessages() {
		return keepMessages;
	}

	@Override
	public boolean isRedirect() {
		return redirect;
	}

	public boolean isEmpty() {
		return hashMap.isEmpty();
	}
}
