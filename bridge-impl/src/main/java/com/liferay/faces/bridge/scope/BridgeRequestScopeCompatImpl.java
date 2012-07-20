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
package com.liferay.faces.bridge.scope;

import javax.faces.context.ExternalContext;
import javax.faces.context.ExternalContextWrapper;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

import com.liferay.faces.bridge.context.ExternalContextImpl;
import com.liferay.faces.bridge.context.flash.BridgeFlash;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * This class provides a compatibility layer that isolates differences between JSF1 and JSF2.
 *
 * @author  Neil Griffin
 */
public class BridgeRequestScopeCompatImpl {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(BridgeRequestScopeCompatImpl.class);

	// Private Data Members
	private Flash flash;

	protected void restoreFlashState(FacesContext facesContext) {

		if ((flash != null) && (flash instanceof BridgeFlash)) {

			ExternalContext externalContext = facesContext.getExternalContext();

			while (externalContext instanceof ExternalContextWrapper) {
				ExternalContextWrapper externalContextWrapper = (ExternalContextWrapper) externalContext;
				externalContext = externalContextWrapper.getWrapped();
			}

			if (externalContext instanceof ExternalContextImpl) {
				ExternalContextImpl externalContextImpl = (ExternalContextImpl) externalContext;
				externalContextImpl.setBridgeFlash((BridgeFlash) flash);
			}
			else {
				logger.error("Unable to get access to the bridge ExternalContextImpl");
			}
		}
	}

	protected void saveFlashState(FacesContext facesContext) {
		flash = facesContext.getExternalContext().getFlash();
	}
}
