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
package com.liferay.faces.bridge.context.internal;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import com.liferay.faces.bridge.component.primefaces.internal.PrimeFacesFileUpload;
import com.liferay.faces.bridge.config.internal.PortletConfigParam;
import com.liferay.faces.bridge.context.BridgeContext;
import com.liferay.faces.bridge.context.IncongruityContext;


/**
 * This class provides a compatibility layer that contains JSF 1.0/1.1/1.2 public methods that subclasses need to
 * override.
 *
 * @author  Neil Griffin
 */
public abstract class ExternalContextCompat_1_2_Impl extends ExternalContext {

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
		PortletConfig portletConfig = bridgeContext.getPortletConfig();
		this.manageIncongruities = PortletConfigParam.ManageIncongruities.getBooleanValue(portletConfig);
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

		if (isEncodingFormWithPrimeFacesAjaxFileUpload()) {
			return encodePartialActionURL(url);
		}
		else {
			return bridgeContext.encodeActionURL(url).toString();
		}
	}

	protected boolean isEncodingFormWithPrimeFacesAjaxFileUpload() {
		FacesContext facesContext = FacesContext.getCurrentInstance();

		if (facesContext.getAttributes().get(PrimeFacesFileUpload.AJAX_FILE_UPLOAD) != null) {
			return true;
		}
		else {
			return false;
		}
	}
}
