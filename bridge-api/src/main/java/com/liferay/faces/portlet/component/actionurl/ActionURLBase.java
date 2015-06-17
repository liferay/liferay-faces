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
package com.liferay.faces.portlet.component.actionurl;
//J-

import javax.annotation.Generated;
import com.liferay.faces.portlet.component.renderurl.RenderURLBase;


/**
 * @author	Neil Griffin
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class ActionURLBase extends RenderURLBase {

	// Public Constants
	public static final String COMPONENT_TYPE = "com.liferay.faces.portlet.component.actionurl.ActionURL";
	public static final String RENDERER_TYPE = "com.liferay.faces.portlet.component.actionurl.ActionURLRenderer";

	// Protected Enumerations
	protected enum ActionURLPropertyKeys {
		name
	}

	public ActionURLBase() {
		super();
		setRendererType(RENDERER_TYPE);
	}

	public String getName() {
		return (String) getStateHelper().eval(ActionURLPropertyKeys.name, null);
	}

	public void setName(String name) {
		getStateHelper().put(ActionURLPropertyKeys.name, name);
	}
}
//J+
