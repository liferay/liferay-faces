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
package com.liferay.faces.portlet.component.param;
//J-

import javax.annotation.Generated;
import javax.faces.component.UIComponentBase;


/**
 * @author	Neil Griffin
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class ParamBase extends UIComponentBase {

	// Public Constants
	public static final String COMPONENT_TYPE = "com.liferay.faces.portlet.component.param.Param";

	// Protected Enumerations
	protected enum ParamPropertyKeys {
		name,
		value
	}

	public ParamBase() {
		super();
		setRendererType("");
	}

	public String getName() {
		return (String) getStateHelper().eval(ParamPropertyKeys.name, null);
	}

	public void setName(String name) {
		getStateHelper().put(ParamPropertyKeys.name, name);
	}

	public String getValue() {
		return (String) getStateHelper().eval(ParamPropertyKeys.value, null);
	}

	public void setValue(String value) {
		getStateHelper().put(ParamPropertyKeys.value, value);
	}
}
//J+
