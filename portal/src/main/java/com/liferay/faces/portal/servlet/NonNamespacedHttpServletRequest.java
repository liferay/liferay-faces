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
package com.liferay.faces.portal.servlet;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;

import com.liferay.taglib.ui.InputEditorTag;


/**
 * This class wraps the Liferay {@link NamespaceServletRequest} class, so that calls to {@link #setAttribute(String,
 * Object)} do not cause attribute names to be prefixed with the response namespace. This is necessary so that Liferay
 * Portal JSP tag classes will be able to set request attributes that can be picked up by JSPs. For example, the {@link
 * InputEditorTag} sets attributes that are picked up by the portal-web/docroot/html/js/editor/ckeditor.jsp page.
 *
 * @author  Neil Griffin
 */
public class NonNamespacedHttpServletRequest extends HttpServletRequestWrapper {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(NonNamespacedHttpServletRequest.class);

	// Private Constants
	private static final String AUI_FORM_USE_NAMESPACE = "aui:form:useNamespace";
	private static final String NAMESPACE_SERVLET_REQUEST_FQCN = "com.liferay.portal.servlet.NamespaceServletRequest";

	public NonNamespacedHttpServletRequest(HttpServletRequest httpServletRequest) {
		super(httpServletRequest);
	}

	@Override
	public Object getAttribute(String name) {

		if (AUI_FORM_USE_NAMESPACE.equals(name)) {

			// Note: The portal-web/docroot/html/taglib/init.jsp file asks the value of this attribute. Need to return
			// false in order to ensure that the portlet namespace is not prepended to method names and element ids.
			return Boolean.FALSE.toString();
		}
		else {
			return super.getAttribute(name);
		}
	}

	@Override
	public void setAttribute(String name, Object value) {

		Object wrappedRequest = getRequest();

		if (wrappedRequest.getClass().getName().equals(NAMESPACE_SERVLET_REQUEST_FQCN)) {

			try {

				// Calling NameSpaceServletRequest.setAttribute(String, Object, false) instead of
				// NameSpaceServletRequest.setAttribute(String, Object) will prevent the attribute name from getting
				// prefixed with the response namespace. The method must be called reflectively since the
				// NameSpaceServletRequest is packaged in portal-impl.jar and is not available at compile-time.
				Method method = wrappedRequest.getClass().getMethod("setAttribute", String.class, Object.class,
						boolean.class);
				method.invoke(wrappedRequest, name, value, false);
			}
			catch (Exception e) {
				logger.error(e);
			}
		}
		else {
			super.setAttribute(name, value);
		}
	}
}
