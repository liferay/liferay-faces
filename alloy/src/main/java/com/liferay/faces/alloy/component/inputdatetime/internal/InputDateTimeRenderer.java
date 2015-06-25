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
package com.liferay.faces.alloy.component.inputdatetime.internal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.faces.FactoryFinder;
import javax.faces.application.Application;
import javax.faces.application.ApplicationFactory;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.component.behavior.ClientBehavior;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import com.liferay.faces.alloy.component.button.Button;
import com.liferay.faces.alloy.component.icon.Icon;
import com.liferay.faces.alloy.component.inputdatetime.InputDateTime;
import com.liferay.faces.util.client.BrowserSniffer;
import com.liferay.faces.util.client.BrowserSnifferFactory;
import com.liferay.faces.util.component.ClientComponent;
import com.liferay.faces.util.component.Styleable;
import com.liferay.faces.util.factory.FactoryExtensionFinder;
import com.liferay.faces.util.lang.StringPool;
import com.liferay.faces.util.render.RendererUtil;


/**
 * @author  Kyle Stiemann
 */
public abstract class InputDateTimeRenderer extends InputDateTimeRendererBase {

	// Protected Constants
	protected static final String INPUT_SUFFIX = "_input";
	protected static final String NODE_EVENT_SIMULATE = "node-event-simulate";
	protected static final String VALUE_CHANGE = "valueChange";

	// Private Constants
	private static final String BOUNDING_BOX_SUFFIX = "_boundingBox";
	private static final String BUTTON_ON_CLICK_EVENT =
		"var input=document.getElementById('{0}');input.focus();input.click();";
	private static final String BUTTON_SUFFIX = "_button";
	private static final String CONTENT_BOX_SUFFIX = "_contentBox";

	@Override
	public void encodeMarkupBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		// Start the encoding of the outermost <div> element.
		ResponseWriter responseWriter = facesContext.getResponseWriter();
		String clientId = uiComponent.getClientId(facesContext);
		responseWriter.startElement(StringPool.DIV, uiComponent);

		// Encode the "id" attribute on the outermost <div> element.
		responseWriter.writeAttribute(StringPool.ID, clientId, StringPool.ID);

		// Encode the "class" and "style" attributes on the outermost <div> element.
		RendererUtil.encodeStyleable(responseWriter, (Styleable) uiComponent);

