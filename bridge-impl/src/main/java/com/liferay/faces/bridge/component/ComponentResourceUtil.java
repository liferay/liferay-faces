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
package com.liferay.faces.bridge.component;

import javax.faces.component.UIComponent;
import javax.faces.component.ValueHolder;

import com.liferay.faces.util.lang.StringPool;


/**
 * @author  Neil Griffin
 */
public class ComponentResourceUtil {

	public static String getComponentValue(UIComponent uiComponentResource) {
		String componentResourceValue = null;

		if (uiComponentResource instanceof ValueHolder) {
			ValueHolder valueHolder = (ValueHolder) uiComponentResource;
			Object valueAsObject = valueHolder.getValue();

			if (valueAsObject != null) {
				componentResourceValue = valueAsObject.toString();
			}
		}

		return componentResourceValue;
	}

	public static String getId(String library, String name) {
		String id = null;

		if (library == null) {
			id = name;
		}
		else {

			if (name == null) {
				id = library;
			}
			else {
				id = library + StringPool.COLON + name;
			}
		}

		return id;
	}
}
