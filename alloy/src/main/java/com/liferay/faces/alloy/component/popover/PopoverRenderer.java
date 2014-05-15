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
package com.liferay.faces.alloy.component.popover;

import java.io.IOException;

import javax.faces.application.ResourceDependencies;
import javax.faces.application.ResourceDependency;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;

import com.liferay.faces.util.component.ComponentUtil;
import com.liferay.faces.util.lang.StringPool;

/**
 * @author  Vernon Singleton
 */

@FacesRenderer(componentFamily = Popover.COMPONENT_FAMILY, rendererType = Popover.RENDERER_TYPE)
@ResourceDependencies(
		{
			@ResourceDependency(library = "liferay-faces-alloy", name = "build/aui-css/css/bootstrap.min.css"),
			@ResourceDependency(library = "liferay-faces-alloy", name = "build/aui/aui-min.js"),
			@ResourceDependency(library = "liferay-faces-alloy", name = "liferay.js")
		}
	)
public class PopoverRenderer extends PopoverRendererBase {
	
	@Override
	public void encodeMarkupBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {
		// no-op
	}
	
	@Override
	public void encodeMarkupEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException {
		// no-op
	}
	
	@Override
	public void encodeJavaScriptMain(FacesContext facesContext,	UIComponent uiComponent) throws IOException {
		
		ResponseWriter responseWriter = facesContext.getResponseWriter();
		
		Popover popover = (Popover) uiComponent;
		String clientVarName = ComponentUtil.getClientVarName(facesContext, popover);
		String clientKey = popover.getClientKey();

		if (clientKey == null) {
			clientKey = clientVarName;
		}
		
		responseWriter.write("var " + clientVarName + " = ");
		super.encodeJavaScriptMain(facesContext, uiComponent);
	}
	
	@Override
	public void encodeJavaScriptCustom(FacesContext facesContext, UIComponent uiComponent) throws IOException {
		
		ResponseWriter responseWriter = facesContext.getResponseWriter();
		
		Popover popover = (Popover) uiComponent;
		String clientVarName = ComponentUtil.getClientVarName(facesContext, popover);
		
		responseWriter.write("A.one('" + popover.getForClientIdEscaped(facesContext) + "')");
		responseWriter.write(StringPool.PERIOD);
		responseWriter.write("on");
		responseWriter.write(StringPool.OPEN_PARENTHESIS);
		responseWriter.write(StringPool.APOSTROPHE);
		responseWriter.write("click");
		responseWriter.write(StringPool.APOSTROPHE);
		responseWriter.write(StringPool.COMMA);
		responseWriter.write("function(event) {");
		responseWriter.write(clientVarName);
		responseWriter.write(".set('visible', !");
		responseWriter.write(clientVarName);
		responseWriter.write(".get('visible'));");
		responseWriter.write(StringPool.CLOSE_CURLY_BRACE);
		responseWriter.write(StringPool.CLOSE_PARENTHESIS);
		responseWriter.write(StringPool.SEMICOLON);
		
	}
	
	@Override
	protected void encodeFor(ResponseWriter responseWriter,
			PopoverAlloy popoverAlloy, String for_, boolean first)
			throws IOException {
		// no-op
	}
	
}
