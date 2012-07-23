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
package com.liferay.faces.bridge.application;

import java.util.List;
import java.util.Map;

import javax.portlet.PortletMode;
import javax.portlet.WindowState;
import javax.portlet.faces.Bridge;


/**
 * @author  Neil Griffin
 */
public interface BridgeNavigationCase {

	/**
	 * Whereas the JSF API {@link NavigationCase#getParameters()} documentation states that this method returns
	 * parameters that are to be included for a navigation-case that contains a redirect, in a portlet environment it
	 * must also return parameters in the non-redirect case.
	 *
	 * @return  The {@link Map} of parameter values.
	 */
	public Map<String, List<String>> getParameters();

	/**
	 * Returns the string representation of the {@link PortletMode} associated with the {@link
	 * Bridge#PORTLET_MODE_PARAMETER} in the to-view-id value of the navigation-case.
	 */
	String getPortletMode();

	/**
	 * Returns the string representation of the {@link WindowState} associated with the {@link
	 * Bridge#PORTLET_WINDOWSTATE_PARAMETER} in the to-view-id value of the navigation-case.
	 */
	String getWindowState();
}
