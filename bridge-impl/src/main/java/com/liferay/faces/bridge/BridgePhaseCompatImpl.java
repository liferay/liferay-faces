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
package com.liferay.faces.bridge;

import java.io.IOException;
import java.util.Iterator;

import javax.faces.application.ResourceHandler;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;
import javax.portlet.PortletConfig;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.faces.Bridge.PortletPhase;


/**
 * This class provides a compatibility layer that isolates differences between JSF1 and JSF2.
 *
 * @author  Neil Griffin
 */
public abstract class BridgePhaseCompatImpl extends BridgePhaseBaseImpl {

	public BridgePhaseCompatImpl(PortletConfig portletConfig) {
		super(portletConfig);
	}

	protected void handleJSF2ResourceRequest(FacesContext facesContext) throws IOException {
		ResourceHandler resourceHandler = facesContext.getApplication().getResourceHandler();
		resourceHandler.handleResourceRequest(facesContext);
	}

	@Override
	protected void init(PortletRequest portletRequest, PortletResponse portletResponse, PortletPhase portletPhase) {

		// This is simply a pass-thru for JSF2.
		super.init(portletRequest, portletResponse, portletPhase);
	}

	protected Throwable getJSF2HandledException(FacesContext facesContext) {

		Throwable handledException = null;

		ExceptionHandler exceptionHandler = facesContext.getExceptionHandler();
		Iterable<ExceptionQueuedEvent> handledExceptionQueuedEvents =
			exceptionHandler.getHandledExceptionQueuedEvents();

		if (handledExceptionQueuedEvents != null) {
			Iterator<ExceptionQueuedEvent> itr = handledExceptionQueuedEvents.iterator();

			while (itr.hasNext()) {
				ExceptionQueuedEvent exceptionQueuedEvent = itr.next();
				ExceptionQueuedEventContext exceptionQueuedEventContext = exceptionQueuedEvent.getContext();
				handledException = exceptionQueuedEventContext.getException();

				break;
			}
		}

		return handledException;
	}

	protected Throwable getJSF2UnhandledException(FacesContext facesContext) {

		Throwable unhandledException = null;
		ExceptionHandler exceptionHandler = facesContext.getExceptionHandler();
		Iterable<ExceptionQueuedEvent> unhandledExceptionQueuedEvents =
			exceptionHandler.getUnhandledExceptionQueuedEvents();

		if (unhandledExceptionQueuedEvents != null) {
			Iterator<ExceptionQueuedEvent> itr = unhandledExceptionQueuedEvents.iterator();

			while (itr.hasNext()) {
				ExceptionQueuedEvent exceptionQueuedEvent = itr.next();
				ExceptionQueuedEventContext exceptionQueuedEventContext = exceptionQueuedEvent.getContext();
				unhandledException = exceptionQueuedEventContext.getException();

				break;
			}
		}

		return unhandledException;
	}

	protected boolean isJSF2ResourceRequest(FacesContext facesContext) {
		ResourceHandler resourceHandler = facesContext.getApplication().getResourceHandler();

		return resourceHandler.isResourceRequest(facesContext);
	}
}
