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
package org.portletfaces.liferay.faces.context;

import com.liferay.faces.portal.context.LiferayFacesContextImpl;


/**
 * @deprecated  This class has been repackaged at {@link com.liferay.faces.portal.context.LiferayFacesContext}. This
 *              class exists in order to assist with migrating portletfaces.org based portletfaces.org projects to the
 *              new liferay-faces-portal project.
 * @author      Neil Griffin
 */
@Deprecated
public class LiferayFacesContext extends LiferayFacesContextImpl {

	// serialVersionUID Note: This class implements Serializable only to avoid extraneous stacktraces from being thrown
	// by Mojarra. All of the private data members are marked as transient since they are lazy-initialized.
	private static final long serialVersionUID = 5946902912570939675L;

	// Singleton Instance
	private static transient LiferayFacesContext instance;

	/**
	 * Returns the implementation singleton instance.
	 */
	public static LiferayFacesContext getInstance() {

		if (instance == null) {
			instance = new LiferayFacesContext();
		}

		return instance;
	}

	/**
	 * Sets the implementation singleton instance.
	 */
	public static void setInstance(LiferayFacesContext liferayFacesContext) {
		instance = liferayFacesContext;
	}
}
