/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
import javax.portlet.ClientDataRequest;
import javax.portlet.PortletContext;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.faces.Bridge;
import javax.servlet.ServletResponse;

import com.liferay.faces.bridge.config.BridgeConfigConstants;
import com.liferay.faces.util.helper.BooleanHelper;


/**
 * This class provides a compatibility layer that isolates differences between JSF1 and JSF2.
 *
 * @author  Neil Griffin
 */
public abstract class ExternalContextCompatImpl extends ExternalContext {

	// Private Constants
	private static final String TRINIDAD_DISABLE_DIALOG_OUTCOMES =
		"org.apache.myfaces.trinidad.DISABLE_DIALOG_OUTCOMES";

	// Protected Data Members
	protected BridgeContext bridgeContext;
	protected ServletResponse facesImplementationServletResponse;
	protected IncongruityContext incongruityContext;
	protected boolean manageIncongruities;
	protected PortletContext portletContext;
	protected PortletRequest portletRequest;
	protected PortletResponse portletResponse;
	protected Bridge.PortletPhase portletPhase;
	protected String requestContextPath;

	public ExternalContextCompatImpl(PortletContext portletContext, PortletRequest portletRequest,
		PortletResponse portletResponse) {

		this.portletContext = portletContext;
		this.portletRequest = portletRequest;
		this.portletResponse = portletResponse;

		// Get the BridgeContext.
		this.bridgeContext = BridgeContext.getCurrentInstance();

		this.incongruityContext = bridgeContext.getIncongruityContext();

		// Determine whether or not lifecycle incongruities should be managed.
		this.manageIncongruities = BooleanHelper.toBoolean(bridgeContext.getInitParameter(
					BridgeConfigConstants.PARAM_MANAGE_INCONGRUITIES), true);

		// Disable the Apache Trinidad 1.2.x "dialog:" URL feature as it causes navigation-handler failures during the
		// EVENT_PHASE of the portlet lifecycle. For more information on the feature, see:
		// http://jsfatwork.irian.at/book_de/trinidad.html
		portletContext.setAttribute(TRINIDAD_DISABLE_DIALOG_OUTCOMES, Boolean.TRUE);
	}

	public String encodePartialActionURL(String url) {

		// no-op for JSF 1.2
		return url;
	}

	protected ServletResponse createFlashHttpServletResponse() {

		// no-op for JSF 1.2
		return null;
	}

	protected boolean isBridgeFlashServletResponseRequired() {

		// no-op for JSF 1.2
		return false;
	}

	protected boolean isEncodingFormWithPrimeFacesAjaxFileUpload() {

		// no-op for JSF 1.2
		return false;
	}

	protected boolean isICEfacesLegacyMode(ClientDataRequest clientDataRequest) {

		// no-op for JSF 1.2
		return false;
	}
}
