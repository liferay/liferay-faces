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
package com.liferay.faces.util.render.internal;

import java.io.IOException;

import javax.faces.component.html.HtmlInputText;
import javax.faces.context.ResponseWriter;


/**
 * This class is a {@link DelegationResponseWriter} that is designed to intercept the encoding of a {@link
 * HtmlInputText} component. This class ensures that the "id" attribute is written correctly and that the "type"
 * attribute is "hidden".
 *
 * @author  Neil Griffin
 */
public class HiddenTextResponseWriter extends DelegationResponseWriterBase {

	// Private Data Members
	private String id;
	private boolean wroteId;

	public HiddenTextResponseWriter(ResponseWriter responseWriter, String id) {
		super(responseWriter);
		this.id = id;
	}

	@Override
	public void endElement(String name) throws IOException {

		if ("input".equals(name)) {

			if (!wroteId) {
				super.writeAttribute("id", id, "id");
			}
		}

		super.endElement(name);
	}

	@Override
	public void writeAttribute(String name, Object value, String property) throws IOException {

		if ("id".equals(name)) {
			super.writeAttribute(name, id, property);
		}
		else if ("type".equals(name)) {
			super.writeAttribute(name, "hidden", property);
		}
		else {
			super.writeAttribute(name, value, property);
		}
	}
}
