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

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

import javax.portlet.MimeResponse;
import javax.portlet.PortletResponse;
import javax.portlet.ResourceResponse;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;


/**
 * @author  Neil Griffin
 */
public class HttpServletResponseAdapterImpl implements HttpServletResponse {

	// Private Data Members
	private PortletResponse portletResponse;

	public HttpServletResponseAdapterImpl(PortletResponse portletResponse) {
		this.portletResponse = portletResponse;
	}

	public void addCookie(Cookie cookie) {
		throw new UnsupportedOperationException();
	}

	public void addDateHeader(String name, long date) {
		throw new UnsupportedOperationException();
	}

	public void addHeader(String name, String value) {
		// Ignore -- Mojarra will call this from the ExternalContextImpl constructor so can't throw
        // UnsupportedOperationException here.
	}

	public void addIntHeader(String arg0, int arg1) {
		throw new UnsupportedOperationException();
	}

	public boolean containsHeader(String name) {
		return false;
	}

	public String encodeRedirectURL(String url) {
		throw new UnsupportedOperationException();
	}

	public String encodeRedirectUrl(String url) {
		throw new UnsupportedOperationException();
	}

	public String encodeURL(String url) {
		throw new UnsupportedOperationException();
	}

	public String encodeUrl(String url) {
		throw new UnsupportedOperationException();
	}

	public void flushBuffer() throws IOException {

		if (portletResponse instanceof MimeResponse) {
			MimeResponse mimeResponse = (MimeResponse) portletResponse;
			mimeResponse.flushBuffer();
		}
	}

	public void reset() {

		if (portletResponse instanceof MimeResponse) {
			MimeResponse mimeResponse = (MimeResponse) portletResponse;
			mimeResponse.reset();
		}
	}

	public void resetBuffer() {

		if (portletResponse instanceof MimeResponse) {
			MimeResponse mimeResponse = (MimeResponse) portletResponse;
			mimeResponse.resetBuffer();
		}
	}

	public void sendError(int sc) throws IOException {
		throw new UnsupportedOperationException();
	}

	public void sendError(int sc, String msg) throws IOException {
		throw new UnsupportedOperationException();
	}

	public void sendRedirect(String location) throws IOException {
		throw new UnsupportedOperationException();
	}

	public int getBufferSize() {

		if (portletResponse instanceof MimeResponse) {
			MimeResponse mimeResponse = (MimeResponse) portletResponse;

			return mimeResponse.getBufferSize();
		}
		else {
			return 0;
		}
	}

	public void setBufferSize(int size) {

		if (portletResponse instanceof MimeResponse) {
			MimeResponse mimeResponse = (MimeResponse) portletResponse;
			mimeResponse.setBufferSize(size);
		}
	}

	public String getCharacterEncoding() {

		if (portletResponse instanceof MimeResponse) {
			MimeResponse mimeResponse = (MimeResponse) portletResponse;

			return mimeResponse.getCharacterEncoding();
		}
		else {
			return null;
		}
	}

	public void setCharacterEncoding(String encoding) {
		throw new UnsupportedOperationException();
	}

	public void setContentLength(int length) {

		if (portletResponse instanceof ResourceResponse) {
			ResourceResponse resourceResponse = (ResourceResponse) portletResponse;
			resourceResponse.setContentLength(length);
		}
	}

	public String getContentType() {

		if (portletResponse instanceof MimeResponse) {
			MimeResponse mimeResponse = (MimeResponse) portletResponse;

			return mimeResponse.getContentType();
		}
		else {
			return null;
		}
	}

	public void setContentType(String contentType) {

		if (portletResponse instanceof MimeResponse) {
			MimeResponse mimeResponse = (MimeResponse) portletResponse;
			mimeResponse.setContentType(contentType);
		}
	}

	public boolean isCommitted() {

		if (portletResponse instanceof MimeResponse) {
			MimeResponse mimeResponse = (MimeResponse) portletResponse;

			return mimeResponse.isCommitted();
		}
		else {
			return false;
		}
	}

	public void setDateHeader(String name, long date) {
		throw new UnsupportedOperationException();
	}

	public void setHeader(String name, String value) {
		throw new UnsupportedOperationException();
	}

	public void setIntHeader(String name, int value) {
		throw new UnsupportedOperationException();
	}

	public Locale getLocale() {

		if (portletResponse instanceof MimeResponse) {
			MimeResponse mimeResponse = (MimeResponse) portletResponse;

			return mimeResponse.getLocale();
		}
		else {
			return null;
		}
	}

	public void setLocale(Locale locale) {

		if (portletResponse instanceof ResourceResponse) {
			ResourceResponse resourceResponse = (ResourceResponse) portletResponse;
			resourceResponse.setLocale(locale);
		}
	}

	public ServletOutputStream getOutputStream() throws IOException {
		throw new UnsupportedOperationException();
	}

	public void setStatus(int sc) {
		throw new UnsupportedOperationException();
	}

	public void setStatus(int sc, String sm) {
		throw new UnsupportedOperationException();
	}

	public PrintWriter getWriter() throws IOException {

		if (portletResponse instanceof MimeResponse) {
			MimeResponse mimeResponse = (MimeResponse) portletResponse;

			return mimeResponse.getWriter();
		}
		else {
			return null;
		}
	}

}
