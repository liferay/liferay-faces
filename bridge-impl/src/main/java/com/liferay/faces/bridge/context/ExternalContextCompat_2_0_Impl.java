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

import javax.portlet.ClientDataRequest;
import javax.portlet.PortletContext;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.faces.Bridge;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import com.liferay.faces.bridge.config.BridgeConfigConstants;
import com.liferay.faces.util.helper.BooleanHelper;


/**
 * This class provides a compatibility layer that isolates differences between JSF 1.2 and JSF 2.0.
 *
 * @author  Neil Griffin
 */
public abstract class ExternalContextCompat_2_0_Impl extends ExternalContextCompat_1_2_Impl {

	// Protected Data Members
	protected ServletResponse facesImplementationServletResponse;
	protected IncongruityContext incongruityContext;
	protected boolean manageIncongruities;
	protected PortletContext portletContext;
	protected PortletRequest portletRequest;
	protected PortletResponse portletResponse;
	protected Bridge.PortletPhase portletPhase;
	protected String requestContextPath;

	public ExternalContextCompat_2_0_Impl(PortletContext portletContext, PortletRequest portletRequest,
		PortletResponse portletResponse) {

		super();

		this.portletContext = portletContext;
		this.portletRequest = portletRequest;
		this.portletResponse = portletResponse;

		this.incongruityContext = bridgeContext.getIncongruityContext();

		// Determine whether or not lifecycle incongruities should be managed.
		this.manageIncongruities = BooleanHelper.toBoolean(bridgeContext.getInitParameter(
					BridgeConfigConstants.PARAM_MANAGE_INCONGRUITIES), true);
	}

	protected HttpServletResponse createFlashHttpServletResponse() {

		// no-op for JSF 1.2
		return null;
	}

	protected boolean isBridgeFlashServletResponseRequired() {

		// no-op for JSF 1.2
		return false;
	}

	protected boolean isICEfacesLegacyMode(ClientDataRequest clientDataRequest) {

		// no-op for JSF 1.2
		return false;
	}
}
