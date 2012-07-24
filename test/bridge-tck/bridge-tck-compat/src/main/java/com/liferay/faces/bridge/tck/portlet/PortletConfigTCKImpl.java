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

import javax.portlet.PortletConfig;



/**
 * This class is a wrapper around the {@link PortletConfig} implementation provided by the portlet container. Its
 * purpose is to provide an override of the {@link PortletConfig#getPortletName()} method that returns the same value
 * specified in the portlet-name element in the WEB-INF/portlet.xml descriptor. This is necessary when running the TCK
 * under Liferay Portal. For more info, see: https://issues.apache.org/jira/browse/PORTLETBRIDGE-225
 *
 * @author  Neil Griffin
 */
public class PortletConfigTCKImpl extends PortletConfigWrapper {

	// Private Data Members
	private boolean liferay = false;
	private PortletConfig wrappedPortletConfig;

	public PortletConfigTCKImpl(PortletConfig portletConfig) {
		this.wrappedPortletConfig = portletConfig;
		liferay = portletConfig.getClass().getName().startsWith("com.liferay");
	}

	@Override
	public String getPortletName() {

		String portletName = super.getPortletName();

		if (liferay) {

			// JSR 329 TCK Challenge: https://issues.apache.org/jira/browse/PORTLETBRIDGE-225
			// Example: Transform "chapter5_2TestsisPostbackTestportlet" to "chapter5_2Tests-isPostbackTest-portlet"			
			portletName = portletName.replaceFirst("Tests", "Tests-");
			portletName = portletName.replaceAll("portlet$", "-portlet");
		}

		return portletName;
	}

	@Override
	public PortletConfig getWrapped() {
		return wrappedPortletConfig;
	}
}
