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
package com.liferay.faces.test.hooks;

/**
 * @author  Neil Griffin
 */
public class PortalPage {

	private String name;
	private String[] portletNames;

	public PortalPage(String name, String portletName) {
		this.name = name;
		this.portletNames = new String[] { portletName };
	}

	public PortalPage(String name, String[] portletNames) {
		this.name = name;
		this.portletNames = portletNames;
	}

	public String getName() {
		return name;
	}

	public String[] getPortletNames() {
		return portletNames;
	}
}
