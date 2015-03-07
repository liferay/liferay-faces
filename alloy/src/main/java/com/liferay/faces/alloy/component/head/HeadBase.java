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
package com.liferay.faces.alloy.component.head;
//J-

import javax.annotation.Generated;
import javax.faces.component.html.HtmlHead;

import com.liferay.faces.util.component.Styleable;

/**
 * @author	Bruno Basto
 * @author	Kyle Stiemann
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class HeadBase extends HtmlHead implements Styleable {

	// Protected Enumerations
	protected enum HeadPropertyKeys {
		style,
		styleClass
	}

	@Override
	public String getStyle() {
		return (String) getStateHelper().eval(HeadPropertyKeys.style, null);
	}

	@Override
	public void setStyle(String style) {
		getStateHelper().put(HeadPropertyKeys.style, style);
	}

	@Override
	public String getStyleClass() {
		return (String) getStateHelper().eval(HeadPropertyKeys.styleClass, null);
	}

	@Override
	public void setStyleClass(String styleClass) {
		getStateHelper().put(HeadPropertyKeys.styleClass, styleClass);
	}
}
//J+
