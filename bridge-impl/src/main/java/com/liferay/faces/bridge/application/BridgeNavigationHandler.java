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
package com.liferay.faces.bridge.application;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.portlet.PortletMode;
import javax.portlet.StateAwareResponse;
import javax.portlet.WindowState;
import javax.portlet.faces.Bridge;


/**
 * <p>This abstract class defines the contract for a brige-specific {@link NavigationHandler} that fortifies the JSF
 * runtime with the ability to handle to-view-id entries in navigaion-case blocks that respect the {@link
 * Bridge#PORTLET_MODE_PARAMETER} parameter for switching to a different {@link PortletMode} and the {@link
 * Bridge#PORTLET_WINDOWSTATE_PARAMETER} parameter for switching to a different {@link WindowState}. It also has the
 * ability to react to changes in portlet modes that were done programattically by portlet developers that called {@link
 * StateAwareResponse#setWindowState(WindowState)} during the INVOKE_APPLICATION phase of the JSF lifecycle.</p>
 *
 * @author  Neil Griffin
 */
public abstract class BridgeNavigationHandler extends NavigationHandler {

	/**
	 * This method is defined in the {@link NavigationHandler} superclass and must be overridden in the bridge
	 * implementation so that it can handle to-view-id entries in navigaion-case blocks that respect the {@link
	 * Bridge#PORTLET_MODE_PARAMETER} parameter for switching to a different {@link PortletMode} and the {@link
	 * Bridge#PORTLET_WINDOWSTATE_PARAMETER} parameter for switching to a different {@link WindowState}.
	 *
	 * @see  {@link NavigationHandler#handleNavigation(FacesContext, String, String)}
	 */
	@Override
	public abstract void handleNavigation(FacesContext facesContext, String fromAction, String outcome);

	/**
	 * This method must react to changes in portlet modes that were done programatically by portlet developers that
	 * called {@link StateAwareResponse#setWindowState(WindowState)} during the INVOKE_APPLICATION phase of the JSF
	 * lifecycle. The viewId to be rendered is the current viewId in the UIViewRoot.
	 *
	 * @param  facesContext     The current FacesContext.
	 * @param  fromPortletMode  The PortletMode that was rendered prior to the developer calling {@link
	 *                          StateAwareResponse#setWindowState(WindowState)}.
	 * @param  toPortletMode    The PortletMode that is to be switched to.
	 */
	public abstract void handleNavigation(FacesContext facesContext, PortletMode fromPortletMode,
		PortletMode toPortletMode);
}
