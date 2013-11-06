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

import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import com.liferay.faces.bridge.component.primefaces.PrimeFacesFileUpload;


/**
 * This class provides a compatibility layer that contains JSF 1.0/1.1/1.2 public methods that subclasses need to
 * override.
 *
 * @author  Neil Griffin
 */
public abstract class ExternalContextCompat_1_2_Impl extends ExternalContext {

	// Protected Data Members
	protected BridgeContext bridgeContext;

	public ExternalContextCompat_1_2_Impl() {

		// Get the BridgeContext.
		this.bridgeContext = BridgeContext.getCurrentInstance();
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
