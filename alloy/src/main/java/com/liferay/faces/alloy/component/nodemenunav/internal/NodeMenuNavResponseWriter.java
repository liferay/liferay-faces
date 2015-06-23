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
package com.liferay.faces.alloy.component.nodemenunav.internal;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.ResponseWriter;

import com.liferay.faces.util.lang.StringPool;
import com.liferay.faces.util.render.internal.DelegationResponseWriterBase;


/**
 * @author  Vernon Singleton
 */
public class NodeMenuNavResponseWriter extends DelegationResponseWriterBase {

	// Private data members
	private boolean disabled;
	private String clientId;
	private String styleClass;

	public NodeMenuNavResponseWriter(ResponseWriter responseWriter, boolean disabled, String clientId,
		String styleClass) {
		super(responseWriter);
		this.clientId = clientId;
		this.disabled = disabled;
		this.styleClass = styleClass;
	}

	@Override
	public void writeAttribute(String name, Object value, String property) throws IOException {

		if (StringPool.CLASS.equals(name)) {
			value = styleClass;
		}

		if ("onclick".equals(name)) {
			value = value.toString().replace("this", "document.getElementById('" + clientId + "')");
		}

		if (!StringPool.ID.equals(name)) {
			super.writeAttribute(name, value, property);
		}
	}

	@Override
	public void writeText(Object text, UIComponent component, String property) throws IOException {
		// no-op
	}

	@Override
	public void writeURIAttribute(String name, Object value, String property) throws IOException {

		if (!disabled) {
			super.writeURIAttribute(name, value, property);
		}
	}
}
