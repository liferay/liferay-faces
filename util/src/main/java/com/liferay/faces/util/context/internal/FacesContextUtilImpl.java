/**
 * Copyright (c) 2000-2015 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.util.context.internal;

import javax.faces.context.FacesContext;
import javax.faces.context.FacesContextWrapper;

import com.liferay.faces.util.context.FacesRequestContext;
import com.liferay.faces.util.context.FacesRequestContextFactory;
import com.liferay.faces.util.factory.FactoryExtensionFinder;


/**
 * @author  Kyle Stiemann
 */
public class FacesContextUtilImpl extends FacesContextWrapper {

	// Private Members
	private FacesContext wrappedFacesContext;

	FacesContextUtilImpl(FacesContext facesContext) {

		this.wrappedFacesContext = facesContext;

		FacesRequestContextFactory facesRequestContextFactory = (FacesRequestContextFactory) FactoryExtensionFinder
			.getFactory(FacesRequestContextFactory.class);
		FacesRequestContext facesRequestContext = facesRequestContextFactory.getFacesRequestContext();
		FacesRequestContext.setCurrentInstance(facesRequestContext);
	}

	@Override
	public void release() {
		FacesRequestContext.getCurrentInstance().release();
		super.release();
	}

	@Override
	public FacesContext getWrapped() {
		return wrappedFacesContext;
	}
}
