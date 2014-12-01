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
package com.liferay.faces.alloy.component.dialog;

import javax.faces.component.FacesComponent;

import com.liferay.faces.util.component.ComponentUtil;
import com.liferay.faces.util.lang.StringPool;


/**
 * @author  Vernon Singleton
 */
@FacesComponent(value = Dialog.COMPONENT_TYPE)
public class Dialog extends DialogBase {

	// Public Constants
	public static final String COMPONENT_TYPE = "com.liferay.faces.alloy.component.dialog.Dialog";
	public static final String RENDERER_TYPE = "com.liferay.faces.alloy.component.dialog.internal.DialogRenderer";
	public static final String DELEGATE_COMPONENT_FAMILY = COMPONENT_FAMILY;
	public static final String DELEGATE_RENDERER_TYPE = "javax.faces.Group";
	public static final String STYLE_CLASS_NAME = "alloy-dialog";

	public Dialog() {
		super();
		setRendererType(RENDERER_TYPE);
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
			style = style + StringPool.SEMICOLON + "display:none;";
		}

		return style;
	}

	@Override
	public String getStyleClass() {

		// getStateHelper().eval(PropertyKeys.styleClass, null) is called because super.getStyleClass() may return the
		// STYLE_CLASS_NAME of the super class.
		String styleClass = (String) getStateHelper().eval(PropertyKeys.styleClass, null);

		return ComponentUtil.concatCssClasses(styleClass, STYLE_CLASS_NAME);
	}
}
