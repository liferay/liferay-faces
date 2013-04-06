/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.alloy.renderkit;

import java.io.IOException;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.Renderer;

import com.liferay.faces.util.lang.StringPool;

import com.liferay.portal.kernel.servlet.taglib.aui.ScriptData;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.theme.ThemeDisplay;


/**
 * @author  Neil Griffin
 */
public class ScriptRenderer extends Renderer {

	// Private Constants
	private static final String AUI_USE = "AUI().use";
	private static final String FUNCTION_A = "function(A)";
	private static final String SCRIPT = "script";
	private static final String INLINE = "inline";
	private static final String POSITION = "position";
	private static final String TEXT_JAVASCRIPT = "text/javascript";
	private static final String TYPE = "type";
	private static final String USE = "use";

	// Private Data Members
	boolean inline;
	String inlineUse;

	@Override
	public void encodeBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		Map<String, Object> attributes = uiComponent.getAttributes();

		ThemeDisplay themeDisplay = (ThemeDisplay) facesContext.getExternalContext().getRequestMap().get(
				WebKeys.THEME_DISPLAY);

		if (themeDisplay != null) {
			inline = (themeDisplay.isIsolated() || themeDisplay.isStateExclusive());
		}

		String position = (String) attributes.get(POSITION);

		if (INLINE.equals(position)) {
			inline = true;
		}

		if (inline) {

			ResponseWriter responseWriter = facesContext.getResponseWriter();
			responseWriter.startElement(SCRIPT, uiComponent);
			responseWriter.writeAttribute(TYPE, TEXT_JAVASCRIPT, null);
			responseWriter.write(StringPool.FORWARD_SLASH);
			responseWriter.write(StringPool.FORWARD_SLASH);
			responseWriter.write(StringPool.SPACE);
			responseWriter.write(StringPool.CDATA_OPEN);
			responseWriter.write(StringPool.NEW_LINE);

			inlineUse = (String) attributes.get(USE);

			if (inlineUse != null) {
				responseWriter.write(AUI_USE);
				responseWriter.write(StringPool.OPEN_PARENTHESIS);
				responseWriter.write(StringPool.APOSTROPHE);
				responseWriter.write(inlineUse);
				responseWriter.write(StringPool.APOSTROPHE);
				responseWriter.write(StringPool.COMMA);
				responseWriter.write(StringPool.NEW_LINE);
				responseWriter.write(FUNCTION_A);
				responseWriter.write(StringPool.SPACE);
				responseWriter.write(StringPool.OPEN_CURLY_BRACE);
				responseWriter.write(StringPool.NEW_LINE);
			}
		}
	}

	@Override
	public void encodeChildren(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		if (inline) {
			super.encodeChildren(facesContext, uiComponent);
		}
		else {
			ResponseWriter backupResponseWriter = facesContext.getResponseWriter();
			BufferedResponseWriter bufferedResponseWriter = new BufferedResponseWriter();
			facesContext.setResponseWriter(bufferedResponseWriter);
			super.encodeChildren(facesContext, uiComponent);

			ExternalContext externalContext = facesContext.getExternalContext();
			ScriptData scriptData = (ScriptData) externalContext.getRequestMap().get(WebKeys.AUI_SCRIPT_DATA);

			if (scriptData == null) {
				scriptData = new ScriptData();
				externalContext.getRequestMap().put(WebKeys.AUI_SCRIPT_DATA, scriptData);
			}

			String portletId = StringPool.BLANK;
			Portlet portlet = (Portlet) facesContext.getExternalContext().getRequestMap().get(WebKeys.RENDER_PORTLET);

			if (portlet != null) {
				portletId = portlet.getPortletId();
			}

			Map<String, Object> attributes = uiComponent.getAttributes();
			String use = (String) attributes.get(USE);
			scriptData.append(portletId, bufferedResponseWriter.toString(), use);
			facesContext.setResponseWriter(backupResponseWriter);
		}
	}

	@Override
	public void encodeEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		if (inline) {
			ResponseWriter responseWriter = facesContext.getResponseWriter();

			if (inlineUse != null) {
				responseWriter.write(StringPool.CLOSE_CURLY_BRACE);
				responseWriter.write(StringPool.NEW_LINE);
				responseWriter.write(StringPool.CLOSE_PARENTHESIS);
				responseWriter.write(StringPool.SEMICOLON);
				responseWriter.write(StringPool.NEW_LINE);
			}

			responseWriter.write(StringPool.FORWARD_SLASH);
			responseWriter.write(StringPool.FORWARD_SLASH);
			responseWriter.write(StringPool.SPACE);
			responseWriter.write(StringPool.CDATA_CLOSE);
			responseWriter.write(StringPool.NEW_LINE);
			responseWriter.endElement(SCRIPT);
		}
	}

	@Override
	public boolean getRendersChildren() {
		return true;
	}

}
