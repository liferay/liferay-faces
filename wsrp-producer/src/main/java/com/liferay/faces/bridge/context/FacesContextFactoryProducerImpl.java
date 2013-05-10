/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import javax.faces.FacesException;
import javax.faces.context.FacesContext;
import javax.faces.context.FacesContextFactory;
import javax.faces.lifecycle.Lifecycle;


/**
 * @author  Neil Griffin
 */
public class FacesContextFactoryProducerImpl extends FacesContextFactory {

	// Private Data Members
	private FacesContextFactory wrappedFacesContextFactory;

	public FacesContextFactoryProducerImpl(FacesContextFactory facesContextFactory) {
		this.wrappedFacesContextFactory = facesContextFactory;
	}

	@Override
	public FacesContext getFacesContext(Object context, Object request, Object response, Lifecycle lifecycle)
		throws FacesException {

		return new FacesContextProducerImpl(wrappedFacesContextFactory.getFacesContext(context, request, response,
					lifecycle), request);
	}

}
