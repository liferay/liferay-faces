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
package com.liferay.faces.alloy.component.accordion;
//J-

import javax.annotation.Generated;
import javax.faces.component.UIData;

import com.liferay.faces.util.component.Styleable;
import com.liferay.faces.util.component.ClientComponent;

/**
 * @author	Bruno Basto
 * @author	Kyle Stiemann
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class AccordionBase extends UIData implements Styleable, ClientComponent {

	// Public Constants
	public static final String COMPONENT_TYPE = "com.liferay.faces.alloy.component.accordion.Accordion";
	public static final String RENDERER_TYPE = "com.liferay.faces.alloy.component.accordion.AccordionRenderer";

	// Protected Enumerations
	protected enum AccordionPropertyKeys {
		clientKey,
		multiple,
		selectedIndex,
		style,
		styleClass
	}

	public AccordionBase() {
		super();
		setRendererType(RENDERER_TYPE);
	}

	@Override
	public String getClientKey() {
		return (String) getStateHelper().eval(AccordionPropertyKeys.clientKey, null);
	}

	@Override
	public void setClientKey(String clientKey) {
		getStateHelper().put(AccordionPropertyKeys.clientKey, clientKey);
	}

	public boolean isMultiple() {
		return (Boolean) getStateHelper().eval(AccordionPropertyKeys.multiple, false);
	}

	public void setMultiple(boolean multiple) {
		getStateHelper().put(AccordionPropertyKeys.multiple, multiple);
	}

	public Integer getSelectedIndex() {
		return (Integer) getStateHelper().eval(AccordionPropertyKeys.selectedIndex, null);
	}

	public void setSelectedIndex(Integer selectedIndex) {
		getStateHelper().put(AccordionPropertyKeys.selectedIndex, selectedIndex);
	}

	@Override
	public String getStyle() {
		return (String) getStateHelper().eval(AccordionPropertyKeys.style, null);
	}

	@Override
	public void setStyle(String style) {
		getStateHelper().put(AccordionPropertyKeys.style, style);
	}

	@Override
	public String getStyleClass() {
		// getStateHelper().eval(AccordionPropertyKeys.styleClass, null) is called because super.getStyleClass() may return the
		// STYLE_CLASS_NAME of the super class.
		String styleClass = (String) getStateHelper().eval(AccordionPropertyKeys.styleClass, null);

		return com.liferay.faces.util.component.ComponentUtil.concatCssClasses(styleClass, "alloy-accordion");
	}

	@Override
	public void setStyleClass(String styleClass) {
		getStateHelper().put(AccordionPropertyKeys.styleClass, styleClass);
	}
}
//J+
