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
package com.liferay.faces.util.component;

/**
 * This interface should be implemented by classes that extend {@link UIComponent} if they render HTML that is styleable
 * with CSS class names.
 *
 * @author  Neil Griffin
 */
public interface Styleable {

	// Public Constants
	public static final String STYLE = "style";
	public static final String STYLE_CLASS = "styleClass";

	public String getStyle();

	public void setStyle(String style);

	public String getStyleClass();

	public void setStyleClass(String styleClass);
}
