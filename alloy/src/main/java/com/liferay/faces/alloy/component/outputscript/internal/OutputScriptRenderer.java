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
package com.liferay.faces.alloy.component.outputscript.internal;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.event.ListenerFor;
import javax.faces.event.PostAddToViewEvent;
import javax.faces.render.FacesRenderer;

import com.liferay.faces.alloy.component.outputscript.OutputScript;
import com.liferay.faces.util.lang.StringPool;
import com.liferay.faces.util.render.internal.BufferedScriptResponseWriter;
import com.liferay.faces.util.render.internal.RendererUtil;


/**
 * @author  Kyle Stiemann
 */
//J-
@FacesRenderer(componentFamily = OutputScript.COMPONENT_FAMILY, rendererType = OutputScript.RENDERER_TYPE)
@ListenerFor(systemEventClass = PostAddToViewEvent.class)
//J+
public class OutputScriptRenderer extends OutputScriptRendererBase {

	// Private Constants
	private static final String FUNCTION_BEGIN_SCRIPT = "(function(){";
	private static final String FUNCTION_END_SCRIPT = "})();";

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

			if (StringPool.BODY.equals(target) && !facesContext.getPartialViewContext().isAjaxRequest()) {

				ResponseWriter responseWriter = facesContext.getResponseWriter();
				BufferedScriptResponseWriter bufferedScriptResponseWriter = new BufferedScriptResponseWriter();
				facesContext.setResponseWriter(bufferedScriptResponseWriter);

				// Note: If the script uses YUI or AlloyUI modules then a YUI sandbox will be created automatically by
				// the ClientScript when RendererUtil.renderScript() is called below.
				if ((use != null) && (use.length() > 0)) {
					super.encodeChildren(facesContext, uiComponent);
				}

				// Otherwise the sandbox the script with an anonymous self-executing function.
				else {
					bufferedScriptResponseWriter.write(FUNCTION_BEGIN_SCRIPT);
					super.encodeChildren(facesContext, uiComponent);
					bufferedScriptResponseWriter.write(FUNCTION_END_SCRIPT);
				}

				// Render the script at the bottom of the page immediately before the closing </body> tag.
				RendererUtil.renderScript(bufferedScriptResponseWriter.toString(), use);
				facesContext.setResponseWriter(responseWriter);
			}

			// Otherwise if the script uses YUI or AlloyUI modules then create a YUI sandbox which specifies the
			// correct modules around the script.
			else if ((use != null) && (use.length() > 0)) {

				// Delegate to the JSF runtime Renderer, but replace the JSF runtime ResponseWriter with an
				// OutputScriptResponseWriter which will render the YUI sandbox code around the script.
				ResponseWriter responseWriter = facesContext.getResponseWriter();

				// In order to determine the exact YUI sandbox string to write, the modules and browser information
				// must be passed to RendererUtil.getAlloyBeginScript().
				String[] modules = use.split(StringPool.COMMA);
				String alloyBeginScript = RendererUtil.getAlloyBeginScript(facesContext, modules);
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

	@Override
	public String getDelegateComponentFamily() {
		return OutputScript.DELEGATE_COMPONENT_FAMILY;
	}

	@Override
	public String getDelegateRendererType() {
		return OutputScript.DELEGATE_RENDERER_TYPE;
	}
}
