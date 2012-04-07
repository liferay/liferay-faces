/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;
import javax.faces.context.FacesContext;


/**
 * @author  Neil Griffin
 */
public class ExceptionHandlerFactoryImpl extends ExceptionHandlerFactory {

	// Private Data Members
	private ExceptionHandlerFactory wrappedExceptionHandlerFactory;

	public ExceptionHandlerFactoryImpl(ExceptionHandlerFactory exceptionHandlerFactory) {
		this.wrappedExceptionHandlerFactory = exceptionHandlerFactory;
	}

	@Override
	public ExceptionHandler getExceptionHandler() {

		ExceptionHandler wrappedExceptionHandler = wrappedExceptionHandlerFactory.getExceptionHandler();
		FacesContext facesContext = FacesContext.getCurrentInstance();

		if (facesContext.getPartialViewContext().isAjaxRequest()) {
			return new ExceptionHandlerAjaxImpl(wrappedExceptionHandler);
		}
		else {
			return wrappedExceptionHandler;
		}
	}

	@Override
	public ExceptionHandlerFactory getWrapped() {
		return wrappedExceptionHandlerFactory;
	}

}
