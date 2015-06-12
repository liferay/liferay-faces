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
package com.liferay.faces.bridge.client.internal;

import com.liferay.faces.util.client.BrowserSniffer;
import com.liferay.faces.util.client.BrowserSnifferFactory;
import com.liferay.faces.util.product.ProductConstants;
import com.liferay.faces.util.product.ProductMap;
import com.liferay.portal.util.PortalUtil;
import javax.faces.FacesException;
import javax.faces.context.ExternalContext;
import javax.portlet.PortletRequest;
import javax.servlet.http.HttpServletRequest;

/**
 * @author  Kyle Stiemann
 */
public class BrowserSnifferFactoryLiferayImpl extends BrowserSnifferFactory {

	// Private Constants
	private static final boolean LIFERAY_PORTAL_DETECTED = ProductMap.getInstance().get(ProductConstants.LIFERAY_PORTAL)
		.isDetected();

	// Private Data Memebers
	private BrowserSnifferFactory wrappedBrowserSnifferFactory;

	public BrowserSnifferFactoryLiferayImpl(BrowserSnifferFactory wrappedBrowserSnifferFactory) {
		this.wrappedBrowserSnifferFactory = wrappedBrowserSnifferFactory;
	}

	@Override
	public BrowserSniffer getBrowserSniffer(ExternalContext externalContext) throws FacesException {

		HttpServletRequest httpServletRequest = null;

		if (LIFERAY_PORTAL_DETECTED) {

			PortletRequest portletRequest = (PortletRequest) externalContext.getRequest();
			httpServletRequest = PortalUtil.getHttpServletRequest(portletRequest);
		}
		// Otherwise there is no way to obtain the underlying HttpServletRequest.

		return getBrowserSniffer(httpServletRequest);
	}

	@Override
	public BrowserSniffer getBrowserSniffer(HttpServletRequest httpServletRequest) throws FacesException {
		return wrappedBrowserSnifferFactory.getBrowserSniffer(httpServletRequest);
	}

	@Override
	public BrowserSnifferFactory getWrapped() {
		return wrappedBrowserSnifferFactory;
	}
}
