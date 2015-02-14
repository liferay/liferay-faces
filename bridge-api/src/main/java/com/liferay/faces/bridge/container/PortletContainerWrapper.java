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
package com.liferay.faces.bridge.container;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import javax.faces.FacesWrapper;
import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.PortletURL;
import javax.portlet.ResourceURL;


/**
 * @author  Neil Griffin
 */
public abstract class PortletContainerWrapper implements PortletContainer, FacesWrapper<PortletContainer> {

	public PortletURL createActionURL(String fromURL) throws MalformedURLException {
		return getWrapped().createActionURL(fromURL);
	}

	public ResourceURL createPartialActionURL(String fromURL) throws MalformedURLException {
		return getWrapped().createPartialActionURL(fromURL);
	}

	public PortletURL createRedirectURL(String fromURL, Map<String, List<String>> parameters)
		throws MalformedURLException {
		return getWrapped().createRedirectURL(fromURL, parameters);
	}

	public PortletURL createRenderURL(String fromURL) throws MalformedURLException {
		return getWrapped().createRenderURL(fromURL);
	}

	public ResourceURL createResourceURL(String fromURL) throws MalformedURLException {
		return getWrapped().createResourceURL(fromURL);
	}

	public void maintainRenderParameters(EventRequest eventRequest, EventResponse eventResponse) {
		getWrapped().maintainRenderParameters(eventRequest, eventResponse);
	}

	@Deprecated
	public void redirect(String url) throws IOException {

		// noinspection deprecation
		getWrapped().redirect(url);
	}

	public String[] getHeader(String name) {
		return getWrapped().getHeader(name);
	}

	public long getHttpServletRequestDateHeader(String name) {
		return getWrapped().getHttpServletRequestDateHeader(name);
	}

	public abstract PortletContainer getWrapped();
}
