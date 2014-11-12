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
package com.liferay.faces.alloy.renderkit.internal;

import java.io.IOException;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import com.liferay.faces.util.context.ExtFacesContext;
import com.liferay.faces.util.lang.StringPool;
import com.liferay.faces.util.render.ContentTypes;
import com.liferay.faces.util.render.internal.BufferedScriptResponseWriter;
import com.liferay.faces.util.render.internal.RendererUtil;


/**
 * @author  Neil Griffin
 */
public class ScriptRenderer extends ScriptRendererCompat {

	// Private Constants
	private static final String BOTTOM = "bottom";
	private static final String INLINE = "inline";
	private static final String POSITION = "position";
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
			responseWriter.startElement(StringPool.SCRIPT, uiComponent);
			responseWriter.writeAttribute(StringPool.TYPE, ContentTypes.TEXT_JAVASCRIPT, null);
			responseWriter.write(StringPool.FORWARD_SLASH);
			responseWriter.write(StringPool.FORWARD_SLASH);
			responseWriter.write(StringPool.SPACE);
			responseWriter.write(StringPool.CDATA_OPEN);
			responseWriter.write(StringPool.NEW_LINE);

			inlineUse = (String) attributes.get(USE);

			if (inlineUse != null) {
				String[] useArray = new String[] { inlineUse };
				String alloyBeginScript = RendererUtil.getAlloyBeginScript(facesContext, useArray);
				responseWriter.write(alloyBeginScript);
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
			BufferedScriptResponseWriter bufferedScriptResponseWriter = new BufferedScriptResponseWriter();
			facesContext.setResponseWriter(bufferedScriptResponseWriter);
			super.encodeChildren(facesContext, uiComponent);
			facesContext.setResponseWriter(backupResponseWriter);

			// If running in an Ajax request, then it is not possible to render the scripts at the bottom of the
			// portal page. Instead, store the script in the JavaScript map so that PartialViewContextCleanupImpl
			// knows to include it in the <eval>...</eval> section of the JSF partial-response.
			if (isAjaxRequest(facesContext)) {
				Map<String, String> javaScriptMap = ExtFacesContext.getInstance().getJavaScriptMap();
				javaScriptMap.put(uiComponent.getClientId(facesContext), bufferedScriptResponseWriter.toString());
			}

			// Otherwise, render the script at the bottom of the portal page by setting the WebKeys.AUI_SCRIPT_DATA
			// request attribute.
			else {
				Map<String, Object> attributes = uiComponent.getAttributes();
				String use = (String) attributes.get(USE);
				String bufferedScript = bufferedScriptResponseWriter.toString();
				RendererUtil.renderScript(facesContext, uiComponent, bufferedScript, use);
			}
		}
	}

	@Override
	public void encodeEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		if (inline) {
			ResponseWriter responseWriter = facesContext.getResponseWriter();

			if (inlineUse != null) {
				responseWriter.write(RendererUtil.ALLOY_END_SCRIPT);
			}

			responseWriter.write(StringPool.FORWARD_SLASH);
			responseWriter.write(StringPool.FORWARD_SLASH);
			responseWriter.write(StringPool.SPACE);
			responseWriter.write(StringPool.CDATA_CLOSE);
			responseWriter.write(StringPool.NEW_LINE);
			responseWriter.endElement(StringPool.SCRIPT);
		}
	}

	@Override
	public boolean getRendersChildren() {
		return true;
	}

}
