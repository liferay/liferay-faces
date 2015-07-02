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
package com.liferay.faces.util.jsp.internal;

import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

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
import javax.servlet.jsp.tagext.Tag;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * This class serves as a {@link PageContext} adapter for invoking JSP {@link Tag} classes directly (outside of JSP)
 * during the execution of the JSF lifecycle.
 *
 * @author  Neil Griffin
 */
public class PageContextStringImpl extends PageContext {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(PageContextStringImpl.class);

	// Private Data Members
	private ApplicationScope applicationScope;
	private ELContext elContext;
	private HttpServletRequest httpServletRequest;
	private HttpServletResponse httpServletResponse;
	private HttpSession httpSession;
	private Servlet page;
	private Map<String, Object> pageScope;
	private Map<String, Object> requestScope;
	private ServletConfig servletConfig;
	private ServletContext servletContext;
	private SessionScope sessionScope;
	private JspWriter stringJspWriter;

	public PageContextStringImpl(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
		ELContext elContext, JspWriter stringJspWriter) {

		this.httpServletRequest = httpServletRequest;
		this.httpServletResponse = httpServletResponse;
		this.elContext = elContext;
		this.httpSession = httpServletRequest.getSession();
		this.servletContext = httpSession.getServletContext();
		this.servletConfig = new ServletConfigAdapter(this.servletContext);
		this.stringJspWriter = stringJspWriter;

		// Initialize scope maps
		this.applicationScope = new ApplicationScope(this.servletContext);
		this.pageScope = new HashMap<String, Object>();
		this.requestScope = new RequestScope(httpServletRequest);
		this.sessionScope = new SessionScope(httpSession);
	}

	@Override
	public Object findAttribute(String name) {

		if (name == null) {
			throw new NullPointerException();
		}
		else {
			Object value = null;

			if (pageScope.containsKey(name)) {
				value = pageScope.get(name);
			}
			else if (requestScope.containsKey(name)) {
				value = requestScope.get(name);
			}
			else if (sessionScope.containsKey(name)) {
				value = sessionScope.get(name);
			}
			else if (applicationScope.containsKey(name)) {
				value = applicationScope.get(name);
			}

			return value;
		}
	}

	@Override
	public void forward(String relativeUrlPath) throws ServletException, IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void handlePageException(Exception e) throws ServletException, IOException {
		logger.error(e);
	}

	@Override
	public void handlePageException(Throwable t) throws ServletException, IOException {
		logger.error(t);
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
	}

	@Override
	public void release() {
		applicationScope = null;
		elContext = null;
		httpServletRequest = null;
		httpServletResponse = null;
		httpSession = null;
		page = null;
		pageScope.clear();
		pageScope = null;
		requestScope = null;
		servletConfig = null;
		servletContext = null;
		sessionScope = null;
		stringJspWriter = null;
	}

	@Override
	public void removeAttribute(String name) {

		if (pageScope.containsKey(name)) {
			pageScope.remove(name);
		}

		if (requestScope.containsKey(name)) {
			requestScope.remove(name);
		}

		if (sessionScope.containsKey(name)) {
			sessionScope.remove(name);
		}

		if (applicationScope.containsKey(name)) {
			applicationScope.remove(name);
		}
	}

	@Override
	public void removeAttribute(String name, int scope) {

		if (name == null) {
			throw new NullPointerException();
		}
		else {

			if ((scope == PAGE_SCOPE)) {

				if (pageScope.containsKey(name)) {
					pageScope.remove(name);
				}
			}
			else if (scope == REQUEST_SCOPE) {

				if (requestScope.containsKey(name)) {
					requestScope.remove(name);
				}
			}
			else if (scope == SESSION_SCOPE) {

				if (sessionScope.containsKey(name)) {
					sessionScope.remove(name);
				}
			}
			else if (scope == APPLICATION_SCOPE) {

				if (applicationScope.containsKey(name)) {
					applicationScope.remove(name);
				}
			}
			else {
				throw new IllegalArgumentException("Invalid scope " + scope);
			}
		}
	}

