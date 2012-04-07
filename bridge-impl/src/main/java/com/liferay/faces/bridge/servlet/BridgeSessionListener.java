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
package com.liferay.faces.bridge.servlet;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.liferay.faces.bridge.BridgeFactoryFinder;
import com.liferay.faces.bridge.scope.BridgeRequestScopeManager;
import com.liferay.faces.bridge.scope.BridgeRequestScopeManagerFactory;


/**
 * @author  Neil Griffin
 */
public class BridgeSessionListener implements HttpSessionListener {

	public void sessionCreated(HttpSessionEvent httpSessionEvent) {
		// This method is required by the HttpSessionListener interface but is not used.
	}

	public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {

		BridgeRequestScopeManagerFactory bridgeRequestScopeManagerFactory = (BridgeRequestScopeManagerFactory)
			BridgeFactoryFinder.getFactory(BridgeRequestScopeManagerFactory.class);
		BridgeRequestScopeManager bridgeRequestScopeManager =
			bridgeRequestScopeManagerFactory.getBridgeRequestScopeManager();
		bridgeRequestScopeManager.removeBridgeRequestScopesBySession(httpSessionEvent.getSession());
	}

}
