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
package com.liferay.faces.alloy.render.internal;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.convert.ConverterException;
import javax.faces.render.RenderKit;
import javax.faces.render.Renderer;

import com.liferay.faces.util.render.internal.DelegationResponseWriter;


/**
 * @author  Neil Griffin
 */
public abstract class DelegatingClientComponentRendererBase extends ClientComponentRendererBase
	implements DelegatingClientComponentRenderer {

	@Override
	public abstract void encodeJavaScriptBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException;

	@Override
	public abstract void encodeJavaScriptCustom(FacesContext facesContext, UIComponent uiComponent) throws IOException;

	@Override
	public abstract void encodeJavaScriptEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException;

	@Override
	public abstract void encodeJavaScriptMain(FacesContext facesContext, UIComponent uiComponent) throws IOException;

	@Override
	public String convertClientId(FacesContext facesContext, String clientId) {

		Renderer delegateRenderer = getDelegateRenderer(facesContext);

		return delegateRenderer.convertClientId(facesContext, clientId);
	}

	@Override
	public void decode(FacesContext facesContext, UIComponent uiComponent) {

		Renderer delegateRenderer = getDelegateRenderer(facesContext);
		decodeClientState(facesContext, uiComponent);
		delegateRenderer.decode(facesContext, uiComponent);
	}

	@Override
	public void encodeAll(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		Renderer delegateRenderer = getDelegateRenderer(facesContext);
		delegateRenderer.encodeBegin(facesContext, uiComponent);
		delegateRenderer.encodeChildren(facesContext, uiComponent);
		delegateRenderer.encodeEnd(facesContext, uiComponent);
	}

	@Override
	public void encodeAll(FacesContext facesContext, UIComponent uiComponent,
		DelegationResponseWriter delegationResponseWriter) throws IOException {

		ResponseWriter originalResponseWriter = facesContext.getResponseWriter();
		facesContext.setResponseWriter(delegationResponseWriter);

		Renderer delegateRenderer = getDelegateRenderer(facesContext);
		delegateRenderer.encodeBegin(facesContext, uiComponent);
		delegateRenderer.encodeChildren(facesContext, uiComponent);
		delegateRenderer.encodeEnd(facesContext, uiComponent);
		facesContext.setResponseWriter(originalResponseWriter);
	}

	@Override
	public void encodeBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		// Must not delegate to the delegate renderer since the ClientComponentRendererBase.encodeEnd(FacesContext,
		// UIComponent) method needs to start driving the rendering process.
		super.encodeBegin(facesContext, uiComponent);
	}

	@Override
	public void encodeBegin(FacesContext facesContext, UIComponent uiComponent,
		DelegationResponseWriter delegationResponseWriter) throws IOException {

		ResponseWriter originalResponseWriter = facesContext.getResponseWriter();
		facesContext.setResponseWriter(delegationResponseWriter);

		// Must not delegate to the delegate renderer since the ClientComponentRendererBase.encodeEnd(FacesContext,
		// UIComponent) method needs to start driving the rendering process.
		super.encodeBegin(facesContext, uiComponent);
		facesContext.setResponseWriter(originalResponseWriter);
	}

	@Override
	public void encodeChildren(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		Renderer delegateRenderer = getDelegateRenderer(facesContext);
		delegateRenderer.encodeChildren(facesContext, uiComponent);
	}

	@Override
	public void encodeChildren(FacesContext facesContext, UIComponent uiComponent,
		DelegationResponseWriter delegationResponseWriter) throws IOException {

		ResponseWriter originalResponseWriter = facesContext.getResponseWriter();
		facesContext.setResponseWriter(delegationResponseWriter);

		Renderer delegateRenderer = getDelegateRenderer(facesContext);
		delegateRenderer.encodeChildren(facesContext, uiComponent);
		facesContext.setResponseWriter(originalResponseWriter);
	}

	@Override
	public void encodeEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		// Must not delegate to the delegate renderer since the ClientComponentRendererBase.encodeEnd(FacesContext,
		// UIComponent) method needs to finish driving the rendering process.
		super.encodeEnd(facesContext, uiComponent);
	}

	@Override
	public void encodeEnd(FacesContext facesContext, UIComponent uiComponent,
		DelegationResponseWriter delegationResponseWriter) throws IOException {

		ResponseWriter originalResponseWriter = facesContext.getResponseWriter();
		facesContext.setResponseWriter(delegationResponseWriter);

		// Must not delegate to the delegate renderer since the ClientComponentRendererBase.encodeEnd(FacesContext,
		// UIComponent) method needs to finish driving the rendering process.
		super.encodeEnd(facesContext, uiComponent);
		facesContext.setResponseWriter(originalResponseWriter);
	}

	@Override
	public void encodeMarkupBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {
		Renderer delegateRenderer = getDelegateRenderer(facesContext);
		delegateRenderer.encodeBegin(facesContext, uiComponent);
	}

	@Override
	public void encodeMarkupBegin(FacesContext facesContext, UIComponent uiComponent,
		DelegationResponseWriter delegationResponseWriter) throws IOException {

		ResponseWriter originalResponseWriter = facesContext.getResponseWriter();
		facesContext.setResponseWriter(delegationResponseWriter);

		Renderer delegateRenderer = getDelegateRenderer(facesContext);
		delegateRenderer.encodeBegin(facesContext, uiComponent);
		facesContext.setResponseWriter(originalResponseWriter);
	}

	@Override
	public void encodeMarkupEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException {
		Renderer delegateRenderer = getDelegateRenderer(facesContext);
		delegateRenderer.encodeEnd(facesContext, uiComponent);
	}

	@Override
	public void encodeMarkupEnd(FacesContext facesContext, UIComponent uiComponent,
		DelegationResponseWriter delegationResponseWriter) throws IOException {

		ResponseWriter originalResponseWriter = facesContext.getResponseWriter();
		facesContext.setResponseWriter(delegationResponseWriter);

		Renderer delegateRenderer = getDelegateRenderer(facesContext);
		delegateRenderer.encodeEnd(facesContext, uiComponent);
		facesContext.setResponseWriter(originalResponseWriter);
	}

	@Override
	public Object getConvertedValue(FacesContext facesContext, UIComponent uiComponent, Object submittedValue)
		throws ConverterException {

		Renderer delegateRenderer = getDelegateRenderer(facesContext);
		Object convertedValue = delegateRenderer.getConvertedValue(facesContext, uiComponent, submittedValue);

		return convertedValue;
	}

	@Override
	public abstract String getDelegateComponentFamily();

	@Override
	public Renderer getDelegateRenderer(FacesContext facesContext) {

		RenderKit renderKit = facesContext.getRenderKit();
		Renderer delegateRenderer = renderKit.getRenderer(getDelegateComponentFamily(), getDelegateRendererType());

		return delegateRenderer;
	}

	@Override
	public abstract String getDelegateRendererType();

	@Override
	public boolean getRendersChildren() {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		Renderer delegateRenderer = getDelegateRenderer(facesContext);

		return delegateRenderer.getRendersChildren();
	}
}
