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
package com.liferay.faces.bridge.scope;

import javax.faces.context.FacesContext;
import javax.portlet.PortletConfig;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionListener;


/**
 * This interface defines a contract for managing a cache of {@link BridgeRequestScope} instances.
 *
 * @author  Neil Griffin
 */
public interface BridgeRequestScopeManager {

	/**
	 * Removes all {@link BridgeRequestScope} instances that are associated with the specified {@link FacesContext}.
	 * This should be called if the portlet container unloads an portlet individually.
	 *
	 * @param  portletConfig  The current {@link PortletConfig}.
	 */
	public void removeBridgeRequestScopesByPortlet(PortletConfig portletConfig);

	/**
	 * Removes all of the {@link BridgeRequestScope} instances from the underlying cache that are associated with the
	 * specified {@link HttpSession}. This method is meant to be called from a {@link HttpSessionListener} when a
	 * session is invalidated or expires.
	 *
	 * @param  httpSession  The current {@link HttpSession}.
	 */
	void removeBridgeRequestScopesBySession(HttpSession httpSession);
}
