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
package com.liferay.faces.alloy.component.head.internal;

import java.io.IOException;
import java.util.List;

import javax.faces.application.ResourceDependencies;
import javax.faces.application.ResourceDependency;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;

import com.liferay.faces.alloy.component.head.Head;
import com.liferay.faces.util.application.ComponentResource;
import com.liferay.faces.util.application.ComponentResourceFactory;
import com.liferay.faces.util.factory.FactoryExtensionFinder;
import com.liferay.faces.util.product.ProductConstants;
import com.liferay.faces.util.product.ProductMap;


/**
 * @author  Neil Griffin
 */
//J-
@FacesRenderer(componentFamily = Head.COMPONENT_FAMILY, rendererType = Head.RENDERER_TYPE)
@ResourceDependencies(
		{
			@ResourceDependency(library = "liferay-faces-alloy", name = "alloy.css"),
			@ResourceDependency(library = "liferay-faces-alloy", name = "alloy.js"),
			@ResourceDependency(library = "liferay-faces-reslib", name = "build/aui-css/css/bootstrap.min.css"),
			@ResourceDependency(library = "liferay-faces-reslib", name = "build/aui/aui-min.js"),
			@ResourceDependency(library = "liferay-faces-reslib", name = "liferay.js")
		}
	)
//J+
public class HeadRenderer extends HeadRendererBase {

	// Private Constants
	private static final boolean LIFERAY_PORTAL_DETECTED = ProductMap.getInstance().get(ProductConstants.LIFERAY_PORTAL)
		.isDetected();

	@Override
	public void encodeChildren(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		// If Liferay Portal is not detected and bootstrap-responsive.min.css is present in the <head>...</head>
		// element, then encode a meta tag as a child of the head that will cause bootstrap to behave responsively.
		if (!LIFERAY_PORTAL_DETECTED) {

			ComponentResourceFactory componentResourceFactory = (ComponentResourceFactory) FactoryExtensionFinder
				.getFactory(ComponentResourceFactory.class);
			UIViewRoot uiViewRoot = facesContext.getViewRoot();
			List<UIComponent> componentResources = uiViewRoot.getComponentResources(facesContext, "head");

			for (UIComponent resource : componentResources) {

				ComponentResource componentResource = componentResourceFactory.getComponentResource(resource);
				String library = componentResource.getLibrary();
				String name = componentResource.getName();

				if ("liferay-faces-reslib".equals(library) &&
						"build/aui-css/css/bootstrap-responsive.min.css".equals(name)) {

					ResponseWriter responseWriter = facesContext.getResponseWriter();
					responseWriter.startElement("meta", null);
					responseWriter.writeAttribute("name", "viewport", null);
					responseWriter.writeAttribute("content", "width=device-width,initial-scale=1", null);
					responseWriter.endElement("meta");

					break;
				}
			}
		}

		super.encodeChildren(facesContext, uiComponent);
	}

	@Override
	public boolean getRendersChildren() {
		return true;
	}
}
