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

import java.io.IOException;

import javax.faces.application.ResourceDependencies;
import javax.faces.application.ResourceDependency;
import javax.faces.component.UIComponent;
import javax.faces.component.ValueHolder;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;

import com.liferay.faces.util.component.ClientComponent;
import com.liferay.faces.util.component.ComponentUtil;
import com.liferay.faces.util.lang.StringPool;

/**
 * @author  Vernon Singleton
 */
@FacesRenderer(componentFamily = OutputRemainingChars.COMPONENT_FAMILY, rendererType = OutputRemainingChars.RENDERER_TYPE)
@ResourceDependencies({	
	@ResourceDependency(library = "liferay-faces-alloy", name = "build/aui-css/css/bootstrap.min.css"),	
	@ResourceDependency(library = "liferay-faces-alloy", name = "build/aui/aui-min.js"), 
	@ResourceDependency(library = "liferay-faces-alloy", name = "liferay.js")
})
public class OutputRemainingCharsRenderer extends OutputRemainingCharsRendererBase {
	
	// Private Constants
	private static final String ALLOY_CLASS_NAME = "CharCounter";
	
	@Override
	public void encodeMarkupBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		ResponseWriter responseWriter = facesContext.getResponseWriter();
		ValueHolder valueHolder = (ValueHolder) uiComponent;
		Object value = valueHolder.getValue();

		OutputRemainingCharsAlloy outputRemainingCharsAlloy = (OutputRemainingCharsAlloy) uiComponent;

		if (value == null) {

			// They may have specified their own counter (or they did not specify a value attribute)
			if (OutputRemainingCharsUtil.isCounterSpecified(facesContext, outputRemainingCharsAlloy)) {
				// Let them use their own counter
			}

			// Otherwise, they did not specify a counter, let's write one out for them
			else {
				encodeRemainingValue(facesContext, responseWriter, uiComponent);
			}
		}

		// Otherwise, there is a value given, so we need to encode a counter
		else {
			encodeRemainingValue(facesContext, responseWriter, uiComponent);
		}
	}

	@Override
	public void encodeJavaScriptCustom(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		ResponseWriter responseWriter = facesContext.getResponseWriter();
		OutputRemainingChars outputRemainingChars = (OutputRemainingChars) uiComponent;

		encodeEventCallback(facesContext, responseWriter, "on", "maxLength", outputRemainingChars.getOnMaxlengthReached(),
			uiComponent);
		encodeEventCallback(facesContext, responseWriter, "once", "maxLength", outputRemainingChars.getOnceMaxlengthReached(),
			uiComponent);

	}
	
	@Override
	public void encodeMarkupEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException {
		// no-op
	}

	protected void encodeEventCallback(FacesContext facesContext, ResponseWriter responseWriter, String methodName,
		String eventName, String callback, UIComponent uiComponent) throws IOException {

		if (callback != null) {
			
			ClientComponent clientComponent = (ClientComponent) uiComponent;
			String clientVarName = ComponentUtil.getClientVarName(facesContext, clientComponent);
			String clientKey = clientComponent.getClientKey();

			if (clientKey == null) {
				clientKey = clientVarName;
			}
			
			encodeLiferayComponent(responseWriter, clientKey);
			
			responseWriter.write(StringPool.PERIOD);
			responseWriter.write(methodName);
			responseWriter.write(StringPool.OPEN_PARENTHESIS);
			responseWriter.write(StringPool.APOSTROPHE);
			responseWriter.write(eventName);
			responseWriter.write(StringPool.APOSTROPHE);
			responseWriter.write(StringPool.COMMA);
			responseWriter.write("function(event) {");
			responseWriter.write(callback);
			responseWriter.write(StringPool.CLOSE_CURLY_BRACE);
			responseWriter.write(StringPool.CLOSE_PARENTHESIS);
			responseWriter.write(StringPool.SEMICOLON);

		}
	}

	@Override
	protected void encodeOnMaxlengthReached(ResponseWriter responseWriter, OutputRemainingCharsAlloy outputRemainingCharsAlloy,
		String onMaxlengthReached, boolean first) throws IOException {
		// no-op
	}

	protected void encodeRemainingValue(FacesContext facesContext, ResponseWriter responseWriter,
		UIComponent uiComponent) throws IOException {

		OutputRemainingCharsAlloy outputRemainingCharsAlloy = (OutputRemainingCharsAlloy) uiComponent;
		String forComponent = outputRemainingCharsAlloy.getFor();

		if (forComponent != null) {

			UIComponent inputUIComponent = uiComponent.findComponent(forComponent);

			// If there is no input found, they may be using an html tag as an input. In that case, they will need to
			// specify maxLength on the outputRemainingChars component instead of their own html input. If there is an input
			// found, use its value for the calculation, if any.
			if ((inputUIComponent != null) && (inputUIComponent instanceof ValueHolder)) {

				Object maxLength = outputRemainingCharsAlloy.getMaxLength();

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

					String defaultCounterSpanId = OutputRemainingCharsUtil.getDefaultCounterSpanId(facesContext, uiComponent);
					OutputRemainingCharsResponseWriter outputRemainingCharsResponseWriter = new OutputRemainingCharsResponseWriter(responseWriter,
							uiComponent, defaultCounterSpanId, remainingCharacters.toString());
					
					super.encodeAll(facesContext, uiComponent, outputRemainingCharsResponseWriter);

				}
			}
		}
	}
	
	@Override
	public String getAlloyClassName() {
		return ALLOY_CLASS_NAME;
	}
	
	@Override
	public String getDelegateComponentFamily() {
		return OutputRemainingChars.DELEGATE_COMPONENT_FAMILY;
	}

	@Override
	public String getDelegateRendererType() {
		return OutputRemainingChars.DELEGATE_RENDERER_TYPE;
	}
}