	@Override
	public Object getAttribute(String name) {

		if (name == null) {
			throw new NullPointerException();
		}
		else {
			Object value = null;

			if (pageScope.containsKey(name)) {
				value = pageScope.get(name);
			}

			return value;
		}
	}

	@Override
	public Object getAttribute(String name, int scope) {

		if (name == null) {
			throw new NullPointerException();
		}
		else {

			Object value = null;

			if ((scope == PAGE_SCOPE)) {

				if (pageScope.containsKey(name)) {
					value = pageScope.get(name);
				}

				return value;
			}
			else if (scope == REQUEST_SCOPE) {

				if (requestScope.containsKey(name)) {
					value = requestScope.get(name);
				}

				return value;
			}
			else if (scope == SESSION_SCOPE) {

				if (sessionScope.containsKey(name)) {
					value = sessionScope.get(name);
				}

				return value;
			}
			else if (scope == APPLICATION_SCOPE) {

				if (applicationScope.containsKey(name)) {
					value = applicationScope.get(name);
				}

				return value;
			}
			else {
				throw new IllegalArgumentException("Invalid scope " + scope);
			}
		}
	}

	@Override
	public void setAttribute(String name, Object value) {

		if (name == null) {
			throw new NullPointerException();
		}
		else {

			if (value == null) {
				removeAttribute(name);
			}
			else {
				pageScope.put(name, value);
			}
		}
	}

	@Override
	public void setAttribute(String name, Object value, int scope) {

		if (name == null) {
			throw new NullPointerException();
		}
		else {

			if (value == null) {

			}
			else {

				if (scope == PAGE_SCOPE) {
					pageScope.put(name, value);
				}
				else if (scope == REQUEST_SCOPE) {
					requestScope.put(name, value);
				}
				else if (scope == SESSION_SCOPE) {
					sessionScope.put(name, value);
				}
				else if (scope == APPLICATION_SCOPE) {
					applicationScope.put(name, value);
				}
				else {
					throw new IllegalArgumentException("Invalid scope " + scope);
				}
			}
		}
	}

	@Override
	public Enumeration<String> getAttributeNamesInScope(int scope) {

		if (scope == PAGE_SCOPE) {
			return Collections.enumeration(pageScope.keySet());
		}
		else if (scope == REQUEST_SCOPE) {
			return Collections.enumeration(requestScope.keySet());
		}
		else if (scope == SESSION_SCOPE) {
			return Collections.enumeration(sessionScope.keySet());
		}
		else if (scope == APPLICATION_SCOPE) {
			return Collections.enumeration(applicationScope.keySet());
		}
		else {
			throw new IllegalArgumentException("Invalid scope " + scope);
		}
	}

	@Override
	public int getAttributesScope(String name) {

		if (name == null) {
			throw new NullPointerException();
		}
		else {
			int scope = 0;

			if (pageScope.containsKey(name)) {
				scope = PAGE_SCOPE;
			}
			else if (requestScope.containsKey(name)) {
				scope = REQUEST_SCOPE;
			}
			else if (sessionScope.containsKey(name)) {
				scope = SESSION_SCOPE;
			}
			else if (applicationScope.containsKey(name)) {
				scope = APPLICATION_SCOPE;
			}

			return scope;
		}
	}

	@Override
	public ELContext getELContext() {
		return elContext;
	}

	@Override
	public Exception getException() {
		return null;
	}

	@Override
	@SuppressWarnings("deprecation")
	public javax.servlet.jsp.el.ExpressionEvaluator getExpressionEvaluator() {
		throw new UnsupportedOperationException();
	}

	@Override
	public JspWriter getOut() {
		return stringJspWriter;
	}

	@Override
	public Object getPage() {

		if (page == null) {
			page = new PageAdapter(servletConfig);
		}

		return page;
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
		return servletConfig;
	}

	@Override
	public ServletContext getServletContext() {
		return servletContext;
	}

	@Override
	public HttpSession getSession() {
		return httpSession;
	}

	@Override
	@SuppressWarnings("deprecation")
	public javax.servlet.jsp.el.VariableResolver getVariableResolver() {
		throw new UnsupportedOperationException();
	}

}
