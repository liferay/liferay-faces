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
package com.liferay.faces.portal.component.permissionsurl;

import javax.faces.component.FacesComponent;

import com.liferay.faces.portal.context.LiferayFacesContext;

/**
 * @author	Vernon Singleton
 */
@FacesComponent(value = PermissionsURL.COMPONENT_TYPE)
public class PermissionsURL extends PermissionsURLBase {

	// Public Constants
	public static final String COMPONENT_TYPE = "com.liferay.faces.portal.component.permissionsurl.PermissionsURL";
	public static final String RENDERER_TYPE = "com.liferay.faces.portal.component.permissionsurl.PermissionsURLRenderer";
	public static final String STYLE_CLASS_NAME = "portal-permissions-url";

	public PermissionsURL() {
		super();
		setRendererType(RENDERER_TYPE);
	}
	
	@Override
	public String getResourceGroupId() {
		String resourceGroupId = super.getResourceGroupId();
		
		if (resourceGroupId == null) {
			resourceGroupId = String.valueOf(LiferayFacesContext.getInstance().getScopeGroupId());
		}
		
		return resourceGroupId;
	}
}
