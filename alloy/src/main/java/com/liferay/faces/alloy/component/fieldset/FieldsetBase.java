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
package com.liferay.faces.alloy.component.fieldset;
//J-

import javax.annotation.Generated;
import javax.faces.component.UIPanel;

import com.liferay.faces.util.component.Styleable;

/**
 * @author	Bruno Basto
 * @author	Kyle Stiemann
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class FieldsetBase extends UIPanel implements Styleable {

	// Public Constants
	public static final String COMPONENT_TYPE = "com.liferay.faces.alloy.component.fieldset.Fieldset";
	public static final String RENDERER_TYPE = "com.liferay.faces.alloy.component.fieldset.FieldsetRenderer";

	// Protected Enumerations
	protected enum FieldsetPropertyKeys {
		disabled,
		legend,
		onclick,
		ondblclick,
		onkeydown,
		onkeypress,
		onkeyup,
		onmousedown,
		onmousemove,
		onmouseout,
		onmouseover,
		onmouseup,
		style,
		styleClass
	}

	public FieldsetBase() {
		super();
		setRendererType(RENDERER_TYPE);
	}

	public boolean isDisabled() {
		return (Boolean) getStateHelper().eval(FieldsetPropertyKeys.disabled, false);
	}

	public void setDisabled(boolean disabled) {
		getStateHelper().put(FieldsetPropertyKeys.disabled, disabled);
	}

	public String getLegend() {
		return (String) getStateHelper().eval(FieldsetPropertyKeys.legend, null);
	}

	public void setLegend(String legend) {
		getStateHelper().put(FieldsetPropertyKeys.legend, legend);
	}

	public String getOnclick() {
		return (String) getStateHelper().eval(FieldsetPropertyKeys.onclick, null);
	}

	public void setOnclick(String onclick) {
		getStateHelper().put(FieldsetPropertyKeys.onclick, onclick);
	}

	public String getOndblclick() {
		return (String) getStateHelper().eval(FieldsetPropertyKeys.ondblclick, null);
	}

	public void setOndblclick(String ondblclick) {
		getStateHelper().put(FieldsetPropertyKeys.ondblclick, ondblclick);
	}

	public String getOnkeydown() {
		return (String) getStateHelper().eval(FieldsetPropertyKeys.onkeydown, null);
	}

	public void setOnkeydown(String onkeydown) {
		getStateHelper().put(FieldsetPropertyKeys.onkeydown, onkeydown);
	}

	public String getOnkeypress() {
		return (String) getStateHelper().eval(FieldsetPropertyKeys.onkeypress, null);
	}

	public void setOnkeypress(String onkeypress) {
		getStateHelper().put(FieldsetPropertyKeys.onkeypress, onkeypress);
	}

	public String getOnkeyup() {
		return (String) getStateHelper().eval(FieldsetPropertyKeys.onkeyup, null);
	}

	public void setOnkeyup(String onkeyup) {
		getStateHelper().put(FieldsetPropertyKeys.onkeyup, onkeyup);
	}

	public String getOnmousedown() {
		return (String) getStateHelper().eval(FieldsetPropertyKeys.onmousedown, null);
	}

	public void setOnmousedown(String onmousedown) {
		getStateHelper().put(FieldsetPropertyKeys.onmousedown, onmousedown);
	}

	public String getOnmousemove() {
		return (String) getStateHelper().eval(FieldsetPropertyKeys.onmousemove, null);
	}

	public void setOnmousemove(String onmousemove) {
		getStateHelper().put(FieldsetPropertyKeys.onmousemove, onmousemove);
	}

	public String getOnmouseout() {
		return (String) getStateHelper().eval(FieldsetPropertyKeys.onmouseout, null);
	}

	public void setOnmouseout(String onmouseout) {
		getStateHelper().put(FieldsetPropertyKeys.onmouseout, onmouseout);
	}

	public String getOnmouseover() {
		return (String) getStateHelper().eval(FieldsetPropertyKeys.onmouseover, null);
	}

	public void setOnmouseover(String onmouseover) {
		getStateHelper().put(FieldsetPropertyKeys.onmouseover, onmouseover);
	}

	public String getOnmouseup() {
		return (String) getStateHelper().eval(FieldsetPropertyKeys.onmouseup, null);
	}

	public void setOnmouseup(String onmouseup) {
		getStateHelper().put(FieldsetPropertyKeys.onmouseup, onmouseup);
	}

	@Override
	public String getStyle() {
		return (String) getStateHelper().eval(FieldsetPropertyKeys.style, null);
	}

	@Override
	public void setStyle(String style) {
		getStateHelper().put(FieldsetPropertyKeys.style, style);
	}

	@Override
	public String getStyleClass() {
		// getStateHelper().eval(FieldsetPropertyKeys.styleClass, null) is called because super.getStyleClass() may return the
		// STYLE_CLASS_NAME of the super class.
		String styleClass = (String) getStateHelper().eval(FieldsetPropertyKeys.styleClass, null);

		return com.liferay.faces.util.component.ComponentUtil.concatCssClasses(styleClass, "alloy-fieldset", "fieldset");
	}

	@Override
	public void setStyleClass(String styleClass) {
		getStateHelper().put(FieldsetPropertyKeys.styleClass, styleClass);
	}
}
//J+
