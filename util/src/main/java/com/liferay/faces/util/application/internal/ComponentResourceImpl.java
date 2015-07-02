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
package com.liferay.faces.util.application.internal;

import com.liferay.faces.util.application.ComponentResource;


/**
 * @author  Neil Griffin
 */
public class ComponentResourceImpl implements ComponentResource {

	// Private Data Members
	private String id;
	private String library;
	private String name;
	private boolean renderable;

	public ComponentResourceImpl(String id, String library, String name, boolean renderable) {
		this.id = id;
		this.library = library;
		this.name = name;
		this.renderable = renderable;
	}

	@Override
	public boolean isRenderable() {
		return renderable;
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
}
