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
package com.liferay.faces.alloy.component.outputscript.internal;

import java.io.IOException;

import javax.faces.context.ResponseWriter;

import com.liferay.faces.util.render.internal.DelegationResponseWriterBase;


/**
 * @author  Kyle Stiemann
 */
public class OutputScriptResponseWriter extends DelegationResponseWriterBase {

	// Private Data Members
	private String alloyBeginScript;
	private boolean firstWriteText;

	public OutputScriptResponseWriter(ResponseWriter responseWriter, String alloyBeginScript) {

		super(responseWriter);
		this.alloyBeginScript = alloyBeginScript;
		this.firstWriteText = true;
	}

	@Override
	public void endElement(String name) throws IOException {
		write("});");
		super.endElement(name);
	}

	@Override
	public void writeText(Object text, String property) throws IOException {

		if (firstWriteText) {
			firstWriteText = false;
			writeText(alloyBeginScript, null);
		}

		super.writeText(text, property);
	}
}
