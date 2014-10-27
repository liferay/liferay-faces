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

import java.util.EventObject;

import javax.faces.application.Application;
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

	public void processEvent(SystemEvent systemEvent) throws AbortProcessingException {
		processSystemEvent(systemEvent);
	}

	protected abstract void processSystemEvent(EventObject systemEvent);

	protected void publishEvent(Application application, FacesContext facesContext,
		ApplicationConfig applicationConfig) {

		application.publishEvent(facesContext, PostConstructApplicationConfigEvent.class, ApplicationConfig.class,
			applicationConfig);
	}

	public boolean isListenerForSource(Object source) {
		return ((source != null) && (source instanceof Application));
	}
}
