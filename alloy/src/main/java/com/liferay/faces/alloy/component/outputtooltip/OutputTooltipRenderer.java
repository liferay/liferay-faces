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

import javax.faces.application.ResourceDependencies;
import javax.faces.application.ResourceDependency;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;

import com.liferay.faces.alloy.renderkit.AlloyRendererUtil;
import com.liferay.faces.util.component.ComponentUtil;
import com.liferay.faces.util.lang.StringPool;


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

	@Override
	public void encodeChildren(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		// It is necessary to override this method since the JSF runtime does not provide the ability for {@link
		// javax.faces.component.html.HtmlOutputText} (the delegation component) to encode any of its children without
		// enabling a special vendor-specific configuration option.
		List<UIComponent> children = uiComponent.getChildren();

		if (children != null) {

			for (UIComponent child : children) {
				child.encodeAll(facesContext);
			}
		}
	}

	@Override
	public void encodeMarkupBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		// Create a ResponseWriter that will transform the opening <span> tag to a <div> tag that always has an "id"
		// attribute. In addition, the ResponseWriter does not transform the closing </span> tag, but instead avoids
		// writing a closing tag. This is necessary so that encodeChildren(FacesContext, UIComponent) can take place
		// normally, and so that encodeMarkupEnd(FacesContext, UIComponent) will have an opportunity to write the
		// closing </div> tag.
		ResponseWriter responseWriter = facesContext.getResponseWriter();
		OutputTooltipResponseWriter outputTooltipResponseWriter = new OutputTooltipResponseWriter(responseWriter,
				uiComponent.getClientId());

		// The delegation renderer provided by the JSF runtime does not attempt to encode the opening <span> tag during
		// of encodeBegin(FacesContext, UIComponent). Instead, the entire <span>...</span> element is encoded during
		// encodeEnd(FacesContext, UIComponent).
		super.encodeMarkupEnd(facesContext, uiComponent, outputTooltipResponseWriter);
	}

	@Override
	public void encodeMarkupEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		// Encode the closing </div> tag that was started by the OutputTooltipResponseWriter.
		ResponseWriter responseWriter = facesContext.getResponseWriter();
		responseWriter.endElement(StringPool.DIV);
	}

	@Override
	protected void encodeHiddenAttributes(ResponseWriter responseWriter, OutputTooltip outputTooltip, boolean first)
		throws IOException {

		// cssClass
		encodeString(responseWriter, AlloyRendererUtil.CSS_CLASS, outputTooltip.getStyleClass(), first);
		first = false;

		// value
		Object value = outputTooltip.getValue();

		if (value != null) {
			encodeString(responseWriter, StringPool.VALUE, value, first);
		}

		encodeOverlayHiddenAttributes(responseWriter, outputTooltip, first);
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
		encodeOverlayZIndex(responseWriter, outputTooltip, zIndex, first);
	}

	@Override
	protected boolean isForRequired() {
		return true;
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
