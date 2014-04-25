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
package com.liferay.faces.alloy.component.charcounter;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlInputTextarea;
import javax.faces.context.FacesContext;

import com.liferay.faces.util.component.ComponentUtil;
import com.liferay.faces.util.lang.StringPool;


/**
 * @author  Vernon Singleton
 */
@FacesComponent(value = CharCounter.COMPONENT_TYPE)
public class CharCounter extends CharCounterBase {

	// Public Constants
	public static final String COMPONENT_TYPE = "com.liferay.faces.alloy.component.charcounter.CharCounter";
	public static final String RENDERER_TYPE = "com.liferay.faces.alloy.component.charcounter.CharCounterRenderer";

	public CharCounter() {
		super();
		setRendererType(RENDERER_TYPE);
	}

	// This returns the escaped counter, using the id assigned by the developer or the id assigned by JSF.
	@Override
	public String getCounter() {
		String counter = super.getCounter();

		// If no counter attribute is given, establish a default for the renderer to use.
		if (counter == null) {
			counter = CharCounterUtil.getDefaultCounterEscaped(getFacesContext(), this);
		}

		return counter;
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
	public String getInput() {
		String input = super.getInput();

		// if no input attribute is given, use the for attribute, if any.
		if (input == null) {
			input = getForClientIdEscaped(getFacesContext());
		}

		return input;
	}

	@Override
	public Object getMaxLength() {
		Object maxLength = super.getMaxLength();

		// If the developer has not specified a maxLength on the charCounter, then
		// try to get the maxlength from the input component that is being pointed to.
		if (maxLength == null) {

			String forComponent = getFor();

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

		return maxLength;
	}
	
	@Override
	public Object getValue() {
		
		boolean counterSpecified = CharCounterUtil.isCounterSpecified(getFacesContext(), this);
		Object value = super.getValue();
		
		// specify a default value
		if ((!counterSpecified) && value == null) {
			value = "{0}";
		}
		
		return value;
	}
}
