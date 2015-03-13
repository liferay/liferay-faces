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
import javax.faces.render.Renderer;


/**
 * This interface defines a contract for a contract for a {@link Renderer} that manifests a "client component", meaning
 * a component that has markup as well as some type of client-side API that is referenced by JavaScript.
 *
 * @author  Neil Griffin
 */
public interface ClientComponentRenderer {

	public void decodeClientState(FacesContext facesContext, UIComponent uiComponent);

	public void encodeClientState(FacesContext facesContext, ResponseWriter responseWriter, UIComponent uiComponent)
		throws IOException;

	public void encodeJavaScriptBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException;

	public void encodeJavaScriptCustom(FacesContext facesContext, UIComponent uiComponent) throws IOException;

	public void encodeJavaScriptEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException;

	public void encodeJavaScriptMain(FacesContext facesContext, UIComponent uiComponent) throws IOException;

	public void encodeMarkupBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException;

	public void encodeMarkupEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException;
}
