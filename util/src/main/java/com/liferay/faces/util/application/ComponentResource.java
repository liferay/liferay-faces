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
package com.liferay.faces.util.application;

/**
 * This interface defines a contract for getting information about a {@link javax.faces.component.UIComponent}} resource
 * such as its name, library, and whether or not it should be rendered.
 *
 * @author  Neil Griffin
 */
public interface ComponentResource {

	public boolean isRenderable();

	public String getId();

	public String getLibrary();

	public String getName();
}
