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
package com.liferay.faces.bridge.context;

import java.util.Iterator;

import javax.faces.FacesException;
import javax.faces.application.ProjectStage;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * The purpose of this class is to wrap the JSF implementation's Ajax exception handler so that exceptions that occur
 * during Ajax are logged to the console. For some reason Mojarra doesn't do that, which makes it very hard for the
 * developer to find out what went wrong.
 *
 * @author  Neil Griffin
 */
public class ExceptionHandlerAjaxImpl extends ExceptionHandlerWrapper {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlerAjaxImpl.class);

	// Private Data Members
	private ExceptionHandler wrappedExceptionHandler;

	public ExceptionHandlerAjaxImpl(ExceptionHandler exceptionHandler) {
		this.wrappedExceptionHandler = exceptionHandler;
	}

	@Override
	public void handle() throws FacesException {

		// Before delegating, log all exceptions to the console.
		Iterable<ExceptionQueuedEvent> unhandledExceptionQueuedEvents = getUnhandledExceptionQueuedEvents();
		Iterator<ExceptionQueuedEvent> itr = unhandledExceptionQueuedEvents.iterator();

		FacesContext facesContext = FacesContext.getCurrentInstance();
		boolean isDevelopment = facesContext.isProjectStage(ProjectStage.Development);

		while (itr.hasNext()) {
			ExceptionQueuedEvent exceptionQueuedEvent = itr.next();
			ExceptionQueuedEventContext exceptionQueuedEventContext = exceptionQueuedEvent.getContext();

			if (exceptionQueuedEventContext != null) {
				Throwable throwable = exceptionQueuedEventContext.getException();

				if (throwable != null) {

					if (isDevelopment) {
						logger.error(throwable);
					}
					else {
						logger.error(throwable.getMessage());
					}
				}
				else {
					logger.error("Unable to get exception from exceptionQueuedEventContext");
				}
			}
			else {
				logger.error("Unable to get exceptionQueuedEventContext from exceptionQueuedEvent");
			}
		}

		// Delegate to the wrapped JSF implementation's ExceptionHandler.
		super.handle();
	}

	@Override
	public ExceptionHandler getWrapped() {
		return wrappedExceptionHandler;
	}

}
