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
package com.liferay.faces.bridge.application;

import javax.faces.application.ViewHandler;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
public class ViewHandlerFactoryImpl extends ViewHandlerFactory {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(ViewHandlerFactoryImpl.class);

	// Private Constants
	private static final String MOJARRA_VIEW_HANDLER_FQCN = "com.sun.faces.application.ViewHandlerImpl";
	private static final String MYFACES_VIEW_HANDLER_FQCN_NEW = "org.apache.myfaces.application.ViewHandlerImpl";
	private static final Class<?> viewHandlerClass;

	static {

		Class<?> clazz = null;

		try {
			clazz = Class.forName(MOJARRA_VIEW_HANDLER_FQCN);
		}
		catch (Exception e1) {

			try {
				clazz = Class.forName(MYFACES_VIEW_HANDLER_FQCN_NEW);
			}
			catch (Exception e2) {
				logger.error("Classloader unable to find either the Mojarra or MyFaces ViewHandler implementations");
			}
		}

		viewHandlerClass = clazz;
	}

	@Override
	public ViewHandler getViewHandler() {

		ViewHandler viewHandler = null;

		if (viewHandlerClass != null) {

			try {
				viewHandler = (ViewHandler) viewHandlerClass.newInstance();
			}
			catch (Exception e) {
				logger.error(e);
			}
		}

		return viewHandler;
	}

	public ViewHandlerFactory getWrapped() {

		// Since this is the factory instance provided by the bridge, it will never wrap another factory.
		return null;
	}

}
