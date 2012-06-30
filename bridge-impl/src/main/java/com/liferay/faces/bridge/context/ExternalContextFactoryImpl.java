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
import javax.faces.context.ExternalContext;
import javax.faces.context.ExternalContextFactory;
import javax.portlet.PortletContext;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import com.liferay.faces.bridge.logging.Logger;
import com.liferay.faces.bridge.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
public class ExternalContextFactoryImpl extends ExternalContextFactory {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(ExternalContextFactoryImpl.class);

	// Private Data Members
	private ExternalContextFactory wrappedFactory;

	public ExternalContextFactoryImpl(ExternalContextFactory externalContextFactory) {
		this.wrappedFactory = externalContextFactory;
	}

	@Override
	public ExternalContext getExternalContext(Object context, Object request, Object response) throws FacesException {

		// If the specified objects are coming from a portlet container, then return an instance of the
		// bridge's ExternalContext implementation.
		//
		// NOTE: Can't use BridgeUtil.isPortletRequest() here because the FacesContext is in the process of
		// initialization.
		if (context instanceof PortletContext) {
			ExternalContext externalContext = new ExternalContextImpl((PortletContext) context,
					(PortletRequest) request, (PortletResponse) response);

			return externalContext;
		}

		// Otherwise, it is possible that a request hit the FacesServlet directly, and we should delegate
		// to the chain. Such requests can happen when JSF UI components are not properly calling
		// @link {ViewHandler.getResourceURL(FacesContext, String)} to get properly encoded resource URLs that
		// invoke the Portlet 2.0 RESOURCE_PHASE. In such a case, we just delegate to the Mojarra or MyFaces
		// wrapped ExternalContextFactory implementation to get an ExternalContext.
		else {
			logger.debug("Received a non-portlet request; delegating to wrapped ExternalContextFactory");

			return wrappedFactory.getExternalContext(context, request, response);
		}
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
