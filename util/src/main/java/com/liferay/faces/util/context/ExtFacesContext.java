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
package com.liferay.faces.util.context;

import javax.faces.context.FacesContext;

import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
public abstract class ExtFacesContext extends FacesContext implements FacesContextHelper {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(ExtFacesContext.class);

	// Singleton Instance
	private static transient ExtFacesContext instance;

	/**
	 * Returns the implementation singleton instance.
	 */
	public static ExtFacesContext getInstance() {

		if (instance == null) {
			logger.error("Instance not initialized -- caller might be static");
		}

		return instance;
	}

	/**
	 * Sets the implementation singleton instance.
	 */
	public static void setInstance(ExtFacesContext extFacesContext) {
		instance = extFacesContext;
	}
}
