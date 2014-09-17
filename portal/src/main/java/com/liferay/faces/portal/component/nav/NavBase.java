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
package com.liferay.faces.portal.component.nav;
//J-

import javax.annotation.Generated;
import javax.faces.component.UIData;
import javax.servlet.jsp.tagext.Tag;

import com.liferay.faces.util.component.Styleable;
import com.liferay.faces.util.component.Taggeable;
import com.liferay.taglib.aui.NavTag;

/**
 * @author	Neil Griffin
 * @author Juan Gonzalez
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class NavBase extends UIData implements Styleable, Taggeable<NavTag> {

	// Protected Enumerations
	protected enum NavPropertyKeys {
		ariaLabel,
		ariaRole,
		collapsible,
		style,
		styleClass
	}

	private Tag parentTag;
	private NavTag tag;

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

	public boolean isCollapsible() {
		return (Boolean) getStateHelper().eval(NavPropertyKeys.collapsible, false);
	}

	public void setCollapsible(boolean collapsible) {
		getStateHelper().put(NavPropertyKeys.collapsible, collapsible);
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
		return (String) getStateHelper().eval(NavPropertyKeys.styleClass, null);
	}

	@Override
	public void setStyleClass(String styleClass) {
		getStateHelper().put(NavPropertyKeys.styleClass, styleClass);
	}
	@Override
	public Tag getParentTag() {
		return parentTag;
	}

	@Override
	public void setParentTag(Tag tag) {
		this.parentTag = tag;
	}

	@Override
	public NavTag getTag() {
		return tag;
	}

	@Override
	public void setTag(NavTag tag) {
		this.tag = tag;
	}
}
//J+
