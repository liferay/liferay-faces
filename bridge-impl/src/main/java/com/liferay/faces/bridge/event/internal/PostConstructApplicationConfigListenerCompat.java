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
package com.liferay.faces.bridge.event.internal;

import java.util.EventObject;

import javax.faces.event.AbortProcessingException;
import javax.faces.event.SystemEvent;
import javax.faces.event.SystemEventListener;

import com.liferay.faces.util.config.ApplicationConfig;


/**
 * @author  Neil Griffin
 */
public abstract class PostConstructApplicationConfigListenerCompat implements SystemEventListener {

	public abstract void processSystemEvent(EventObject systemEvent) throws AbortProcessingException;

	@Override
	public void processEvent(SystemEvent systemEvent) throws AbortProcessingException {
		processSystemEvent(systemEvent);
	}

	@Override
	public boolean isListenerForSource(Object source) {
		return ((source != null) && (source instanceof ApplicationConfig));
	}
}
