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
package com.liferay.faces.bridge.filter.internal;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.filter.PortletRequestWrapper;
import javax.portlet.filter.PortletResponseWrapper;

import com.liferay.faces.bridge.container.liferay.internal.LiferayConstants;


/**
 * @author  Neil Griffin
 */
public class PortletContainerDetector {

	// Private Constants
	private static final String PLUTO_PACKAGE_NAMESPACE = "org.apache.pluto";

	/**
	 * Determines whether or not the specified {@link PortletResponse} is one created by Liferay Portal. If the
	 * specified {@link PortletResponse} is an instance of {@link javax.portlet.filter.PortletResponseWrapper} then it
	 * will work with the wrapped {@link PortletResponse}.
	 *
	 * @param   portletResponse  The current {@link PortletResponse}.
	 *
	 * @return  true if the specified portletResponse was created by Pluto.
	 */
	public static boolean isPlutoPortletResponse(PortletResponse portletResponse) {

		if (portletResponse != null) {

			while (portletResponse instanceof PortletResponseWrapper) {
				PortletResponseWrapper portletResponseWrapper = (PortletResponseWrapper) portletResponse;
				portletResponse = portletResponseWrapper.getResponse();
			}

			return portletResponse.getClass().getName().startsWith(PLUTO_PACKAGE_NAMESPACE);
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
