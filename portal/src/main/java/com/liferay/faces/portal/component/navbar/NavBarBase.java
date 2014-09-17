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
package com.liferay.faces.portal.component.navbar;
//J-

import javax.annotation.Generated;
import javax.faces.component.UIPanel;
import javax.servlet.jsp.tagext.Tag;

import com.liferay.faces.util.component.Styleable;
import com.liferay.faces.util.component.Taggeable;
import com.liferay.taglib.aui.NavBarTag;

/**
 * @author	Neil Griffin
 * @author	Juan Gonzalez
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class NavBarBase extends UIPanel implements Styleable, Taggeable<NavBarTag> {

	// Protected Enumerations
	protected enum NavBarPropertyKeys {
		style,
		styleClass
	}

	private Tag parentTag;

	private NavBarTag tag;

	@Override
	public Tag getParentTag() {
		return parentTag;
	}

	@Override
	public void setParentTag(Tag tag) {
		this.parentTag = tag;
	}

	@Override
	public NavBarTag getTag() {
		return tag;
	}

	@Override
	public void setTag(NavBarTag tag) {
		this.tag = tag;
	}

	@Override
	public String getStyle() {
		return (String) getStateHelper().eval(NavBarPropertyKeys.style, null);
	}

	@Override
	public void setStyle(String style) {
		getStateHelper().put(NavBarPropertyKeys.style, style);
	}

	@Override
	public String getStyleClass() {
		return (String) getStateHelper().eval(NavBarPropertyKeys.styleClass, null);
	}

	@Override
	public void setStyleClass(String styleClass) {
		getStateHelper().put(NavBarPropertyKeys.styleClass, styleClass);
	}
}
//J+
