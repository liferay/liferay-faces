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

import com.liferay.faces.util.render.DelegatingRenderer;
import com.liferay.faces.util.render.internal.DelegationResponseWriter;


/**
 * @author  Neil Griffin
 */
public interface DelegatingClientComponentRenderer extends DelegatingRenderer {

	/**
	 * Calls the delegate renderer's {@link ClientComponentRenderer#encodeMarkupBegin(FacesContext, UIComponent)} method
	 * with the specified {@link DelegationResponseWriter}.
	 */
	public void encodeMarkupBegin(FacesContext facesContext, UIComponent uiComponent,
		DelegationResponseWriter delegationResponseWriter) throws IOException;

	/**
	 * Calls the delegate renderer's {@link ClientComponentRenderer#encodeMarkupEnd(FacesContext, UIComponent)} method
	 * with the specified {@link DelegationResponseWriter}.
	 */
	public void encodeMarkupEnd(FacesContext facesContext, UIComponent uiComponent,
		DelegationResponseWriter delegationResponseWriter) throws IOException;
}
