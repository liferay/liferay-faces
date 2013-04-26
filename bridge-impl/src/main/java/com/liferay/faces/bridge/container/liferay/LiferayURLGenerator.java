/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.bridge.container.liferay;

import java.util.Map;

import javax.portlet.PortletMode;
import javax.portlet.WindowState;


/**
 * This interface defines a contract for generating URLs that are compatible with Liferay Portal.
 *
 * @author  Neil Griffin
 */
public interface LiferayURLGenerator {

	/**
	 * Generates a Liferay-compatible URL with the specified additional URL parameters.
	 *
	 * @param   additionalParameterMap  Additional URL parameters.
	 *
	 * @return  The Liferay-compatible URL.
	 */
	public String generateURL(Map<String, String[]> additionalParameterMap);

	/**
	 * Generates a Liferay-compatible URL with the specified additional URL parameters and resource ID.
	 *
	 * @param   additionalParameterMap  Additional URL parameters.
	 * @param   resourceId              The resource ID.
	 *
	 * @return  The Liferay-compatible URL.
	 */
	public String generateURL(Map<String, String[]> additionalParameterMap, String resourceId);

	/**
	 * Generates a Liferay-compatible URL with the specified additional URL parameters, portlet mode, and window state.
	 *
	 * @param   additionalParameterMap  Additional URL parameters.
	 * @param   portletMode             The portlet mode.
	 * @param   windowState             The window state.
	 *
	 * @return
	 */
	public String generateURL(Map<String, String[]> additionalParameterMap, PortletMode portletMode,
		WindowState windowState);

	/**
	 * Returns the portlet lifecycle ID for type of URL generator.
	 */
	public String getPortletLifecycleId();
}
