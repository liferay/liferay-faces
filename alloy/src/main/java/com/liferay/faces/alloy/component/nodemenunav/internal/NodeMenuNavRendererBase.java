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
package com.liferay.faces.alloy.component.nodemenunav.internal;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import com.liferay.faces.alloy.component.menu.Menu;
import com.liferay.faces.alloy.render.internal.DelegatingAlloyRendererBase;
import com.liferay.faces.util.component.Styleable;
import com.liferay.faces.util.render.internal.DelegationResponseWriter;


/**
 * @author  Vernon Singleton
 */
public abstract class NodeMenuNavRendererBase extends DelegatingAlloyRendererBase {

	// Private constants
	private static final String ALLOY_MODULE_NAME = "node-menunav"; // Needed when yui="false"
	private static final String COLON_OPTIONS = ":options";

	// Needed when yui="false"
	// Modules
	protected static final String[] MODULES = { ALLOY_MODULE_NAME };

	// Needed when yui="false"
	@Override
	public void encodeAlloyAttributes(FacesContext facesContext, ResponseWriter respoonseWriter,
		UIComponent uiComponent) throws IOException {
		// no-op
	}

	@Override
	public void encodeChildren(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		// Encode all children except for <alloy:menu> since menus get rendered after the anchor tag is closed in
		// encodeEnd.
		List<UIComponent> children = uiComponent.getChildren();

		for (UIComponent child : children) {

			if (!(child instanceof Menu)) {
				child.encodeAll(facesContext);
			}
		}
	}

	@Override
	public void encodeJavaScriptCustom(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		ResponseWriter responseWriter = facesContext.getResponseWriter();

		Map<String, Object> attributes = uiComponent.getAttributes();
		boolean disabled = (Boolean) attributes.get("disabled");

		if (!disabled) {

			String escapedOptionsDivId = escapeClientId(getDefaultOptionsId(facesContext, uiComponent) + ":0");

			// AlloyRendererUtil.LIFERAY_Z_INDEX_OVERLAY
			responseWriter.write("A.one('#");
			responseWriter.write(escapedOptionsDivId);
			responseWriter.write("')._node['style'].zIndex=" + LIFERAY_Z_INDEX_OVERLAY + ";");

			// The <div> containing menu items was initially styled with "display:none;" in order to prevent blinking
			// when JavaScript attempts to hide it. At this point in JavaScript execution, JavaScript is done
			// manipulating the DOM and it is necessary to set the style back to "display:block;" so that the menu items
			// will be visible when needed.
			responseWriter.write("A.one('#");
			responseWriter.write(escapedOptionsDivId);
			responseWriter.write("')._node['style'].display='block';");
		}
	}

	@Override
	public void encodeJavaScriptMain(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		ResponseWriter responseWriter = facesContext.getResponseWriter();
		String clientId = uiComponent.getClientId(facesContext);
		String escapeClientId = escapeClientId(clientId);

		responseWriter.write("A.one('#");
		responseWriter.write(escapeClientId);
		responseWriter.write("').plug(A.Plugin.NodeMenuNav,{autoSubmenuDisplay:false,mouseOutHideDelay:0});");
	}

	public void encodeLabel(UIComponent uiComponent, ResponseWriter responseWriter, FacesContext facesContext,
		int depth) throws IOException {

		UIComponent facet = uiComponent.getFacet("label");

		if (facet == null) {
			String label = (String) uiComponent.getAttributes().get("label");

			if (label == null) {

				if (depth == 0) {
					responseWriter.startElement("span", uiComponent);
					responseWriter.writeAttribute("class", "caret", "class");
					responseWriter.endElement("span");
				}
				else {
					responseWriter.startElement("span", uiComponent);
					responseWriter.write("&nbsp;");
					responseWriter.endElement("span");
				}
			}
			else {
				responseWriter.writeText(label, null);
			}
		}
		else {
			facet.encodeAll(facesContext);
		}
	}

	@Override
	public void encodeMarkupBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		ResponseWriter responseWriter = facesContext.getResponseWriter();

		String clientId = uiComponent.getClientId(facesContext);

		// start yui3-menu div
		responseWriter.startElement("div", uiComponent);
		responseWriter.writeAttribute("id", clientId, "id");
		responseWriter.writeAttribute("class", "yui3-menu yui3-menu-horizontal yui3-splitbuttonnav", "class");

		// start yui3-menu-content div
		responseWriter.startElement("div", uiComponent);
		responseWriter.writeAttribute("class", "yui3-menu-content", "class");

		responseWriter.startElement("ul", uiComponent);
		responseWriter.startElement("li", uiComponent);

		// Start the span containing the btn-group
		responseWriter.startElement("span", uiComponent);
		responseWriter.writeAttribute("class", "yui3-menu-label btn-group", "class");

		// ResponseWriter blocks the text value and blocks writing of URIAttributes, if necessary
		Map<String, Object> attributes = uiComponent.getAttributes();
		boolean disabled = (Boolean) attributes.get("disabled");
		Styleable styleable = (Styleable) uiComponent;
		String styleClass = styleable.getStyleClass();
		DelegationResponseWriter delegationResponseWriter = new NodeMenuNavResponseWriter(responseWriter, disabled,
				uiComponent.getClientId(facesContext), styleClass);

