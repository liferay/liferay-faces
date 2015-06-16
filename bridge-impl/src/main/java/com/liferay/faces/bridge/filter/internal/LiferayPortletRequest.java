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

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.filter.PortletRequestWrapper;
import javax.servlet.http.HttpServletRequest;

import com.liferay.faces.util.HttpHeaders;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;

import com.liferay.portal.model.Portlet;


/**
 * This class wraps an instance of com.liferay.portlet.PortletRequestImpl and provides decorator methods that access the
 * wrapped instance via reflection in order to avoid a compile-time dependency.
 */
public class LiferayPortletRequest {

	// Private Constants
	private static final String METHOD_NAME_GET_ORIGINAL_HTTP_SERVLET_REQUEST = "getOriginalHttpServletRequest";
	private static final String METHOD_NAME_GET_PORTLET = "getPortlet";

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(LiferayPortletRequest.class);

	// Private Data Members
	private LiferayHttpServletRequest liferayHttpServletRequest;
	private Portlet portlet;
	private List<String> propertyNameList;
	private PortletRequest wrappedPortletRequest;

	public LiferayPortletRequest(PortletRequest portletRequest) {

		if (portletRequest != null) {

			while (portletRequest instanceof PortletRequestWrapper) {
				PortletRequestWrapper portletRequestWrapper = (PortletRequestWrapper) portletRequest;
				portletRequest = portletRequestWrapper.getRequest();
			}
		}

		this.wrappedPortletRequest = portletRequest;

		try {
			Method method = wrappedPortletRequest.getClass().getMethod(METHOD_NAME_GET_PORTLET, (Class[]) null);

			this.portlet = (Portlet) method.invoke(wrappedPortletRequest, (Object[]) null);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		try {
			Method method = wrappedPortletRequest.getClass().getMethod(METHOD_NAME_GET_ORIGINAL_HTTP_SERVLET_REQUEST,
					(Class[]) null);
			this.liferayHttpServletRequest = new LiferayHttpServletRequest((HttpServletRequest) method.invoke(
						wrappedPortletRequest, (Object[]) null));
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		this.propertyNameList = new ArrayList<String>();

		boolean foundIfModifiedSince = false;
		boolean foundUserAgent = false;
		Enumeration<String> propertyNames = portletRequest.getPropertyNames();

		while (propertyNames.hasMoreElements() && !foundUserAgent) {
			String propertyName = propertyNames.nextElement();
			propertyNameList.add(propertyName);

			if (HttpHeaders.IF_MODIFIED_SINCE.equals(propertyName)) {
				foundIfModifiedSince = true;
			}
			else if (HttpHeaders.USER_AGENT.equals(propertyName)) {
				foundUserAgent = true;
			}
		}

		if (!foundIfModifiedSince) {

			Enumeration<String> headerNames = liferayHttpServletRequest.getHeaderNames();

			while (headerNames.hasMoreElements() && !foundIfModifiedSince) {
				String headerName = headerNames.nextElement();
				foundIfModifiedSince = (HttpHeaders.IF_MODIFIED_SINCE.equalsIgnoreCase(headerName));
			}

			if (foundIfModifiedSince) {
				propertyNameList.add(HttpHeaders.IF_MODIFIED_SINCE);
			}
		}

		if (!foundUserAgent) {

			Enumeration<String> headerNames = liferayHttpServletRequest.getHeaderNames();

			while (headerNames.hasMoreElements() && !foundUserAgent) {
				String headerName = headerNames.nextElement();
				foundUserAgent = (HttpHeaders.USER_AGENT.equalsIgnoreCase(headerName));
			}

			if (foundUserAgent) {
				propertyNameList.add(HttpHeaders.USER_AGENT);
			}
		}
	}

	public Portlet getPortlet() {
		return portlet;
	}

	public Enumeration<String> getProperties(String name) {

		Enumeration<String> properties = wrappedPortletRequest.getProperties(name);

		if (!properties.hasMoreElements() &&
				(HttpHeaders.USER_AGENT.equals(name) || HttpHeaders.IF_MODIFIED_SINCE.equals(name))) {
			properties = liferayHttpServletRequest.getHeaders(name);
		}

		return properties;
	}

	public String getProperty(String name) {

		String property = wrappedPortletRequest.getProperty(name);

		if (property == null) {
			property = liferayHttpServletRequest.getHeader(name);
		}

		return property;
	}

	public Enumeration<String> getPropertyNames() {
		return Collections.enumeration(propertyNameList);
	}
}
