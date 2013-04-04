/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
package com.liferay.faces.bridge.application;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.component.ValueHolder;


/**
 * @author  Neil Griffin
 */
public class ResourceInfo {

	private String className;
	private String id;
	private String library;
	private String name;
	private String rendererType;
	private String value;

	public ResourceInfo(UIComponent uiComponentResource) {

		if (uiComponentResource != null) {

			className = uiComponentResource.getClass().getName();

			Map<String, Object> attributes = uiComponentResource.getAttributes();

			if (attributes != null) {
				Object nameAsObject = attributes.get("name");

				if (nameAsObject != null) {
					name = nameAsObject.toString();
					id = name;
				}

				Object libraryAsObject = attributes.get("library");

				if (libraryAsObject != null) {
					library = libraryAsObject.toString();
					id = library + ":" + name;
				}
			}

			rendererType = uiComponentResource.getRendererType();

			if (uiComponentResource instanceof ValueHolder) {
				ValueHolder valueHolder = (ValueHolder) uiComponentResource;
				Object valueAsObject = valueHolder.getValue();

				if (valueAsObject != null) {
					value = valueAsObject.toString();
				}
			}
		}
	}

	public String getClassName() {
		return className;
	}

	public String getId() {
		return id;
	}

	public String getLibrary() {
		return library;
	}

	public String getName() {
		return name;
	}

	public String getRendererType() {
		return rendererType;
	}

	public String getValue() {
		return value;
	}

}
