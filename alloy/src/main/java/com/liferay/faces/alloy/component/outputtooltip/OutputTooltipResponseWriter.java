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
package com.liferay.faces.alloy.component.outputtooltip;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.ResponseWriter;

import com.liferay.faces.util.lang.StringPool;
import com.liferay.faces.util.render.IdDelegationResponseWriter;


/**
 * The purpose of this class is to transform the output written by the JSF implementation's renderer so that
 * &lt;span&gt; elements will become &lt;div&gt; elements and so that the "id" attribute is always rendered. This is
 * necessary so that the AlloyUI JavaScript will be able to find the contextBox.
 *
 * @author  Vernon Singleton
 */
public class OutputTooltipResponseWriter extends IdDelegationResponseWriter {

	public OutputTooltipResponseWriter(ResponseWriter responseWriter, String idValue) {
		super(responseWriter, StringPool.DIV, idValue);
	}

	@Override
	public void endElement(String name) throws IOException {

		// Prevent the JSF runtime from closing the </span> tag since children need to be encoded by the
		// OutputToolTipRenderer.encodeChildren(FacesContext, UIComponent) method.
		if (!StringPool.SPAN.equals(name)) {
			super.endElement(name);
		}
	}

	@Override
	public void startElement(String name, UIComponent component) throws IOException {

		// When the JSF runtime's renderer attempts to render an opening <span> tag, render an opening <div> tag
		// instead.
		if (StringPool.SPAN.equals(name)) {
			name = StringPool.DIV;
		}

		super.startElement(name, component);
	}
}
