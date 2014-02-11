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
package com.liferay.faces.alloy.renderkit;

import java.io.IOException;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import com.liferay.faces.util.context.ExtFacesContext;
import com.liferay.faces.util.lang.StringPool;
import com.liferay.faces.util.portal.ScriptDataUtil;
import com.liferay.faces.util.portal.WebKeys;

import com.liferay.portal.kernel.servlet.taglib.aui.ScriptData;
import com.liferay.portal.model.Portlet;


/**
 * @author  Neil Griffin
 */
public class ScriptRenderer extends ScriptRendererCompat {

	// Private Constants
	private static final String AUI_USE = "AUI().use";
	private static final String BOTTOM = "bottom";
	private static final String FUNCTION_A = "function(A)";
	private static final String SCRIPT = "script";
	private static final String INLINE = "inline";
	private static final String POSITION = "position";
	private static final String TEXT_JAVASCRIPT = "text/javascript";
	private static final String TYPE = "type";
	private static final String USE = "use";

	// Private Data Members
	private boolean inline;
	private String inlineUse;

	@Override
	public void encodeBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		Map<String, Object> attributes = uiComponent.getAttributes();

		// If the current URL is a "refresh" type of URL (isolated) or the window state is exclusive, then the script
		// must be rendered inline.
		inline = isInline(facesContext);

		// Otherwise, if the current request was triggered by Ajax, then the script must be rendered inline.
		if (!inline) {

			if (isAjaxRequest(facesContext)) {
				inline = true;
			}
		}

		// If the developer specified "inline" as the value of the position attribute, then the script must be
		// rendered inline.
		String position = (String) attributes.get(POSITION);

		if (position != null) {

			if (INLINE.equals(position)) {
				inline = true;
			}
			else if (BOTTOM.equals(position)) {
				inline = false;
			}
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

		// If the script is to be rendered inline, then simply ask the children to encode themselves to the response.
		if (inline) {
			super.encodeChildren(facesContext, uiComponent);
		}

		// Otherwise,
		else {

			// Ask the children to encode themselves and capture the markup in a string.
			ResponseWriter backupResponseWriter = facesContext.getResponseWriter();
			BufferedResponseWriter bufferedResponseWriter = new BufferedResponseWriter();
			facesContext.setResponseWriter(bufferedResponseWriter);
			super.encodeChildren(facesContext, uiComponent);
			facesContext.setResponseWriter(backupResponseWriter);

			// If running in an Ajax request, then it is not possible to render the scripts at the bottom of the
			// portal page. Instead, store the script in the JavaScript map so that PartialViewContextCleanupImpl
			// knows to include it in the <eval>...</eval> section of the JSF partial-response.
			if (isAjaxRequest(facesContext)) {
				Map<String, String> javaScriptMap = ExtFacesContext.getInstance().getJavaScriptMap();
				javaScriptMap.put(uiComponent.getClientId(facesContext), bufferedResponseWriter.toString());
			}

			// Otherwise, render the script at the bottom of the portal page by setting the WebKeys.AUI_SCRIPT_DATA
			// request attribute.
			else {
				ExternalContext externalContext = facesContext.getExternalContext();
				ScriptData scriptData = (ScriptData) externalContext.getRequestMap().get(WebKeys.AUI_SCRIPT_DATA);

				if (scriptData == null) {
					scriptData = new ScriptData();
					externalContext.getRequestMap().put(WebKeys.AUI_SCRIPT_DATA, scriptData);
				}

				Map<String, Object> attributes = uiComponent.getAttributes();
				String use = (String) attributes.get(USE);
				String portletId = StringPool.BLANK;
				Portlet portlet = (Portlet) facesContext.getExternalContext().getRequestMap().get(
						WebKeys.RENDER_PORTLET);

				if (portlet != null) {
					portletId = portlet.getPortletId();
				}

				ScriptDataUtil.append(scriptData, portletId, bufferedResponseWriter.toString(), use);
			}
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
