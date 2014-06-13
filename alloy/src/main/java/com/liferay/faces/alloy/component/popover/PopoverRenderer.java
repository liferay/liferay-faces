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

import javax.faces.application.ProjectStage;
import javax.faces.application.ResourceDependencies;
import javax.faces.application.ResourceDependency;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;

import com.liferay.faces.alloy.renderkit.AlloyRendererUtil;
import com.liferay.faces.util.component.ComponentUtil;
import com.liferay.faces.util.lang.StringPool;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;


/**
 * @author  Vernon Singleton
 */
@FacesRenderer(componentFamily = Popover.COMPONENT_FAMILY, rendererType = Popover.RENDERER_TYPE)
@ResourceDependencies(
	{
		@ResourceDependency(library = "liferay-faces-alloy", name = "build/aui-css/css/bootstrap.min.css"),
		@ResourceDependency(
			library = "liferay-faces-alloy", name = "build/aui/aui-min.js"
		), @ResourceDependency(library = "liferay-faces-alloy", name = "liferay.js")
	}
)
public class PopoverRenderer extends PopoverRendererBase {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(PopoverRenderer.class);

	@Override
	public void encodeJavaScriptCustom(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		ResponseWriter responseWriter = facesContext.getResponseWriter();

		Popover popover = (Popover) uiComponent;
		String clientVarName = ComponentUtil.getClientVarName(facesContext, popover);

		if (popover.getFor() == null) {

			if (facesContext.isProjectStage(ProjectStage.Development)) {
				logger.error(
					"Popover needs to point to something. Try using its 'for' attribute to point to an 'id' in the component tree.");
			}
		}
		else {
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

		// alloy's divs should be in place and hidden by now.
		// so we need to unhide the div we have hidden until now.
		// any chance for blinking should be over by now.
		responseWriter.write("A.one('" + StringPool.POUND +
			ComponentUtil.escapeClientId(popover.getClientId(facesContext)) + "')._node['style'].display = 'block';");

	}

	@Override
	public void encodeJavaScriptMain(FacesContext facesContext, UIComponent uiComponent) throws IOException {

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
	public void encodeMarkupBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {
		Popover popover = (Popover) uiComponent;

		// Ensure that an id is written out
		popover.setId(StringPool.UNDERLINE + popover.getId());

		super.encodeMarkupBegin(facesContext, uiComponent);
	}

	@Override
	protected void encodeAlign(ResponseWriter responseWriter, Popover popover, Object for_, boolean first)
		throws IOException {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		for_ = "{ node: '" + popover.getForClientIdEscaped(facesContext) + "' }";

		super.encodeAlign(responseWriter, popover, for_, first);
	}

	@Override
	protected void encodeHiddenAttributes(ResponseWriter responseWriter, Popover popover, boolean first)
		throws IOException {

		FacesContext facesContext = FacesContext.getCurrentInstance();

		// contentBox
		String clientId = popover.getClientId(facesContext);
		String contentBox = StringPool.POUND + ComponentUtil.escapeClientId(clientId);
		encodeString(responseWriter, AlloyRendererUtil.CONTENT_BOX, contentBox, first);

		first = false;

		// headerContent
		String headerText = popover.getHeaderText();

		if (headerText != null) {
			encodeString(responseWriter, AlloyRendererUtil.HEADER_CONTENT, headerText, first);
		}

		// render : true
		encodeWidgetRender(responseWriter, first);

		// visible
		Boolean autoShow = popover.isAutoShow();

		if (autoShow != null) {
			encodeBoolean(responseWriter, AlloyRendererUtil.VISIBLE, autoShow, first);
		}
	}

	@Override
	public String getDelegateComponentFamily() {
		return Popover.DELEGATE_COMPONENT_FAMILY;
	}

	@Override
	public String getDelegateRendererType() {
		return Popover.DELEGATE_RENDERER_TYPE;
	}

	@Override
	public boolean getRendersChildren() {
		return true;
	}

}
