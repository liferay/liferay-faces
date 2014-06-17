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
package com.liferay.faces.alloy.component.outputtooltip;

import java.io.IOException;
import java.util.List;

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
//J-
@FacesRenderer(componentFamily = OutputTooltip.COMPONENT_FAMILY, rendererType = OutputTooltip.RENDERER_TYPE)
@ResourceDependencies(
	{
		@ResourceDependency(library = "liferay-faces-alloy", name = "build/aui-css/css/bootstrap.min.css"),
		@ResourceDependency(library = "liferay-faces-alloy", name = "build/aui/aui-min.js"),
		@ResourceDependency(library = "liferay-faces-alloy", name = "liferay.js")
	}
)
//J+
public class OutputTooltipRenderer extends OutputTooltipRendererBase {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(OutputTooltipRenderer.class);

	@Override
	public void encodeChildren(FacesContext facesContext, UIComponent uiComponent) throws IOException {
		List<UIComponent> children = uiComponent.getChildren();

		if (children != null) {

			for (UIComponent child : children) {
				child.encodeAll(facesContext);
			}
		}
	}

	@Override
	public void encodeJavaScriptCustom(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		ResponseWriter responseWriter = facesContext.getResponseWriter();

		OutputTooltip tooltip = (OutputTooltip) uiComponent;

		// alloy's divs should be in place and hidden by now.
		// so we need to unhide the div we have hidden until now.
		// any chance for blinking should be over by now.
		responseWriter.write("A.one('" + StringPool.POUND +
			ComponentUtil.escapeClientId(tooltip.getClientId(facesContext)) + "')._node['style'].display = 'block';");

	}

	@Override
	public void encodeMarkupBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		ResponseWriter responseWriter = facesContext.getResponseWriter();
		OutputTooltipResponseWriter outputTooltipResponseWriter = new OutputTooltipResponseWriter(responseWriter,
				uiComponent);

		OutputTooltip outputTooltip = (OutputTooltip) uiComponent;

		if (outputTooltip.getFor() == null) {

			if (facesContext.isProjectStage(ProjectStage.Development)) {
				logger.error(
					"The outputTooltip needs to point to something. Try using its 'for' attribute to point to an 'id' in the component tree.");
			}
		}

		// Mojarra's HTML Basic calls encodeEnd for fun
		super.encodeMarkupEnd(facesContext, uiComponent, outputTooltipResponseWriter);
	}

	@Override
	public void encodeMarkupEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException {
		ResponseWriter responseWriter = facesContext.getResponseWriter();
		responseWriter.endElement(StringPool.DIV);
	}

	@Override
	protected void encodeCssClass(ResponseWriter responseWriter, OutputTooltip outputTooltip, String styleClass,
		boolean first) throws IOException {
		encodeNonEscapedString(responseWriter, CSS_CLASS, styleClass, first);
	}

	@Override
	protected void encodeHiddenAttributes(ResponseWriter responseWriter, OutputTooltip tooltip, boolean first)
		throws IOException {

		FacesContext facesContext = FacesContext.getCurrentInstance();

		// contentBox
		String clientId = tooltip.getClientId(facesContext);
		String contentBox = StringPool.POUND + ComponentUtil.escapeClientId(clientId);
		encodeNonEscapedString(responseWriter, AlloyRendererUtil.CONTENT_BOX, contentBox, first);

		first = false;

		// render : true
		encodeWidgetRender(responseWriter, first);
	}

	@Override
	protected void encodeTrigger(ResponseWriter responseWriter, OutputTooltip outputTooltip, String for_, boolean first)
		throws IOException {

		UIComponent uiComponent = outputTooltip.findComponent(for_);

		if (uiComponent != null) {
			String forClientId = uiComponent.getClientId();
			for_ = StringPool.POUND + ComponentUtil.escapeClientId(forClientId);
		}

		encodeNonEscapedString(responseWriter, TRIGGER, for_, first);
	}

	@Override
	protected void encodeZIndex(ResponseWriter responseWriter, OutputTooltip outputTooltip, Integer zIndex,
		boolean first) throws IOException {

		if (zIndex == Integer.MIN_VALUE) {
			encodeNonEscapedObject(responseWriter, Z_INDEX, AlloyRendererUtil.LIFERAY_Z_INDEX_TOOLTIP, first);
		}
		else {
			super.encodeZIndex(responseWriter, outputTooltip, zIndex, first);
		}
	}

	@Override
	public String getDelegateComponentFamily() {
		return OutputTooltip.DELEGATE_COMPONENT_FAMILY;
	}

	@Override
	public String getDelegateRendererType() {
		return OutputTooltip.DELEGATE_RENDERER_TYPE;
	}

}
