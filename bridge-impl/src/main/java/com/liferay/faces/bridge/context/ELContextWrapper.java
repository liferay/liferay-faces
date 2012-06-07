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
package com.liferay.faces.bridge.context;

import java.util.Locale;

import javax.el.ELContext;
import javax.el.ELResolver;
import javax.el.FunctionMapper;
import javax.el.VariableMapper;
import javax.faces.FacesWrapper;


/**
 * @author  Neil Griffin
 */
public abstract class ELContextWrapper extends ELContext implements FacesWrapper<ELContext> {

	@SuppressWarnings("rawtypes")
	@Override
	public void putContext(Class key, Object contextObject) {
		getWrapped().putContext(key, contextObject);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Object getContext(Class key) {
		return getWrapped().getContext(key);
	}

	@Override
	public boolean isPropertyResolved() {
		return getWrapped().isPropertyResolved();
	}

	@Override
	public ELResolver getELResolver() {
		return getWrapped().getELResolver();
	}

	@Override
	public FunctionMapper getFunctionMapper() {
		return getWrapped().getFunctionMapper();
	}

	@Override
	public Locale getLocale() {
		return getWrapped().getLocale();
	}

	@Override
	public void setLocale(Locale locale) {
		getWrapped().setLocale(locale);
	}

	@Override
	public void setPropertyResolved(boolean resolved) {
		getWrapped().setPropertyResolved(resolved);
	}

	@Override
	public VariableMapper getVariableMapper() {
		return getWrapped().getVariableMapper();
	}

	public abstract ELContext getWrapped();

}
