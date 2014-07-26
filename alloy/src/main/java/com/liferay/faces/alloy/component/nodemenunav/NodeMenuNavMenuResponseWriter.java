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
package com.liferay.faces.alloy.component.nodemenunav;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.ResponseWriter;

import com.liferay.faces.util.lang.StringPool;
import com.liferay.faces.util.render.DelegationResponseWriterBase;


/**
 * @author  Vernon Singleton
 */
public class NodeMenuNavMenuResponseWriter extends DelegationResponseWriterBase {

	// Private data members
	public String parentName;
	public UIComponent parentComponent;
	public boolean isParent;
	public boolean noClass;

	public NodeMenuNavMenuResponseWriter(ResponseWriter responseWriter) {
		super(responseWriter);
		this.parentName = null;
		this.parentComponent = null;
		this.isParent = true;
		this.noClass = true;
	}

	@Override
	public void startElement(String name, UIComponent component) throws IOException {

		if (parentName == null) {

			if (name.equalsIgnoreCase(StringPool.SCRIPT)) {
				// com.sun.faces.renderkit.RenderKitUtils.renderJsfJs(context) has just been called? It is a little late
				// for that is it not? We will let the next guy be the parent we care about, thanks.
			}
			else {
				parentName = name;
				parentComponent = component;
				isParent = true;
				noClass = true;
			}
		}
		else {

			// Catch cases where no class was written to a piece of menu item content
			if (isParent) {

				if (parentName != null) {

					if (noClass) {
						super.writeAttribute(StringPool.CLASS, "yui3-menuitem-content", StringPool.CLASS);
						noClass = false;
					}
				}
			}

			isParent = false;
		}

		super.startElement(name, component);
	}

	@Override
	public void writeAttribute(String name, Object value, String property) throws IOException {

		if (name.equals(StringPool.CLASS)) {
			noClass = false;

			if (isParent) {

				if (parentName != null) {

					if (name.equals(StringPool.CLASS)) {
						value = "yui3-menuitem-content " + value;
					}
				}
			}
		}

		super.writeAttribute(name, value, property);
	}

	@Override
	public void writeText(Object text, String property) throws IOException {

		// Catch cases where no class was written to a piece of menu item content
		if (isParent) {

			if (parentName != null) {

				if (noClass) {
					super.writeAttribute(StringPool.CLASS, "yui3-menuitem-content", StringPool.CLASS);
					noClass = false;
				}
			}
		}

		super.writeText(text, property);
	}

}
