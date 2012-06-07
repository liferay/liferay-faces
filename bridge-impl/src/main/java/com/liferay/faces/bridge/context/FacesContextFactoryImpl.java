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

import javax.faces.FacesException;
import javax.faces.context.FacesContext;
import javax.faces.context.FacesContextFactory;
import javax.faces.lifecycle.Lifecycle;

import com.liferay.faces.bridge.BridgeConstants;
import com.liferay.faces.bridge.config.ProductMap;


/**
 * This class is necessary in order to pass the facesContextFactoryServiceProviderTest in the TCK. In addition, if the
 * TCK is detected, then it provides a special FacesContext implementation that handles special cases within the TCK.
 *
 * @author  Neil Griffin
 */
public class FacesContextFactoryImpl extends FacesContextFactory {

	// Private Constants
	private static final boolean TCK_JSR_329_DETECTED = ProductMap.getInstance().get(BridgeConstants.TCK_JSR_329)
		.isDetected();

	// Private Data Members
	private FacesContextFactory wrappedFacesContextFactory;

	public FacesContextFactoryImpl(FacesContextFactory facesContextFactory) {
		this.wrappedFacesContextFactory = facesContextFactory;
	}

	/**
	 * It was not necessary to create a {@link FacesContext} implementation required for this bridge implementation, so
	 * this method simply calls through to the wrapped factory.
	 */
	@Override
	public FacesContext getFacesContext(Object context, Object request, Object response, Lifecycle lifecycle)
		throws FacesException {

		FacesContext facesContext = wrappedFacesContextFactory.getFacesContext(context, request, response, lifecycle);

		if (TCK_JSR_329_DETECTED) {
			facesContext = new FacesContextTCKImpl(facesContext);
		}

		return facesContext;
	}

	@Override
	public FacesContextFactory getWrapped() {
		return wrappedFacesContextFactory;
	}

}
