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
package com.liferay.faces.alloy.renderkit;

import java.io.IOException;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.Renderer;

import com.liferay.faces.util.component.ComponentUtil;
import com.liferay.faces.util.lang.StringPool;


/**
 * @author  Neil Griffin
 */
public class ColumnRenderer extends Renderer {

	@Override
	public void encodeBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {
		super.encodeBegin(facesContext, uiComponent);

		Map<String, Object> attributes = uiComponent.getAttributes();

		ResponseWriter responseWriter = facesContext.getResponseWriter();
		responseWriter.startElement("div", uiComponent);

		String id = uiComponent.getClientId(facesContext);
		responseWriter.writeAttribute("id", id, "id");

		StringBuilder classNames = new StringBuilder();

		// aui_deprecated.css: column
		classNames.append("column");

		String columnWidth = (String) attributes.get("columnWidth");

		if ((columnWidth != null) && (columnWidth.length() > 0)) {

			// aui_deprecated.css: w
			classNames.append(" w");
			classNames.append(columnWidth);
		}

		String cssClass = (String) attributes.get("cssClass");

		if ((cssClass != null) && (cssClass.length() > 0)) {
			classNames.append(StringPool.SPACE);
			classNames.append(cssClass);
		}

		String styleClass = (String) attributes.get("styleClass");

		if ((styleClass != null) && (styleClass.length() > 0)) {
			classNames.append(StringPool.SPACE);
			classNames.append(styleClass);
		}

		Boolean firstFlag = Boolean.FALSE;
		String first = (String) attributes.get("first");

		if (first != null) {

			if (first.trim().equalsIgnoreCase("true")) {
				firstFlag = true;
			}
		}

		if (firstFlag) {

			// aui_deprecated.css: column-first
			classNames.append(" column-first");
		}

		Boolean lastFlag = Boolean.FALSE;
		String last = (String) attributes.get("last");

		if (last != null) {

			if (last.trim().equalsIgnoreCase("true")) {
				lastFlag = true;
			}
		}

		if (lastFlag) {

			// aui_deprecated.css: column-last
			classNames.append(" column-last");
		}

		responseWriter.writeAttribute("class", classNames.toString(), null);
		responseWriter.startElement("div", null);
		classNames = new StringBuilder();

		// Used to be aui-column-content, but has been replaced by non-deprecated column-content
		classNames.append("column-content");

		if ((cssClass != null) && (cssClass.length() > 0)) {
			classNames.append(StringPool.SPACE);

			// "-content" classes may be deprecated
			classNames.append(ComponentUtil.appendToCssClasses(cssClass, "-content"));
		}

		if ((styleClass != null) && (styleClass.length() > 0)) {
			classNames.append(StringPool.SPACE);

			// "-content" classes may be deprecated
			classNames.append(ComponentUtil.appendToCssClasses(styleClass, "-content"));
		}

		responseWriter.writeAttribute("class", classNames.toString(), null);
	}

	@Override
	public void encodeEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException {
		super.encodeEnd(facesContext, uiComponent);

		ResponseWriter responseWriter = facesContext.getResponseWriter();
		responseWriter.endElement("div");
		responseWriter.endElement("div");
	}

}
