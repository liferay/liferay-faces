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
package com.liferay.faces.alloy.component.panel;
//J-

import javax.annotation.Generated;
import com.liferay.faces.alloy.component.panelgroup.PanelGroupBlockLayout;

import com.liferay.faces.util.component.Styleable;

/**
 * @author	Bruno Basto
 * @author	Kyle Stiemann
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class PanelBase extends PanelGroupBlockLayout implements Styleable {

	// Public Constants
	public static final String COMPONENT_TYPE = "com.liferay.faces.alloy.component.panel.Panel";
	public static final String RENDERER_TYPE = "com.liferay.faces.alloy.component.panel.PanelRenderer";

	// Protected Enumerations
	protected enum PanelPropertyKeys {
		footerText,
		headerText,
		styleClass
	}

	public PanelBase() {
		super();
		setRendererType(RENDERER_TYPE);
	}

	public String getFooterText() {
		return (String) getStateHelper().eval(PanelPropertyKeys.footerText, null);
	}

	public void setFooterText(String footerText) {
		getStateHelper().put(PanelPropertyKeys.footerText, footerText);
	}

	public String getHeaderText() {
		return (String) getStateHelper().eval(PanelPropertyKeys.headerText, null);
	}

	public void setHeaderText(String headerText) {
		getStateHelper().put(PanelPropertyKeys.headerText, headerText);
	}

	@Override
	public String getStyleClass() {
		// getStateHelper().eval(PanelPropertyKeys.styleClass, null) is called because super.getStyleClass() may return the
		// STYLE_CLASS_NAME of the super class.
		String styleClass = (String) getStateHelper().eval(PanelPropertyKeys.styleClass, null);

		return com.liferay.faces.util.component.ComponentUtil.concatCssClasses(styleClass, "alloy-panel", "alloy-panel-default");
	}

	@Override
	public void setStyleClass(String styleClass) {
		getStateHelper().put(PanelPropertyKeys.styleClass, styleClass);
	}
}
//J+
