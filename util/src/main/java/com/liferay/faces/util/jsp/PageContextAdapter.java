/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.util.jsp;

import java.io.IOException;
import java.util.Enumeration;

import javax.el.ELContext;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;


/**
 * This class serves as a {@link PageContext} adapter for invoking JSP {@link Tag} classes directly (outside of JSP)
 * during the execution of the JSF lifecycle.
 *
 * @author  Neil Griffin
 */
public class PageContextAdapter extends PageContext {

	// Private Data Members
	private HttpServletRequest httpServletRequest;
	private HttpServletResponse httpServletResponse;

	public PageContextAdapter(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		this.httpServletRequest = httpServletRequest;
		this.httpServletRequest = httpServletRequest;
	}

	@Override
	public Object findAttribute(String name) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void forward(String relativeUrlPath) throws ServletException, IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void handlePageException(Exception e) throws ServletException, IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void handlePageException(Throwable t) throws ServletException, IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void include(String relativeUrlPath) throws ServletException, IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void include(String relativeUrlPath, boolean flush) throws ServletException, IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void initialize(Servlet servlet, ServletRequest request, ServletResponse response,
		java.lang.String errorPageURL, boolean needsSession, int bufferSize, boolean autoFlush) throws IOException,
		IllegalStateException, IllegalArgumentException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void release() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void removeAttribute(String name) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void removeAttribute(String name, int scope) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object getAttribute(String name) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object getAttribute(String name, int scope) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setAttribute(String name, Object value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setAttribute(String name, Object value, int scope) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Enumeration<String> getAttributeNamesInScope(int scope) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getAttributesScope(String name) {
		throw new UnsupportedOperationException();
	}

	@Override
	public ELContext getELContext() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Exception getException() {
		throw new UnsupportedOperationException();
	}

	@Override
	@SuppressWarnings("deprecation")
	public javax.servlet.jsp.el.ExpressionEvaluator getExpressionEvaluator() {
		throw new UnsupportedOperationException();
	}

	@Override
	public JspWriter getOut() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object getPage() {
		throw new UnsupportedOperationException();
	}

	@Override
	public ServletRequest getRequest() {
		return httpServletRequest;
	}

	@Override
	public ServletResponse getResponse() {
		return httpServletResponse;
	}

	@Override
	public ServletConfig getServletConfig() {
		throw new UnsupportedOperationException();
	}

	@Override
	public ServletContext getServletContext() {
		throw new UnsupportedOperationException();
	}

	@Override
	public HttpSession getSession() {
		return httpServletRequest.getSession();
	}

	@Override
	@SuppressWarnings("deprecation")
	public javax.servlet.jsp.el.VariableResolver getVariableResolver() {
		throw new UnsupportedOperationException();
	}
}
