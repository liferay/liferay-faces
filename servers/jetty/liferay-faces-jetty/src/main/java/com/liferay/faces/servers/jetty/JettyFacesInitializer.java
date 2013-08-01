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
package com.liferay.faces.servers.jetty;

import java.util.EventListener;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;


/**
 * This class helps provide a workaround for <a href="http://issues.liferay.com/browse/FACES-1668">FACES-1668</a> by
 * enabling zero-config registration of the Mojarra {{com.sun.faces.config.ConfigureListener}} or MyFaces
 * {{org.apache.myfaces.webapp.StartupServletContextListener}} servlet context listeners on Jetty.
 *
 * @author  Neil Griffin
 */
public class JettyFacesInitializer implements ServletContainerInitializer {

	// Logger
	private static final Logger logger = Logger.getLogger(JettyFacesInitializer.class.getName());

	@SuppressWarnings("unchecked")
	public void onStartup(Set<Class<?>> classes, ServletContext servletContext) throws ServletException {

		try {
			Class<EventListener> mojarraConfigureListener = (Class<EventListener>) Class.forName(
					"com.sun.faces.config.ConfigureListener");
			servletContext.addListener(mojarraConfigureListener);
			logger.log(Level.INFO, "Automatically registered Mojarra ConfigureListener on Jetty");
		}
		catch (ClassNotFoundException e1) {

			try {
				Class<EventListener> myFacesStartupListener = (Class<EventListener>) Class.forName(
						"org.apache.myfaces.webapp.StartupServletContextListener");
				servletContext.addListener(myFacesStartupListener);
				logger.log(Level.INFO, "Automatically registered MyFaces ConfigureListener on Jetty");
			}
			catch (ClassNotFoundException e2) {

				logger.log(Level.SEVERE,
					"Unable to find Mojarra or MyFaces servlet context listeners for zero-config startup on Jetty");
			}
		}

	}

}
