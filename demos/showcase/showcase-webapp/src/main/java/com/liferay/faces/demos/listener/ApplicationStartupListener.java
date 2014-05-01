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
package com.liferay.faces.demos.listener;

import javax.faces.application.Application;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.PostConstructApplicationEvent;
import javax.faces.event.SystemEvent;
import javax.faces.event.SystemEventListener;

import com.liferay.faces.demos.context.ShowcaseMessageContext;
import com.liferay.faces.util.context.MessageContext;


/**
 * @author  Neil Griffin
 */
public class ApplicationStartupListener implements SystemEventListener {

	public void processEvent(SystemEvent systemEvent) throws AbortProcessingException {

		if (systemEvent instanceof PostConstructApplicationEvent) {

			MessageContext messageContext = MessageContext.getInstance();
			MessageContext showcaseMessageContext = new ShowcaseMessageContext(messageContext);
			MessageContext.setInstance(showcaseMessageContext);
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
