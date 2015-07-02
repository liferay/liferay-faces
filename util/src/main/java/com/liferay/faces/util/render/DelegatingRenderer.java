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
package com.liferay.faces.util.render;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.render.RenderKit;
import javax.faces.render.Renderer;

import com.liferay.faces.util.render.internal.DelegationResponseWriter;


/**
 * This interface defines a contract for a {@link Renderer} that has the ability to delegate to the corresponding
 * methods of a different renderer, as well as utilize an alternate {@link ResponseWriter} in order to control/filter
 * the encoding of elements and attributes.
 *
 * @author  Neil Griffin
 */
public interface DelegatingRenderer {

	/**
	 * Convenience method that calls the delegate renderer's {@link Renderer#encodeBegin(FacesContext, UIComponent)},
	 * {@link Renderer#encodeChildren(FacesContext, UIComponent)}, and {@link Renderer#encodeEnd(FacesContext,
	 * UIComponent)} methods.
	 */
	public void encodeAll(FacesContext facesContext, UIComponent uiComponent) throws IOException;

	/**
	 * Convenience method that calls the delegate renderer's {@link Renderer#encodeBegin(FacesContext, UIComponent)},
	 * {@link Renderer#encodeChildren(FacesContext, UIComponent)}, and {@link Renderer#encodeEnd(FacesContext,
	 * UIComponent)} methods using the specified {@link DelegationResponseWriter}.
	 */
	public void encodeAll(FacesContext facesContext, UIComponent uiComponent,
		DelegationResponseWriter delegationResponseWriter) throws IOException;

	/**
	 * Calls the delegate renderer's {@link Renderer#encodeBegin(FacesContext, UIComponent)} method using the specified
	 * {@link DelegationResponseWriter}.
	 */
	public void encodeBegin(FacesContext facesContext, UIComponent uiComponent,
		DelegationResponseWriter delegationResponseWriter) throws IOException;

	/**
	 * Calls the delegate renderer's {@link Renderer#encodeChildren(FacesContext, UIComponent)} method using the
	 * specified {@link DelegationResponseWriter}.
	 */
	public void encodeChildren(FacesContext facesContext, UIComponent uiComponent,
		DelegationResponseWriter delegationResponseWriter) throws IOException;

	/**
	 * Calls the delegate renderer's {@link Renderer#encodeEnd(FacesContext, UIComponent)} method using the specified
	 * {@link DelegationResponseWriter}.
	 */
	public void encodeEnd(FacesContext facesContext, UIComponent uiComponent,
		DelegationResponseWriter delegationResponseWriter) throws IOException;

	/**
	 * Returns the component family associated with the delegate renderer.
	 */
	public String getDelegateComponentFamily();

	/**
	 * Returns the delegate renderer from the {@link RenderKit} associated with the specified {@link FacesContext}.
	 */
	public Renderer getDelegateRenderer(FacesContext facesContext);

	/**
	 * Returns the renderer-type associated with the delegate renderer.
	 */
	public String getDelegateRendererType();
}
