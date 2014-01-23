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
public class TextBoxListRenderer extends Renderer {

	// Private Constants
	private static final String CSS_CLASS = "cssClass";
	private static final String STYLE_CLASS = "styleClass";

	@Override
	public void encodeBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {
		super.encodeBegin(facesContext, uiComponent);

		Map<String, Object> attributes = uiComponent.getAttributes();

		ResponseWriter responseWriter = facesContext.getResponseWriter();
		responseWriter.startElement(StringPool.DIV, uiComponent);

		String id = uiComponent.getClientId(facesContext);
		responseWriter.writeAttribute(StringPool.ID, id, StringPool.ID);

		StringBuilder outerDivClassNames = new StringBuilder();
		outerDivClassNames.append("aui-widget aui-component aui-textboxlist ");

		String cssClass = (String) attributes.get(CSS_CLASS);

		if ((cssClass != null) && (cssClass.length() > 0)) {
			outerDivClassNames.append(StringPool.SPACE);
			outerDivClassNames.append(cssClass);
		}

		String styleClass = (String) attributes.get(STYLE_CLASS);

		if ((styleClass != null) && (styleClass.length() > 0)) {
			outerDivClassNames.append(StringPool.SPACE);
			outerDivClassNames.append(styleClass);
		}

		String autoComplete = (String) attributes.get("autoComplete");

		if (autoComplete == null) {
			autoComplete = StringPool.FALSE;
		}
		else {
			autoComplete = autoComplete.trim().toLowerCase();
		}

		if (autoComplete.length() > 0) {

			if (autoComplete.equals(StringPool.TRUE)) {
				outerDivClassNames.append(StringPool.SPACE);
				outerDivClassNames.append("aui-autocomplete");
			}

		}

		String tagSelector = (String) attributes.get("tagSelector");

		if (tagSelector == null) {
			tagSelector = StringPool.TRUE;
		}
		else {
			tagSelector = tagSelector.trim().toLowerCase();
		}

		if (tagSelector.length() > 0) {

			if (tagSelector.equals(StringPool.TRUE)) {
				outerDivClassNames.append(StringPool.SPACE);
				outerDivClassNames.append("aui-tagselector aui-tagselector-focused");
			}
		}

		responseWriter.writeAttribute(StringPool.CLASS, outerDivClassNames.toString(), null);
		responseWriter.startElement(StringPool.DIV, uiComponent);

		StringBuilder innerDivClassNames = new StringBuilder();
		innerDivClassNames.append("lfr-tags-selector-content  aui-textboxlist-content");

		if ((cssClass != null) && (cssClass.length() > 0)) {
			innerDivClassNames.append(StringPool.SPACE);
			innerDivClassNames.append(ComponentUtil.appendToCssClasses(cssClass, "-content"));
		}

		if ((styleClass != null) && (styleClass.length() > 0)) {
			innerDivClassNames.append(StringPool.SPACE);
			innerDivClassNames.append(ComponentUtil.appendToCssClasses(styleClass, "-content"));
		}

		if (autoComplete.length() > 0) {

			if (autoComplete.equals(StringPool.TRUE)) {
				innerDivClassNames.append(StringPool.SPACE);
				innerDivClassNames.append("aui-autocomplete-content");
			}
		}

		if (tagSelector.length() > 0) {

			if (tagSelector.equals(StringPool.TRUE)) {
				innerDivClassNames.append(StringPool.SPACE);
				innerDivClassNames.append("aui-tagselector-content");
			}
		}

		responseWriter.writeAttribute(StringPool.CLASS, innerDivClassNames.toString(), null);
		responseWriter.startElement(StringPool.LI, uiComponent);

		responseWriter.writeAttribute(StringPool.CLASS, "aui-helper-clearfix aui-textboxlistentry-holder", null);

	}

	@Override
	public void encodeEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException {
		super.encodeEnd(facesContext, uiComponent);

		ResponseWriter responseWriter = facesContext.getResponseWriter();
		responseWriter.endElement(StringPool.UL);
		responseWriter.endElement(StringPool.DIV);
		responseWriter.endElement(StringPool.DIV);
	}

}
