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
package com.liferay.faces.alloy.component.nodemenunav;

import java.io.IOException;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import com.liferay.faces.alloy.renderkit.AlloyRendererUtil;
import com.liferay.faces.alloy.renderkit.DelegatingAlloyRendererBase;
import com.liferay.faces.util.lang.StringPool;
import com.liferay.faces.util.render.RendererUtil;


/**
 * @author  Vernon Singleton
 */
public abstract class NodeMenuNavRendererBase extends DelegatingAlloyRendererBase {

	// Private constants
	private static final String ALLOY_MODULE_NAME = "node-menunav"; // Needed when yui="false"
	private static final String DEFAULT_SUB_MENU_LABEL = "--->>>";

	// Protected constants
	protected static final String A_DOT = "A.";

	// Public constants
	public static final String COLON_OPTIONS = ":options";
	public static final String DEFAULT_BTN = "btn";
	public static final String DEFAULT_BUTTON = "btn-default";
	public static final String IMAGE = "image";
	public static final String LIFERAY_COMPONENT = AlloyRendererUtil.LIFERAY_COMPONENT;

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

		if ((facesContext == null) || (uiComponent == null)) {
			throw new NullPointerException();
		}

		List<UIComponent> children = uiComponent.getChildren();

		for (UIComponent child : children) {

			if (!child.getClass().getName().contains("com.liferay.faces.alloy.component.menu.Menu")) {
				child.encodeAll(facesContext);
			}
		}
	}

	@Override
	public void encodeJavaScriptCustom(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		NodeMenuNav nodeMenuNav = (NodeMenuNav) uiComponent;
		ResponseWriter responseWriter = facesContext.getResponseWriter();

		boolean disabled = nodeMenuNav.isDisabled();

		if (!disabled) {

			String escapedOptionsDivId = RendererUtil.escapeClientId(getDefaultOptionsId(facesContext, uiComponent) +
					StringPool.COLON + "0");

			// AlloyRendererUtil.LIFERAY_Z_INDEX_OVERLAY
			responseWriter.write(AlloyRendererUtil.A_DOT_ONE);
			responseWriter.write(StringPool.OPEN_PARENTHESIS);
			responseWriter.write(StringPool.APOSTROPHE);
			responseWriter.write(StringPool.POUND);
			responseWriter.write(escapedOptionsDivId);
			responseWriter.write("')._node['style'].zIndex=" + AlloyRendererUtil.LIFERAY_Z_INDEX_OVERLAY + ";");

			// The <div> containing menu items was initially styled with "display:none;" in order to prevent blinking
			// when JavaScript attempts to hide it. At this point in JavaScript execution, JavaScript is done
			// manipulating the DOM and it is necessary to set the style back to "display:block;" so that the menu items
			// will be visible when needed.
			responseWriter.write(AlloyRendererUtil.A_DOT_ONE);
			responseWriter.write(StringPool.OPEN_PARENTHESIS);
			responseWriter.write(StringPool.APOSTROPHE);
			responseWriter.write(StringPool.POUND);
			responseWriter.write(escapedOptionsDivId);
			responseWriter.write("')._node['style'].display='block';");

			//J-
			// Here is an example of what renders from this:
			//	A.one('#j\x5fidt70\x5c\x3aj\x5fidt71\x5c\x3aj\x5fidt72\x5c\x3a0\x5c\x3aj\x5fidt73\x5c\x3aoptions')._node['style'].display='block';
			//J+
		}
	}

	@Override
	public void encodeJavaScriptMain(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		ResponseWriter responseWriter = facesContext.getResponseWriter();

		//J-
		// At a minimum we need to render something like this, but hopefully without the var
		//
		// var menu = Y.one("#menuId3");
		// menu.plug(Y.Plugin.NodeMenuNav, {
		//		autoSubmenuDisplay : false,
		//		mouseOutHideDelay : 0
		// });
		//J+

		responseWriter.write(A_DOT);
		responseWriter.write("one");
		responseWriter.write(StringPool.OPEN_PARENTHESIS);
		responseWriter.write(StringPool.QUOTE);
		responseWriter.write(StringPool.POUND);
		responseWriter.write(RendererUtil.escapeClientId(uiComponent.getClientId(facesContext)));
		responseWriter.write(StringPool.QUOTE);
		responseWriter.write(StringPool.CLOSE_PARENTHESIS);
		responseWriter.write(StringPool.PERIOD);
		responseWriter.write("plug");
		responseWriter.write(StringPool.OPEN_PARENTHESIS);
		responseWriter.write("A.Plugin.NodeMenuNav, ");
		responseWriter.write(StringPool.OPEN_CURLY_BRACE);
		responseWriter.write(" autoSubmenuDisplay : false,");
		responseWriter.write(" mouseOutHideDelay : 0 ");
		responseWriter.write(StringPool.CLOSE_CURLY_BRACE);
		responseWriter.write(StringPool.CLOSE_PARENTHESIS);
		responseWriter.write(StringPool.SEMICOLON);

		//J-
		// Here is an example of what renders from this:
		//	A.one("#j\x5fidt39\x5c\x3aj\x5fidt40\x5c\x3aj\x5fidt41").plug(A.Plugin.NodeMenuNav, { autoSubmenuDisplay : false, mouseOutHideDelay : 0 });
		//J+

	}

	public void encodeLabel(UIComponent uiComponent, ResponseWriter responseWriter, FacesContext facesContext,
		int depth) throws IOException {
		UIComponent facet = uiComponent.getFacet("label");

		if (facet == null) {
			String label = (String) uiComponent.getAttributes().get("label");

			if (label == null) {

				if (depth == 0) {
					responseWriter.startElement(StringPool.SPAN, uiComponent);
					responseWriter.writeAttribute(StringPool.CLASS, "caret", StringPool.CLASS);
					responseWriter.endElement(StringPool.SPAN);
				}
				else {
					responseWriter.startElement(StringPool.SPAN, uiComponent);
					responseWriter.writeText(DEFAULT_SUB_MENU_LABEL, StringPool.LABEL);
					responseWriter.endElement(StringPool.SPAN);
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

		NodeMenuNav nodeMenuNav = (NodeMenuNav) uiComponent;
		ResponseWriter responseWriter = facesContext.getResponseWriter();

		String clientId = uiComponent.getClientId(facesContext);

		// start yui3-menu div
		responseWriter.startElement(StringPool.DIV, uiComponent);
		responseWriter.writeAttribute(StringPool.ID, clientId, StringPool.ID);
		responseWriter.writeAttribute(StringPool.CLASS, "yui3-menu yui3-menu-horizontal yui3-splitbuttonnav",
			StringPool.CLASS);

		// start yui3-menu-content div
		responseWriter.startElement(StringPool.DIV, uiComponent);
		responseWriter.writeAttribute(StringPool.CLASS, "yui3-menu-content", StringPool.CLASS);

		responseWriter.startElement(StringPool.UL, uiComponent);
		responseWriter.startElement(StringPool.LI, uiComponent);

		// Start the span containing the btn-group
		responseWriter.startElement(StringPool.SPAN, uiComponent);
		responseWriter.writeAttribute(StringPool.CLASS, "yui3-menu-label btn-group", StringPool.CLASS);

		// ResponseWriter blocks the text value and blocks writing of URIAttributes, if necessary
		boolean disabled = nodeMenuNav.isDisabled();
		String styleClass = nodeMenuNav.getStyleClass();
		NodeMenuNavResponseWriter nodeMenuNavResponseWriter = new NodeMenuNavResponseWriter(responseWriter, disabled,
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
		super.encodeMarkupBegin(facesContext, uiComponent, nodeMenuNavResponseWriter);

	}

	@Override
	public void encodeMarkupEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		// close the main anchor tag
		super.encodeMarkupEnd(facesContext, uiComponent);

		ResponseWriter responseWriter = facesContext.getResponseWriter();
		NodeMenuNav nodeMenuNav = (NodeMenuNav) uiComponent;
		boolean disabled = nodeMenuNav.isDisabled();
		String styleClass = nodeMenuNav.getStyleClass();
		String defaultOptionsDivId = getDefaultOptionsId(facesContext, uiComponent);

		responseWriter.startElement("a", uiComponent);
		responseWriter.writeAttribute(StringPool.CLASS, styleClass, StringPool.CLASS);

		int depth = 0;

		if (!disabled) {
			responseWriter.writeAttribute(StringPool.HREF,
				StringPool.POUND + defaultOptionsDivId + StringPool.COLON + depth, StringPool.HREF);
		}

		List<UIComponent> children = uiComponent.getChildren();

		// find the menu among the children and render its label, if any, and then recurse over the menu's children
		for (UIComponent child : children) {

			if (child.getClass().getName().contains("com.liferay.faces.alloy.component.menu.Menu")) {

				encodeLabel(child, responseWriter, facesContext, depth);
				responseWriter.endElement("a");

				// End the span containing the btn-group
				responseWriter.endElement(StringPool.SPAN);

				// recurse over (first and only expected) menu
				encodeMenuRecurse(child, responseWriter, disabled, styleClass, defaultOptionsDivId, depth,
					facesContext);

				break;
			}
		}

		responseWriter.endElement(StringPool.LI);
		responseWriter.endElement(StringPool.UL);

		// end yui3-menu-content div
		responseWriter.endElement(StringPool.DIV);

		// end yui3-menu div
		responseWriter.endElement(StringPool.DIV);

	}

	public void encodeMenuRecurse(UIComponent uiComponent, ResponseWriter responseWriter, boolean disabled,
		String styleClass, String optionsDivId, int depth, FacesContext facesContext) throws IOException {

		String menuId = optionsDivId + StringPool.COLON + depth;

		// start a listItem tag for a sub-menu
		if (depth > 0) {
			responseWriter.startElement(StringPool.LI, uiComponent);
			responseWriter.startElement("a", uiComponent);
			responseWriter.writeAttribute(StringPool.CLASS, "yui3-menu-label", StringPool.CLASS);

			if (!disabled) {
				responseWriter.writeAttribute(StringPool.HREF, StringPool.POUND + menuId, StringPool.HREF);
			}

			encodeLabel(uiComponent, responseWriter, facesContext, depth);
			responseWriter.endElement("a");
		}

		// start inner yui3-menu div
		responseWriter.startElement(StringPool.DIV, uiComponent);
		responseWriter.writeAttribute(StringPool.ID, menuId, StringPool.ID);
		responseWriter.writeAttribute(StringPool.CLASS, "yui3-menu", StringPool.CLASS);

		// hide the main menu to prevent blinking
		if (depth == 0) {
			responseWriter.writeAttribute("style", "display: none;", "style");
		}

		// start inner yui3-menu-content div
		responseWriter.startElement(StringPool.DIV, uiComponent);
		responseWriter.writeAttribute(StringPool.CLASS, "yui3-menu-content", StringPool.CLASS);

		responseWriter.startElement(StringPool.UL, uiComponent);

		// encode the children of the menu
		List<UIComponent> children = uiComponent.getChildren();

		for (UIComponent child : children) {

			if (child.getClass().getName().contains("com.liferay.faces.alloy.component.menu.Menu")) {
				encodeMenuRecurse(child, responseWriter, disabled, styleClass, optionsDivId, (depth + 1), facesContext);
			}
			else {

				responseWriter.startElement(StringPool.LI, uiComponent);
				responseWriter.writeAttribute(StringPool.CLASS, "yui3-menuitem", StringPool.CLASS);

				ResponseWriter originalResponseWriter = facesContext.getResponseWriter();
				facesContext.setResponseWriter(new NodeMenuNavMenuResponseWriter(originalResponseWriter));

				child.encodeAll(facesContext);

				facesContext.setResponseWriter(originalResponseWriter);

				responseWriter.endElement(StringPool.LI);
			}
		}

		responseWriter.endElement(StringPool.UL);

		// end inner yui3-menu-content div
		responseWriter.endElement(StringPool.DIV);

		// end inner yui3-menu div
		responseWriter.endElement(StringPool.DIV);

		// end the listItem tag for a sub-menu
		if (depth > 0) {
			responseWriter.endElement(StringPool.LI);
		}
	}

	@Override
	public String getAlloyClassName() {
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
	protected String[] getModules() {
		return MODULES;
	}

	@Override
	public boolean getRendersChildren() {
		return true;
	}

}
