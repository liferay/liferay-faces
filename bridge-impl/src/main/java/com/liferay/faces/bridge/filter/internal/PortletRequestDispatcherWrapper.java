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
package com.liferay.faces.bridge.filter.internal;

import java.io.IOException;

import javax.faces.FacesWrapper;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.PortletResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;


/**
 * @author  Neil Griffin
 */
public abstract class PortletRequestDispatcherWrapper implements PortletRequestDispatcher,
	FacesWrapper<PortletRequestDispatcher> {

	public void forward(PortletRequest request, PortletResponse response) throws PortletException, IOException {
		getWrapped().forward(request, response);
	}

	public void include(RenderRequest request, RenderResponse response) throws PortletException, IOException {
		getWrapped().include(request, response);
	}

	public void include(PortletRequest request, PortletResponse response) throws PortletException, IOException {
		getWrapped().include(request, response);
	}

	public abstract PortletRequestDispatcher getWrapped();
}
