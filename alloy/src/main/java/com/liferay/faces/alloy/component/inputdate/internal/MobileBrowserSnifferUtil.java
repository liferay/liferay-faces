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
package com.liferay.faces.alloy.component.inputdate.internal;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.liferay.faces.util.lang.StringPool;
import com.liferay.faces.util.portal.PortalUtil;
import com.liferay.faces.util.product.ProductConstants;
import com.liferay.faces.util.product.ProductMap;


/**
 * @author  Kyle Stiemann
 */
public class MobileBrowserSnifferUtil {

	private static final boolean LIFERAY_FACES_BRIDGE_DETECTED = ProductMap.getInstance().get(
			ProductConstants.LIFERAY_FACES_BRIDGE).isDetected();
	private static final boolean LIFERAY_PORTAL_DETECTED = ProductMap.getInstance().get(ProductConstants.LIFERAY_PORTAL)
		.isDetected();
	public static final String USER_AGENT = "User-Agent";

	public static boolean isMobile(HttpServletRequest httpServletRequest) {

		String userAgent = StringPool.BLANK;

		if (httpServletRequest != null) {

			userAgent = String.valueOf(httpServletRequest.getAttribute(USER_AGENT));

			if ((userAgent == null) || userAgent.trim().equals("null")) {

				userAgent = httpServletRequest.getHeader(USER_AGENT);

				if (userAgent != null) {
					userAgent = userAgent.toLowerCase();
				}
				else {
					userAgent = StringPool.BLANK;
				}
			}
		}

		return (userAgent.contains("mobile") || (userAgent.contains("android") && userAgent.contains("nexus")));
	}

	public static boolean isMobile(FacesContext facesContext) {

		boolean mobile = false;

		if (LIFERAY_PORTAL_DETECTED) {

			HttpServletRequest httpServletRequest = PortalUtil.getHttpServeletRequest(
					facesContext.getExternalContext());
			mobile = com.liferay.faces.util.client.BrowserSnifferUtil.isMobile(httpServletRequest);
		}
		else if (LIFERAY_FACES_BRIDGE_DETECTED) {

		}
		else {
			HttpServletRequest httpServletRequest = (HttpServletRequest) facesContext.getExternalContext().getRequest();
			mobile = isMobile(httpServletRequest);
		}

		return mobile;
	}
}
