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
package com.liferay.faces.bridge.context;

import javax.faces.context.ExternalContext;
import javax.faces.context.Flash;
import javax.portlet.PortletContext;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.servlet.http.HttpServletResponse;

import com.liferay.faces.bridge.BridgeFactoryFinder;
import com.liferay.faces.bridge.context.flash.BridgeFlash;
import com.liferay.faces.bridge.context.flash.BridgeFlashFactory;
import com.liferay.faces.bridge.context.flash.FlashHttpServletResponse;


/**
 * @author  Neil Griffin
 */
public abstract class ExternalContextCompat_2_0_FlashImpl extends ExternalContextCompat_1_2_Impl {

	// Lazy-Initialized Data Members
	private Flash flash;

	public ExternalContextCompat_2_0_FlashImpl(PortletContext portletContext, PortletRequest portletRequest,
		PortletResponse portletResponse) {
		super(portletContext, portletRequest, portletResponse);
	}

	protected HttpServletResponse createFlashHttpServletResponse() {
		return new FlashHttpServletResponse(portletResponse, getRequestLocale());
	}

	protected boolean isBridgeFlashServletResponseRequired() {

		if ((flash != null) && (flash instanceof BridgeFlash)) {
			BridgeFlash bridgeFlash = (BridgeFlash) flash;

			return bridgeFlash.isServletResponseRequired();
		}
		else {
			return false;
		}
	}

	/**
	 * @see    {@link ExternalContext#getFlash()}
	 * @since  JSF 2.0
	 */
	@Override
	public Flash getFlash() {

		if (flash == null) {
			BridgeFlashFactory bridgeFlashFactory = (BridgeFlashFactory) BridgeFactoryFinder.getFactory(
					BridgeFlashFactory.class);
			flash = bridgeFlashFactory.getBridgeFlash();
		}

		return flash;
	}

	// NOTE: PROPOSED-FOR-JSR344-API
	// http://java.net/jira/browse/JAVASERVERFACES_SPEC_PUBLIC-1070
	// NOTE: PROPOSED-FOR-BRIDGE3-API (Called by BridgeRequestScope in order to restore the Flash scope)
	// https://issues.apache.org/jira/browse/PORTLETBRIDGE-207
	public void setFlash(Flash flash) {
		this.flash = flash;
	}

}
