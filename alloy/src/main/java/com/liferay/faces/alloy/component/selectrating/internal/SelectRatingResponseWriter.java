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
package com.liferay.faces.alloy.component.selectrating.internal;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.ResponseWriter;

import com.liferay.faces.alloy.render.internal.AlloyRenderer;
import com.liferay.faces.util.component.Styleable;
import com.liferay.faces.util.lang.StringPool;
import com.liferay.faces.util.render.internal.DelegationResponseWriterBase;


//J-
/**
 * The purpose of this class is to decorate the JSF runtime {@link ResponseWriter} in order to:
 * 1. Remove DOM elements (table parts) from the html that Alloy JavaScript is not expecting.
 * 2. Replace those table parts with new elements that Alloy can deal with.
 * 3. Gather information from the elements as rendered by JSF before the Alloy JavaScript munges it.
 * 4. Provide getters to retrieve the information gathered after encoding the component.
 *
 * JSF's selectOneRadio basically renders what Alloy needs, but it also renders some table elements that are not
 * needed, and puts labels where alloy would not look for them, so this ResponseWriter helps the renderer ignore
 * the unnecessary table parts and put things like the labels into the title attributes of the radio inputs where
 * alloy will get them.	 Please refer to JSF vdldocs for more details:
 * {@link https://javaserverfaces.java.net/nonav/docs/2.2/vdldocs/facelets/h/selectOneRadio.html}
 */
//J+

/**
 * @author  Vernon Singleton
 */
public class SelectRatingResponseWriter extends DelegationResponseWriterBase {

	// Public constants.
	public static final int NO_SELECTION_INDEX = -1;

	// Private data members.
	private long index;
	private boolean inputElement;
	private boolean labelElement;
	private String onClick;
	private long selectedIndex;
	private Object title;

	public SelectRatingResponseWriter(ResponseWriter responseWriter) {
		super(responseWriter);
		this.index = NO_SELECTION_INDEX;
		this.selectedIndex = NO_SELECTION_INDEX;
	}

	@Override
	public void endElement(String name) throws IOException {

		if ("input".equalsIgnoreCase(name)) {
			inputElement = false;
		}

		if ("label".equalsIgnoreCase(name)) {
			labelElement = false;

			super.writeAttribute("title", title, "title");
			super.writeAttribute(Styleable.STYLE, "display:none;", null);
			super.endElement("input");
		}
	}

	@Override
	public void startElement(String name, UIComponent component) throws IOException {

		if ("input".equalsIgnoreCase(name)) {
			inputElement = true;
			title = null;
			index += 1;
			super.startElement(name, component);
		}
		else if ("label".equalsIgnoreCase(name)) {
			labelElement = true;
		}
	}

	// f:selectItems uses this for writing chars into a label
	@Override
	public void write(char[] chars, int off, int len) throws IOException {

		if (labelElement) {
			String newString = new String(chars, off, len);
			title = newString;
		}
	}

	@Override
	public void writeAttribute(String name, Object value, String property) throws IOException {

		if (inputElement) {

			if ("checked".equals(name)) {

				// We have found the input that is "checked"
				setSelectedIndex(this.index);
			}

			super.writeAttribute(name, value, property);
		}

		if ("onclick".equalsIgnoreCase(name)) {

			// we have found JSF's onclick
			onClick = (String) value;
		}
	}

	// f:selectItem uses this for writing text into a label
	@Override
	public void writeText(Object text, UIComponent component, String property) throws IOException {

		if (labelElement) {
			title = text;
		}
	}

	public String getOnClick() {
		return onClick;
	}

	public long getSelectedIndex() {
		return selectedIndex;
	}

	public void setSelectedIndex(long selectedIndex) {
		this.selectedIndex = selectedIndex;
	}
}
