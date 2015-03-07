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
package com.liferay.faces.portal.component.navitem.internal;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.render.FacesRenderer;

import com.liferay.faces.portal.component.navitem.NavItem;

import com.liferay.taglib.aui.NavItemTag;


/**
 * @author  Neil Griffin
 */
//J-
@FacesRenderer(componentFamily = NavItem.COMPONENT_FAMILY, rendererType = NavItem.RENDERER_TYPE)
//J+
public class NavItemRenderer extends NavItemRendererBase {

	@Override
	public NavItem cast(UIComponent uiComponent) {
		return (NavItem) uiComponent;
	}

	@Override
	public void copyFrameworkAttributes(FacesContext facesContext, NavItem navItem, NavItemTag navItemTag) {
		navItemTag.setId(navItem.getClientId());
		navItemTag.setCssClass(navItem.getStyleClass());
	}

	@Override
	public void copyNonFrameworkAttributes(FacesContext facesContext, NavItem navItem, NavItemTag navItemTag) {
		navItemTag.setAnchorCssClass(navItem.getAnchorCssClass());
		navItemTag.setAnchorData(navItem.getAnchorData());
		navItemTag.setAnchorId(navItem.getAnchorId());
		navItemTag.setAriaLabel(navItem.getAriaLabel());
		navItemTag.setAriaRole(navItem.getAriaRole());
		navItemTag.setData(navItem.getData());
		navItemTag.setHref(navItem.getHref());
		navItemTag.setIconCssClass(navItem.getIconCssClass());
		navItemTag.setLabel(navItem.getLabel());
		navItemTag.setSelected(navItem.isSelected());
		navItemTag.setTitle(navItem.getTitle());
		navItemTag.setUseDialog(navItem.isUseDialog());
	}

	@Override
	public NavItemTag newTag() {
		return new NavItemTag();
	}

	@Override
	public String getChildInsertionMarker() {
		return "</li>";
	}
}
