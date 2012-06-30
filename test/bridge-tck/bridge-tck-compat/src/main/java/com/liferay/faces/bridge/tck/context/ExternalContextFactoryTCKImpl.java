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
package com.liferay.faces.bridge.tck.context;

import javax.faces.FacesException;
import javax.faces.context.ExternalContext;
import javax.faces.context.ExternalContextFactory;
import javax.portlet.PortletContext;



/**
 * @author  Neil Griffin
 */
public class ExternalContextFactoryTCKImpl extends ExternalContextFactory {

	// Private Data Members
	private ExternalContextFactory wrappedFactory;

	public ExternalContextFactoryTCKImpl(ExternalContextFactory externalContextFactory) {
		this.wrappedFactory = externalContextFactory;
	}

	@Override
	public ExternalContext getExternalContext(Object context, Object request, Object response) throws FacesException {

		ExternalContext externalContext = wrappedFactory.getExternalContext(context, request, response);

		if (context instanceof PortletContext) {

			// Wap the bridge's ExternalContext implementation with a wrapper that can handle special cases of the TCK.
			externalContext = new ExternalContextTCKImpl(externalContext);
		}

		return externalContext;
	}

	/**
	 * This is an overridden method that provides the ability for the FacesWrapper decorator pattern to delegate to
	 * other ExternalContextFactory implementations that are registered.
	 */
	@Override
	public ExternalContextFactory getWrapped() {
		return wrappedFactory;
	}

}
