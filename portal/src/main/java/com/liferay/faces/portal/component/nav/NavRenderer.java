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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.UIData;
import javax.faces.context.FacesContext;
import javax.faces.render.FacesRenderer;

import com.liferay.faces.portal.component.navitem.NavItem;
import com.liferay.faces.portal.render.internal.PortalTagRenderer;

import com.liferay.taglib.aui.NavTag;


/**
 * @author  Neil Griffin
 */
//J-
@FacesRenderer(componentFamily = Nav.COMPONENT_FAMILY, rendererType = Nav.RENDERER_TYPE)
//J+
public class NavRenderer extends PortalTagRenderer<Nav, NavTag> {

	@Override
	public Nav cast(UIComponent uiComponent) {
		return (Nav) uiComponent;
	}

	@Override
	public void copyFrameworkAttributes(FacesContext facesContext, Nav nav, NavTag navTag) {
		navTag.setId(nav.getClientId());
		navTag.setCssClass(nav.getStyleClass());
	}

	@Override
	public void copyNonFrameworkAttributes(FacesContext facesContext, Nav nav, NavTag navTag) {
		navTag.setAriaLabel(nav.getAriaLabel());
		navTag.setAriaRole(nav.getAriaRole());
		navTag.setCollapsible(nav.isCollapsible());
	}

	@Override
	public void encodeChildren(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		// Get the "value" and "var" attributes of the Nav component and determine if iteration should take place
		// using a prototype child tab.
		Nav nav = (Nav) uiComponent;
		Object value = nav.getValue();
		String var = nav.getVar();
		boolean iterateOverDataModel = ((value != null) && (var != null));
		NavItem prototypeChildNavItem = null;

		if (uiComponent instanceof Nav) {
			prototypeChildNavItem = getFirstChildNavItem(nav);
		}

		// Encode the content for each tab.
		if ((iterateOverDataModel) && (prototypeChildNavItem != null)) {
			int rowCount = nav.getRowCount();

			for (int i = 0; i < rowCount; i++) {
				nav.setRowIndex(i);
				prototypeChildNavItem.encodeAll(facesContext);
			}
		}
		else {
			List<UIComponent> children = nav.getChildren();

			for (int i = 0; i < children.size(); i++) {
				UIComponent child = children.get(i);

				if (child.isRendered()) {
					child.encodeAll(facesContext);
				}
			}
		}

		nav.setRowIndex(-1);
	}

	@Override
	public NavTag newTag() {
		return new NavTag();
	}

	@Override
	public String getChildInsertionMarker() {
		return "</ul>";
	}

	public List<NavItem> getChildNavItems(UIData uiData) {

		List<NavItem> childNavItems = new ArrayList<NavItem>();

		List<UIComponent> children = uiData.getChildren();

		for (UIComponent child : children) {

			if (child instanceof NavItem) {
				childNavItems.add((NavItem) child);
			}
		}

		return childNavItems;
	}

	public NavItem getFirstChildNavItem(UIData uiData) {

		NavItem prototypeChildType = null;

		List<NavItem> childNavItems = getChildNavItems(uiData);

		if (childNavItems.size() > 0) {
			prototypeChildType = childNavItems.get(0);
		}

		return prototypeChildType;
	}

	@Override
	public boolean getRendersChildren() {
		return true;
	}
}
