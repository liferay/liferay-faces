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
package com.liferay.faces.portal.component.nav;
//J-

import javax.annotation.Generated;
import javax.faces.component.UIData;

import com.liferay.faces.util.component.Styleable;

/**
 * @author	Neil Griffin
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class NavBase extends UIData implements Styleable {

	// Public Constants
	public static final String COMPONENT_TYPE = "com.liferay.faces.portal.component.nav.Nav";
	public static final String RENDERER_TYPE = "com.liferay.faces.portal.component.nav.NavRenderer";

	// Protected Enumerations
	protected enum NavPropertyKeys {
		ariaLabel,
		ariaRole,
		responsive,
		style,
		styleClass
	}

	public NavBase() {
		super();
		setRendererType(RENDERER_TYPE);
	}

	public String getAriaLabel() {
		return (String) getStateHelper().eval(NavPropertyKeys.ariaLabel, null);
	}

	public void setAriaLabel(String ariaLabel) {
		getStateHelper().put(NavPropertyKeys.ariaLabel, ariaLabel);
	}

	public String getAriaRole() {
		return (String) getStateHelper().eval(NavPropertyKeys.ariaRole, null);
	}

	public void setAriaRole(String ariaRole) {
		getStateHelper().put(NavPropertyKeys.ariaRole, ariaRole);
	}

	public boolean isResponsive() {
		return (Boolean) getStateHelper().eval(NavPropertyKeys.responsive, true);
	}

	public void setResponsive(boolean responsive) {
		getStateHelper().put(NavPropertyKeys.responsive, responsive);
	}

	@Override
	public String getStyle() {
		return (String) getStateHelper().eval(NavPropertyKeys.style, null);
	}

	@Override
	public void setStyle(String style) {
		getStateHelper().put(NavPropertyKeys.style, style);
	}

	@Override
	public String getStyleClass() {
		// getStateHelper().eval(NavPropertyKeys.styleClass, null) is called because super.getStyleClass() may return the
		// STYLE_CLASS_NAME of the super class.
		String styleClass = (String) getStateHelper().eval(NavPropertyKeys.styleClass, null);

		return com.liferay.faces.util.component.ComponentUtil.concatCssClasses(styleClass, "portal-nav");
	}

	@Override
	public void setStyleClass(String styleClass) {
		getStateHelper().put(NavPropertyKeys.styleClass, styleClass);
	}
}
//J+
