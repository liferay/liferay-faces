/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.alloy.component;

import javax.faces.component.FacesComponent;
import javax.faces.component.NamingContainer;


/**
 * @author  Neil Griffin
 * @author  Kyle Stiemann
 */
@FacesComponent(value = "com.liferay.faces.alloy.component.AUIRow")
public class AUIRow extends AUIPanel implements NamingContainer {

	// Private Constants
	private static final String CSS_CLASS = "cssClass";
	private static final String FLUID = "fluid";
	private static final String RENDERER_TYPE = "com.liferay.faces.alloy.renderkit.RowRenderer";
	private static final String STYLE_CLASS = "styleClass";

	public String getCssClass() {
		return (String) getStateHelper().eval(CSS_CLASS, null);
	}

	public void setCssClass(String cssClass) {
		getStateHelper().put(CSS_CLASS, cssClass);
	}

	public Boolean isFluid() {
		return (Boolean) getStateHelper().eval(FLUID, true);
	}

	public void setFluid(Boolean fluid) {
		getStateHelper().put(FLUID, fluid);
	}

	@Override
	public String getRendererType() {
		return RENDERER_TYPE;
	}

	public String getStyleClass() {
		return (String) getStateHelper().eval(STYLE_CLASS, null);
	}

	public void setStyleClass(String styleClass) {
		getStateHelper().put(STYLE_CLASS, styleClass);
	}
}
