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
package com.liferay.faces.alloy.component.commandlink.internal;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.ResponseWriter;

import com.liferay.faces.util.render.internal.DelegationResponseWriter;
import com.liferay.faces.util.render.internal.DelegationResponseWriterBase;


/**
 * This class is a {@link DelegationResponseWriter} that will suppress writing of the "value" attribute (the rendered
 * text of the hyperlink) if the {@link CommandLink} has children, so that the rendered children will determine the text
 * of the hyperlink.
 *
 * @author  Neil Griffin
 */
public class CommandLinkResponseWriter extends DelegationResponseWriterBase {

	// Private Data Members
	private int anchorCount;
	private boolean hasChildren;

	public CommandLinkResponseWriter(ResponseWriter responseWriter, boolean hasChildren) {
		super(responseWriter);
		this.hasChildren = hasChildren;
	}

	@Override
	public void endElement(String name) throws IOException {

		if ("a".equals(name)) {
			anchorCount--;
		}

		super.endElement(name);
	}

	@Override
	public void startElement(String name, UIComponent component) throws IOException {

		if ("a".equals(name)) {
			anchorCount++;
		}

		super.startElement(name, component);
	}

	@Override
	public void writeText(Object text, UIComponent component, String property) throws IOException {

		if (!(hasChildren && (anchorCount == 1))) {
			super.writeText(text, component, property);
		}
	}
}
