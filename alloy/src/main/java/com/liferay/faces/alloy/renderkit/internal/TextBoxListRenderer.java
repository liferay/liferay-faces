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
package com.liferay.faces.alloy.renderkit.internal;

import java.io.IOException;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.Renderer;

import com.liferay.faces.util.component.ComponentUtil;


/**
 * @author      Neil Griffin
 * @deprecated  No replacement available.
 */
@Deprecated
public class TextBoxListRenderer extends Renderer {

	// Private Constants
	private static final String CSS_CLASS = "cssClass";
	private static final String STYLE_CLASS = "styleClass";

	@Override
	public void encodeBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {
		super.encodeBegin(facesContext, uiComponent);

		Map<String, Object> attributes = uiComponent.getAttributes();

		ResponseWriter responseWriter = facesContext.getResponseWriter();
		responseWriter.startElement("div", uiComponent);

		String id = uiComponent.getClientId(facesContext);
		responseWriter.writeAttribute("id", id, "id");

		StringBuilder outerDivClassNames = new StringBuilder();

//       aui-widget not found in 6.2
//       aui-component found in 6.2 only in javascript TODO
//       aui-textboxlist not found in 6.2
		outerDivClassNames.append("aui-widget aui-component aui-textboxlist ");

		String cssClass = (String) attributes.get(CSS_CLASS);

		if ((cssClass != null) && (cssClass.length() > 0)) {
			outerDivClassNames.append(" ");
			outerDivClassNames.append(cssClass);
		}

		String styleClass = (String) attributes.get(STYLE_CLASS);

		if ((styleClass != null) && (styleClass.length() > 0)) {
			outerDivClassNames.append(" ");
			outerDivClassNames.append(styleClass);
		}

		String autoComplete = (String) attributes.get("autoComplete");

		if (autoComplete == null) {
			autoComplete = "false";
		}
		else {
			autoComplete = autoComplete.trim().toLowerCase();
		}

		if (autoComplete.length() > 0) {

			if (autoComplete.equals("true")) {
				outerDivClassNames.append(" ");
//               aui-autocomplete not found in 6.2
				outerDivClassNames.append("aui-autocomplete");
			}

		}

		String tagSelector = (String) attributes.get("tagSelector");

		if (tagSelector == null) {
			tagSelector = "true";
		}
		else {
			tagSelector = tagSelector.trim().toLowerCase();
		}

		if (tagSelector.length() > 0) {

			if (tagSelector.equals("true")) {
				outerDivClassNames.append(" ");

				// tagselector tagselector-focused ARE in 6.2 (these classes were aui-tagselector
				// aui-tagselector-focused) TODO
				outerDivClassNames.append("tagselector tagselector-focused");
			}
		}

		responseWriter.writeAttribute("class", outerDivClassNames.toString(), null);
		responseWriter.startElement("div", uiComponent);

		StringBuilder innerDivClassNames = new StringBuilder();

		// aui_deprecated.css: textboxlist-content
		innerDivClassNames.append("lfr-tags-selector-content textboxlist-content");

		if ((cssClass != null) && (cssClass.length() > 0)) {
			innerDivClassNames.append(" ");
			innerDivClassNames.append(ComponentUtil.appendToCssClasses(cssClass, "-content"));
		}

		if ((styleClass != null) && (styleClass.length() > 0)) {
			innerDivClassNames.append(" ");
			innerDivClassNames.append(ComponentUtil.appendToCssClasses(styleClass, "-content"));
		}

		if (autoComplete.length() > 0) {

			if (autoComplete.equals("true")) {
				innerDivClassNames.append(" ");

				// aui_deprecated.css: autocomplete-content
				innerDivClassNames.append("autocomplete-content");
			}
		}

		if (tagSelector.length() > 0) {

			if (tagSelector.equals("true")) {
				innerDivClassNames.append(" ");

				// tagselector-content IS in 6.2 (these classes were tagselector-content) TODO
				innerDivClassNames.append("aui-tagselector-content");
			}
		}

		responseWriter.writeAttribute("class", innerDivClassNames.toString(), null);
		responseWriter.startElement("ul", uiComponent);

		responseWriter.writeAttribute("class",

			// aui_deprecated.css: helper-clearfix textboxlistentry-holder
			"helper-clearfix textboxlistentry-holder", null);

	}

	@Override
	public void encodeEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException {
		super.encodeEnd(facesContext, uiComponent);

		ResponseWriter responseWriter = facesContext.getResponseWriter();
		responseWriter.endElement("ul");
		responseWriter.endElement("div");
		responseWriter.endElement("div");
	}

}
