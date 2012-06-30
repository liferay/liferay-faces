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
package com.liferay.faces.bridge.tck.el;

import java.util.Iterator;

import javax.el.ELContext;
import javax.el.ELResolver;
import javax.faces.FacesWrapper;


/**
 * @author  Neil Griffin
 */
public abstract class ELResolverWrapper extends ELResolver implements FacesWrapper<ELResolver> {

	@Override
	public Class<?> getCommonPropertyType(ELContext context, Object base) {
		return getWrapped().getCommonPropertyType(context, base);
	}

	@Override
	public Iterator<?> getFeatureDescriptors(ELContext context, Object base) {
		return getWrapped().getFeatureDescriptors(context, base);
	}

	@Override
	public Class<?> getType(ELContext context, Object base, Object property) {
		return getWrapped().getType(context, base, property);
	}

	@Override
	public Object getValue(ELContext context, Object base, Object property) {
		return getWrapped().getValue(context, base, property);
	}

	@Override
	public void setValue(ELContext context, Object base, Object property, Object value) {
		getWrapped().setValue(context, base, property, value);
	}

	public abstract ELResolver getWrapped();

	@Override
	public boolean isReadOnly(ELContext context, Object base, Object property) {
		return getWrapped().isReadOnly(context, base, property);
	}

}
