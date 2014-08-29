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
import java.util.Locale;

import javax.faces.FactoryFinder;
import javax.faces.application.Application;
import javax.faces.application.ApplicationFactory;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import com.liferay.faces.alloy.component.button.Button;
import com.liferay.faces.alloy.component.icon.Icon;
import com.liferay.faces.alloy.renderkit.AlloyRendererUtil;
import com.liferay.faces.util.component.ClientComponent;
import com.liferay.faces.util.component.ComponentUtil;
import com.liferay.faces.util.component.Styleable;
import com.liferay.faces.util.lang.StringPool;
import com.liferay.faces.util.render.RendererUtil;


/**
 * @author  Kyle Stiemann
 */
public abstract class InputDateTimeRenderer extends InputDateTimeRendererBase {

	// Protected Constants
	protected static final String BOUNDING_BOX_SUFFIX = "_boundingBox";
	protected static final String BUTTON = "button";
	protected static final String BUTTON_SUFFIX = "_button";
	protected static final String CONTENT_BOX_SUFFIX = "_contentBox";
	protected static final String INPUT_SUFFIX = "_input";

	// Private Constants
	private static final String BOTH = "both";
	private static final String BUTTON_ON_CLICK_EVENT =
		"var input=document.getElementById('{0}');input.focus();input.click();";
	private static final String LANG = "lang";
	private static final String POPOVER = "popover";
	private static final String TOKEN_0 = "{0}";
	private static final String TRIGGER = "trigger";

	@Override
	public void encodeMarkupBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		// Start the encoding of the outermost <div> element.
		ResponseWriter responseWriter = facesContext.getResponseWriter();
		responseWriter.startElement(StringPool.DIV, uiComponent);

		// Encode the "id" attribute on the outermost <div> element.
		String clientId = uiComponent.getClientId(facesContext);
		responseWriter.writeAttribute(StringPool.ID, clientId, StringPool.ID);

		// Encode the "class" and "style" attributes on the outermost <div> element.
		Styleable styleable = (Styleable) uiComponent;
		RendererUtil.encodeStyleable(responseWriter, styleable);

