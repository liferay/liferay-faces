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
package com.liferay.faces.portlet.component.namespace;
//J-

import javax.annotation.Generated;
import javax.faces.component.UIComponentBase;


/**
 * @author	Neil Griffin
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class NamespaceBase extends UIComponentBase {

	// Public Constants
	public static final String COMPONENT_TYPE = "com.liferay.faces.portlet.component.namespace.Namespace";
	public static final String RENDERER_TYPE = "com.liferay.faces.portlet.component.namespace.NamespaceRenderer";

	// Protected Enumerations
	protected enum NamespacePropertyKeys {
		var
	}

	public NamespaceBase() {
		super();
		setRendererType(RENDERER_TYPE);
	}

	public String getVar() {
		return (String) getStateHelper().eval(NamespacePropertyKeys.var, null);
	}

	public void setVar(String var) {
		getStateHelper().put(NamespacePropertyKeys.var, var);
	}

	public abstract com.liferay.faces.util.component.StateHelper getStateHelper();
}
//J+
