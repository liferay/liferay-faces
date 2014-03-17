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
package com.liferay.faces.alloy.component.tabview;

import java.io.IOException;
import java.util.List;

import javax.faces.application.ResourceDependency;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;
import javax.faces.render.Renderer;

import com.liferay.faces.alloy.component.tab.Tab;
import com.liferay.faces.alloy.component.tab.TabComponent;
import com.liferay.faces.util.component.ComponentUtil;
import com.liferay.faces.util.component.Styleable;
import com.liferay.faces.util.lang.StringPool;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * This class is a JSF {@link Renderer} for the aui:tabView component.
 *
 * @author  Neil Griffin
 */
@FacesRenderer(componentFamily = TabView.COMPONENT_FAMILY, rendererType = TabView.RENDERER_TYPE)
@ResourceDependency(library = "liferay-faces-alloy", name = "liferay.js")
public class TabViewRenderer extends TabViewRendererBase {

	// Private Constants
	private static final String NAV_NAV_TABS = "nav nav-tabs";
	private static final String TAB_CONTENT = "tab-content";

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(TabViewRenderer.class);

	@Override
	public void encodeChildren(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		// Get the "value" and "var" attributes of the TabView component and determine if iteration should take place
		// using a prototype child tab.
		TabView tabView = null;
		Object value = null;
		String var = null;
		boolean iterateOverDataModel = false;
		Tab prototypeChildTab = null;

		if (uiComponent instanceof TabView) {
			tabView = (TabView) uiComponent;
			value = tabView.getValue();
			var = tabView.getVar();
			iterateOverDataModel = ((value != null) && (var != null));
			prototypeChildTab = getPrototypeChildTab(tabView);
		}

		// Encode the starting <ul> unordered list element that represents the list of clickable tabs.
		ResponseWriter responseWriter = facesContext.getResponseWriter();
		responseWriter.startElement(StringPool.UL, tabView);
		encodeClassAttribute(responseWriter, tabView, NAV_NAV_TABS);

		if (iterateOverDataModel) {

			if (prototypeChildTab != null) {

				int rowCount = tabView.getRowCount();

				for (int i = 0; i < rowCount; i++) {
					tabView.setRowIndex(i);
					encodeTabListItem(facesContext, responseWriter, prototypeChildTab);
				}

				tabView.setRowIndex(-1);

			}
			else {
				logger.warn("Unable to iterate because aui:tabView does not have an aui:tab child element.");
			}
		}
		else {
			List<UIComponent> children = uiComponent.getChildren();

			for (UIComponent child : children) {

				if (child instanceof TabComponent) {
					TabComponent childTabComponent = (TabComponent) child;
					encodeTabListItem(facesContext, responseWriter, childTabComponent);
				}
				else {
					logger.warn("Unable to render child element of p:tabView since it is not p:tab");
				}
			}
		}

		responseWriter.endElement(StringPool.UL);

		// Encode the starting <div> element that represents the content for the selected tab.
		responseWriter.startElement(StringPool.DIV, uiComponent);
		encodeClassAttribute(responseWriter, (Styleable) uiComponent, TAB_CONTENT);

		// Encode the content for each tab.
		if ((iterateOverDataModel) && (prototypeChildTab != null)) {
			int rowCount = tabView.getRowCount();

			for (int i = 0; i < rowCount; i++) {
				tabView.setRowIndex(i);
				prototypeChildTab.encodeAll(facesContext);
			}
		}
		else {
			List<UIComponent> children = tabView.getChildren();

			for (int i = 0; i < children.size(); i++) {
				UIComponent child = children.get(i);
				tabView.setRowIndex(i);
				child.encodeAll(facesContext);
			}
		}

		tabView.setRowIndex(-1);

		// Encode the closing </div> element for the content.
		responseWriter.endElement(StringPool.DIV);
	}

	@Override
	protected void encodeHTMLBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		// Encode the starting <div> element that represents the component.
		ResponseWriter responseWriter = facesContext.getResponseWriter();
		responseWriter.startElement(StringPool.DIV, uiComponent);
		responseWriter.writeAttribute(StringPool.ID, uiComponent.getClientId(), StringPool.ID);
		encodeClassAttribute(responseWriter, (Styleable) uiComponent, null);
	}

	@Override
	protected void encodeHTMLEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		// Encode the closing </div> element.
		ResponseWriter responseWriter = facesContext.getResponseWriter();
		responseWriter.endElement(StringPool.DIV);
	}

	protected void encodeTabListItem(FacesContext facesContext, ResponseWriter responseWriter,
		TabComponent tabComponent) throws IOException {

		UIComponent uiComponent = (UIComponent) tabComponent;
		responseWriter.startElement(StringPool.LI, uiComponent);
		responseWriter.startElement(StringPool.ASCII_TABLE[97], uiComponent);

		String escapedClientId = ComponentUtil.escapeClientId(uiComponent.getClientId(facesContext));
		responseWriter.writeAttribute(StringPool.HREF, StringPool.POUND + escapedClientId, null);

		String label = (String) tabComponent.getLabel();

		if (label == null) {
			label = Tab.LABEL;
		}

		responseWriter.write(label);
		responseWriter.endElement(StringPool.ASCII_TABLE[97]);
		responseWriter.endElement(StringPool.LI);
	}

	protected Tab getPrototypeChildTab(TabView tabView) {

		Tab prototypeChildType = null;
		List<UIComponent> children = tabView.getChildren();

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
