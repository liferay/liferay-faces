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
package com.liferay.faces.portlet.component.resourceurl;
//J-

import javax.annotation.Generated;
import com.liferay.faces.portlet.component.baseurl.BaseURL;


/**
 * @author	Neil Griffin
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class ResourceURLBase extends BaseURL {

	// Public Constants
	public static final String COMPONENT_TYPE = "com.liferay.faces.portlet.component.resourceurl.ResourceURL";
	public static final String RENDERER_TYPE = "com.liferay.faces.portlet.component.resourceurl.ResourceURLRenderer";

	// Protected Enumerations
	protected enum ResourceURLPropertyKeys {
		cacheability,
		id
	}

	public ResourceURLBase() {
		super();
		setRendererType(RENDERER_TYPE);
	}

	public String getCacheability() {
		return (String) getStateHelper().eval(ResourceURLPropertyKeys.cacheability, javax.portlet.ResourceURL.PAGE);
	}

	public void setCacheability(String cacheability) {
		getStateHelper().put(ResourceURLPropertyKeys.cacheability, cacheability);
	}

	@Override
	public String getId() {
		return (String) getStateHelper().eval(ResourceURLPropertyKeys.id, null);
	}

	@Override
	public void setId(String id) {
		getStateHelper().put(ResourceURLPropertyKeys.id, id);
	}
}
//J+
