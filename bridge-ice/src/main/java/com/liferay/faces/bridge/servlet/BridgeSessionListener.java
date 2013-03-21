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
package com.liferay.faces.bridge.servlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;


/**
 * This class exists simply to prevent startup errors when developers happen to uniformly specify {@link
 * BridgeSessionListener} as a listener in the WEB-INF/web.xml for all portlets projects. Since ICEfaces 1.8 does not
 * utilize the "Bridge Request Scope" feature of the JSR 329 Portlet Bridge Spec, the methods in this class perform no
 * operation (no-op).
 *
 * @author  Neil Griffin
 */
public class BridgeSessionListener implements HttpSessionListener, ServletContextListener {

	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		// no-op
	}

	public void contextInitialized(ServletContextEvent servletContextEvent) {
		// no-op
	}

	public void sessionCreated(HttpSessionEvent httpSessionEvent) {
		// no-op
	}

	public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
		// no-op
	}

}
