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
package com.liferay.faces.init;

import java.util.EventListener;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;


/**
 * This class enables zero-config registration of the Mojarra {@link com.sun.faces.config.ConfigureListener} or the
 * MyFaces {@link org.apache.myfaces.webapp.StartupServletContextListener}. This is necessary when the servlet container
 * does not have the ability to discover these servlet context listeners via TLD scanning and the developer does not
 * want to specify them as listeners in the WEB-INF/web.xml descriptor.
 *
 * @author  Neil Griffin
 */
public class LiferayFacesInitializer implements ServletContainerInitializer {

	// Logger
	private static final Logger logger = Logger.getLogger(LiferayFacesInitializer.class.getName());

	// Private Constants
	private static final String MOJARRA_CONFIGURE_LISTENER = "com.sun.faces.config.ConfigureListener";
	private static final String MYFACES_STARTUP_LISTENER = "org.apache.myfaces.webapp.StartupServletContextListener";

	@SuppressWarnings("unchecked")
	public void onStartup(Set<Class<?>> classes, ServletContext servletContext) throws ServletException {

		try {
			Class<EventListener> mojarraConfigureListener = (Class<EventListener>) Class.forName(
					MOJARRA_CONFIGURE_LISTENER);
			servletContext.addListener(mojarraConfigureListener);
			logger.log(Level.INFO, "Registered Mojarra " + MOJARRA_CONFIGURE_LISTENER);
		}
		catch (ClassNotFoundException e1) {

			try {
				Class<EventListener> myFacesStartupListener = (Class<EventListener>) Class.forName(
						MYFACES_STARTUP_LISTENER);
				servletContext.addListener(myFacesStartupListener);
				logger.log(Level.INFO, "Registered MyFaces " + MYFACES_STARTUP_LISTENER);
			}
			catch (ClassNotFoundException e2) {

				logger.log(Level.SEVERE,
					"Unable to find Mojarra or MyFaces servlet context listeners for zero-config startup");
			}
		}

	}

}
