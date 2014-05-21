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
package com.liferay.faces.alloy.component.fieldset;
//J-

import javax.annotation.Generated;
import javax.faces.component.UIPanel;

import com.liferay.faces.util.component.Styleable;

/**
 * @author  Bruno Basto
 * @author  Kyle Stiemann
 */
@Generated(value = "com.liferay.alloy.tools.builder.FacesBuilder")
public abstract class FieldsetBase extends UIPanel implements Styleable {

	// Public Constants
	public static final String LEGEND = "legend";
	public static final String ONCLICK = "onclick";
	public static final String ONDBLCLICK = "ondblclick";
	public static final String ONKEYDOWN = "onkeydown";
	public static final String ONKEYPRESS = "onkeypress";
	public static final String ONKEYUP = "onkeyup";
	public static final String ONMOUSEDOWN = "onmousedown";
	public static final String ONMOUSEMOVE = "onmousemove";
	public static final String ONMOUSEOUT = "onmouseout";
	public static final String ONMOUSEOVER = "onmouseover";
	public static final String ONMOUSEUP = "onmouseup";
	public static final String STYLE = "style";
	public static final String STYLE_CLASS = "styleClass";

	public String getLegend() {
		return (String) getStateHelper().eval(LEGEND, null);
	}

	public void setLegend(String legend) {
		getStateHelper().put(LEGEND, legend);
	}

	public String getOnclick() {
		return (String) getStateHelper().eval(ONCLICK, null);
	}

	public void setOnclick(String onclick) {
		getStateHelper().put(ONCLICK, onclick);
	}

	public String getOndblclick() {
		return (String) getStateHelper().eval(ONDBLCLICK, null);
	}

	public void setOndblclick(String ondblclick) {
		getStateHelper().put(ONDBLCLICK, ondblclick);
	}

	public String getOnkeydown() {
		return (String) getStateHelper().eval(ONKEYDOWN, null);
	}

	public void setOnkeydown(String onkeydown) {
		getStateHelper().put(ONKEYDOWN, onkeydown);
	}

	public String getOnkeypress() {
		return (String) getStateHelper().eval(ONKEYPRESS, null);
	}

	public void setOnkeypress(String onkeypress) {
		getStateHelper().put(ONKEYPRESS, onkeypress);
	}

	public String getOnkeyup() {
		return (String) getStateHelper().eval(ONKEYUP, null);
	}

	public void setOnkeyup(String onkeyup) {
		getStateHelper().put(ONKEYUP, onkeyup);
	}

	public String getOnmousedown() {
		return (String) getStateHelper().eval(ONMOUSEDOWN, null);
	}

	public void setOnmousedown(String onmousedown) {
		getStateHelper().put(ONMOUSEDOWN, onmousedown);
	}

	public String getOnmousemove() {
		return (String) getStateHelper().eval(ONMOUSEMOVE, null);
	}

	public void setOnmousemove(String onmousemove) {
		getStateHelper().put(ONMOUSEMOVE, onmousemove);
	}

	public String getOnmouseout() {
		return (String) getStateHelper().eval(ONMOUSEOUT, null);
	}

	public void setOnmouseout(String onmouseout) {
		getStateHelper().put(ONMOUSEOUT, onmouseout);
	}

	public String getOnmouseover() {
		return (String) getStateHelper().eval(ONMOUSEOVER, null);
	}

	public void setOnmouseover(String onmouseover) {
		getStateHelper().put(ONMOUSEOVER, onmouseover);
	}

	public String getOnmouseup() {
		return (String) getStateHelper().eval(ONMOUSEUP, null);
	}

	public void setOnmouseup(String onmouseup) {
		getStateHelper().put(ONMOUSEUP, onmouseup);
	}

	public String getStyle() {
		return (String) getStateHelper().eval(STYLE, null);
	}

	public void setStyle(String style) {
		getStateHelper().put(STYLE, style);
	}

	public String getStyleClass() {
		return (String) getStateHelper().eval(STYLE_CLASS, null);
	}

	public void setStyleClass(String styleClass) {
		getStateHelper().put(STYLE_CLASS, styleClass);
	}
}
//J+
