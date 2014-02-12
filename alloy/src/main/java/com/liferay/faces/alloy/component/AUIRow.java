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
package com.liferay.faces.alloy.component;

import javax.el.ValueExpression;
import javax.faces.component.NamingContainer;
import javax.faces.context.FacesContext;


/**
 * @author  Neil Griffin
 * @author  Kyle Stiemann
 */
public class AUIRow extends AUIPanel implements NamingContainer {

	// Public Constants
	public static final String FLUID = "fluid";
	public static final String RENDERER_TYPE = "com.liferay.faces.alloy.renderkit.RowRenderer";

	// Private Constants
	private static final String CSS_CLASS = "cssClass";
	private static final String STYLE_CLASS = "styleClass";

	// Private Data Members
	private String cssClass;
	private Boolean fluid;
	private String styleClass;

	public String getCssClass() {

		if (cssClass == null) {
			ValueExpression valueExpression = getValueExpression(CSS_CLASS);

			if (valueExpression != null) {
				cssClass = (String) valueExpression.getValue(FacesContext.getCurrentInstance().getELContext());
			}
		}

		return cssClass;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}

	public Boolean isFluid() {

		if (fluid == null) {
			ValueExpression valueExpression = getValueExpression(FLUID);

			if (valueExpression != null) {
				fluid = (Boolean) valueExpression.getValue(FacesContext.getCurrentInstance().getELContext());
			}
			else {
				fluid = Boolean.FALSE;
			}
		}

		return fluid;
	}

	public void setFluid(Boolean fluid) {
		this.fluid = fluid;
	}

	@Override
	public String getRendererType() {
		return RENDERER_TYPE;
	}

	public String getStyleClass() {

		if (styleClass == null) {
			ValueExpression valueExpression = getValueExpression(STYLE_CLASS);

			if (valueExpression != null) {
				styleClass = (String) valueExpression.getValue(FacesContext.getCurrentInstance().getELContext());
			}
		}

		return styleClass;
	}

	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}
}
