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
package com.liferay.faces.bridge.component.html.internal;

import java.util.Map;


/**
 * @author  Neil Griffin
 */
public class MessageAttributes extends AttributesWrapper {

	// Private Data Members
	private Map<String, Object> wrappedAttributes;

	public MessageAttributes(Map<String, Object> attributes) {
		this.wrappedAttributes = attributes;
	}

	@Override
	public Object get(Object key) {

		Object value = super.get(key);

		if (key != null) {

			String styleClass = (String) super.get("styleClass");

			if ("errorClass".equals(key) || "fatalClass".equals(key)) {
				value = getClassAttributeValue(value, "portlet-msg-error", styleClass);
			}
			else if ("infoClass".equals(key)) {
				value = getClassAttributeValue(value, "portlet-msg-info", styleClass);
			}
			else if ("warnClass".equals(key)) {
				value = getClassAttributeValue(value, "portlet-msg-warn", styleClass);
			}
		}

		return value;
	}

	protected String getClassAttributeValue(Object value, String defaultValue, String styleClass) {

		String classAttributeValue = null;

		if (value == null) {

			if (styleClass == null) {
				classAttributeValue = defaultValue;
			}
			else {
				classAttributeValue = defaultValue.concat(" ").concat(styleClass);
			}
		}
		else {

			String valueAsString = value.toString();

			if (valueAsString.contains(defaultValue)) {
				classAttributeValue = valueAsString;
			}
			else {
				classAttributeValue = defaultValue.concat(" ").concat(valueAsString);
			}
		}

		return classAttributeValue;
	}

	public Map<String, Object> getWrapped() {
		return wrappedAttributes;
	}
}
