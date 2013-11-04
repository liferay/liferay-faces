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
package com.liferay.faces.bridge.scope;

import javax.faces.context.ExternalContext;
import javax.faces.lifecycle.ClientWindow;


/**
 * @author  Neil Griffin
 */
public abstract class BridgeRequestScopeCompat_2_2_Impl extends BridgeRequestScopeCompatImpl {

	private static final String BRIDGE_REQ_SCOPE_ATTR_CLIENT_WINDOW = "com.liferay.faces.bridge.clientWindow";

	protected void restoreClientWindow(ExternalContext externalContext) {
		ClientWindow clientWindow = (ClientWindow) getAttribute(BRIDGE_REQ_SCOPE_ATTR_CLIENT_WINDOW);
		externalContext.setClientWindow(clientWindow);
	}

	protected void saveClientWindow(ExternalContext externalContext) {
		ClientWindow clientWindow = externalContext.getClientWindow();
		setAttribute(BRIDGE_REQ_SCOPE_ATTR_CLIENT_WINDOW, clientWindow);
	}
}
