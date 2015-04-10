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
package com.liferay.faces.alloy.component.button.internal;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.ResponseWriter;

import com.liferay.faces.util.lang.StringPool;
import com.liferay.faces.util.render.internal.DelegationResponseWriterBase;


/**
 * @author  Juan Gonzalez
 */
public class ScriptResponseWriter extends DelegationResponseWriterBase {

	public ScriptResponseWriter(ResponseWriter responseWriter) {
		super(responseWriter);
	}

	@Override
	public void endElement(String name) throws IOException {

		if (StringPool.SCRIPT.equalsIgnoreCase(name)) {
			super.endElement(name);
		}
	}

	@Override
	public void startElement(String name, UIComponent component) throws IOException {

		if (StringPool.SCRIPT.equalsIgnoreCase(name)) {
			super.startElement(name, component);
		}
	}
}
