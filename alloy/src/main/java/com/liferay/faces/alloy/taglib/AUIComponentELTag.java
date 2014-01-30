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
package com.liferay.faces.alloy.taglib;

import javax.faces.component.UIComponent;
import javax.faces.webapp.UIComponentELTag;


/**
 * @author  Neil Griffin
 */
public abstract class AUIComponentELTag extends UIComponentELTag {

	// Private Data Members
	private javax.el.ValueExpression cssClass;
	private javax.el.ValueExpression styleClass;

	@Override
	public void release() {
		super.release();
		this.cssClass = null;
		this.styleClass = null;
	}

	public javax.el.ValueExpression getCssClass() {
		return cssClass;
	}

	public void setCssClass(javax.el.ValueExpression cssClass) {
		this.cssClass = cssClass;
	}

	@Override
	protected void setProperties(UIComponent uiComponent) {
		super.setProperties(uiComponent);

		if (cssClass != null) {
			uiComponent.setValueExpression("cssClass", cssClass);
		}

		if (styleClass != null) {
			uiComponent.setValueExpression("styleClass", styleClass);
		}
	}

	public javax.el.ValueExpression getStyleClass() {
		return styleClass;
	}

	public void setStyleClass(javax.el.ValueExpression styleClass) {
		this.styleClass = styleClass;
	}

}
