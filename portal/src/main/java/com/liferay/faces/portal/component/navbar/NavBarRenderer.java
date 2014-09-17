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

import javax.faces.component.UIComponent;
import javax.faces.render.FacesRenderer;

import com.liferay.faces.portal.render.internal.PortalTagRenderer;

import com.liferay.taglib.aui.NavBarTag;


/**
 * @author  Neil Griffin
 * @author	 Juan Gonzalez
 */
//J-
@FacesRenderer(componentFamily = NavBar.COMPONENT_FAMILY, rendererType = NavBar.RENDERER_TYPE)
//J+
public class NavBarRenderer extends PortalTagRenderer<NavBar, NavBarTag> {

	@Override
	public NavBar cast(UIComponent uiComponent) {
		return (NavBar) uiComponent;
	}

	@Override
	public void copyFrameworkAttributes(NavBar navBar, NavBarTag navBarTag) {
		navBarTag.setCssClass(navBar.getStyleClass());
	}

	@Override
	public void copyNonFrameworkAttributes(NavBar u, NavBarTag t) {
		// no-op
	}

	@Override
	public NavBarTag newTag() {
		return new NavBarTag();
	}

	@Override
	public String getChildInsertionMarker() {

		// Typical output looks like the following:

		//J-
		// <div class="navbar portal-nav-bar" id="_1_WAR_showcaseportlet_j_idt47" >
		//	 <div class="navbar-inner">
		//	   <div class="container">
		//	   </div>
		//	   </div>
		// </div>
		//J+
		return "</div>";
	}

	@Override
	public boolean getRendersChildren() {
		return true;
	}
}
