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
package com.liferay.faces.alloy.component.inputdatetime;

import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import com.liferay.faces.util.component.Styleable;
import com.liferay.faces.util.lang.StringPool;
import com.liferay.faces.util.render.DelegationResponseWriterBase;


/**
 * @author  Kyle Stiemann
 */
public class InputDateTimeResponseWriter extends DelegationResponseWriterBase {

	public InputDateTimeResponseWriter(ResponseWriter responseWriter) {
		super(responseWriter);
	}

	@Override
	public void writeAttribute(String name, Object value, String property) throws IOException {

		if (StringPool.CLASS.equalsIgnoreCase(name) || Styleable.STYLE.equalsIgnoreCase(name)) {
			// no-op Because the writing of these attributes needs to be controlled directly by
			// InputDateTimeRendererBase.
		}
		else {

			// If writing the id attribute, then append the input id suffix, because the input must have an id which
			// is different than its parent span.
			if (StringPool.ID.equalsIgnoreCase(name)) {
				FacesContext facesContext = FacesContext.getCurrentInstance();
				value = value + InputDateTimeUtil.getInputIdSuffix(facesContext);
			}

			super.writeAttribute(name, value, property);
		}
	}
}
