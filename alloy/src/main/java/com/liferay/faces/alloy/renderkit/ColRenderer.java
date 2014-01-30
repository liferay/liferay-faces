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

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.Renderer;

import com.liferay.faces.alloy.component.AUICol;
import com.liferay.faces.util.lang.StringPool;


/**
 * @author  Neil Griffin
 * @author  Kyle Stiemann
 */
public class ColRenderer extends Renderer {

	@Override
	public void encodeBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		super.encodeBegin(facesContext, uiComponent);

		AUICol auiCol = (AUICol) uiComponent;

		ResponseWriter responseWriter = facesContext.getResponseWriter();
		responseWriter.startElement("div", uiComponent);

		String id = auiCol.getClientId(facesContext);
		responseWriter.writeAttribute("id", id, null);

		StringBuilder classNames = new StringBuilder();

		Integer width = auiCol.getWidth();
		Integer span = auiCol.getSpan();

		if (span != null) {

			if ((span < 1) || (span > AUICol.COLUMNS)) {
				throw new IOException("span number must be between 1 and " + AUICol.COLUMNS);
			}
		}

		if (width != null) {

			if ((width < 1) || (width > 100)) {
				throw new IOException("width must be between 1 and 100");
			}

			span = getColumnUnitSize(width);
		}

		classNames.append("span");
		classNames.append(span);

		Integer offsetWidth = auiCol.getOffsetWidth();
		Integer offset = auiCol.getOffset();

		if (offset != null) {

			if ((offset < 1) || (offset > AUICol.COLUMNS)) {
				throw new IOException("offset must be between 1 and " + AUICol.COLUMNS);
			}
		}

		if (offsetWidth != null) {

			if ((offsetWidth < 1) || (offsetWidth > 100)) {
				throw new IOException("offsetWidth must be between 1 and 100");
			}

			offset = getColumnUnitSize(offsetWidth);
		}

		if (offset != null) {
			classNames.append(" aui-offset");
			classNames.append(offset);
		}

		String cssClass = auiCol.getCssClass();

		if ((cssClass != null) && (cssClass.length() > 0)) {
			classNames.append(StringPool.SPACE);
			classNames.append(cssClass);
		}

		String styleClass = auiCol.getStyleClass();

		if ((styleClass != null) && (styleClass.length() > 0)) {
			classNames.append(StringPool.SPACE);
			classNames.append(styleClass);
		}

		responseWriter.writeAttribute("class", classNames.toString(), null);

		Boolean first = auiCol.isFirst();

		if (first != null) {
			responseWriter.writeAttribute("first", first.toString(), null);
		}

		Boolean last = auiCol.isLast();

		if (last != null) {
			responseWriter.writeAttribute("last", last.toString(), null);
		}
	}

	@Override
	public void encodeEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		super.encodeEnd(facesContext, uiComponent);

		ResponseWriter responseWriter = facesContext.getResponseWriter();
		responseWriter.endElement("div");
	}

	protected Integer getColumnUnitSize(Integer width) {
		return (int) Math.round(AUICol.COLUMNS * ((double) width / 100));
	}
}
