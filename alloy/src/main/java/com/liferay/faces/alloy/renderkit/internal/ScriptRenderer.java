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
package com.liferay.faces.alloy.renderkit.internal;

import java.io.IOException;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import com.liferay.faces.alloy.render.internal.AlloyRendererUtil;
import com.liferay.faces.util.ContentTypes;
import com.liferay.faces.util.client.BrowserSniffer;
import com.liferay.faces.util.client.BrowserSnifferFactory;
import com.liferay.faces.util.client.Script;
import com.liferay.faces.util.client.ScriptFactory;
import com.liferay.faces.util.factory.FactoryExtensionFinder;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;
import com.liferay.faces.util.render.internal.BufferedScriptResponseWriter;


/**
 * @author  Neil Griffin
 */
public class ScriptRenderer extends ScriptRendererCompat {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(ScriptRenderer.class);

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

				if (isBottomOfPageSupported()) {
					inline = false;
				}
				else {
					inline = true;
					logger.warn("position=\"bottom\" not supported -- rendering script inline");
				}
			}
		}

		if (inline) {

			ResponseWriter responseWriter = facesContext.getResponseWriter();
			responseWriter.startElement("script", uiComponent);
			responseWriter.writeAttribute("type", ContentTypes.TEXT_JAVASCRIPT, null);
			responseWriter.write("// <![CDATA[\n");

			inlineUse = (String) attributes.get(USE);

			if (inlineUse != null) {
				String[] useArray = new String[] { inlineUse };
				BrowserSnifferFactory browserSnifferFactory = (BrowserSnifferFactory) FactoryExtensionFinder.getFactory(
						BrowserSnifferFactory.class);
				BrowserSniffer browserSniffer = browserSnifferFactory.getBrowserSniffer(
						facesContext.getExternalContext());
				String alloyBeginScript = AlloyRendererUtil.getAlloyBeginScript(useArray, browserSniffer);
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

			Map<String, Object> attributes = uiComponent.getAttributes();
			String use = (String) attributes.get(USE);

			ScriptFactory scriptFactory = (ScriptFactory) FactoryExtensionFinder.getFactory(ScriptFactory.class);

			Script script;
			String scriptSourceCode = bufferedScriptResponseWriter.toString();

			if (use == null) {
				script = scriptFactory.getScript(scriptSourceCode);
			}
			else {
				script = scriptFactory.getAlloyScript(scriptSourceCode, new String[] { use });
			}

			addScriptToBottomOfPage(script);
		}
	}

	@Override
	public void encodeEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		if (inline) {
			ResponseWriter responseWriter = facesContext.getResponseWriter();

			if (inlineUse != null) {
				responseWriter.write("});");
			}

			responseWriter.write("// ]]>\n");
			responseWriter.endElement("script");
		}
	}

	@Override
	public boolean getRendersChildren() {
		return true;
	}

}
