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
package com.liferay.faces.util.event.internal;

import java.lang.reflect.Method;
import java.util.EventObject;
import java.util.List;

import javax.faces.application.Application;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import com.liferay.faces.util.config.ApplicationConfig;
import com.liferay.faces.util.config.ConfiguredSystemEventListener;
import com.liferay.faces.util.config.FacesConfig;
import com.liferay.faces.util.event.PostConstructApplicationConfigEvent;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * This class isolates differences between JSF1 <> JSF 2 in order to minimize diffs across branches.
 *
 * @author  Neil Griffin
 */
public abstract class ApplicationStartupListenerCompat {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(ApplicationStartupListenerCompat.class);

	public abstract void processSystemEvent(EventObject systemEvent);

	protected void publishEvent(Application application, FacesContext facesContext,
		ApplicationConfig applicationConfig) {

		FacesConfig facesConfig = applicationConfig.getFacesConfig();
		List<ConfiguredSystemEventListener> configuredSystemEventListeners =
			facesConfig.getConfiguredSystemEventListeners();

		if (configuredSystemEventListeners != null) {

			for (ConfiguredSystemEventListener configuredSystemEventListener : configuredSystemEventListeners) {

				String systemEventClass = configuredSystemEventListener.getSystemEventClass();

				if (PostConstructApplicationConfigEvent.class.getName().equals(systemEventClass)) {
					String systemEventListenerFQCN = configuredSystemEventListener.getSystemEventListenerClass();

					try {
						Class<?> systemEventListenerClass = Class.forName(systemEventListenerFQCN);
						Object systemEventListener = systemEventListenerClass.newInstance();

						try {
							Method processSystemEventMethod = systemEventListenerClass.getMethod("processSystemEvent",
									new Class[] { EventObject.class });
							processSystemEventMethod.invoke(systemEventListener,
								new Object[] { new PostConstructApplicationConfigEvent(applicationConfig) });
						}
						catch (NoSuchMethodException e) {
							// ignore
						}
					}
					catch (Exception e) {
						logger.error(e);
					}
				}
			}
		}

	}

	protected String getApplicationContextPath(ExternalContext externalContext) {

		String applicationContextPath = null;

		try {
			applicationContextPath = externalContext.getRequestContextPath();
		}
		catch (UnsupportedOperationException e) {
			// MyFaces does not support this feature during startup. However, this is OK since the value is only needed
			// in a JSF 2.2 environment. For more information, see
			// ApplicationStartupListenerCompat_2_2#getApplicationContextPath(ExternalContext) and
			// ExternalContextCompat_2_2_Impl#getApplicationContxtPath()
		}

		return applicationContextPath;
	}
}
