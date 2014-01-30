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
package com.liferay.faces.portal.context;

import javax.faces.context.FacesContext;

import com.liferay.faces.util.context.FacesContextHelper;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Neil Griffin
 */
public abstract class LiferayFacesContext extends FacesContext implements FacesContextHelper, PortletHelper,
	LiferayPortletHelper {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(LiferayFacesContext.class);

	// Singleton Instance
	private static transient LiferayFacesContext instance = new LiferayFacesContextImpl();

	/**
	 * Returns the implementation singleton instance.
	 */
	public static LiferayFacesContext getInstance() {

		if (instance == null) {
			logger.error("Instance not initialized -- caller might be static");
		}

		return instance;
	}

	/**
	 * Sets the implementation singleton instance.
	 */
	public static void setInstance(LiferayFacesContext liferayFacesContext) {
		instance = liferayFacesContext;
	}

	/**
	 * Returns the Liferay build number.
	 */
	public abstract int getBuildNumber();
}
