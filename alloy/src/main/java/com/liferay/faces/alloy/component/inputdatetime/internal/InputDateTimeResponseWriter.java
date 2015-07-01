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
package com.liferay.faces.alloy.component.inputdatetime.internal;

import java.io.IOException;

import javax.faces.context.ResponseWriter;

import com.liferay.faces.util.component.Styleable;
import com.liferay.faces.util.render.internal.IdDelegationResponseWriter;


/**
 * @author  Kyle Stiemann
 */
public class InputDateTimeResponseWriter extends IdDelegationResponseWriter {

	// Private Data Members
	private boolean nativeInputDateTime;

	public InputDateTimeResponseWriter(ResponseWriter responseWriter, String inputClientId,
		boolean nativeInputDateTime) {
		super(responseWriter, "input", inputClientId);
		this.nativeInputDateTime = nativeInputDateTime;
	}

	@Override
	public void writeAttribute(String name, Object value, String property) throws IOException {

		if ("class".equalsIgnoreCase(name)) {
			super.writeAttribute(name, "input-medium field", property);
		}
		else if (Styleable.STYLE.equalsIgnoreCase(name)) {
			// no-op because the style attribute is applied to the outermost div instead of the input.
		}
		else {
			super.writeAttribute(name, value, property);
		}
	}

	public boolean isNative() {
		return nativeInputDateTime;
	}
}
