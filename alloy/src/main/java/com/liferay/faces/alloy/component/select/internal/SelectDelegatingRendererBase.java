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
package com.liferay.faces.alloy.component.select.internal;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import com.liferay.faces.util.render.DelegatingRendererBase;
import com.liferay.faces.util.render.internal.DelegationResponseWriter;


/**
 * In order to render alloy:selectManyCheckbox and alloy:selectOneRadio using CSS classes and elements consistent with
 * Liferay Portal's (and AlloyUI's) style, SelectDelegatingRendererBase replaces the default ResponseWriter with {@link
 * TableSuppressingResponseWriter} and {@link LabelSurroundingResponseWriter}. This class is designed to be extended by
 * {@link com.liferay.faces.alloy.component.selectmanycheckbox.internal.SelectManyCheckboxRenderer} and {@link
 * com.liferay.faces.alloy.component.selectoneradio.internal.SelectOneRadioRenderer}.
 *
 * @author  Kyle Stiemann
 */
public abstract class SelectDelegatingRendererBase extends DelegatingRendererBase {

	@Override
	public void encodeAll(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		ResponseWriter responseWriter = facesContext.getResponseWriter();
		super.encodeAll(facesContext, uiComponent, getSelectDelegationResponseWriter(responseWriter));
	}

	@Override
	public void encodeBegin(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		ResponseWriter responseWriter = facesContext.getResponseWriter();
		super.encodeBegin(facesContext, uiComponent, getSelectDelegationResponseWriter(responseWriter));
	}

	@Override
	public void encodeChildren(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		ResponseWriter responseWriter = facesContext.getResponseWriter();
		super.encodeChildren(facesContext, uiComponent, getSelectDelegationResponseWriter(responseWriter));
	}

	@Override
	public void encodeEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException {

		ResponseWriter responseWriter = facesContext.getResponseWriter();
		super.encodeEnd(facesContext, uiComponent, getSelectDelegationResponseWriter(responseWriter));
	}

	private DelegationResponseWriter getSelectDelegationResponseWriter(ResponseWriter responseWriter) {

		DelegationResponseWriter delegationResponseWriter = new TableSuppressingResponseWriter(responseWriter);
		delegationResponseWriter = new LabelSurroundingResponseWriter(delegationResponseWriter, getSelectType());

		return delegationResponseWriter;
	}

	protected abstract String getSelectType();
}
