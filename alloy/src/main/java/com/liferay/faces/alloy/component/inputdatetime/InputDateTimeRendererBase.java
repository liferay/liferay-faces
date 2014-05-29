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
package com.liferay.faces.alloy.component.inputdatetime;

import java.io.IOException;
import java.util.List;

import javax.faces.FactoryFinder;
import javax.faces.application.Application;
import javax.faces.application.ApplicationFactory;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import com.liferay.faces.alloy.component.button.Button;
import com.liferay.faces.alloy.component.icon.Icon;
import com.liferay.faces.alloy.component.inputtext.InputTextRenderer;
import com.liferay.faces.util.component.ComponentUtil;
import com.liferay.faces.util.component.Styleable;
import com.liferay.faces.util.lang.StringPool;
import com.liferay.faces.util.render.RendererUtil;


/**
 * @author  Kyle Stiemann
 */
public abstract class InputDateTimeRendererBase extends InputTextRenderer {

	// Protected Constants
	protected static final String BUTTON = "button";

	// Private Constants
	private static final String BOTH = "both";
	private static final String BUTTON_ON_CLICK_EVENT =
		"var input=document.getElementById('{0}');input.focus();input.click();";
	private static final String TOKEN_0 = "{0}";

	@Override
	public void encodeBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		// Start the encoding of the outermost <span> element.
		ResponseWriter responseWriter = facesContext.getResponseWriter();
		responseWriter.startElement(StringPool.SPAN, uiComponent);

		// Encode the "id" attribute on the outermost <span> element.
		String clientId = uiComponent.getClientId(facesContext);
		responseWriter.writeAttribute(StringPool.ID, clientId, StringPool.ID);

		// Encode the "class" and "style" attributes on the outermost <span> element.
		Styleable styleable = (Styleable) uiComponent;
		RendererUtil.encodeStyleable(responseWriter, styleable);

		// Start the encoding of the text input by delegating to the renderer from the JSF runtime.
		InputDateTimeResponseWriter inputDateTimeResponseWriter = new InputDateTimeResponseWriter(responseWriter);
		super.encodeBegin(facesContext, uiComponent, inputDateTimeResponseWriter);
	}

	@Override
	public void encodeEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		// Finish the encoding of the text input by delegating to the renderer from the JSF runtime.
		ResponseWriter responseWriter = facesContext.getResponseWriter();
		InputDateTimeResponseWriter inputDateTimeResponseWriter = new InputDateTimeResponseWriter(responseWriter);
		super.encodeEnd(facesContext, uiComponent, inputDateTimeResponseWriter);

		// Determine the escaped "id" of the text input.
		ApplicationFactory applicationFactory = (ApplicationFactory) FactoryFinder.getFactory(
				FactoryFinder.APPLICATION_FACTORY);
		Application application = applicationFactory.getApplication();
		String clientId = uiComponent.getClientId(facesContext);
		String inputClientId = clientId + InputDateTimeUtil.getInputIdSuffix(facesContext);
		String escapedInputClientId = ComponentUtil.escapeClientId(inputClientId);
		String trigger = escapedInputClientId;

		// Determine whether or not the text input is enabled.
		InputDateTimeBase inputDateTimeBase = (InputDateTimeBase) uiComponent;
		boolean disabled = inputDateTimeBase.isDisabled();

		// If the "showOn" attribute indicates that a button is to be rendered, then
		String showOn = inputDateTimeBase.getShowOn();

		if (showOn.equals(BOTH) || showOn.equals(BUTTON)) {

			// Create an icon component that is to remain detached from the component tree.
			Icon icon = (Icon) application.createComponent(Icon.COMPONENT_TYPE);
			String buttonIconName = inputDateTimeBase.getButtonIconName();

			if (buttonIconName != null) {
				icon.setName(buttonIconName);
			}

			// Create a button component that that is also to remain detached from the component tree.
			Button button = (Button) application.createComponent(Button.COMPONENT_TYPE);
			List<UIComponent> buttonChildren = button.getChildren();
			buttonChildren.add(icon);
			button.setDisabled(disabled);

			// If the component is enabled, then
			if (!disabled) {

				if (showOn.equals(BUTTON)) {

					// Because the button is not in the component tree, its clientId is not generated. Therefore the
					// component's generated clientId must be used to create the button's clientId and ensure that it is
					// unique. However, UIComponent.setId() throws an IllegalArgumentException if the id contains
					// colons. To workaround this, the colons must be replaced by underscores.
					String underlineClientId = clientId.replace(StringPool.COLON, StringPool.UNDERLINE);

					// Prefix the id with an underscore in order to ensure that the id always gets written, and append
					// "button" to the id to ensure that it is unique.
					StringBuilder buttonIdStringBuilder = new StringBuilder();
					buttonIdStringBuilder.append(StringPool.UNDERLINE);
					buttonIdStringBuilder.append(underlineClientId);
					buttonIdStringBuilder.append(StringPool.UNDERLINE);
					buttonIdStringBuilder.append(BUTTON);

					String buttonId = buttonIdStringBuilder.toString();
					button.setId(buttonId);

					// Since the pickDate's trigger needs to be set directly, prefix the escaped clientId of the
					// button with the "#" symbol.
					String buttonClientId = button.getClientId(facesContext);
					String escapedButtonClientId = ComponentUtil.escapeClientId(buttonClientId);
					String buttonTrigger = escapedButtonClientId;
					trigger = buttonTrigger;
				}
				else {

					// If the both the button and the input are supposed to trigger the pickDate, set the button's
					// onclick to focus and click the input.
					String onClick = BUTTON_ON_CLICK_EVENT.replace(TOKEN_0, inputClientId);
					button.setOnclick(onClick);
				}
			}

			// Invoke the button's renderer so that it renders itself and its child icon.
			button.encodeAll(facesContext);
		}

		// Finish the encoding of the outermost </span> element.
		responseWriter.endElement(StringPool.SPAN);

		// Since the pickDate's trigger needs to be set directly, prefix the escaped clientId of the trigger with the
		// "#" symbol.
		trigger = StringPool.POUND + trigger;

		// If the component is enabled, then create either a date or time picker (that is to remain detached from the
		// component tree) and invoke its corresponding renderer.
		if (!disabled) {
			encodePicker(facesContext, uiComponent, application, trigger, escapedInputClientId);
		}
	}

	protected abstract void encodePicker(FacesContext facesContext, UIComponent uiComponent, Application application,
		String trigger, String escapedClientId) throws IOException;
}
