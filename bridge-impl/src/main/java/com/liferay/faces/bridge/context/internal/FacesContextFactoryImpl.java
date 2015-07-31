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

import javax.faces.FacesException;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.FacesContextFactory;
import javax.faces.lifecycle.Lifecycle;
import javax.portlet.PortletContext;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.liferay.faces.util.helper.Wrapper;


/**
 * @author  Neil Griffin
 */
public class FacesContextFactoryImpl extends FacesContextFactory implements Wrapper<FacesContextFactory> {

	// Private Data Members
	private FacesContextFactory wrappedFacesContextFactory;

	public FacesContextFactoryImpl(FacesContextFactory facesContextFactory) {
		this.wrappedFacesContextFactory = facesContextFactory;
	}

	@Override
	public FacesContext getFacesContext(Object context, Object request, Object response, Lifecycle lifecycle)
		throws FacesException {

		// If this is a request coming from the portlet container, then return an instance of FacesContext that is
		// compatible with the portlet lifecycle.
		if ((context != null) && (context instanceof PortletContext)) {
			PortletContext portletContext = (PortletContext) context;
			PortletRequest portletRequest = (PortletRequest) request;
			String requestContextPath = portletRequest.getContextPath();
			PortletResponse portletResponse = (PortletResponse) response;
			ServletContext servletContext = new ServletContextAdapterImpl(portletContext, requestContextPath);
			ServletRequest servletRequest = new ServletRequestAdapterImpl(portletRequest);
			ServletResponse servletResponse = new HttpServletResponseAdapterImpl(portletResponse);
			FacesContext wrappedFacesContext = wrappedFacesContextFactory.getFacesContext(servletContext,
					servletRequest, servletResponse, lifecycle);

			ExternalContext externalContext = new ExternalContextImpl(portletContext, portletRequest, portletResponse);

			return new FacesContextImpl(wrappedFacesContext, externalContext);
		}

		// If the specified context is a ServletContext, then it is possible that the session is expiring.
		else if ((context != null) && (context instanceof ServletContext)) {

			// If the session is expiring, then return an instance of FacesContext that can function in a limited
			// manner during session expiration.
			String requestFQCN = "";

			if (request != null) {
				requestFQCN = request.getClass().getName().toLowerCase();
			}

			String responseFQCN = "";

			if (response != null) {
				responseFQCN = response.getClass().getName().toLowerCase();
			}

			// NOTE: BridgeSessionListener creates classes named HttpServletRequestExpirationImpl and
			// HttpServletResponseExpirationImpl.
			if ((requestFQCN.length() == 0) || (responseFQCN.length() == 0) || requestFQCN.contains("expiration") ||
					responseFQCN.contains("expiration")) {

				ExternalContext externalContext = new ExternalContextExpirationImpl((ServletContext) context);

				return new FacesContextExpirationImpl(externalContext);
			}

			// Otherwise, delegate to the wrapped factory.
			else {
				return wrappedFacesContextFactory.getFacesContext(context, request, response, lifecycle);
			}
		}

		// Otherwise, delegate to the wrapped factory.
		else {
			return wrappedFacesContextFactory.getFacesContext(context, request, response, lifecycle);
		}
	}

	public FacesContextFactory getWrapped() {
		return wrappedFacesContextFactory;
	}

}
