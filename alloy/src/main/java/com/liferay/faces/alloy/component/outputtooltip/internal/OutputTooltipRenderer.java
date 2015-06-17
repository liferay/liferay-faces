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
package com.liferay.faces.alloy.component.outputtooltip.internal;

import java.io.IOException;
import java.util.List;

import javax.faces.application.ProjectStage;
import javax.faces.application.ResourceDependencies;
import javax.faces.application.ResourceDependency;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;

import com.liferay.faces.alloy.component.outputtooltip.OutputTooltip;
import com.liferay.faces.util.component.ClientComponent;
import com.liferay.faces.util.component.Styleable;
import com.liferay.faces.util.lang.StringPool;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;
import com.liferay.faces.util.render.RendererUtil;
import com.liferay.faces.util.render.internal.DelegationResponseWriter;


/**
 * @author  Vernon Singleton
 */
//J-
@FacesRenderer(componentFamily = OutputTooltip.COMPONENT_FAMILY, rendererType = OutputTooltip.RENDERER_TYPE)
@ResourceDependencies(
	{
		@ResourceDependency(library = "liferay-faces-alloy", name = "alloy.css"),
		@ResourceDependency(library = "liferay-faces-reslib", name = "build/aui-css/css/bootstrap.min.css"),
		@ResourceDependency(library = "liferay-faces-reslib", name = "build/aui/aui-min.js"),
		@ResourceDependency(library = "liferay-faces-reslib", name = "liferay.js")
	}
)
//J+
public class OutputTooltipRenderer extends OutputTooltipRendererBase {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(OutputTooltipRenderer.class);

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
	public void encodeJavaScriptCustom(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		ResponseWriter responseWriter = facesContext.getResponseWriter();
		OutputTooltip tooltip = (OutputTooltip) uiComponent;
		encodeOverlayJavaScriptCustom(responseWriter, facesContext, tooltip);

		if ((tooltip.getFor() == null) && facesContext.isProjectStage(ProjectStage.Development)) {
			logger.error("The 'for' attribute is required for alloy:outputTooltip");
		}

		ClientComponent clientComponent = (ClientComponent) uiComponent;
		String clientVarName = getClientVarName(facesContext, clientComponent);
		String clientKey = clientComponent.getClientKey();

		if (clientKey == null) {
			clientKey = clientVarName;
		}

		// In order to workaround a bug where the tooltip appears in the incorrect place, set the trigger again after
		// the tooltip has been initialized
		encodeLiferayComponentVar(responseWriter, clientVarName, clientKey);
		responseWriter.write(clientVarName);
		responseWriter.write(".set('trigger',");
		responseWriter.write(clientVarName);
		responseWriter.write(".get('trigger'));");
	}

	@Override
	public void encodeMarkupBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		// Encode the opening boundingBox <div> tag. Ensure that the "id" attribute is always written so
		// that Alloy's JavaScript will be able to locate the boundingBox in the DOM.
		ResponseWriter responseWriter = facesContext.getResponseWriter();
		String clientId = uiComponent.getClientId(facesContext);
		responseWriter.startElement(StringPool.DIV, uiComponent);
		responseWriter.writeAttribute(StringPool.ID, clientId, StringPool.ID);

		// Encode the "style" and "class" attributes on the boundingBox <div> tag.
		Styleable styleable = (Styleable) uiComponent;
		RendererUtil.encodeStyleable(responseWriter, styleable);

		// Encode the opening contentBox <div> tag with a unique id.
		String contentBoxClientId = clientId.concat(CONTENT_BOX_SUFFIX);
		responseWriter.startElement(StringPool.DIV, null);
		responseWriter.writeAttribute(StringPool.ID, contentBoxClientId, null);

		// Create a response writer that will ignore the opening and closing "span" element tags along with the "id",
		// "style", and "class" attributes.
		DelegationResponseWriter delegationResponseWriter = new OutputTooltipResponseWriter(responseWriter, clientId);

		// The delegation renderer provided by the JSF runtime does not attempt to encode the opening <span> tag during
		// of encodeBegin(FacesContext, UIComponent). Instead, the entire <span>...</span> element is encoded during
		// encodeEnd(FacesContext, UIComponent).
		super.encodeMarkupEnd(facesContext, uiComponent, delegationResponseWriter);
	}

	@Override
	public void encodeMarkupEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		// Encode the closing contentBox </div> tag.
		ResponseWriter responseWriter = facesContext.getResponseWriter();
		responseWriter.endElement(StringPool.DIV);

		// Encode the closing boundingBox </div> tag.
		responseWriter.endElement(StringPool.DIV);
	}

	@Override
	protected void encodeHiddenAttributes(FacesContext facesContext, ResponseWriter responseWriter,
		OutputTooltip outputTooltip, boolean first) throws IOException {

		// Encode the "cssClass" Alloy hidden attribute.
		encodeString(responseWriter, "cssClass", outputTooltip.getStyleClass(), first);

		first = false;

		encodeOverlayHiddenAttributes(facesContext, responseWriter, outputTooltip, first);
	}

	@Override
	protected void encodeZIndex(ResponseWriter responseWriter, OutputTooltip outputTooltip, Integer zIndex,
		boolean first) throws IOException {

		encodeOverlayZIndex(responseWriter, outputTooltip, zIndex, LIFERAY_Z_INDEX_TOOLTIP, first);
	}
}
