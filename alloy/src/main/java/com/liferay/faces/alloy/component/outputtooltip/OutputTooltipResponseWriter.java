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
import com.liferay.faces.util.render.DelegationResponseWriterBase;


/**
 * The purpose of this class is to transform the output written by the JSF implementation's renderer so that
 * &lt;span&gt; elements will become &lt;div&gt; elements and so that the "id" attribute is always rendered. This is
 * necessary so that the AlloyUI JavaScript will be able to find the contextBox.
 *
 * @author  Vernon Singleton
 */
public class OutputTooltipResponseWriter extends DelegationResponseWriterBase {

	public OutputTooltipResponseWriter(ResponseWriter responseWriter, UIComponent uiComponent) {
		super(responseWriter);
	}

	@Override
	public void endElement(String name) throws IOException {

		if (StringPool.SPAN.equals(name)) {
			name = StringPool.DIV;
		}
	}

	@Override
	public void startElement(String name, UIComponent component) throws IOException {

		if (StringPool.SPAN.equals(name)) {
			super.startElement(StringPool.DIV, component);
			super.writeAttribute(StringPool.ID, component.getClientId(), StringPool.ID);
		}
		else {
			super.startElement(name, component);
		}
	}

	@Override
	public void writeAttribute(String name, Object value, String property) throws IOException {

		if (!StringPool.ID.equals(name)) {
			super.writeAttribute(name, value, property);
		}
	}
}