		// Start the encoding of the text input by delegating to the renderer from the JSF runtime.
		String inputClientId = clientId.concat(INPUT_SUFFIX);
		BrowserSnifferFactory browserSnifferFactory = (BrowserSnifferFactory) FactoryExtensionFinder.getFactory(
				BrowserSnifferFactory.class);
		BrowserSniffer browserSniffer = browserSnifferFactory.getBrowserSniffer(facesContext.getExternalContext());
		InputDateTime inputDateTime = (InputDateTime) uiComponent;
		InputDateTimeResponseWriter inputDateTimeResponseWriter = getInputDateTimeResponseWriter(responseWriter,
				inputClientId, isNative(browserSniffer, inputDateTime));
		super.encodeMarkupBegin(facesContext, uiComponent, inputDateTimeResponseWriter);
	}

	@Override
	public void encodeMarkupEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		ResponseWriter responseWriter = facesContext.getResponseWriter();
		String clientId = uiComponent.getClientId(facesContext);
		String inputClientId = clientId.concat(INPUT_SUFFIX);
		BrowserSnifferFactory browserSnifferFactory = (BrowserSnifferFactory) FactoryExtensionFinder.getFactory(
				BrowserSnifferFactory.class);
		BrowserSniffer browserSniffer = browserSnifferFactory.getBrowserSniffer(facesContext.getExternalContext());
		InputDateTime inputDateTime = (InputDateTime) uiComponent;
		InputDateTimeResponseWriter inputDateTimeResponseWriter = getInputDateTimeResponseWriter(responseWriter,
				inputClientId, isNative(browserSniffer, inputDateTime));
		super.encodeMarkupEnd(facesContext, uiComponent, inputDateTimeResponseWriter);

		// Determine whether or not the text input is enabled.
		boolean disabled = inputDateTime.isDisabled();

		// If the "showOn" attribute indicates that a button is to be rendered and the component is not being rendered
		// for a mobile browser, then render the button.
		String showOn = inputDateTime.getShowOn();

		if (("both".equals(showOn) || "button".equals(showOn)) && !isNative(browserSniffer, inputDateTime)) {
			ApplicationFactory applicationFactory = (ApplicationFactory) FactoryFinder.getFactory(
					FactoryFinder.APPLICATION_FACTORY);
			Application application = applicationFactory.getApplication();

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

				if ("button".equals(showOn)) {

					String buttonClientId = getButtonClientId(facesContext, inputDateTime);
					button.setId(buttonClientId);
				}
				else {

					// If the both the button and the input are supposed to trigger the datePicker, set the button's
					// onclick to focus and click the input.
					String onClick = BUTTON_ON_CLICK_EVENT.replace("{0}", inputClientId);
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
			responseWriter.writeAttribute(StringPool.ID, boundingBoxClientId, null);
			responseWriter.startElement(StringPool.DIV, uiComponent);

			String contentBoxClientId = clientId.concat(CONTENT_BOX_SUFFIX);
			responseWriter.writeAttribute(StringPool.ID, contentBoxClientId, null);
			responseWriter.endElement(StringPool.DIV);
			responseWriter.endElement(StringPool.DIV);
		}

		// Finish the encoding of the outermost </div> element.
		responseWriter.endElement(StringPool.DIV);
	}

	protected void encodeHiddenAttributesInputDateTime(FacesContext facesContext, ResponseWriter responseWriter,
		InputDateTime inputDateTime, boolean first) throws IOException {

		encodePopover(facesContext, responseWriter, inputDateTime, first);
		first = false;

		String triggerClientId;
		String showOn = inputDateTime.getShowOn();

		if ("button".equals(showOn)) {
			triggerClientId = getButtonClientId(facesContext, inputDateTime);
		}
		else {
			String clientId = inputDateTime.getClientId(facesContext);
			triggerClientId = clientId.concat(INPUT_SUFFIX);
		}

		encodeClientId(responseWriter, "trigger", triggerClientId, first);
		first = false;
	}

	protected void encodePopover(FacesContext facesContext, ResponseWriter responseWriter, InputDateTime inputDateTime,
		boolean first) throws IOException {

		// The popover attribute value provides the opportunity to specify a zIndex key:value pair via JSON
		// syntax. For example: "popover: {zIndex: 1}"
		encodeNonEscapedObject(responseWriter, "popover", StringPool.BLANK, first);
		responseWriter.write(StringPool.OPEN_CURLY_BRACE);

		Integer zIndex = inputDateTime.getzIndex();
		String zIndexString;

		if (zIndex != null) {
			zIndexString = zIndex.toString();
		}
		else {
			zIndexString = LIFERAY_Z_INDEX_TOOLTIP;
		}

		boolean popoverFirst = true;
		encodeNonEscapedObject(responseWriter, Z_INDEX, zIndexString, popoverFirst);
		popoverFirst = false;

		String clientId = inputDateTime.getClientId(facesContext);
		String boundingBoxClientId = clientId.concat(BOUNDING_BOX_SUFFIX);
		encodeClientId(responseWriter, BOUNDING_BOX, boundingBoxClientId, popoverFirst);
		popoverFirst = false;

		String contentBoxClientId = clientId.concat(CONTENT_BOX_SUFFIX);
		encodeClientId(responseWriter, CONTENT_BOX, contentBoxClientId, popoverFirst);
		popoverFirst = false;
		responseWriter.write(StringPool.CLOSE_CURLY_BRACE);
	}

	protected String getButtonClientId(FacesContext facesContext, ClientComponent clientComponent) {

		// Because the button is not in the component tree, its clientId is not known in advance.
		// Therefore the component's generated clientId with a "button" suffix must be used to create the
		// button's clientId and ensure that it is unique. However, UIComponent.setId() throws an
		// IllegalArgumentException if the id contains colons. To workaround this, the colons must be
		// replaced by underscores using ComponentUtil.getClientVarName().
		String inputDateClientVarName = getClientVarName(facesContext, clientComponent);

		// The JSF runtime's renderer does not write ids which are prefixed with
		// UIViewRoot.UNIQUE_ID_PREFIX ("j_id"). Therefore, prefix the id with an underscore in order to
		// force the the JSF runtime's renderer to write the id.
		return StringPool.UNDERLINE + inputDateClientVarName + BUTTON_SUFFIX;
	}

	protected abstract String getButtonIconName();

	@Override
	protected boolean isSandboxed(FacesContext facesContext, UIComponent uiComponent) {

		// In order to support the "lang" attribute of the YUI object, it is necessary to determine if the user has
		// specified a locale other than that of the server or view root. If so, then the javascript must be rendered
		// inline.
		InputDateTime inputDateTime = (InputDateTime) uiComponent;
		Locale locale = inputDateTime.getObjectAsLocale(inputDateTime.getLocale(facesContext));
		UIViewRoot viewRoot = facesContext.getViewRoot();
		Locale viewRootLocale = viewRoot.getLocale();

		return !locale.equals(viewRootLocale);
	}

	protected boolean isNative(BrowserSniffer browserSniffer, InputDateTime inputDateTime) {
		return browserSniffer.isMobile() && inputDateTime.isNativeWhenMobile();
	}

	protected abstract InputDateTimeResponseWriter getInputDateTimeResponseWriter(ResponseWriter responseWriter,
		String inputClientId, boolean nativeInputDateTime);

	protected abstract List<String> getModules(List<String> modules, FacesContext facesContext,
		InputDateTime inputDateTime);

	protected String[] getModules(String[] defaultModules, FacesContext facesContext, UIComponent uiComponent) {

		List<String> modules = new ArrayList<String>();
		BrowserSnifferFactory browserSnifferFactory = (BrowserSnifferFactory) FactoryExtensionFinder.getFactory(
				BrowserSnifferFactory.class);
		BrowserSniffer browserSniffer = browserSnifferFactory.getBrowserSniffer(facesContext.getExternalContext());
		InputDateTime inputDateTime = (InputDateTime) uiComponent;

		if (isNative(browserSniffer, inputDateTime)) {
			String nativeAlloyModuleName = defaultModules[0].concat("-native");
			modules.add(nativeAlloyModuleName);
		}
		else {

			modules.addAll(Arrays.asList(defaultModules));

			Map<String, List<ClientBehavior>> clientBehaviorMap = inputDateTime.getClientBehaviors();
			List<ClientBehavior> valueChangeClientBehaviors = clientBehaviorMap.get(VALUE_CHANGE);

			if ((valueChangeClientBehaviors != null) && !valueChangeClientBehaviors.isEmpty()) {
				modules.add(NODE_EVENT_SIMULATE);
			}

			modules = getModules(Collections.unmodifiableList(modules), facesContext, inputDateTime);
		}

		return modules.toArray(new String[] {});
	}

	@Override
	public String getYUIConfig(FacesContext facesContext, ResponseWriter responseWriter, UIComponent uiComponent)
		throws IOException {

		InputDateTime inputDateTime = (InputDateTime) uiComponent;
		Locale locale = inputDateTime.getObjectAsLocale(inputDateTime.getLocale(facesContext));

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(StringPool.OPEN_CURLY_BRACE);
		stringBuilder.append("lang");
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
