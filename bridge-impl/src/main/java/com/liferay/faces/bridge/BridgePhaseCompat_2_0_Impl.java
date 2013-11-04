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
package com.liferay.faces.bridge;

import java.io.IOException;

import javax.faces.context.FacesContext;


/**
 * This class provides a compatibility layer that isolates differences between JSF1 and JSF2.
 *
 * @author  Neil Griffin
 */
public abstract class BridgePhaseCompat_2_0_Impl extends BridgePhaseCompat_1_2_Impl {

	public BridgePhaseCompat_2_0_Impl(PortletConfig portletConfig) {
		super(portletConfig);
	}

	public void handleJSF2ResourceRequest(FacesContext facesContext) throws IOException {
		// no-op for JSF 1.x
	}

	protected void clearHeadManagedBeanResources(FacesContext facesContext) {
		// no-op for JSF 1.x
	}

	public Throwable getJSF2HandledException(FacesContext facesContext) {

		// no-op for JSF 1.x
		return null;
	}

	public Throwable getJSF2UnhandledException(FacesContext facesContext) {

		// no-op for JSF 1.x
		return null;
	}

	public boolean isJSF2AjaxRequest(FacesContext facesContext) {

		// no-op for JSF 1.x
		return false;
	}

	public boolean isJSF2ResourceRequest(FacesContext facesContext) {

		// no-op for JSF 1.x
		return false;
	}
}
