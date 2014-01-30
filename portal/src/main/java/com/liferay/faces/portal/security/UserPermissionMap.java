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
package com.liferay.faces.portal.security;

import java.util.HashMap;

import com.liferay.faces.portal.context.LiferayFacesContext;


/**
 * The <code>UserPermissionMap</code> is an API that provides a way to lookup user permissions with a simply <code>
 * java.util.Map</code> interface.
 *
 * @author  Neil Griffin
 */
public class UserPermissionMap extends HashMap<String, Boolean> {

	// serialVersionUID
	private static final long serialVersionUID = 3480405658664457419L;

	// Self-Injections
	LiferayFacesContext liferayFacesContext = LiferayFacesContext.getInstance();

	@Override
	public Boolean get(Object actionIdAsObject) {

		Boolean value = super.get(actionIdAsObject);

		if (value == null) {

			value = Boolean.FALSE;

			String actionId = (String) actionIdAsObject;

			if (actionId != null) {
				value = liferayFacesContext.userHasPortletPermission(actionId);
			}

			put(actionId, value);
		}

		return value;
	}

}
