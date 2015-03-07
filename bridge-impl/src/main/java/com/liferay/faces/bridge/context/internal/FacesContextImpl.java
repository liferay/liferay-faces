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
package com.liferay.faces.bridge.context.internal;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import com.liferay.faces.util.context.FacesContextWrapper;


/**
 * @author  Neil Griffin
 */
public class FacesContextImpl extends FacesContextWrapper {

	// Private Data Members
	private ExternalContext externalContext;
	private FacesContext wrappedFacesContext;

	public FacesContextImpl(FacesContext facesContext, ExternalContext externalContext) {

		this.wrappedFacesContext = facesContext;
		this.externalContext = externalContext;
		setCurrentInstance(this);
	}

	@Override
	public ExternalContext getExternalContext() {
		return externalContext;
	}

	@Override
	public FacesContext getWrapped() {
		return wrappedFacesContext;
	}

}