		//J-
		// We have now written out something like this:
		//
		// <div id="menuId3" class="yui3-menu yui3-menu-horizontal yui3-splitbuttonnav">
		//	<div class="yui3-menu-content">
		//		<ul>
		//			<li>
		//				<span class="yui3-menu-label btn-group">
		//					... the main anchor tag for the splitButton goes here ...
		//J+

		// start the main anchor tag
		super.encodeMarkupBegin(facesContext, uiComponent, delegationResponseWriter);

	}

	@Override
	public void encodeMarkupEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		// close the main anchor tag
		super.encodeMarkupEnd(facesContext, uiComponent);

		ResponseWriter responseWriter = facesContext.getResponseWriter();
		Map<String, Object> attributes = uiComponent.getAttributes();
		boolean disabled = (Boolean) attributes.get("disabled");
		Styleable styleable = (Styleable) uiComponent;
		String styleClass = styleable.getStyleClass();
		String defaultOptionsDivId = getDefaultOptionsId(facesContext, uiComponent);

		responseWriter.startElement("a", uiComponent);
		responseWriter.writeAttribute("class", styleClass, "class");

		int depth = 0;

		if (!disabled) {
			responseWriter.writeAttribute("href", "#" + defaultOptionsDivId + ":" + depth, "href");
		}

		List<UIComponent> children = uiComponent.getChildren();

		// Find the menu among the children and render its label, if any, and then recurse over the menu's children
		for (UIComponent child : children) {

			if (child instanceof Menu) {

				encodeLabel(child, responseWriter, facesContext, depth);
				responseWriter.endElement("a");

				// End the span containing the btn-group
				responseWriter.endElement("span");

				// Recurse over (first and only expected) menu
				encodeMenuRecurse(child, responseWriter, disabled, styleClass, defaultOptionsDivId, depth,
					facesContext);

				break;
			}
		}

		responseWriter.endElement("li");
		responseWriter.endElement("ul");

		// end yui3-menu-content div
		responseWriter.endElement("div");

		// end yui3-menu div
		responseWriter.endElement("div");
	}

	public void encodeMenuRecurse(UIComponent uiComponent, ResponseWriter responseWriter, boolean disabled,
		String styleClass, String optionsDivId, int depth, FacesContext facesContext) throws IOException {

		String menuId = optionsDivId + ":" + depth;

		// Start a listItem tag for a sub-menu
		if (depth > 0) {
			responseWriter.startElement("li", uiComponent);
			responseWriter.startElement("a", uiComponent);
			responseWriter.writeAttribute("class", "yui3-menu-label", "class");

			if (!disabled) {
				responseWriter.writeAttribute("href", "#" + menuId, "href");
			}

			encodeLabel(uiComponent, responseWriter, facesContext, depth);
			responseWriter.endElement("a");
		}

		// Start inner yui3-menu div
		responseWriter.startElement("div", uiComponent);
		responseWriter.writeAttribute("id", menuId, "id");
		responseWriter.writeAttribute("class", "yui3-menu", "class");

		// Hide the main menu to prevent blinking
		if (depth == 0) {
			responseWriter.writeAttribute("style", "display: none;", "style");
		}

		// Start inner yui3-menu-content div
		responseWriter.startElement("div", uiComponent);
		responseWriter.writeAttribute("class", "yui3-menu-content", "class");

		responseWriter.startElement("ul", uiComponent);

		// Encode the children of the menu
		List<UIComponent> children = uiComponent.getChildren();

		for (UIComponent child : children) {

			responseWriter.startElement("li", uiComponent);
			responseWriter.writeAttribute("class", "yui3-menuitem", "class");

			ResponseWriter originalResponseWriter = facesContext.getResponseWriter();
			DelegationResponseWriter delegationResponseWriter = new NodeMenuNavMenuResponseWriter(
					originalResponseWriter);
			facesContext.setResponseWriter(delegationResponseWriter);
			child.encodeAll(facesContext);
			facesContext.setResponseWriter(originalResponseWriter);
			responseWriter.endElement("li");
		}

		responseWriter.endElement("ul");

		// End inner yui3-menu-content div
		responseWriter.endElement("div");

		// End inner yui3-menu div
		responseWriter.endElement("div");

		// End the listItem tag for a sub-menu
		if (depth > 0) {
			responseWriter.endElement("li");
		}
	}

	@Override
	public String getAlloyClassName(FacesContext facesContext, UIComponent uiComponent) {
		return null;
	}

	protected String getDefaultOptionsId(FacesContext facesContext, UIComponent uiComponent) {
		return uiComponent.getClientId(facesContext) + COLON_OPTIONS;
	}

	@Override
	public String getDelegateComponentFamily() {
		return null;
	}

	@Override
	public String getDelegateRendererType() {
		return null;
	}

	// Needed when yui="false"
	@Override
	protected String[] getModules(FacesContext facesContext, UIComponent uiComponent) {
		return MODULES;
	}

	@Override
	public boolean getRendersChildren() {
		return true;
	}
}
