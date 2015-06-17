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
package com.liferay.faces.alloy.component.outputremainingchars.internal;

import java.io.IOException;

import javax.faces.application.ResourceDependencies;
import javax.faces.application.ResourceDependency;
import javax.faces.component.UIComponent;
import javax.faces.component.UINamingContainer;
import javax.faces.component.ValueHolder;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;

import com.liferay.faces.alloy.component.outputremainingchars.OutputRemainingChars;
import com.liferay.faces.util.lang.StringPool;


/**
 * @author  Vernon Singleton
 */
//J-
@FacesRenderer(
	componentFamily = OutputRemainingChars.COMPONENT_FAMILY, rendererType = OutputRemainingChars.RENDERER_TYPE
)
@ResourceDependencies(
	{
		@ResourceDependency(library = "liferay-faces-reslib", name = "build/aui-css/css/bootstrap.min.css"),
		@ResourceDependency(library = "liferay-faces-reslib", name = "build/aui/aui-min.js"),
		@ResourceDependency(library = "liferay-faces-reslib", name = "liferay.js")
	}
)
//J+
public class OutputRemainingCharsRenderer extends OutputRemainingCharsRendererBase {

	// Private Constants
	private static final String COUNTER = "counter";
	private static final String ALLOY_MAX_LENGTH_EVENT_NAME = "maxLength";

	@Override
	public void encodeJavaScriptCustom(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		ResponseWriter responseWriter = facesContext.getResponseWriter();
		OutputRemainingChars outputRemainingChars = (OutputRemainingChars) uiComponent;
		String clientVarName = getClientVarName(facesContext, outputRemainingChars);
		String clientKey = outputRemainingChars.getClientKey();

		if (clientKey == null) {
			clientKey = clientVarName;
		}

		String onMaxlengthReached = outputRemainingChars.getOnMaxlengthReached();
		String onceMaxlengthReached = outputRemainingChars.getOnceMaxlengthReached();

		if ((onMaxlengthReached != null) || (onceMaxlengthReached != null)) {
			encodeLiferayComponentVar(responseWriter, clientVarName, clientKey);

			if (onMaxlengthReached != null) {
				encodeEventCallback(responseWriter, clientVarName, StringPool.ON, ALLOY_MAX_LENGTH_EVENT_NAME,
					onMaxlengthReached);
			}

			if (onceMaxlengthReached != null) {
				encodeEventCallback(responseWriter, clientVarName, StringPool.ONCE, ALLOY_MAX_LENGTH_EVENT_NAME,
					onceMaxlengthReached);
			}
		}
	}

	@Override
	public void encodeMarkupBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {
		ResponseWriter responseWriter = facesContext.getResponseWriter();
		OutputRemainingChars outputRemainingChars = (OutputRemainingChars) uiComponent;
		String forComponent = outputRemainingChars.getFor();

		if (forComponent != null) {

			UIComponent inputUIComponent = uiComponent.findComponent(forComponent);

			// If there is no input found, they may be using an html tag as an input. In that case, they will need to
			// specify maxLength on the outputRemainingChars component instead of their own html input. If there is an
			// input found, use its value for the calculation, if any.
			if ((inputUIComponent != null) && (inputUIComponent instanceof ValueHolder)) {

				Object maxLength = outputRemainingChars.getMaxLength();

				if (maxLength != null) {
					Long max = new Long(maxLength.toString());
					Long givenCharacters = 0L;

					ValueHolder inputValueHolder = (ValueHolder) inputUIComponent;
					String inputValue = (String) inputValueHolder.getValue();

					if (inputValue != null) {
						givenCharacters = new Long(inputValue.length());
					}

					Long remainingCharacters = max - givenCharacters;

					if (remainingCharacters < 0) {
						remainingCharacters = 0L;
					}

					char separatorChar = UINamingContainer.getSeparatorChar(facesContext);
					String defaultCounterSpanId = uiComponent.getClientId(facesContext) + separatorChar + COUNTER;
					OutputRemainingCharsResponseWriter outputRemainingCharsResponseWriter =
						new OutputRemainingCharsResponseWriter(responseWriter, uiComponent, defaultCounterSpanId,
							remainingCharacters.toString());

					super.encodeAll(facesContext, uiComponent, outputRemainingCharsResponseWriter);
				}
			}
		}
	}

	@Override
	public void encodeMarkupEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException {
		// no-op since the delegate renderer's encodeEnd() method must not be invoked.
	}

	@Override
	protected void encodeHiddenAttributes(FacesContext facesContext, ResponseWriter responseWriter,
		OutputRemainingChars outputRemainingChars, boolean first) throws IOException {

		char separatorChar = UINamingContainer.getSeparatorChar(facesContext);
		String counter = outputRemainingChars.getClientId(facesContext) + separatorChar + COUNTER;
		encodeClientId(responseWriter, COUNTER, counter, first);
		first = false;
	}
}
