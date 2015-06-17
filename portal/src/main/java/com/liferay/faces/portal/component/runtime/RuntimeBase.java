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
package com.liferay.faces.portal.component.runtime;
//J-

import javax.annotation.Generated;
import javax.faces.component.UIPanel;

import com.liferay.faces.util.component.Styleable;

/**
 * @author	Neil Griffin
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class RuntimeBase extends UIPanel implements Styleable {

	// Public Constants
	public static final String COMPONENT_TYPE = "com.liferay.faces.portal.component.runtime.Runtime";
	public static final String RENDERER_TYPE = "com.liferay.faces.portal.component.runtime.RuntimeRenderer";

	// Protected Enumerations
	protected enum RuntimePropertyKeys {
		defaultPreferences,
		portletName,
		queryString,
		style,
		styleClass
	}

	public RuntimeBase() {
		super();
		setRendererType(RENDERER_TYPE);
	}

	public String getDefaultPreferences() {
		return (String) getStateHelper().eval(RuntimePropertyKeys.defaultPreferences, null);
	}

	public void setDefaultPreferences(String defaultPreferences) {
		getStateHelper().put(RuntimePropertyKeys.defaultPreferences, defaultPreferences);
	}

	public String getPortletName() {
		return (String) getStateHelper().eval(RuntimePropertyKeys.portletName, null);
	}

	public void setPortletName(String portletName) {
		getStateHelper().put(RuntimePropertyKeys.portletName, portletName);
	}

	public String getQueryString() {
		return (String) getStateHelper().eval(RuntimePropertyKeys.queryString, null);
	}

	public void setQueryString(String queryString) {
		getStateHelper().put(RuntimePropertyKeys.queryString, queryString);
	}

	@Override
	public String getStyle() {
		return (String) getStateHelper().eval(RuntimePropertyKeys.style, null);
	}

	@Override
	public void setStyle(String style) {
		getStateHelper().put(RuntimePropertyKeys.style, style);
	}

	@Override
	public String getStyleClass() {
		// getStateHelper().eval(RuntimePropertyKeys.styleClass, null) is called because super.getStyleClass() may return the
		// STYLE_CLASS_NAME of the super class.
		String styleClass = (String) getStateHelper().eval(RuntimePropertyKeys.styleClass, null);

		return com.liferay.faces.util.component.ComponentUtil.concatCssClasses(styleClass, "portal-runtime");
	}

	@Override
	public void setStyleClass(String styleClass) {
		getStateHelper().put(RuntimePropertyKeys.styleClass, styleClass);
	}
}
//J+
