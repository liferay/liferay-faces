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
package com.liferay.faces.bridge.context;

import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.portlet.PortletContext;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import com.liferay.faces.bridge.config.BridgeConfigConstants;
import com.liferay.faces.util.helper.BooleanHelper;


/**
 * This class provides a compatibility layer that contains JSF 1.0/1.1/1.2 public methods that subclasses need to
 * override.
 *
 * @author  Neil Griffin
 */
public abstract class ExternalContextCompat_1_2_Impl extends ExternalContext {

	// Private Constants
	private static final String TRINIDAD_DISABLE_DIALOG_OUTCOMES =
		"org.apache.myfaces.trinidad.DISABLE_DIALOG_OUTCOMES";

	// Protected Data Members
	protected BridgeContext bridgeContext;
	protected IncongruityContext incongruityContext;
	protected boolean manageIncongruities;
	protected PortletContext portletContext;
	protected PortletRequest portletRequest;
	protected PortletResponse portletResponse;

	public ExternalContextCompat_1_2_Impl(PortletContext portletContext, PortletRequest portletRequest,
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

	/**
	 * Note: The reason why this method appears here in {@link ExternalContextCompat_1_2_Impl} is because the method was
	 * first introduced with JSF 1.0 and and also because it needs to be overridden by {@link
	 * ExternalContextCompat_2_2_Impl} since it has special requirements for JSF 2.2.
	 *
	 * @see    {@link ExternalContext#encodeActionURL(String, Map)}
	 * @since  JSF 1.0
	 */
	@Override
	public String encodeActionURL(String url) {
		return bridgeContext.encodeActionURL(url).toString();
	}
}
