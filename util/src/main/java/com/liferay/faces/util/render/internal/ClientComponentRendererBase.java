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
package com.liferay.faces.util.render.internal;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.Renderer;

import com.liferay.faces.util.lang.StringPool;
import com.liferay.faces.util.render.ContentTypes;


/**
 * This is an abstract class that provides base rendering functionality. It extends normal rendering with methods such
 * as {@link #encodeMarkupBegin(FacesContext, UIComponent)} and {@link #encodeMarkupEnd(FacesContext, UIComponent)} that
 * provide a more fine-grained rendering sequence.
 *
 * @author  Neil Griffin
 */
public abstract class ClientComponentRendererBase extends Renderer implements ClientComponentRenderer {

	@Override
	public abstract void encodeJavaScriptBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException;

	@Override
	public abstract void encodeJavaScriptCustom(FacesContext facesContext, UIComponent uiComponent) throws IOException;

	@Override
	public abstract void encodeJavaScriptEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException;

	@Override
	public abstract void encodeJavaScriptMain(FacesContext facesContext, UIComponent uiComponent) throws IOException;

	@Override
	public abstract void encodeMarkupBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException;

	@Override
	public abstract void encodeMarkupEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException;

	@Override
	public void decode(FacesContext facesContext, UIComponent uiComponent) {

		decodeClientState(facesContext, uiComponent);
		super.decode(facesContext, uiComponent);
	}

	@Override
	public void decodeClientState(FacesContext facesContext, UIComponent uiComponent) {
		// no-op
	}

	@Override
	public void encodeBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		super.encodeBegin(facesContext, uiComponent);

		encodeMarkupBegin(facesContext, uiComponent);
	}

	@Override
	public void encodeClientState(FacesContext facesContext, ResponseWriter responseWriter, UIComponent uiComponent)
		throws IOException {
		// no-op
	}

	@Override
	public void encodeEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		super.encodeEnd(facesContext, uiComponent);

		ResponseWriter responseWriter = facesContext.getResponseWriter();

		encodeClientState(facesContext, responseWriter, uiComponent);
		encodeMarkupEnd(facesContext, uiComponent);
		encodeJavaScript(facesContext, uiComponent);
	}

	protected void encodeJavaScript(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		ResponseWriter responseWriter = facesContext.getResponseWriter();

		BufferedScriptResponseWriter bufferedScriptResponseWriter = new BufferedScriptResponseWriter();
		facesContext.setResponseWriter(bufferedScriptResponseWriter);

		encodeJavaScriptBegin(facesContext, uiComponent);
		encodeJavaScriptMain(facesContext, uiComponent);
		encodeJavaScriptCustom(facesContext, uiComponent);
		encodeJavaScriptEnd(facesContext, uiComponent);

		String use = null;

		if (!isSandboxed(facesContext, uiComponent)) {

			String[] modules = getModules(facesContext, uiComponent);

			if (modules != null) {

				StringBuilder stringBuilder = new StringBuilder();

				for (int i = 0; i < modules.length; i++) {

					if (i > 0) {
						stringBuilder.append(StringPool.COMMA);
					}

					stringBuilder.append(modules[i]);
				}

				use = stringBuilder.toString();
			}
		}

		RendererUtil.renderBufferedScript(bufferedScriptResponseWriter, use);
		facesContext.setResponseWriter(responseWriter);
	}

	protected boolean isSandboxed(FacesContext facesContext, UIComponent uiComponent) {
		return false;
	}

	protected String[] getModules(FacesContext facesContext, UIComponent uiComponent) {
		return null;
	}

	protected boolean isAjax(FacesContext facesContext) {
		return facesContext.getPartialViewContext().isAjaxRequest();
	}
}
