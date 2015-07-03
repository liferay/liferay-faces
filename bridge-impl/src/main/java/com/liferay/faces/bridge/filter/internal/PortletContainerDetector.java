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

import javax.portlet.PortletResponse;
import javax.portlet.filter.PortletResponseWrapper;


/**
 * @author  Neil Griffin
 */
public class PortletContainerDetector {

	/**
	 * Determines whether or not the specified {@link javax.portlet.PortletResponse} is one created by Liferay Portal.
	 * If the specified {@link javax.portlet.PortletResponse} is an instance of {@link
	 * javax.portlet.filter.PortletResponseWrapper} then it will work with the wrapped {@link
	 * javax.portlet.PortletResponse}.
	 *
	 * @param   portletResponse  The current {@link javax.portlet.PortletResponse}.
	 *
	 * @return  true if the specified portletRequest was created by Liferay Portal.
	 */
	public static boolean isLiferayPortletResponse(PortletResponse portletResponse) {

		if (portletResponse != null) {

			while (portletResponse instanceof PortletResponseWrapper) {
				PortletResponseWrapper portletResponseWrapper = (PortletResponseWrapper) portletResponse;
				portletResponse = portletResponseWrapper.getResponse();
			}

			return portletResponse.getClass().getName().startsWith("com.liferay");
		}
		else {
			return false;
		}
	}

	/**
	 * Determines whether or not the specified {@link javax.portlet.PortletResponse} is one created by Liferay Portal.
	 * If the specified {@link javax.portlet.PortletResponse} is an instance of {@link
	 * javax.portlet.filter.PortletResponseWrapper} then it will work with the wrapped {@link
	 * javax.portlet.PortletResponse}.
	 *
	 * @param   portletResponse  The current {@link javax.portlet.PortletResponse}.
	 *
	 * @return  true if the specified portletResponse was created by Pluto.
	 */
	public static boolean isPlutoPortletResponse(PortletResponse portletResponse) {

		if (portletResponse != null) {

			while (portletResponse instanceof PortletResponseWrapper) {
				PortletResponseWrapper portletResponseWrapper = (PortletResponseWrapper) portletResponse;
				portletResponse = portletResponseWrapper.getResponse();
			}

			return portletResponse.getClass().getName().startsWith("org.apache.pluto");
		}
		else {
			return false;
		}
	}
}
