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
package com.liferay.faces.util.portal;

import javax.faces.context.ExternalContext;
import javax.servlet.http.HttpServletRequest;


/**
 * @author  Kyle Stiemann
 */
public class PortalUtil {

	public static HttpServletRequest getHttpServeletRequest(ExternalContext externalContext) {

		Object request = externalContext.getRequest();
		HttpServletRequest httpServletRequest;

		if (request instanceof javax.portlet.PortletRequest) {
			javax.portlet.PortletRequest portletRequest = (javax.portlet.PortletRequest) request;
			httpServletRequest = com.liferay.portal.util.PortalUtil.getHttpServletRequest(portletRequest);
		}
		else {
			httpServletRequest = (HttpServletRequest) request;
		}

		return httpServletRequest;
	}

}
