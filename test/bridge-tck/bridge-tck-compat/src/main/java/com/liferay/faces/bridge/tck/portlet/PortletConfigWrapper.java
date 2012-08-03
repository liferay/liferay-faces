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

import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.xml.namespace.QName;


/**
 * @author  Neil Griffin
 */

public class PortletConfigWrapper implements PortletConfig {

	private PortletConfig wrappedPortletConfig;

	public PortletConfigWrapper(PortletConfig portletConfig) {
		this.wrappedPortletConfig = portletConfig;
	}

	public Map<String, String[]> getContainerRuntimeOptions() {
		return wrappedPortletConfig.getContainerRuntimeOptions();
	}

	public String getDefaultNamespace() {
		return wrappedPortletConfig.getDefaultNamespace();
	}

	public String getInitParameter(String arg0) {
		return wrappedPortletConfig.getInitParameter(arg0);
	}

	public Enumeration<String> getInitParameterNames() {
		return wrappedPortletConfig.getInitParameterNames();
	}

	public PortletContext getPortletContext() {
		return wrappedPortletConfig.getPortletContext();
	}

	public String getPortletName() {

		String portletName = wrappedPortletConfig.getPortletName();

		// Example: Transform "chapter5_2TestsisPostbackTestportlet" to "chapter5_2Tests-isPostbackTest-portlet"
		portletName = portletName.replaceFirst("Tests", "Tests-");
		portletName = portletName.replaceAll("portlet$", "-portlet");

		return portletName;
	}

	public Enumeration<QName> getProcessingEventQNames() {
		return wrappedPortletConfig.getProcessingEventQNames();
	}

	public Enumeration<String> getPublicRenderParameterNames() {
		return wrappedPortletConfig.getPublicRenderParameterNames();
	}

	public Enumeration<QName> getPublishingEventQNames() {
		return wrappedPortletConfig.getPublishingEventQNames();
	}

	public ResourceBundle getResourceBundle(Locale arg0) {
		return wrappedPortletConfig.getResourceBundle(arg0);
	}

	public Enumeration<Locale> getSupportedLocales() {
		return wrappedPortletConfig.getSupportedLocales();
	}

}
