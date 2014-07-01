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
package com.liferay.faces.alloy.component.accordion;

import java.io.IOException;
import java.util.List;

import javax.faces.application.ResourceDependencies;
import javax.faces.application.ResourceDependency;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;

import com.liferay.faces.alloy.component.tab.Tab;
import com.liferay.faces.alloy.renderkit.AlloyRendererUtil;
import com.liferay.faces.util.component.Styleable;
import com.liferay.faces.util.lang.StringPool;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;
import com.liferay.faces.util.render.RendererUtil;


/**
 * @author  Vernon Singleton
 */
//J-
@FacesRenderer(componentFamily = Accordion.COMPONENT_FAMILY, rendererType = Accordion.RENDERER_TYPE)
@ResourceDependencies(
	{
		@ResourceDependency(library = "liferay-faces-alloy", name = "build/aui-css/css/bootstrap.min.css"),
		@ResourceDependency(library = "liferay-faces-alloy", name = "build/aui/aui-min.js"),
		@ResourceDependency(library = "liferay-faces-alloy", name = "liferay.js")
	}
)
//J+
public class AccordionRenderer extends AccordionRendererBase {

	// Private Constants
	private static final String CONTAINER = "container";
	private static final String DASH_CONTENT = "-content";
	private static final String DASH_CONTENT_TOGGLER_CONTENT = DASH_CONTENT + " toggler-content-collapsed";
	private static final String DASH_HEADER = "-header";
	private static final String DASH_HEADER_TOGGLER_HEADER = DASH_HEADER + " toggler-header-collapsed";

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(AccordionRenderer.class);

	@Override
	public void encodeChildren(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		// Get the "value" and "var" attributes of the Accordion component and determine if iteration should take place
		// using a prototype child tab.
		Accordion accordion = null;
		Object value = null;
		String var = null;
		boolean iterateOverDataModel = false;
		Tab prototypeChildTab = null;

		if (uiComponent instanceof Accordion) {
			accordion = (Accordion) uiComponent;
			value = accordion.getValue();
			var = accordion.getVar();
			iterateOverDataModel = ((value != null) && (var != null));
			prototypeChildTab = getPrototypeChildTab(accordion);
		}

		ResponseWriter responseWriter = facesContext.getResponseWriter();

		if (iterateOverDataModel) {

			if (prototypeChildTab != null) {
				int rowCount = accordion.getRowCount();

				for (int i = 0; i < rowCount; i++) {
					accordion.setRowIndex(i);
					encodeHeader(facesContext, responseWriter, uiComponent, prototypeChildTab);
					encodeContent(facesContext, responseWriter, uiComponent, prototypeChildTab);
				}

				accordion.setRowIndex(-1);
			}
			else {
				logger.warn("Unable to iterate because alloy:accordion does not have an alloy:tab child element.");
			}
		}
		else {
			List<UIComponent> children = uiComponent.getChildren();

			for (UIComponent child : children) {

				if (child instanceof Tab) {
					Tab childTab = (Tab) child;
					encodeHeader(facesContext, responseWriter, uiComponent, childTab);
					encodeContent(facesContext, responseWriter, uiComponent, child);
				}
				else {
					logger.warn("Unable to render child element of alloy:accordion since it is not alloy:tab");
				}
			}
		}

		accordion.setRowIndex(-1);
	}

	@Override
	public void encodeJavaScriptCustom(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		ResponseWriter responseWriter = facesContext.getResponseWriter();
		String clientId = uiComponent.getClientId(facesContext);
		String escapedClientId = StringPool.POUND + RendererUtil.escapeClientId(clientId);

		// The outermost div was initially styled with "display:none;" in order to prevent
		// blinking when Alloy's JavaScript attempts to hide the div. At this point in
		// JavaScript execution, Alloy is done manipulating the DOM and it is necessary to
		// set the style back to "display:block;" so that the component will be visible
		// when needed.
		responseWriter.write(AlloyRendererUtil.A_DOT_ONE);
		responseWriter.write(StringPool.OPEN_PARENTHESIS);
		responseWriter.write(StringPool.APOSTROPHE);
		responseWriter.write(escapedClientId);
		responseWriter.write("')._node['style'].display='block';");

	}

	@Override
	public void encodeMarkupBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		// Encode the starting <div> element that represents the accordion.
		ResponseWriter responseWriter = facesContext.getResponseWriter();
		responseWriter.startElement(StringPool.DIV, uiComponent);
		responseWriter.writeAttribute(StringPool.ID, uiComponent.getClientId(facesContext), StringPool.ID);
		RendererUtil.encodeStyleable(responseWriter, (Styleable) uiComponent);
	}

	@Override
	public void encodeMarkupEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		// Encode the closing </div> element for the accordion.
		ResponseWriter responseWriter = facesContext.getResponseWriter();
		responseWriter.endElement(StringPool.DIV);
	}

	protected void encodeContent(FacesContext facesContext, ResponseWriter responseWriter, UIComponent uiComponent,
		UIComponent childUiComponent) throws IOException {

		responseWriter.startElement(StringPool.DIV, childUiComponent);

		// Encode the class attribute here only ... not the style.  The parent div is hidden at
		// this point with style="display: none;" to prevent blinking, but these children of the
		// accordion do not need a style
		String contentClass = uiComponent.getId() + DASH_CONTENT_TOGGLER_CONTENT;
		responseWriter.writeAttribute(StringPool.CLASS, contentClass, Styleable.STYLE_CLASS);

		childUiComponent.encodeAll(facesContext);
		responseWriter.endElement(StringPool.DIV);

	}

	protected void encodeHeader(FacesContext facesContext, ResponseWriter responseWriter, UIComponent uiComponent,
		Tab tab) throws IOException {

		responseWriter.startElement(StringPool.DIV, tab);

		// Encode the class attribute here only ... not the style.  The parent div is hidden at
		// this point with style="display: none;" to prevent blinking, but these children of the
		// accordion do not need a style
		String headerClass = uiComponent.getId() + DASH_HEADER_TOGGLER_HEADER;
		responseWriter.writeAttribute(StringPool.CLASS, headerClass, Styleable.STYLE_CLASS);

		String label = (String) tab.getLabel();

		if (label == null) {
			label = StringPool.LABEL;
		}

		responseWriter.write(label);
		responseWriter.endElement(StringPool.DIV);
	}

	@Override
	protected void encodeHiddenAttributes(FacesContext facesContext, ResponseWriter responseWriter, Accordion accordion,
		boolean first) throws IOException {

		encodeWidgetRender(responseWriter, first);

		first = false;

		encodeClientId(responseWriter, CONTAINER, accordion.getClientId(facesContext), first);
		encodeBoolean(responseWriter, "animated", true, first);

		// closeAllOnExpand: true,
		Boolean multiple = accordion.isMultiple();
		encodeBoolean(responseWriter, "closeAllOnExpand", !multiple, first);

		String headerClass = StringPool.PERIOD + accordion.getId() + DASH_HEADER;
		String contentClass = StringPool.PERIOD + accordion.getId() + DASH_CONTENT;

		encodeString(responseWriter, "content", contentClass, first);
		encodeString(responseWriter, "header", headerClass, first);

		encodeBoolean(responseWriter, "expanded", false, first);
	}

	protected Tab getPrototypeChildTab(Accordion accordion) {

		Tab prototypeChildType = null;
		List<UIComponent> children = accordion.getChildren();

		for (UIComponent child : children) {

			if (child instanceof Tab) {
				prototypeChildType = (Tab) child;

				break;
			}
		}

		return prototypeChildType;
	}

	@Override
	public boolean getRendersChildren() {
		return true;
	}
}
