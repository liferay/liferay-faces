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
package com.liferay.faces.alloy.component.outputremainingchars;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlInputTextarea;
import javax.faces.context.FacesContext;

import com.liferay.faces.util.component.ComponentUtil;
import com.liferay.faces.util.lang.StringPool;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Vernon Singleton
 */
@FacesComponent(value = OutputRemainingChars.COMPONENT_TYPE)
public class OutputRemainingChars extends OutputRemainingCharsBase {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(OutputRemainingChars.class);
	
	// Public Constants
	public static final String COMPONENT_TYPE =
		"com.liferay.faces.alloy.component.outputremainingchars.OutputRemainingChars";
	public static final String RENDERER_TYPE =
		"com.liferay.faces.alloy.component.outputremainingchars.OutputRemainingCharsRenderer";
	public static final String STYLE_CLASS_NAME = "alloy-output-remaining-chars";

	public OutputRemainingChars() {
		super();
		setRendererType(RENDERER_TYPE);
	}

	// Returns the clientId of the component that the "for" attribute points to.
	protected String getForClientId(FacesContext facesContext) {

		String forClientId = null;

		String forComponent = getFor();

		if (forComponent != null) {
			UIComponent uiComponent = findComponent(forComponent);

			if (uiComponent != null) {
				forClientId = uiComponent.getClientId(facesContext);
			}
		}

		return forClientId;
	}

	public String getForClientIdEscaped(FacesContext facesContext) {
		return StringPool.POUND + ComponentUtil.escapeClientId(getForClientId(facesContext));
	}

	@Override
	public Object getMaxLength() {
		Object maxLength = super.getMaxLength();

		// If the developer has not specified a maxLength on the outputRemainingChars, then
		// try to get the maxlength from the associated input component.
		if (maxLength == null) {

			String forComponent = getFor();

			if (forComponent == null) {
				logger.error("getMaxLength: Please specify a 'for' attribute for the outputRemainingChars component.");
			} else {
				UIComponent inputComponent = findComponent(forComponent);
			
				if (inputComponent != null) {
	
					if (inputComponent instanceof HtmlInputText) {
						HtmlInputText htmlInputText = (HtmlInputText) inputComponent;
						maxLength = htmlInputText.getMaxlength();
					}
					else if (inputComponent instanceof HtmlInputTextarea) {
						HtmlInputTextarea htmlInputTextarea = (HtmlInputTextarea) inputComponent;
						maxLength = htmlInputTextarea.getAttributes().get("maxlength");
					}
				}
			}
		}

		return maxLength;
	}

	@Override
	public String getStyleClass() {

		String styleClass = (String) getStateHelper().eval(STYLE_CLASS, null);

		return ComponentUtil.concatCssClasses(styleClass, STYLE_CLASS_NAME);
	}

	@Override
	public Object getValue() {
		Object value = super.getValue();

		// Specify a default value
		if (value == null) {
			value = "{0}";
		}

		return value;
	}
}
