/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
/**
 * Copyright (c) 2011 American Automobile Association. All rights reserved.
 */
package com.liferay.faces.alloy.renderkit;

import java.io.IOException;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.Renderer;

import com.liferay.faces.util.helper.BooleanHelper;
import com.liferay.faces.util.helper.StringHelper;
import com.liferay.faces.util.lang.StringPool;


/**
 * @author  Neil Griffin
 */
public class DialogRenderer extends Renderer {

	@Override
	public void encodeBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		super.encodeBegin(facesContext, uiComponent);

		ResponseWriter responseWriter = facesContext.getResponseWriter();

		responseWriter.startElement("script", uiComponent);
		responseWriter.writeAttribute("type", "text/javascript", null);

		responseWriter.write("AUI().use('aui-dialog', function(A) {");
		responseWriter.write("A.on(\"domready\", function() {");
		responseWriter.write("var instance = new A.Dialog({");

		// Begin writing the bodyContent script parameter, first using the value of the bodyContent attribute, then
		// encoding the children as the remaining part of the value.
		responseWriter.write("bodyContent : '");

		Map<String, Object> attributes = uiComponent.getAttributes();

		String bodyContent = StringHelper.toString(attributes.get("bodyContent"), null);

		if (bodyContent != null) {
			responseWriter.write(bodyContent);
		}
	}

	@Override
	public void encodeChildren(FacesContext facesContext, UIComponent component) throws IOException {

		// Since children of an aui:dialog are rendered as part of the bodyContent script parameter, need to make sure
		// that the markup rendered by the children is one continuous string that has single quotes escaped. In order
		// to ensure this, the SanitizingResponseWriter is used for the children.
		ResponseWriter responseWriterBackup = facesContext.getResponseWriter();
		facesContext.setResponseWriter(new SanitizingResponseWriter(responseWriterBackup));

		try {
			super.encodeChildren(facesContext, component);
		}
		catch (Exception e) {

			if (e instanceof IOException) {
				throw (IOException) e;
			}
			else {
				e.printStackTrace();
			}
		}

		facesContext.setResponseWriter(responseWriterBackup);
	}

	@Override
	public void encodeEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException {
		super.encodeEnd(facesContext, uiComponent);

		// Now that the children are rendered, finish encoding the bodyContent script parameter.
		Map<String, Object> attributes = uiComponent.getAttributes();

		ResponseWriter responseWriter = facesContext.getResponseWriter();
		responseWriter.write("',");

		// Write the remaining attributes.
		writeBooleanAttribute(responseWriter, attributes, "centered", true, false);
		writeBooleanAttribute(responseWriter, attributes, "close", true, false);
		writeBooleanAttribute(responseWriter, attributes, "constrain2view", true, false);
		writeBooleanAttribute(responseWriter, attributes, "destroyOnClose", true, false);
		writeBooleanAttribute(responseWriter, attributes, "draggable", true, false);
		writeStringAttribute(responseWriter, attributes, "height", null, false, false);
		writeBooleanAttribute(responseWriter, attributes, "modal", false, false);
		writeBooleanAttribute(responseWriter, attributes, "resizable", false, false);
		writeBooleanAttribute(responseWriter, attributes, "stack", true, false);
		writeStringAttribute(responseWriter, attributes, "title", null, false, true);
		writeStringAttribute(responseWriter, attributes, "width", null, true, false);
		responseWriter.write("}).render();");
		responseWriter.write("});");
		responseWriter.write("});");
		responseWriter.endElement("script");
	}

	protected void writeBooleanAttribute(ResponseWriter responseWriter, Map<String, Object> attributes, String name,
		boolean defaultValue, boolean lastAttribute) throws IOException {
		String value = Boolean.toString(BooleanHelper.toBoolean(attributes.get(name), defaultValue));
		writeStringAttribute(responseWriter, name, value, lastAttribute, false);
	}

	protected void writeStringAttribute(ResponseWriter responseWriter, String name, String value, boolean lastAttribute,
		boolean quote) throws IOException {

		if (value != null) {
			responseWriter.write(name);
			responseWriter.write(" : ");

			if (quote) {
				responseWriter.write(StringPool.QUOTE);
			}

			responseWriter.write(value);

			if (quote) {
				responseWriter.write(StringPool.QUOTE);
			}

			if (!lastAttribute) {
				responseWriter.write(StringPool.COMMA);
			}
		}
	}

	protected void writeStringAttribute(ResponseWriter responseWriter, Map<String, Object> attributes, String name,
		String defaultValue, boolean lastAttribute, boolean quote) throws IOException {
		String value = StringHelper.toString(attributes.get(name), defaultValue);
		writeStringAttribute(responseWriter, name, value, lastAttribute, quote);
	}

	@Override
	public boolean getRendersChildren() {
		return true;
	}

}
