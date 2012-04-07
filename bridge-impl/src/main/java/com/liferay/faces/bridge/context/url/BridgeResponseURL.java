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
package com.liferay.faces.bridge.context.url;

import javax.portlet.PortletModeException;
import javax.portlet.StateAwareResponse;
import javax.portlet.WindowStateException;
import javax.portlet.faces.Bridge;


/**
 * This interface represents a bridge "response" URL, meaning a URL that has convenience methods for representing URLs
 * according to the deviation requirements of {@link javax.faces.context.ExternalContext#encodeResourceURL(String)}
 * listed in Section 6.1.3.1 of the Bridge Spec.

 * @author  Neil Griffin
 */
public interface BridgeResponseURL extends BridgeURL {

	/**
	 * Convenience method that applies the parameters found in this URL to the specified {@link StateAwareResponse} by
	 * calling methods such as {@link StateAwareResponse#setPortletMode(javax.portlet.PortletMode)} and {@link
	 * StateAwareResponse#setWindowState(javax.portlet.WindowState)}, etc.
	 *
	 * @param   stateAwareResponse  The current response.
	 *
	 * @throws  PortletModeException  If {@link Bridge#PORTLET_MODE_PARAMETER} has an invalid value.
	 * @throws  WindowStateException  If {@link Bridge#PORTLET_WINDOWSTATE_PARAMETER} has an invalid value.
	 */
	void applyToResponse(StateAwareResponse stateAwareResponse) throws PortletModeException, WindowStateException;
}
