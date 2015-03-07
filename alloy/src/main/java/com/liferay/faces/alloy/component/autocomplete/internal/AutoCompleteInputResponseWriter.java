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
package com.liferay.faces.alloy.component.autocomplete.internal;

import java.io.IOException;

import javax.faces.context.ResponseWriter;

import com.liferay.faces.util.component.Styleable;
import com.liferay.faces.util.render.internal.IdDelegationResponseWriter;


/**
 * The purpose of this response writer is to prevent the JSF runtime from writing the "style" and "styleClass"
 * attributes on the autocomplete input. Since it extends {@link IdDelegationResponseWriter} it also ensures that the
 * JSF runtime writes a specific value for the "id" attribute.
 *
 * @author  Kyle Stiemann
 */
public class AutoCompleteInputResponseWriter extends IdDelegationResponseWriter {

	public AutoCompleteInputResponseWriter(ResponseWriter responseWriter, String idElement, String idValue) {
		super(responseWriter, idElement, idValue);
	}

	@Override
	public void writeAttribute(String name, Object value, String property) throws IOException {

		if (!Styleable.STYLE.equals(name) && !Styleable.STYLE_CLASS.equals(name)) {
			super.writeAttribute(name, value, property);
		}
	}
}
