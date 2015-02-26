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
package com.liferay.faces.util.application;

/**
 * @author  Kyle Stiemann
 */
public class FacesResource {

	private String contentType;
	private String library;
	private String name;

	public FacesResource(String library, String name) {
		this(library, name, null);
	}

	public FacesResource(String library, String name, String contentType) {
		this.library = library;
		this.name = name;
		this.contentType = contentType;
	}

	public String getContentType() {
		return contentType;
	}

	public String getLibrary() {
		return library;
	}

	public String getName() {
		return name;
	}
}
