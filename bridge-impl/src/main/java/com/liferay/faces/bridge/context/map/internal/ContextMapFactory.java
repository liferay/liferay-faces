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
package com.liferay.faces.bridge.context.map.internal;

import java.util.List;
import java.util.Map;

import javax.faces.FacesWrapper;
import javax.portlet.PortletContext;
import javax.servlet.ServletContext;

import com.liferay.faces.bridge.context.BridgeContext;
import com.liferay.faces.util.model.UploadedFile;


/**
 * This abstract class provides a contract for defining a factory that knows how to create {@link Map} instances. It is
 * inspired by the factory pattern found in the JSF API like {@link javax.faces.context.FacesContextFactory} and {@link
 * javax.faces.context.ExternalContextFactory}. By implementing the {@link javax.faces.FacesWrapper} interface, the
 * class provides implementations with the opportunity to wrap another factory (participate in a chain-of-responsibility
 * pattern). If an implementation wraps a factory, then it should provide a one-arg constructor so that the wrappable
 * factory can be passed at initialization time.
 *
 * @author  Neil Griffin
 */
public abstract class ContextMapFactory implements FacesWrapper<ContextMapFactory> {

	/**
	 * Returns a {@link Map} of application-scoped attributes stored in the underlying {@link
	 * javax.portlet.PortletContext}.
	 */
	public abstract Map<String, Object> getApplicationScopeMap(BridgeContext bridgeContext);

	/**
	 * Returns a {@link Map} of URL parameters that are found in the query-string of the current Faces view.
	 */
	public abstract Map<String, String> getFacesViewParameterMap(BridgeContext bridgeContext);

	/**
	 * Returns a {@link Map} of init-param values associated with the portlet context.
	 */
	public abstract Map<String, String> getInitParameterMap(PortletContext portletContext);

	/**
	 * Returns a {@link Map} of cookies associated with the request.
	 */
	public abstract Map<String, Object> getRequestCookieMap(BridgeContext bridgeContext);

	/**
	 * Returns a {@link Map} of request headers with a single (the first) value for each key.
	 */
	public abstract Map<String, String> getRequestHeaderMap(BridgeContext bridgeContext);

	/**
	 * Returns a {@link Map} of request headers with multiple values for each key.
	 */
	public abstract Map<String, String[]> getRequestHeaderValuesMap(BridgeContext bridgeContext);

	/**
	 * Returns a {@link Map} of request parameters with a single (the first) value for each key.
	 */
	public abstract Map<String, String> getRequestParameterMap(BridgeContext bridgeContext);

	/**
	 * Returns a {@link Map} of request parameters with multiple values for each key.
	 */
	public abstract Map<String, String[]> getRequestParameterValuesMap(BridgeContext bridgeContext);

	/**
	 * Returns a {@link Map} of request-scoped attributes stored in the underlying {@link javax.portlet.PortletRequest}.
	 */
	public abstract Map<String, Object> getRequestScopeMap(BridgeContext bridgeContext);

	/**
	 * Returns a {@link Map} of attributes stored in the underlying {@link javax.servlet.ServletContext}.
	 */
	public abstract Map<String, Object> getServletContextAttributeMap(ServletContext servletContext);

	/**
	 * Returns a {@link Map} of session-scoped attributes stored in the underlying {@link javax.portlet.PortletSession}.
	 */
	public abstract Map<String, Object> getSessionScopeMap(BridgeContext bridgeContext, int scope);

	/**
	 * Returns a {@link Map} of uploaded files.
	 */
	public abstract Map<String, List<UploadedFile>> getUploadedFileMap(BridgeContext bridgeContext);
}
