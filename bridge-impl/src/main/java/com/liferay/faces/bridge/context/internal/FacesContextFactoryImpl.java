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
package com.liferay.faces.bridge.context.internal;

import javax.faces.FacesException;
import javax.faces.FactoryFinder;
import javax.faces.context.ExternalContext;
import javax.faces.context.ExternalContextFactory;
import javax.faces.context.FacesContext;
import javax.faces.context.FacesContextFactory;
import javax.faces.lifecycle.Lifecycle;
import javax.servlet.ServletContext;


/**
 * @author  Neil Griffin
 */
public class FacesContextFactoryImpl extends FacesContextFactory {

	// Private Data Members
	private FacesContextFactory wrappedFacesContextFactory;

	public FacesContextFactoryImpl(FacesContextFactory facesContextFactory) {
		wrappedFacesContextFactory = facesContextFactory;
	}

	@Override
	public FacesContext getFacesContext(Object context, Object request, Object response, Lifecycle lifecycle)
		throws FacesException {

		// If the session is expiring, then return an instance of FacesContext that can function in a
		// limited manner during session expiration.
		if ((context instanceof ServletContext) && (request == null) && (response == null)) {

			ExternalContextFactory externalContextFactory = (ExternalContextFactory) FactoryFinder.getFactory(
					FactoryFinder.EXTERNAL_CONTEXT_FACTORY);

			ExternalContext externalContext = externalContextFactory.getExternalContext(context, request, response);

			return new FacesContextExpirationImpl(externalContext);
		}

		// Otherwise, delegate to the wrapped factory.
		else {
			return wrappedFacesContextFactory.getFacesContext(context, request, response, lifecycle);
		}
	}
}