		// Start the encoding of the text input by delegating to the renderer from the JSF runtime.
		InputDateTimeResponseWriter inputDateTimeResponseWriter = new InputDateTimeResponseWriter(responseWriter,
				StringPool.INPUT, clientId.concat(INPUT_SUFFIX));
		super.encodeMarkupBegin(facesContext, uiComponent, inputDateTimeResponseWriter);
	}

	@Override
	public void encodeMarkupEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		// Finish the encoding of the text input by delegating to the renderer from the JSF runtime.
		ResponseWriter responseWriter = facesContext.getResponseWriter();
		String clientId = uiComponent.getClientId(facesContext);
		String inputClientId = clientId.concat(INPUT_SUFFIX);
		InputDateTimeResponseWriter inputDateTimeResponseWriter = new InputDateTimeResponseWriter(responseWriter,
				StringPool.INPUT, inputClientId);
		super.encodeMarkupEnd(facesContext, uiComponent, inputDateTimeResponseWriter);

		// Determine the escaped "id" of the text input.
		ApplicationFactory applicationFactory = (ApplicationFactory) FactoryFinder.getFactory(
				FactoryFinder.APPLICATION_FACTORY);
		Application application = applicationFactory.getApplication();

		// Determine whether or not the text input is enabled.
		InputDateTime inputDateTimeBase = (InputDateTime) uiComponent;
		boolean disabled = inputDateTimeBase.isDisabled();

		// If the "showOn" attribute indicates that a button is to be rendered, then
		String showOn = inputDateTimeBase.getShowOn();

		if (BOTH.equals(showOn) || BUTTON.equals(showOn)) {

			// Create an icon component that is to remain detached from the component tree.
			Icon icon = (Icon) application.createComponent(Icon.COMPONENT_TYPE);
			String buttonIconName = getButtonIconName();
			icon.setName(buttonIconName);

			// Create a button component that that is also to remain detached from the component tree.
			Button button = (Button) application.createComponent(Button.COMPONENT_TYPE);
			List<UIComponent> buttonChildren = button.getChildren();
			buttonChildren.add(icon);
			button.setDisabled(disabled);

			// If the component is enabled, then
			if (!disabled) {

				if (BUTTON.equals(showOn)) {

					String buttonClientId = getButtonClientId(facesContext, inputDateTimeBase);
					button.setId(buttonClientId);
				}
				else {

					// If the both the button and the input are supposed to trigger the datePicker, set the button's
					// onclick to focus and click the input.
					String onClick = BUTTON_ON_CLICK_EVENT.replace(TOKEN_0, inputClientId);
					button.setOnclick(onClick);
				}
			}

			// Invoke the button's renderer so that it renders itself and its child icon.
			button.encodeAll(facesContext);
		}

		// If the component is enabled, then create the boundingBox and contentBox of the picker.
		if (!disabled) {

			responseWriter.startElement(StringPool.DIV, uiComponent);

			String boundingBoxClientId = clientId.concat(BOUNDING_BOX_SUFFIX);
			responseWriter.writeAttribute(StringPool.ID, boundingBoxClientId, StringPool.ID);
			responseWriter.startElement(StringPool.DIV, uiComponent);

			String contentBoxClientId = clientId.concat(CONTENT_BOX_SUFFIX);
			responseWriter.writeAttribute(StringPool.ID, contentBoxClientId, StringPool.ID);
			responseWriter.endElement(StringPool.DIV);
			responseWriter.endElement(StringPool.DIV);
		}

		// Finish the encoding of the outermost </div> element.
		responseWriter.endElement(StringPool.DIV);
	}

	protected void encodeInputDateTimeHiddenAttributes(FacesContext facesContext, ResponseWriter responseWriter,
		InputDateTime inputDateTime, boolean first) throws IOException {

		encodePopover(facesContext, responseWriter, inputDateTime, first);
		first = false;

		String triggerClientId;
		String showOn = inputDateTime.getShowOn();

		if (BUTTON.equals(showOn)) {
			triggerClientId = getButtonClientId(facesContext, inputDateTime);
		}
		else {
			String clientId = inputDateTime.getClientId(facesContext);
			triggerClientId = clientId.concat(INPUT_SUFFIX);
		}

		encodeClientId(responseWriter, TRIGGER, triggerClientId, first);
		first = false;
	}

	protected void encodePopover(FacesContext facesContext, ResponseWriter responseWriter, InputDateTime inputDateTime,
		boolean first) throws IOException {

		// The popover attribute value provides the opportunity to specify a zIndex key:value pair via JSON
		// syntax. For example: "popover: {zIndex: 1}"
		encodeNonEscapedObject(responseWriter, POPOVER, StringPool.BLANK, first);
		responseWriter.write(StringPool.OPEN_CURLY_BRACE);

		Integer zIndex = inputDateTime.getzIndex();
		String zIndexString;

		if (zIndex != null) {
			zIndexString = zIndex.toString();
		}
		else {
			zIndexString = AlloyRendererUtil.LIFERAY_Z_INDEX_TOOLTIP;
		}

		boolean popoverFirst = true;
		encodeNonEscapedObject(responseWriter, Z_INDEX, zIndexString, popoverFirst);
		popoverFirst = false;

		String clientId = inputDateTime.getClientId(facesContext);
		String boundingBoxClientId = clientId.concat(BOUNDING_BOX_SUFFIX);
		encodeClientId(responseWriter, AlloyRendererUtil.BOUNDING_BOX, boundingBoxClientId, popoverFirst);
		popoverFirst = false;

		String contentBoxClientId = clientId.concat(CONTENT_BOX_SUFFIX);
		encodeClientId(responseWriter, AlloyRendererUtil.CONTENT_BOX, contentBoxClientId, popoverFirst);
		popoverFirst = false;
		responseWriter.write(StringPool.CLOSE_CURLY_BRACE);
	}

	protected String getButtonClientId(FacesContext facesContext, ClientComponent clientComponent) {

		// Because the button is not in the component tree, its clientId is not known in advance.
		// Therefore the component's generated clientId with a "button" suffix must be used to create the
		// button's clientId and ensure that it is unique. However, UIComponent.setId() throws an
		// IllegalArgumentException if the id contains colons. To workaround this, the colons must be
		// replaced by underscores using ComponentUtil.getClientVarName().
		String inputDateClientVarName = ComponentUtil.getClientVarName(facesContext, clientComponent);

		// The JSF runtime's renderer does not write ids which are prefixed with
		// UIViewRoot.UNIQUE_ID_PREFIX ("j_id"). Therefore, prefix the id with an underscore in order to
		// force the the JSF runtime's renderer to write the id.
		return StringPool.UNDERLINE + inputDateClientVarName + BUTTON_SUFFIX;
	}

	protected abstract String getButtonIconName();

	@Override
	protected boolean isForceInline(FacesContext facesContext, UIComponent uiComponent) {

		// In order to support the "lang" attribute of the YUI object, it is necessary to determine if the user has
		// specified a locale other than that of the server or view root. If so, then the javascript must be rendered
		// inline.
		InputDateTime inputDateTime = (InputDateTime) uiComponent;
		Locale locale = InputDateTimeUtil.getObjectAsLocale(inputDateTime.getLocale(facesContext));
		UIViewRoot viewRoot = facesContext.getViewRoot();
		Locale viewRootLocale = viewRoot.getLocale();

		return !locale.equals(viewRootLocale);
	}

	@Override
	public String getYUIConfig(FacesContext facesContext, ResponseWriter responseWriter, UIComponent uiComponent)
		throws IOException {

		InputDateTime inputDateTime = (InputDateTime) uiComponent;
		Locale locale = InputDateTimeUtil.getObjectAsLocale(inputDateTime.getLocale(facesContext));

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(StringPool.OPEN_CURLY_BRACE);
		stringBuilder.append(LANG);
		stringBuilder.append(StringPool.COLON);

		// RFC 1766 requires the subtags of locales to be delimited by hyphens rather than underscores.
		// http://www.faqs.org/rfcs/rfc1766.html
		stringBuilder.append(StringPool.APOSTROPHE);

		String localeString = locale.toString().replaceAll(StringPool.UNDERLINE, StringPool.DASH);
		stringBuilder.append(localeString);
		stringBuilder.append(StringPool.APOSTROPHE);
		stringBuilder.append(StringPool.CLOSE_CURLY_BRACE);

		return stringBuilder.toString();
	}
}
