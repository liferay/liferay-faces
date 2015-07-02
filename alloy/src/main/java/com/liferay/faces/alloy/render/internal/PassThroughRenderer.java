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
package com.liferay.faces.alloy.render.internal;

import java.io.IOException;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.ResponseWriter;
import javax.faces.render.Renderer;


/**
 * @author  Kyle Stiemann
 */
public class PassThroughRenderer extends Renderer {

	// Protected Constants
	protected static final String[] MOUSE_DOM_EVENTS = {
			"onclick", "ondblclick", "onmousedown", "onmousemove", "onmouseout", "onmouseover", "onmouseup"
		};
	protected static final String[] KEYBOARD_DOM_EVENTS = { "onkeydown", "onkeypress", "onkeyup" };

	/**
	 * This method exists as a convenience for Component developers to encode attributes that pass through to the DOM in
	 * JSF 2.1.
	 */
	protected void encodePassThroughAttributes(ResponseWriter responseWriter, UIComponent uiComponent,
		final String[] PASS_THROUGH_ATTRIBUTES) throws IOException {

		Map<String, Object> attributes = uiComponent.getAttributes();

		for (final String PASS_THROUGH_ATTRIBUTE : PASS_THROUGH_ATTRIBUTES) {

			Object passThroughAttributeValue = attributes.get(PASS_THROUGH_ATTRIBUTE);

			if (passThroughAttributeValue != null) {
				responseWriter.writeAttribute(PASS_THROUGH_ATTRIBUTE, passThroughAttributeValue,
					PASS_THROUGH_ATTRIBUTE);
			}
		}
	}
}
