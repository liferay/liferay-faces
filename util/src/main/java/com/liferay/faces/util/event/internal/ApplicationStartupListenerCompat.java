/**
 * Copyright (c) 2000-2015 Liferay, Inc. All rights reserved.
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

import java.util.EventObject;

import javax.faces.application.Application;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.SystemEvent;
import javax.faces.event.SystemEventListener;

import com.liferay.faces.util.config.ApplicationConfig;
import com.liferay.faces.util.event.PostConstructApplicationConfigEvent;


/**
 * This class isolates differences between JSF1 <> JSF 2 in order to minimize diffs across branches.
 *
 * @author  Neil Griffin
 */
public abstract class ApplicationStartupListenerCompat implements SystemEventListener {

	public abstract void processSystemEvent(EventObject systemEvent);

	public void processEvent(SystemEvent systemEvent) throws AbortProcessingException {
		processSystemEvent(systemEvent);
	}

	protected void publishEvent(Application application, FacesContext facesContext,
		ApplicationConfig applicationConfig) {

		application.publishEvent(facesContext, PostConstructApplicationConfigEvent.class, ApplicationConfig.class,
			applicationConfig);
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

	public boolean isListenerForSource(Object source) {
		return ((source != null) && (source instanceof Application));
	}
}
