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

import javax.el.ELContext;
import javax.el.ELResolver;
import javax.faces.context.FacesContext;


/**
 * This class is designed to be an {@link ELResolverWrapper} around the Mojarra/MyFaces {@link ELResolver}
 * implementation. Its purpose is to handle special cases of the TCK.
 *
 * @author  Neil Griffin
 */
public class ELResolverTCKImpl extends ELResolverWrapper {

	// Private Constants
	private static final String FACES_CONTEXT = "facesContext";

	// Private Data Members
	private ELResolver wrappedELResolver;

	public ELResolverTCKImpl(ELResolver elResolver) {
		this.wrappedELResolver = elResolver;
	}

	@Override
	public Object getValue(ELContext context, Object base, Object property) {
		Object value = super.getValue(context, base, property);

		// TestPage203: JSF_ELTest
		if (FACES_CONTEXT.equals(property)) {

			// JSR 329 TCK Challenge: https://issues.apache.org/jira/browse/PORTLETBRIDGE-224
			value = FacesContext.getCurrentInstance();
		}

		return value;
	}

	@Override
	public ELResolver getWrapped() {
		return wrappedELResolver;
	}
}
