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
package com.liferay.faces.bridge.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

import javax.faces.FacesWrapper;
import javax.portlet.MimeResponse;
import javax.portlet.PortletResponse;
import javax.portlet.ResourceResponse;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * This class provides an {@link HttpServletResponse} adapter/wrapper around the current {@link PortletResponse}.
 * Typical usage is to hack-around Servlet-API dependencies in JSF implementations.
 *
 * @author  Neil Griffin
 */
public class HttpServletResponseAdapter extends HttpServletResponse_3_0_Adapter
	implements FacesWrapper<PortletResponse> {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(HttpServletResponseAdapter.class);

	// Private Data Members
	private PortletResponse wrappedPortletResponse;
	private Locale requestLocale;

	public HttpServletResponseAdapter(PortletResponse portletResponse, Locale requestLocale) {
		this.wrappedPortletResponse = portletResponse;
	}

	public void addCookie(Cookie cookie) {
		getWrapped().addProperty(cookie);
	}

	public void addDateHeader(String name, long value) {
		// ignore / no-op
	}

	public void addHeader(String name, String value) {
		// ignore / no-op
	}

	public void addIntHeader(String name, int value) {
		// ignore / no-op
	}

	public boolean containsHeader(String name) {
		throw new UnsupportedOperationException();
	}

	public String encodeRedirectURL(String url) {
		throw new UnsupportedOperationException();
	}

	public String encodeRedirectUrl(String url) {
		throw new UnsupportedOperationException();
	}

	public String encodeURL(String url) {
		return getWrapped().encodeURL(url);
	}

	public String encodeUrl(String url) {
		return getWrapped().encodeURL(url);
	}

	public void flushBuffer() throws IOException {
		PortletResponse portletResponse = getWrapped();

		if (portletResponse instanceof MimeResponse) {
			MimeResponse mimeResponse = (MimeResponse) portletResponse;
			mimeResponse.flushBuffer();
		}
		else {
			throw new UnsupportedOperationException();
		}
	}

	public void reset() {
		PortletResponse portletResponse = getWrapped();

		if (portletResponse instanceof MimeResponse) {
			MimeResponse mimeResponse = (MimeResponse) portletResponse;
			mimeResponse.reset();
		}
		else {
			throw new UnsupportedOperationException();
		}
	}

	public void resetBuffer() {

		PortletResponse portletResponse = getWrapped();

		if (portletResponse instanceof MimeResponse) {
			MimeResponse mimeResponse = (MimeResponse) portletResponse;
			mimeResponse.resetBuffer();
		}
		else {
			throw new UnsupportedOperationException();
		}
	}

	public void sendError(int sc) throws IOException {
		logger.warn("No equivalent for HttpServletResponse.sendError(int=[{0}]) for PortletResponse", sc);
	}

	public void sendError(int sc, String message) throws IOException {
		throw new UnsupportedOperationException();
	}

	public void sendRedirect(String location) throws IOException {
		throw new UnsupportedOperationException();
	}

	public int getBufferSize() {

		PortletResponse portletResponse = getWrapped();

		if (portletResponse instanceof MimeResponse) {
			MimeResponse mimeResponse = (MimeResponse) portletResponse;

			return mimeResponse.getBufferSize();
		}
		else {
			return 0;
		}
	}

	public void setBufferSize(int size) {
		throw new UnsupportedOperationException();
	}

	public String getCharacterEncoding() {

		PortletResponse portletResponse = getWrapped();

		if (portletResponse instanceof MimeResponse) {
			MimeResponse mimeResponse = (MimeResponse) portletResponse;

			return mimeResponse.getCharacterEncoding();
		}
		else {
			return null;
		}
	}

	public void setCharacterEncoding(String charset) {
		throw new UnsupportedOperationException();
	}

	public void setContentLength(int len) {
		throw new UnsupportedOperationException();
	}

	public String getContentType() {

		PortletResponse portletResponse = getWrapped();

		if (portletResponse instanceof MimeResponse) {
			MimeResponse mimeResponse = (MimeResponse) portletResponse;

			return mimeResponse.getContentType();
		}
		else {
			return null;
		}
	}

	public void setContentType(String type) {

		PortletResponse portletResponse = getWrapped();

		if (portletResponse instanceof MimeResponse) {

			MimeResponse mimeResponse = (MimeResponse) portletResponse;
			mimeResponse.setContentType(type);
		}
		else {
			throw new UnsupportedOperationException();
		}
	}

	public boolean isCommitted() {

		PortletResponse portletResponse = getWrapped();

		if (portletResponse instanceof MimeResponse) {
			MimeResponse mimeResponse = (MimeResponse) portletResponse;

			return mimeResponse.isCommitted();
		}
		else {
			return true;
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
		return requestLocale;
	}

	public void setLocale(Locale loc) {

		PortletResponse portletResponse = getWrapped();

		if (portletResponse instanceof ResourceResponse) {
			ResourceResponse resourceResponse = (ResourceResponse) portletResponse;

			resourceResponse.setLocale(loc);
		}
		else {
			// ignore / no-op
		}
	}

	public ServletOutputStream getOutputStream() throws IOException {
		return new ServletOutputStreamAdapter(getWrapped());
	}

	public void setStatus(int sc) {
		logger.warn("No equivalent for HttpServletResponse.setStatus(int=[{0}]) for PortletResponse", sc);
	}

	public void setStatus(int sc, String sm) {
		throw new UnsupportedOperationException();
	}

	public PortletResponse getWrapped() {
		return wrappedPortletResponse;
	}

	public PrintWriter getWriter() throws IOException {

		PortletResponse portletResponse = getWrapped();

		if (portletResponse instanceof MimeResponse) {
			MimeResponse mimeResponse = (MimeResponse) portletResponse;

			return mimeResponse.getWriter();
		}
		else {
			throw new UnsupportedOperationException();
		}
	}

}
