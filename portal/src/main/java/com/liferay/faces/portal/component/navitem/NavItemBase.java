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
package com.liferay.faces.portal.component.navitem;
//J-

import javax.annotation.Generated;
import javax.faces.component.UIColumn;

import com.liferay.faces.util.component.Styleable;

/**
 * @author	Neil Griffin
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class NavItemBase extends UIColumn implements Styleable {

	// Public Constants
	public static final String COMPONENT_TYPE = "com.liferay.faces.portal.component.navitem.NavItem";
	public static final String RENDERER_TYPE = "com.liferay.faces.portal.component.navitem.NavItemRenderer";

	// Protected Enumerations
	protected enum NavItemPropertyKeys {
		anchorCssClass,
		anchorData,
		anchorId,
		ariaLabel,
		ariaRole,
		data,
		href,
		iconCssClass,
		label,
		selected,
		style,
		styleClass,
		title,
		useDialog
	}

	public NavItemBase() {
		super();
		setRendererType(RENDERER_TYPE);
	}

	public String getAnchorCssClass() {
		return (String) getStateHelper().eval(NavItemPropertyKeys.anchorCssClass, null);
	}

	public void setAnchorCssClass(String anchorCssClass) {
		getStateHelper().put(NavItemPropertyKeys.anchorCssClass, anchorCssClass);
	}

	public Object getAnchorData() {
		return (Object) getStateHelper().eval(NavItemPropertyKeys.anchorData, null);
	}

	public void setAnchorData(Object anchorData) {
		getStateHelper().put(NavItemPropertyKeys.anchorData, anchorData);
	}

	public String getAnchorId() {
		return (String) getStateHelper().eval(NavItemPropertyKeys.anchorId, null);
	}

	public void setAnchorId(String anchorId) {
		getStateHelper().put(NavItemPropertyKeys.anchorId, anchorId);
	}

	public String getAriaLabel() {
		return (String) getStateHelper().eval(NavItemPropertyKeys.ariaLabel, null);
	}

	public void setAriaLabel(String ariaLabel) {
		getStateHelper().put(NavItemPropertyKeys.ariaLabel, ariaLabel);
	}

	public String getAriaRole() {
		return (String) getStateHelper().eval(NavItemPropertyKeys.ariaRole, null);
	}

	public void setAriaRole(String ariaRole) {
		getStateHelper().put(NavItemPropertyKeys.ariaRole, ariaRole);
	}

	public Object getData() {
		return (Object) getStateHelper().eval(NavItemPropertyKeys.data, null);
	}

	public void setData(Object data) {
		getStateHelper().put(NavItemPropertyKeys.data, data);
	}

	public String getHref() {
		return (String) getStateHelper().eval(NavItemPropertyKeys.href, null);
	}

	public void setHref(String href) {
		getStateHelper().put(NavItemPropertyKeys.href, href);
	}

	public String getIconCssClass() {
		return (String) getStateHelper().eval(NavItemPropertyKeys.iconCssClass, null);
	}

	public void setIconCssClass(String iconCssClass) {
		getStateHelper().put(NavItemPropertyKeys.iconCssClass, iconCssClass);
	}

	public String getLabel() {
		return (String) getStateHelper().eval(NavItemPropertyKeys.label, null);
	}

	public void setLabel(String label) {
		getStateHelper().put(NavItemPropertyKeys.label, label);
	}

	public boolean isSelected() {
		return (Boolean) getStateHelper().eval(NavItemPropertyKeys.selected, false);
	}

	public void setSelected(boolean selected) {
		getStateHelper().put(NavItemPropertyKeys.selected, selected);
	}

	@Override
	public String getStyle() {
		return (String) getStateHelper().eval(NavItemPropertyKeys.style, null);
	}

	@Override
	public void setStyle(String style) {
		getStateHelper().put(NavItemPropertyKeys.style, style);
	}

	@Override
	public String getStyleClass() {
		// getStateHelper().eval(NavItemPropertyKeys.styleClass, null) is called because super.getStyleClass() may return the
		// STYLE_CLASS_NAME of the super class.
		String styleClass = (String) getStateHelper().eval(NavItemPropertyKeys.styleClass, null);

		return com.liferay.faces.util.component.ComponentUtil.concatCssClasses(styleClass, "portal-nav-item");
	}

	@Override
	public void setStyleClass(String styleClass) {
		getStateHelper().put(NavItemPropertyKeys.styleClass, styleClass);
	}

	public String getTitle() {
		return (String) getStateHelper().eval(NavItemPropertyKeys.title, null);
	}

	public void setTitle(String title) {
		getStateHelper().put(NavItemPropertyKeys.title, title);
	}

	public boolean isUseDialog() {
		return (Boolean) getStateHelper().eval(NavItemPropertyKeys.useDialog, false);
	}

	public void setUseDialog(boolean useDialog) {
		getStateHelper().put(NavItemPropertyKeys.useDialog, useDialog);
	}
}
//J+
