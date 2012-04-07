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
package com.liferay.faces.bridge.context.flash;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;


/**
 * This class decorates/wraps the {@link Flash} implementation provided by the JSF runtime. Specifically, this wrapper
 * was created to workaround some Servlet-API dependencies in some of the methods in the {@link
 * com.sun.faces.context.flash.ELFlash} implementation provided by Mojarra. See the {@link
 * com.liferay.faces.bridge.context.ExternalContextImpl#getResponse()} method for usage of the Mojarra workaround.
 *
 * @author  Neil Griffin
 */
public abstract class BridgeFlashWrapper extends BridgeFlash {

	public void clear() {
		getWrapped().clear();
	}

	public boolean containsKey(Object key) {
		return getWrapped().containsKey(key);
	}

	public boolean containsValue(Object value) {
		return getWrapped().containsValue(value);
	}

	@Override
	public void doPostPhaseActions(FacesContext facesContext) {
		getWrapped().doPostPhaseActions(facesContext);
	}

	@Override
	public void doPrePhaseActions(FacesContext facesContext) {
		getWrapped().doPrePhaseActions(facesContext);
	}

	public Set<java.util.Map.Entry<String, Object>> entrySet() {
		return getWrapped().entrySet();
	}

	public Object get(Object key) {
		return getWrapped().get(key);
	}

	@Override
	public void keep(String key) {
		getWrapped().keep(key);
	}

	public Set<String> keySet() {
		return getWrapped().keySet();
	}

	public Object put(String key, Object value) {
		return getWrapped().put(key, value);
	}

	public void putAll(Map<? extends String, ? extends Object> values) {
		getWrapped().putAll(values);
	}

	@Override
	public void putNow(String key, Object value) {
		getWrapped().putNow(key, value);
	}

	public Object remove(Object key) {
		return getWrapped().remove(key);
	}

	public int size() {
		return getWrapped().size();
	}

	public Collection<Object> values() {
		return getWrapped().values();
	}

	@Override
	public abstract boolean isServletResponseRequired();

	@Override
	public void setKeepMessages(boolean newValue) {
		getWrapped().setKeepMessages(newValue);
	}

	@Override
	public void setRedirect(boolean newValue) {
		getWrapped().setRedirect(newValue);
	}

	@Override
	public boolean isKeepMessages() {
		return getWrapped().isKeepMessages();
	}

	@Override
	public boolean isRedirect() {
		return getWrapped().isRedirect();
	}

	public abstract Flash getWrapped();

	public boolean isEmpty() {
		return getWrapped().isEmpty();
	}
}
