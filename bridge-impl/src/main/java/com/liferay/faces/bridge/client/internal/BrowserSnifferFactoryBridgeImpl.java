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

import javax.faces.context.ExternalContext;
import javax.portlet.PortletRequest;
import javax.servlet.http.HttpServletRequest;

import com.liferay.faces.util.client.BrowserSniffer;
import com.liferay.faces.util.client.BrowserSnifferFactory;
import com.liferay.faces.util.product.ProductConstants;
import com.liferay.faces.util.product.ProductMap;

import com.liferay.portal.util.PortalUtil;


/**
 * @author  Kyle Stiemann
 */
public class BrowserSnifferFactoryBridgeImpl extends BrowserSnifferFactory {

	// Private Constants
	private static final boolean LIFERAY_PORTAL_DETECTED = ProductMap.getInstance().get(ProductConstants.LIFERAY_PORTAL)
		.isDetected();

	// Private Data Memebers
	private BrowserSnifferFactory wrappedBrowserSnifferFactory;

	public BrowserSnifferFactoryBridgeImpl(BrowserSnifferFactory browserSnifferFactory) {
		this.wrappedBrowserSnifferFactory = browserSnifferFactory;
	}

	@Override
	public BrowserSniffer getBrowserSniffer(ExternalContext externalContext) {

		if (LIFERAY_PORTAL_DETECTED) {

			PortletRequest portletRequest = (PortletRequest) externalContext.getRequest();
			HttpServletRequest httpServletRequest = PortalUtil.getHttpServletRequest(portletRequest);

			// Calling ExternalContext.setRequest(httpServletRequest) adds extra overhead because all of the
			// underlying maps have to get re-created. Instead, create a simple ExternalContextWrapper.
			externalContext = new ExternalContextBrowserSnifferImpl(externalContext, httpServletRequest);
		}

		// Otherwise we cannot obtain the HttpServletRequest, so we cannot obtain information about the browser, so
		// return a BrowserSniffer implementation which returns false for all booleans, 0 for all numbers, and "" for
		// all Strings.
		else {
			return new BrowserSnifferPortalImpl();
		}

		return wrappedBrowserSnifferFactory.getBrowserSniffer(externalContext);
	}

	@Override
	public BrowserSnifferFactory getWrapped() {
		return wrappedBrowserSnifferFactory;
	}
}
