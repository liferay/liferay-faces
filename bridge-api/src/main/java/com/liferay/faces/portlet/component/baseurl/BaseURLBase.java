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
package com.liferay.faces.portlet.component.baseurl;
//J-

import javax.annotation.Generated;
import javax.faces.component.UIComponentBase;


/**
 * @author	Neil Griffin
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class BaseURLBase extends UIComponentBase {

	// Public Constants
	public static final String COMPONENT_TYPE = "com.liferay.faces.portlet.component.baseurl.BaseURL";
	public static final String RENDERER_TYPE = "com.liferay.faces.portlet.component.baseurl.BaseURLRenderer";

	// Protected Enumerations
	protected enum BaseURLPropertyKeys {
		escapeXml,
		secure,
		var
	}

	public BaseURLBase() {
		super();
		setRendererType(RENDERER_TYPE);
	}

	public boolean isEscapeXml() {
		return (Boolean) getStateHelper().eval(BaseURLPropertyKeys.escapeXml, true);
	}

	public void setEscapeXml(boolean escapeXml) {
		getStateHelper().put(BaseURLPropertyKeys.escapeXml, escapeXml);
	}

	public Boolean getSecure() {
		return (Boolean) getStateHelper().eval(BaseURLPropertyKeys.secure, null);
	}

	public void setSecure(Boolean secure) {
		getStateHelper().put(BaseURLPropertyKeys.secure, secure);
	}

	public String getVar() {
		return (String) getStateHelper().eval(BaseURLPropertyKeys.var, null);
	}

	public void setVar(String var) {
		getStateHelper().put(BaseURLPropertyKeys.var, var);
	}
}
//J+
