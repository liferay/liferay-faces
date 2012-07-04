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
package com.liferay.faces.bridge.tck.portlet;

import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.faces.FacesWrapper;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.xml.namespace.QName;


/**
 * @author  Neil Griffin
 */
public abstract class PortletConfigWrapper implements PortletConfig, FacesWrapper<PortletConfig> {

	public Map<String, String[]> getContainerRuntimeOptions() {
		return getWrapped().getContainerRuntimeOptions();
	}

	public String getDefaultNamespace() {
		return getWrapped().getDefaultNamespace();
	}

	public String getInitParameter(String arg0) {
		return getWrapped().getInitParameter(arg0);
	}

	public Enumeration<String> getInitParameterNames() {
		return getWrapped().getInitParameterNames();
	}

	public PortletContext getPortletContext() {
		return getWrapped().getPortletContext();
	}

	public String getPortletName() {
		return getWrapped().getPortletName();
	}

	public Enumeration<QName> getProcessingEventQNames() {
		return getWrapped().getProcessingEventQNames();
	}

	public Enumeration<String> getPublicRenderParameterNames() {
		return getWrapped().getPublicRenderParameterNames();
	}

	public Enumeration<QName> getPublishingEventQNames() {
		return getWrapped().getPublishingEventQNames();
	}

	public ResourceBundle getResourceBundle(Locale arg0) {
		return getWrapped().getResourceBundle(arg0);
	}

	public Enumeration<Locale> getSupportedLocales() {
		return getWrapped().getSupportedLocales();
	}

	public abstract PortletConfig getWrapped();

}
