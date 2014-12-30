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
package com.liferay.faces.alloy.component.body.internal;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;
import javax.faces.render.Renderer;

import com.liferay.faces.alloy.component.body.Body;
import com.liferay.faces.util.client.BrowserSniffer;
import com.liferay.faces.util.client.BrowserSnifferFactory;
import com.liferay.faces.util.factory.FactoryExtensionFinder;
import com.liferay.faces.util.logging.Logger;
import com.liferay.faces.util.logging.LoggerFactory;
import com.liferay.faces.util.product.Product;
import com.liferay.faces.util.product.ProductConstants;
import com.liferay.faces.util.product.ProductMap;
import com.liferay.faces.util.render.internal.DelegatingRendererBase;


/**
 * @author  Neil Griffin
 */
@FacesRenderer(componentFamily = Body.COMPONENT_FAMILY, rendererType = Body.RENDERER_TYPE)
public class BodyRenderer extends DelegatingRendererBase {

	// Private Constants
	private static final boolean LIFERAY_FACES_BRIDGE_DETECTED = ProductMap.getInstance().get(
			ProductConstants.LIFERAY_FACES_BRIDGE).isDetected();

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(BodyRenderer.class);

	// Private Data Members
	private Renderer delegateRenderer;

	public BodyRenderer() {
		String delegateBodyRendererFQCN = "com.sun.faces.renderkit.html_basic.BodyRenderer";

		if (LIFERAY_FACES_BRIDGE_DETECTED) {
			delegateBodyRendererFQCN = "com.liferay.faces.bridge.renderkit.html_basic.BodyRendererBridgeImpl";
		}
		else {
			Product jsf = ProductMap.getInstance().get(ProductConstants.JSF);

			if ((jsf != null) && ProductConstants.MYFACES.equals(jsf.getTitle())) {
				delegateBodyRendererFQCN = "org.apache.myfaces.renderkit.html.HtmlBodyRenderer";
			}
		}

		try {
			Class<?> delegateBodyRendererClass = Class.forName(delegateBodyRendererFQCN);
			delegateRenderer = (Renderer) delegateBodyRendererClass.newInstance();
		}
		catch (Exception e) {
			logger.error(e);
		}
	}

	@Override
	public void encodeEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		if (LIFERAY_FACES_BRIDGE_DETECTED) {
			super.encodeEnd(facesContext, uiComponent);
		}
		else {
			BrowserSnifferFactory browserSnifferFactory = (BrowserSnifferFactory) FactoryExtensionFinder.getFactory(
					BrowserSnifferFactory.class);
			BrowserSniffer browserSniffer = browserSnifferFactory.getBrowserSniffer(facesContext.getExternalContext());
			boolean browserIE = browserSniffer.isIe();
			float browserMajorVersion = browserSniffer.getMajorVersion();
			ResponseWriter responseWriter = facesContext.getResponseWriter();
			BodyResponseWriter delegationResponseWriter = new BodyResponseWriter(responseWriter, browserIE,
					browserMajorVersion);
			super.encodeEnd(facesContext, uiComponent, delegationResponseWriter);
		}
	}

	@Override
	public String getDelegateComponentFamily() {
		return Body.DELEGATE_COMPONENT_FAMILY;
	}

	@Override
	public Renderer getDelegateRenderer(FacesContext facesContext) {
		return delegateRenderer;
	}

	@Override
	public String getDelegateRendererType() {
		return Body.DELEGATE_RENDERER_TYPE;
	}
}
