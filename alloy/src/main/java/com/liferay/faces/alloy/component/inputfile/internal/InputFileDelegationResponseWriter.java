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
package com.liferay.faces.alloy.component.inputfile.internal;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.ResponseWriter;

import com.liferay.faces.alloy.component.inputfile.InputFile;
import com.liferay.faces.util.render.internal.DelegationResponseWriter;


/**
 * This class is a {@link DelegationResponseWriter} that encodes HTML5 attributes for file upload components.
 *
 * @author  Neil Griffin
 */
public class InputFileDelegationResponseWriter extends InputFileDelegationResponseWriterCompat {

	// Private Data Members
	private boolean auto;

	public InputFileDelegationResponseWriter(ResponseWriter responseWriter) {
		this(responseWriter, true);
	}

	public InputFileDelegationResponseWriter(ResponseWriter responseWriter, boolean auto) {
		super(responseWriter);
		this.auto = auto;
	}

	@Override
	public void startElement(String name, UIComponent uiComponent) throws IOException {

		super.startElement(name, uiComponent);

		if ("input".equals(name)) {

			InputFile inputFile = (InputFile) uiComponent;

			String multiple = inputFile.getMultiple();

			if ("multiple".equalsIgnoreCase(multiple)) {
				super.writeAttribute("multiple", multiple, "multiple");
			}
		}
	}

	@Override
	public void writeAttribute(String name, Object value, String property) throws IOException {

		if ("onchange".equals(name)) {

			if (auto) {
				super.writeAttribute(name, value, property);
			}
		}
		else {
			super.writeAttribute(name, value, property);
		}
	}
}
