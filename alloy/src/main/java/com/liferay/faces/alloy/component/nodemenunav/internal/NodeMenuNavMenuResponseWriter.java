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

import com.liferay.faces.util.render.internal.DelegationResponseWriterBase;


/**
 * @author  Vernon Singleton
 */
public class NodeMenuNavMenuResponseWriter extends DelegationResponseWriterBase {

	// Private data members
	public String parentName;
	public UIComponent parentComponent;
	public boolean parent;
	public boolean wroteClass;

	public NodeMenuNavMenuResponseWriter(ResponseWriter responseWriter) {
		super(responseWriter);
	}

	@Override
	public void startElement(String name, UIComponent component) throws IOException {

		if (parentName == null) {

			// If starting a <script> element then it is likely that that
			// com.sun.faces.renderkit.RenderKitUtils.renderJsfJs(FacesContext) decided that it was necessary write the
			// jsf.js resource since it doesn't appear in the <head> element. In this case, assume that a subsequent
			// call to this method will be the parent.
			if (!"<script>".equals(name)) {
				parentName = name;
				parentComponent = component;
				parent = true;
			}
		}
		else {

			// Catch cases where no class was written to a piece of menu item content
			if ((parent) && (parentName != null) && (!wroteClass)) {
				super.writeAttribute("class", "yui3-menuitem-content", "class");
				wroteClass = true;
			}

			parent = false;
		}

		super.startElement(name, component);
	}

	@Override
	public void writeAttribute(String name, Object value, String property) throws IOException {

		if (name.equals("class")) {
			wroteClass = true;

			if ((parent) && (parentName != null) && (name.equals("class"))) {
				value = "yui3-menuitem-content " + value;
			}
		}

		super.writeAttribute(name, value, property);
	}

	@Override
	public void writeText(Object text, String property) throws IOException {

		// Catch cases where no class was written to a piece of menu item content
		if ((parent) && (parentName != null) && (!wroteClass)) {
			super.writeAttribute("class", "yui3-menuitem-content", "class");
			wroteClass = true;
		}

		super.writeText(text, property);
	}
}
