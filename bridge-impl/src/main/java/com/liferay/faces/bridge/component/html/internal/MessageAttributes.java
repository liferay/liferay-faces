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
package com.liferay.faces.bridge.component.html.internal;

import java.util.Map;


/**
 * @author  Neil Griffin
 */
public class MessageAttributes extends AttributesWrapper {

	// Private Constants
	private static final String ERROR_CLASS = "errorClass";
	private static final String FATAL_CLASS = "fatalClass";
	private static final String INFO_CLASS = "infoClass";
	private static final String PORTLET_MSG_ERROR = "portlet-msg-error";
	private static final String PORTLET_MSG_INFO = "portlet-msg-info";
	private static final String PORTLET_MSG_WARN = "portlet-msg-warn";
	private static final String WARN_CLASS = "warnClass";

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

			if (ERROR_CLASS.equals(key) || FATAL_CLASS.equals(key)) {

				if (value == null) {

					if (styleClass == null) {
						value = PORTLET_MSG_ERROR;
					}
					else {
						value = PORTLET_MSG_ERROR.concat(" ").concat(styleClass);
					}
				}
				else {

					String valueAsString = (String) value;

					if (!valueAsString.contains(PORTLET_MSG_ERROR)) {
						value = valueAsString.concat(" ").concat(PORTLET_MSG_ERROR);
					}
				}
			}
			else if (INFO_CLASS.equals(key)) {

				if (value == null) {

					if (styleClass == null) {
						value = PORTLET_MSG_INFO;
					}
					else {
						value = PORTLET_MSG_INFO.concat(" ").concat(styleClass);
					}
				}
				else {

					String valueAsString = (String) value;

					if (!valueAsString.contains(PORTLET_MSG_INFO)) {
						value = valueAsString.concat(" ").concat(PORTLET_MSG_INFO);
					}
				}
			}
			else if (WARN_CLASS.equals(key)) {

				if (value == null) {

					if (styleClass == null) {
						value = PORTLET_MSG_WARN;
					}
					else {
						value = PORTLET_MSG_WARN.concat(" ").concat(styleClass);
					}
				}
				else {

					String valueAsString = (String) value;

					if (!valueAsString.contains(PORTLET_MSG_WARN)) {
						value = valueAsString.concat(" ").concat(PORTLET_MSG_WARN);
					}
				}
			}
		}

		return value;
	}

	public Map<String, Object> getWrapped() {
		return wrappedAttributes;
	}
}
