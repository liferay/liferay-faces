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
package com.liferay.faces.alloy.component.outputscript;

import java.io.IOException;

import javax.faces.context.ResponseWriter;

import com.liferay.faces.util.render.DelegationResponseWriterBase;
import com.liferay.faces.util.render.RendererUtil;


/**
 * @author  Kyle Stiemann
 */
public class OutputScriptResponseWriter extends DelegationResponseWriterBase {

	// Private Data Members
	private String auiBeginScript;
	private boolean firstWrite;

	public OutputScriptResponseWriter(ResponseWriter responseWriter, String auiBeginScript) {

		super(responseWriter);
		this.auiBeginScript = auiBeginScript;
		this.firstWrite = true;
	}

	@Override
	public void endElement(String name) throws IOException {
		write(RendererUtil.AUI_END_SCRIPT);
		super.endElement(name);
	}

	@Override
	public void writeText(Object text, String property) throws IOException {

		if (firstWrite) {
			firstWrite = false;
			writeText(auiBeginScript, null);
		}

		super.writeText(text, property);
	}
}
