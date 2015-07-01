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
package com.liferay.faces.alloy.component.popover;

import javax.faces.component.FacesComponent;


/**
 * @author  Vernon Singleton
 */
@FacesComponent(value = Popover.COMPONENT_TYPE)
public class Popover extends PopoverBase {

	@Override
	public String getHeaderText() {
		return (String) getStateHelper().eval(PopoverPropertyKeys.headerText, "&nbsp;");
	}

	@Override
	public String getStyle() {
		String style = super.getStyle();

		// Initially style the outermost <div> (which is the contentBox) with "display:none;" in order to prevent
		// blinking when Alloy's JavaScript attempts to hide the contentBox.
		if (style == null) {
			style = "display:none;";
		}
		else {
			style = style + ";display:none;";
		}

		return style;
	}
}
