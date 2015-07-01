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

import javax.faces.component.UIComponent;
import javax.faces.context.ResponseWriter;


/**
 * This class is a {@link DelegationResponseWriter} that ensures the "id" attribute is always written to a particular
 * element. There are two general use cases for this class: 1. Ensure that the id is always rendered for a specific
 * element. 2. Render a different id than the delegating renderer would normally render.
 *
 * @author  Neil Griffin
 */
public class IdDelegationResponseWriter extends DelegationResponseWriterBase {

	// Private Data Members
	private String idElement;
	private String idValue;
	private boolean writingIdElement;

	public IdDelegationResponseWriter(ResponseWriter responseWriter, String idElement, String idValue) {
		super(responseWriter);
		this.idElement = idElement;
		this.idValue = idValue;
	}

	@Override
	public void endElement(String name) throws IOException {

		super.endElement(name);

		if ((writingIdElement) && idElement.equals(name)) {
			writingIdElement = false;
		}
	}

	@Override
	public void startElement(String name, UIComponent component) throws IOException {

		super.startElement(name, component);

		if ((!writingIdElement) && (idElement != null) && idElement.equals(name)) {
			writingIdElement = true;
			super.writeAttribute("id", idValue, "id");
		}
	}

	@Override
	public void writeAttribute(String name, Object value, String property) throws IOException {

		if ("id".equals(name)) {

			if (!writingIdElement) {
				super.writeAttribute(name, value, property);
			}
		}
		else {
			super.writeAttribute(name, value, property);
		}
	}
}
