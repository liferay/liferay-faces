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
package com.liferay.faces.bridge.filter;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Locale;

import javax.portlet.CacheControl;
import javax.portlet.MimeResponse;
import javax.portlet.PortletURL;
import javax.portlet.ResourceURL;
import javax.portlet.filter.PortletResponseWrapper;
import javax.portlet.filter.RenderResponseWrapper;
import javax.portlet.filter.ResourceResponseWrapper;


/**
 * This is a wrapper style decorator class that is modeled after the {@link RenderResponseWrapper} and {@link
 * ResourceResponseWrapper} found in the Portlet API.
 *
 * @author  Neil Griffin
 */
public class MimeResponseWrapper extends PortletResponseWrapper implements MimeResponse {

	public MimeResponseWrapper(MimeResponse mimeResponse) {
		super(mimeResponse);
	}

	public PortletURL createActionURL() {
		return getResponse().createActionURL();
	}

	public PortletURL createRenderURL() {
		return getResponse().createRenderURL();
	}

	public ResourceURL createResourceURL() {
		return getResponse().createResourceURL();
	}

	public void flushBuffer() throws IOException {
		getResponse().flushBuffer();
	}

	public void reset() {
		getResponse().reset();
	}

	public void resetBuffer() {
		getResponse().resetBuffer();
	}

	public int getBufferSize() {
		return getResponse().getBufferSize();
	}

	public void setBufferSize(int size) {
		getResponse().setBufferSize(size);
	}

	public CacheControl getCacheControl() {
		return getResponse().getCacheControl();
	}

	public String getCharacterEncoding() {
		return getResponse().getCharacterEncoding();
	}

	public String getContentType() {
		return getResponse().getContentType();
	}

	public void setContentType(String type) {
		getResponse().setContentType(type);
	}

	public boolean isCommitted() {
		return getResponse().isCommitted();
	}

	public Locale getLocale() {
		return getResponse().getLocale();
	}

	public OutputStream getPortletOutputStream() throws IOException {
		return getResponse().getPortletOutputStream();
	}

	@Override
	public MimeResponse getResponse() {
		return (MimeResponse) super.getResponse();
	}

	public PrintWriter getWriter() throws IOException {
		return getResponse().getWriter();
	}

}
