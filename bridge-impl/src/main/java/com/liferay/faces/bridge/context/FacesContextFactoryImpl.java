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

		FacesContext facesContext = null;

		if (context instanceof PortletContext) {
			PortletContext portletContext = (PortletContext) context;
			PortletRequest portletRequest = (PortletRequest) request;
			String requestContextPath = portletRequest.getContextPath();
			PortletResponse portletResponse = (PortletResponse) response;
			ServletContext servletContext = new ServletContextAdapterImpl(portletContext, requestContextPath);
			ServletRequest servletRequest = new ServletRequestAdapterImpl(portletRequest);
			ServletResponse servletResponse = new HttpServletResponseAdapterImpl(portletResponse);
			facesContext = wrappedFacesContextFactory.getFacesContext(servletContext, servletRequest, servletResponse,
					lifecycle);
			facesContext = new FacesContextImpl(facesContext, portletContext, portletRequest, portletResponse,
					lifecycle);
		}
		else {
			facesContext = wrappedFacesContextFactory.getFacesContext(context, request, response, lifecycle);
		}

		return facesContext;
	}

	public FacesContextFactory getWrapped() {
		return wrappedFacesContextFactory;
	}

}
