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
package com.liferay.faces.bridge.container;

import javax.portlet.PortletRequest;
import javax.portlet.filter.PortletRequestWrapper;

import com.liferay.faces.bridge.container.liferay.LiferayConstants;


/**
 * @author  Neil Griffin
 */
public class PortletContainerDetector {

	// Private Constants
	private static final String PLUTO_PACKAGE_NAMESPACE = "org.apache.pluto";

	/**
	 * Determines whether or not the specified {@link PortletRequest} is one created by Liferay Portal. If the specified
	 * {@link PortletRequest} is an instance of {@link PortletRequestWrapper} then it will work with the wrapped {@link
	 * PortletRequest}.
	 *
	 * @param   portletRequest  The current {@link PortletRequest}.
	 *
	 * @return  true if the specified portletRequest was created by Liferay Portal.
	 */
	public static boolean isLiferayPortletRequest(PortletRequest portletRequest) {

		if (portletRequest != null) {

			while (portletRequest instanceof PortletRequestWrapper) {
				PortletRequestWrapper portletRequestWrapper = (PortletRequestWrapper) portletRequest;
				portletRequest = portletRequestWrapper.getRequest();
			}

			return portletRequest.getClass().getName().startsWith(LiferayConstants.PACKAGE_NAMESPACE);
		}
		else {
			return false;
		}
	}

	/**
	 * Determines whether or not the specified {@link PortletRequest} is one created by Liferay Portal. If the specified
	 * {@link PortletRequest} is an instance of {@link PortletRequestWrapper} then it will work with the wrapped {@link
	 * PortletRequest}.
	 *
	 * @param   portletRequest  The current {@link PortletRequest}.
	 *
	 * @return  true if the specified portletRequest was created by Pluto.
	 */
	public static boolean isPlutoPortletRequest(PortletRequest portletRequest) {

		if (portletRequest != null) {

			while (portletRequest instanceof PortletRequestWrapper) {
				PortletRequestWrapper portletRequestWrapper = (PortletRequestWrapper) portletRequest;
				portletRequest = portletRequestWrapper.getRequest();
			}

			return portletRequest.getClass().getName().startsWith(PLUTO_PACKAGE_NAMESPACE);
		}
		else {
			return false;
		}
	}
}
