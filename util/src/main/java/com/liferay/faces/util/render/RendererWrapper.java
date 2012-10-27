/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
import javax.faces.convert.ConverterException;
import javax.faces.render.Renderer;

import com.liferay.faces.util.helper.Wrapper;


/**
 * @author  Neil Griffin
 */
public abstract class RendererWrapper extends Renderer implements Wrapper<Renderer> {

	@Override
	public String convertClientId(FacesContext context, String clientId) {
		return getWrapped().convertClientId(context, clientId);
	}

	@Override
	public void decode(FacesContext context, UIComponent component) {
		getWrapped().decode(context, component);
	}

	@Override
	public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
		getWrapped().encodeBegin(context, component);
	}

	@Override
	public void encodeChildren(FacesContext context, UIComponent component) throws IOException {
		getWrapped().encodeChildren(context, component);
	}

	@Override
	public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
		getWrapped().encodeEnd(context, component);
	}

	@Override
	public Object getConvertedValue(FacesContext context, UIComponent component, Object submittedValue)
		throws ConverterException {
		return getWrapped().getConvertedValue(context, component, submittedValue);
	}

	@Override
	public boolean getRendersChildren() {
		return getWrapped().getRendersChildren();
	}

	public abstract Renderer getWrapped();
}
