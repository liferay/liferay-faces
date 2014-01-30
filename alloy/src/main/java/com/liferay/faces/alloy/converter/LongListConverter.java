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
package com.liferay.faces.alloy.converter;

import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;


/**
 * @author  Neil Griffin
 */
public class LongListConverter implements Converter {

	public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String valueAsString) {

		List<Long> longList = null;

		if (valueAsString != null) {
			String[] longValues = valueAsString.split(",");
			longList = new ArrayList<Long>(longValues.length);

			for (String longValue : longValues) {

				if (longValue.trim().length() > 0) {
					longList.add(Long.parseLong(longValue));
				}
			}
		}

		return longList;
	}

	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object valueAsObject) {

		String valueAsString = null;
		@SuppressWarnings("unchecked")
		List<Long> longList = (List<Long>) valueAsObject;

		if (longList != null) {
			StringBuilder buf = new StringBuilder();
			boolean first = true;

			for (Long longValue : longList) {

				if (first) {
					first = false;
				}
				else {
					buf.append(",");
				}

				buf.append(longValue.toString());
			}

			valueAsString = buf.toString();
		}

		return valueAsString;
	}

}
