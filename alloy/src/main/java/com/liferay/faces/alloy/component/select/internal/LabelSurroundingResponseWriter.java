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
package com.liferay.faces.alloy.component.select.internal;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.ResponseWriter;

import com.liferay.faces.util.render.internal.DelegationResponseWriterBase;


/**
 * This class is designed to move &lt;input&gt;s rendered by {@link
 * com.liferay.faces.alloy.component.selectmanycheckbox.internal.SelectManyCheckboxRenderer} and {@link
 * com.liferay.faces.alloy.component.selectoneradio.internal.SelectOneRadioRenderer} inside their &lt;label&gt; tags.
 * This is done in order to be more consistent with Liferay Portal's (and AlloyUI's) style.
 *
 * @author  Kyle Stiemann
 */
public class LabelSurroundingResponseWriter extends DelegationResponseWriterBase {

	// Private Data Members
	private String cssClass;
	private Map<String, Object> inputAttributes = new HashMap<String, Object>();
	private boolean inputMovedInsideLabel = false;
	private boolean writingInput = false;
	private boolean writingLabel = false;

	public LabelSurroundingResponseWriter(ResponseWriter responseWriter, String cssClass) {

		super(responseWriter);
		this.cssClass = cssClass;
	}

	@Override
	public void endElement(String name) throws IOException {

		if ("input".equals(name)) {
			writingInput = false;
		}
		else if ("label".equals(name)) {

			super.endElement(name);
			writingLabel = false;
		}
		else {
			super.endElement(name);
		}
	}

	@Override
	public void startElement(String name, UIComponent component) throws IOException {

		if ("input".equals(name)) {

			writingInput = true;
			inputMovedInsideLabel = false;
		}
		else if ("label".equals(name)) {

			writingLabel = true;
			super.startElement(name, component);
		}
		else {
			super.startElement(name, component);
		}
	}

	@Override
	public void writeAttribute(String name, Object value, String property) throws IOException {

		if (writingInput) {
			inputAttributes.put(name, value);
		}
		else if (writingLabel && "class".equals(name)) {
			cssClass = cssClass + " " + value;
		}
		else {
			super.writeAttribute(name, value, property);
		}
	}

	@Override
	public void writeText(Object text, UIComponent component, String property) throws IOException {

		if (writingLabel && !inputMovedInsideLabel) {

			super.writeAttribute("class", cssClass, null);
			super.startElement("input", component);

			String cssClasses = (String) inputAttributes.get("class");

			if (cssClasses == null) {
				cssClasses = "field";
			}
			else {
				cssClasses = cssClasses + " field";
			}

			inputAttributes.put("class", cssClasses);

			for (Map.Entry<String, Object> attribute : inputAttributes.entrySet()) {
				super.writeAttribute(attribute.getKey(), attribute.getValue(), null);
			}

			inputAttributes.clear();
			super.endElement("input");
			inputMovedInsideLabel = true;
		}

		super.writeText(text, component, property);
	}
}
