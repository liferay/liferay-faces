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
package com.liferay.faces.alloy.component.inputtime.internal;

import java.io.IOException;

import javax.faces.context.ResponseWriter;

import com.liferay.faces.alloy.component.inputdatetime.internal.InputDateTimeResponseWriter;


/**
 * @author  Kyle Stiemann
 */
public class InputTimeResponseWriter extends InputDateTimeResponseWriter {

	public InputTimeResponseWriter(ResponseWriter responseWriter, String inputClientId, boolean nativeInputTime) {
		super(responseWriter, inputClientId, nativeInputTime);
	}

	@Override
	public void writeAttribute(String name, Object value, String property) throws IOException {

		if ("type".equalsIgnoreCase(name) && isNative()) {
			super.writeAttribute(name, "time", property);
		}
		else {
			super.writeAttribute(name, value, property);
		}
	}
}
