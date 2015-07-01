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
package com.liferay.faces.alloy.component.outputscript.internal;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.event.ListenerFor;
import javax.faces.event.PostAddToViewEvent;
import javax.faces.render.FacesRenderer;

import com.liferay.faces.alloy.component.outputscript.OutputScript;
import com.liferay.faces.alloy.render.internal.AlloyRendererUtil;
import com.liferay.faces.util.client.BrowserSniffer;
import com.liferay.faces.util.client.BrowserSnifferFactory;
import com.liferay.faces.util.client.Script;
import com.liferay.faces.util.client.ScriptFactory;
import com.liferay.faces.util.context.FacesRequestContext;
import com.liferay.faces.util.factory.FactoryExtensionFinder;
import com.liferay.faces.util.render.internal.BufferedScriptResponseWriter;


/**
 * @author  Kyle Stiemann
 */
//J-
@FacesRenderer(componentFamily = OutputScript.COMPONENT_FAMILY, rendererType = OutputScript.RENDERER_TYPE)
@ListenerFor(systemEventClass = PostAddToViewEvent.class)
//J+
public class OutputScriptRenderer extends OutputScriptRendererBase {

	@Override
	public void encodeChildren(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		OutputScript outputScript = (OutputScript) uiComponent;
		String name = outputScript.getName();

		// If the name attribute is specified, then simply delegate to the JSF runtime renderer since it knows how to
		// render a JSF resource.
		if ((name != null) && (name.length() > 0)) {
			super.encodeChildren(facesContext, uiComponent);
		}

		// Otherwise, since it is not a JSF resource:
		else {

			// If target="body" and this is not an Ajax request, then
			String use = outputScript.getUse();
			String target = outputScript.getTarget();

			if ("body".equals(target) && !facesContext.getPartialViewContext().isAjaxRequest()) {

				ResponseWriter responseWriter = facesContext.getResponseWriter();
				BufferedScriptResponseWriter bufferedScriptResponseWriter = new BufferedScriptResponseWriter();
				facesContext.setResponseWriter(bufferedScriptResponseWriter);
				super.encodeChildren(facesContext, uiComponent);
				facesContext.setResponseWriter(responseWriter);

				Script script;
				String bufferedScriptString = bufferedScriptResponseWriter.toString();
				ScriptFactory scriptFactory = (ScriptFactory) FactoryExtensionFinder.getFactory(ScriptFactory.class);

				if ((use != null) && (use.length() > 0)) {
					script = scriptFactory.getAlloyScript(bufferedScriptString, use.split(","));
				}
				else {
					script = scriptFactory.getScript(bufferedScriptString);
				}

				// Render the script at the bottom of the page immediately before the closing </body> tag.
				FacesRequestContext facesRequestContext = FacesRequestContext.getCurrentInstance();
				facesRequestContext.addScript(script);
			}

			// Otherwise if the script uses YUI or AlloyUI modules then create a YUI sandbox which specifies the
			// correct modules around the script.
			else if ((use != null) && (use.length() > 0)) {

				// Delegate to the JSF runtime Renderer, but replace the JSF runtime ResponseWriter with an
				// OutputScriptResponseWriter which will render the YUI sandbox code around the script.
				ResponseWriter responseWriter = facesContext.getResponseWriter();

				// In order to determine the exact YUI sandbox string to write, the modules and browser information
				// must be passed to RendererUtil.getAlloyBeginScript().
				String[] modules = use.split(",");
				BrowserSnifferFactory browserSnifferFactory = (BrowserSnifferFactory) FactoryExtensionFinder.getFactory(
						BrowserSnifferFactory.class);
				BrowserSniffer browserSniffer = browserSnifferFactory.getBrowserSniffer(
						facesContext.getExternalContext());
				String alloyBeginScript = AlloyRendererUtil.getAlloyBeginScript(modules, browserSniffer);
				OutputScriptResponseWriter outputScriptResponseWriter = new OutputScriptResponseWriter(responseWriter,
						alloyBeginScript);
				super.encodeChildren(facesContext, uiComponent, outputScriptResponseWriter);
			}

			// Otherwise, simply delegate to the JSF runtime renderer since target="head" or target="form".
			else {
				super.encodeChildren(facesContext, uiComponent);
			}
		}
	}
}
