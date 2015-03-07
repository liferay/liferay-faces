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
package com.liferay.faces.portal.component.navbarsearch.internal;

import javax.faces.component.UIComponent;
import javax.faces.component.UINamingContainer;
import javax.faces.context.FacesContext;
import javax.faces.render.FacesRenderer;

import com.liferay.faces.portal.component.navbarsearch.NavBarSearch;
import com.liferay.faces.portal.render.internal.PortalTagRenderer;

import com.liferay.taglib.aui.NavBarSearchTag;


/**
 * @author  Juan Gonzalez
 */
//J-
@FacesRenderer(componentFamily = NavBarSearch.COMPONENT_FAMILY, rendererType = NavBarSearch.RENDERER_TYPE)
//J+
public class NavBarSearchRenderer extends PortalTagRenderer<NavBarSearch, NavBarSearchTag> {

	@Override
	public NavBarSearchTag newTag() {
		return new NavBarSearchTag();
	}

	@Override
	protected NavBarSearch cast(UIComponent uiComponent) {
		return (NavBarSearch) uiComponent;
	}

	@Override
	protected void copyFrameworkAttributes(FacesContext facesContext, NavBarSearch navBarSearch,
		NavBarSearchTag navBarSearchTag) {
		char separatorChar = UINamingContainer.getSeparatorChar(facesContext);
		String id = navBarSearch.getClientId().replace(separatorChar, '_').concat("_jsptag");
		navBarSearchTag.setId(id);
		navBarSearchTag.setCssClass(navBarSearch.getStyleClass());
	}

	@Override
	protected void copyNonFrameworkAttributes(FacesContext facesContext, NavBarSearch u, NavBarSearchTag t) {
		// no-op
	}

	@Override
	public String getChildInsertionMarker() {
		return "</div>";
	}
}
