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

import com.liferay.faces.alloy.util.AlloyUtil;
import com.liferay.faces.alloy.util.StringConstants;


/**
 * @author  Neil Griffin
 */
public class TextBoxListRenderer extends Renderer {

	@Override
	public void encodeBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {
		super.encodeBegin(facesContext, uiComponent);

		Map<String, Object> attributes = uiComponent.getAttributes();

		ResponseWriter responseWriter = facesContext.getResponseWriter();
		responseWriter.startElement(StringConstants.ELEMENT_DIV, uiComponent);

		String id = uiComponent.getClientId(facesContext);
		responseWriter.writeAttribute(StringConstants.ATTRIBUTE_ID, id, StringConstants.ATTRIBUTE_ID);

		StringBuilder outerDivClassNames = new StringBuilder();

//       aui-widget not found in 6.2
//       aui-component found in 6.2 only in javascript TODO
//       aui-textboxlist not found in 6.2
		outerDivClassNames.append("aui-widget aui-component aui-textboxlist ");

		String cssClass = (String) attributes.get(StringConstants.ATTRIBUTE_CSS_CLASS);

		if ((cssClass != null) && (cssClass.length() > 0)) {
			outerDivClassNames.append(StringConstants.CHAR_SPACE);
			outerDivClassNames.append(cssClass);
		}

		String styleClass = (String) attributes.get(StringConstants.ATTRIBUTE_STYLE_CLASS);

		if ((styleClass != null) && (styleClass.length() > 0)) {
			outerDivClassNames.append(StringConstants.CHAR_SPACE);
			outerDivClassNames.append(styleClass);
		}

		String autoComplete = (String) attributes.get("autoComplete");

		if (autoComplete == null) {
			autoComplete = StringConstants.BOOLEAN_FALSE;
		}
		else {
			autoComplete = autoComplete.trim().toLowerCase();
		}

		if (autoComplete.length() > 0) {

			if (autoComplete.equals(StringConstants.BOOLEAN_TRUE)) {
				outerDivClassNames.append(StringConstants.CHAR_SPACE);
//               aui-autocomplete not found in 6.2
				outerDivClassNames.append("aui-autocomplete");
			}

		}

		String tagSelector = (String) attributes.get("tagSelector");

		if (tagSelector == null) {
			tagSelector = StringConstants.BOOLEAN_TRUE;
		}
		else {
			tagSelector = tagSelector.trim().toLowerCase();
		}

		if (tagSelector.length() > 0) {

			if (tagSelector.equals(StringConstants.BOOLEAN_TRUE)) {
				outerDivClassNames.append(StringConstants.CHAR_SPACE);

				// tagselector tagselector-focused ARE in 6.2 (these classes were aui-tagselector
				// aui-tagselector-focused) TODO
				outerDivClassNames.append("tagselector tagselector-focused");
			}
		}

		responseWriter.writeAttribute(StringConstants.ATTRIBUTE_CLASS, outerDivClassNames.toString(), null);
		responseWriter.startElement(StringConstants.ELEMENT_DIV, uiComponent);

		StringBuilder innerDivClassNames = new StringBuilder();

		// aui_deprecated.css: textboxlist-content
		innerDivClassNames.append("lfr-tags-selector-content textboxlist-content");

		if ((cssClass != null) && (cssClass.length() > 0)) {
			innerDivClassNames.append(StringConstants.CHAR_SPACE);
			innerDivClassNames.append(AlloyUtil.appendToCssClasses(cssClass, "-content"));
		}

		if ((styleClass != null) && (styleClass.length() > 0)) {
			innerDivClassNames.append(StringConstants.CHAR_SPACE);
			innerDivClassNames.append(AlloyUtil.appendToCssClasses(styleClass, "-content"));
		}

		if (autoComplete.length() > 0) {

			if (autoComplete.equals(StringConstants.BOOLEAN_TRUE)) {
				innerDivClassNames.append(StringConstants.CHAR_SPACE);

				// aui_deprecated.css: autocomplete-content
				innerDivClassNames.append("autocomplete-content");
			}
		}

		if (tagSelector.length() > 0) {

			if (tagSelector.equals(StringConstants.BOOLEAN_TRUE)) {
				innerDivClassNames.append(StringConstants.CHAR_SPACE);

				// tagselector-content IS in 6.2 (these classes were tagselector-content) TODO
				innerDivClassNames.append("aui-tagselector-content");
			}
		}

		responseWriter.writeAttribute(StringConstants.ATTRIBUTE_CLASS, innerDivClassNames.toString(), null);
		responseWriter.startElement(StringConstants.ELEMENT_UL, uiComponent);

		responseWriter.writeAttribute(StringConstants.ATTRIBUTE_CLASS,

			// aui_deprecated.css: helper-clearfix textboxlistentry-holder
			"helper-clearfix textboxlistentry-holder", null);

	}

	@Override
	public void encodeEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException {
		super.encodeEnd(facesContext, uiComponent);

		ResponseWriter responseWriter = facesContext.getResponseWriter();
		responseWriter.endElement(StringConstants.ELEMENT_UL);
		responseWriter.endElement(StringConstants.ELEMENT_DIV);
		responseWriter.endElement(StringConstants.ELEMENT_DIV);
	}

}
