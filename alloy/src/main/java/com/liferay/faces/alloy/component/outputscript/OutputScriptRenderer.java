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
package com.liferay.faces.alloy.component.outputscript;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.ComponentSystemEventListener;
import javax.faces.event.ListenerFor;
import javax.faces.event.PostAddToViewEvent;
import javax.faces.render.FacesRenderer;
import javax.faces.render.Renderer;
import com.liferay.faces.util.lang.StringPool;
import com.liferay.faces.util.render.BufferedScriptResponseWriter;
import com.liferay.faces.util.render.RendererUtil;


/**
 * @author  Bruno Basto
 * @author  Kyle Stiemann
 */
//J-
@FacesRenderer(componentFamily = OutputScript.COMPONENT_FAMILY, rendererType = OutputScript.RENDERER_TYPE)
@ListenerFor(systemEventClass = PostAddToViewEvent.class)
//J+
public class OutputScriptRenderer extends OutputScriptRendererBase implements ComponentSystemEventListener {

	// Private Constants
	private static final String FUNCTION_BEGIN_SCRIPT = "(function () {";
	private static final String FUNCTION_END_SCRIPT = "})();";

	@Override
	public void encodeChildren(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		OutputScript outputScript = (OutputScript) uiComponent;
		String target = outputScript.getTarget();
		String name = outputScript.getName();

		String use = outputScript.getUse();

		if (name == null) {

			// If target is body and this is not an ajax request, buffer the script which will be rendered in the
			// final script tag before the closing body tag.
			if (StringPool.BODY.equals(target) && !isAjax(facesContext)) {

				ResponseWriter responseWriter = facesContext.getResponseWriter();
				BufferedScriptResponseWriter bufferedScriptResponseWriter = new BufferedScriptResponseWriter();
				facesContext.setResponseWriter(bufferedScriptResponseWriter);

				// If the script uses YUI or AlloyUI modules then the script will be sandboxed automatically.
				if ((use != null) && (use.length() > 0)) {
					super.encodeChildren(facesContext, uiComponent);
				}

				// Otherwise the sandbox the script with an anonymous self-executing function.
				else {
					bufferedScriptResponseWriter.write(FUNCTION_BEGIN_SCRIPT);
					super.encodeChildren(facesContext, uiComponent);
					bufferedScriptResponseWriter.write(FUNCTION_END_SCRIPT);
				}

				RendererUtil.handleBufferedScript(facesContext, uiComponent, bufferedScriptResponseWriter, use);
				facesContext.setResponseWriter(responseWriter);
			}

			// Otherwise if the script uses YUI or AlloyUI modules then create a YUI sandbox which specifies the
			// correct modules around the script.
			else if ((use != null) && (use.length() > 0)) {

				// Delegate to the JSF runtime Renderer, but replace the JSF runtime ResponseWriter with an
				// OutputScriptResponseWriter which will render the YUI sandbox code around the script.
				ResponseWriter responseWriter = facesContext.getResponseWriter();

				// In order to determine the exact YUI sandbox string to write, the modules and browser information
				// must be passed to RendererUtil.getAUIBeginScript().
				String[] modules = use.split(StringPool.COMMA);
				String auiBeginScript = RendererUtil.getAUIBeginScript(facesContext, modules);
				OutputScriptResponseWriter outputScriptResponseWriter = new OutputScriptResponseWriter(responseWriter,
						auiBeginScript);
				super.encodeChildren(facesContext, uiComponent, outputScriptResponseWriter);
			}
			else {
				super.encodeChildren(facesContext, uiComponent);
			}
		}
		else {
			super.encodeChildren(facesContext, uiComponent);
		}
	}

	@Override
	public void processEvent(ComponentSystemEvent componentSystemEvent) throws AbortProcessingException {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		Renderer delegateRenderer = getDelegateRenderer(facesContext);

		if (delegateRenderer instanceof ComponentSystemEventListener) {
			ComponentSystemEventListener delegateComponentSystemEventListener = (ComponentSystemEventListener)
				delegateRenderer;
			delegateComponentSystemEventListener.processEvent(componentSystemEvent);
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

	protected boolean isAjax(FacesContext facesContext) {
		return facesContext.getPartialViewContext().isAjaxRequest();
	}
}
