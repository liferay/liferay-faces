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
package com.liferay.faces.alloy.event;

import javax.faces.application.Application;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.PostConstructApplicationEvent;
import javax.faces.event.SystemEvent;
import javax.faces.event.SystemEventListener;

import com.liferay.faces.alloy.context.MessageContextAlloyImpl;
import com.liferay.faces.util.context.MessageContext;


/**
 * This class provides the ability to listen to the {@link PostConstructApplicationEvent} during startup.
 *
 * @author  Neil Griffin
 */
public class ApplicationStartupListener implements SystemEventListener {

	public void processEvent(SystemEvent systemEvent) throws AbortProcessingException {

		if (systemEvent instanceof PostConstructApplicationEvent) {

			// Register the Liferay Faces Alloy internationalized messages in the MessageContext delegation chain.
			MessageContext messageContext = MessageContext.getInstance();
			MessageContext.setInstance(new MessageContextAlloyImpl(messageContext));
		}
	}

	public boolean isListenerForSource(Object source) {

		if ((source != null) && (source instanceof Application)) {
			return true;
		}
		else {
			return false;
		}
	}
}
