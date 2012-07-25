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
package com.liferay.faces.bridge.application.view;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Locale;

import javax.portlet.CacheControl;
import javax.portlet.PortletMode;
import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceURL;
import javax.servlet.http.Cookie;

import org.w3c.dom.DOMException;
import org.w3c.dom.Element;


/**
 * @author  Neil Griffin
 */
public class BridgeAfterViewContentResponseRenderImpl extends BridgeAfterViewContentResponse implements RenderResponse {

	// Private Data Members
	private RenderResponse wrappedRenderResponse;

	public BridgeAfterViewContentResponseRenderImpl(RenderResponse renderResponse, Locale requestLocale) {
		super(renderResponse, requestLocale);
		this.wrappedRenderResponse = renderResponse;
	}

	public void addProperty(Cookie cookie) {
		wrappedRenderResponse.addProperty(cookie);
	}

	public void addProperty(String name, String value) {
		wrappedRenderResponse.addProperty(name, value);
	}

	public void addProperty(String name, Element value) {
		wrappedRenderResponse.addProperty(name, value);
	}

	public PortletURL createActionURL() {
		return wrappedRenderResponse.createActionURL();
	}

	public Element createElement(String name) throws DOMException {
		return wrappedRenderResponse.createElement(name);
	}

	public PortletURL createRenderURL() {
		return wrappedRenderResponse.createRenderURL();
	}

	public ResourceURL createResourceURL() {
		return wrappedRenderResponse.createResourceURL();
	}

	public CacheControl getCacheControl() {
		return wrappedRenderResponse.getCacheControl();
	}

	public String getNamespace() {
		return wrappedRenderResponse.getNamespace();
	}

	public void setNextPossiblePortletModes(Collection<PortletMode> portletModes) {
		wrappedRenderResponse.setNextPossiblePortletModes(portletModes);
	}

	public OutputStream getPortletOutputStream() throws IOException {
		return wrappedRenderResponse.getPortletOutputStream();
	}

	public void setProperty(String name, String value) {
		wrappedRenderResponse.setProperty(name, value);
	}

	public void setTitle(String title) {
		wrappedRenderResponse.setTitle(title);
	}
}
