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
package com.liferay.faces.portal.listener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * This listener provides the ability to use the liferay-ui:input-editor in a JSF portlet.
 *
 * @author  Neil Griffin
 */
public class StartupListener implements ServletContextListener {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(StartupListener.class);

	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		// This method is required by the interface but has no functionality.
	}

	public void contextInitialized(ServletContextEvent servletContextEvent) {

		ServletContext servletContext = servletContextEvent.getServletContext();

		if (servletContext.getAttribute(StartupListener.class.getName()) == null) {

			logger.info("Context initialized for contextPath=[{0}]", servletContext.getContextPath());

			// Prevent multiple-instantiation of this listener.
			servletContext.setAttribute(StartupListener.class.getName(), Boolean.TRUE);

			try {
				URL jspURL = getClass().getClassLoader().getResource(
						"META-INF/resources/liferay-ui/jsp/input-editor.jsp");

				if (jspURL != null) {
					String realPath = servletContext.getRealPath("/");
					File destFolder = new File(realPath, "resources/liferay-ui/jsp");
					destFolder.mkdirs();

					File destFile = new File(destFolder, "input-editor.jsp");
					InputStream inputStream = jspURL.openStream();
					OutputStream outputStream = new FileOutputStream(destFile);
					byte[] bytes = new byte[1024];
					int bytesRead;

					while ((bytesRead = inputStream.read(bytes)) != -1) {
						outputStream.write(bytes, 0, bytesRead);
					}

					outputStream.flush();
					outputStream.close();
					inputStream.close();
					logger.info("Copied input-editor.jsp from LiferayFaces JAR to context path file=[{0}]",
						destFile.getAbsolutePath());
				}
				else {
					logger.warn(
						"Unable to find input-editor.jsp in LiferayFaces JAR which means liferay-ui:input-editor won't work");
				}
			}
			catch (Exception e) {
				logger.error(e);
			}
		}
		else {
			logger.debug("Preventing multiple instantiation for contextPath=[{0}]", servletContext.getContextPath());
		}
	}

}
