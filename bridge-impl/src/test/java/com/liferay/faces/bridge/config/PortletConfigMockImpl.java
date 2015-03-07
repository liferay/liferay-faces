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
package com.liferay.faces.bridge.config;

import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.xml.namespace.QName;


/**
 * @author  Neil Griffin
 */
public class PortletConfigMockImpl implements PortletConfig {

	@Override
	public Map<String, String[]> getContainerRuntimeOptions() {
		return null;
	}

	@Override
	public String getDefaultNamespace() {
		return null;
	}

	@Override
	public String getInitParameter(String name) {
		return null;
	}

	@Override
	public Enumeration<String> getInitParameterNames() {
		return null;
	}

	@Override
	public PortletContext getPortletContext() {
		return null;
	}

	@Override
	public String getPortletName() {
		return null;
	}

	@Override
	public Enumeration<QName> getProcessingEventQNames() {
		return null;
	}

	@Override
	public Enumeration<String> getPublicRenderParameterNames() {
		return null;
	}

	@Override
	public Enumeration<QName> getPublishingEventQNames() {
		return null;
	}

	@Override
	public ResourceBundle getResourceBundle(Locale locale) {
		return null;
	}

	@Override
	public Enumeration<Locale> getSupportedLocales() {
		return null;
	}
}
