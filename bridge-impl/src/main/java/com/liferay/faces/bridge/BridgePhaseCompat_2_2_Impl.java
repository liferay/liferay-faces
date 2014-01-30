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
package com.liferay.faces.bridge;

import javax.faces.context.FacesContext;
import javax.faces.lifecycle.Lifecycle;
import javax.portlet.PortletConfig;


/**
 * This class provides a compatibility layer that isolates differences related to JSF 2.2.
 *
 * @author  Neil Griffin
 */
public abstract class BridgePhaseCompat_2_2_Impl extends BridgePhaseCompat_2_0_Impl {

	public BridgePhaseCompat_2_2_Impl(PortletConfig portletConfig) {
		super(portletConfig);
	}

	protected void attachClientWindowToLifecycle(FacesContext facesContext, Lifecycle lifecycle) {
		lifecycle.attachWindow(facesContext);
	}
}
