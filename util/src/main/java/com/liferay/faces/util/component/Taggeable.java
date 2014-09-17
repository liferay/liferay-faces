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
package com.liferay.faces.util.component;

import javax.servlet.jsp.tagext.Tag;


/**
 * This interface should be implemented by classes that extend {@link UIComponent} if they render portal tags that needs
 * tag hierachy
 *
 * @author  Juan Gonzalez
 */
public interface Taggeable<T extends Tag> {

	public Tag getParentTag();

	public void setParentTag(Tag tag);

	public T getTag();

	public void setTag(T tag);
}
