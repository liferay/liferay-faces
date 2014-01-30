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
package com.liferay.faces.bridge.renderkit.html_basic;

import com.liferay.faces.util.lang.StringPool;


/**
 * This implementation is a special case that is meant to be used when JSF component renderers do not properly call
 * startElement() first. It represents a pseudo-element that has has a blank (empty string) node name.
 *
 * @author  Neil Griffin
 */
public class ElementBlankImpl extends ElementImpl {

	public ElementBlankImpl() {
		super(StringPool.BLANK);
	}

	@Override
	public String toString() {
		return getTextContent();
	}

}
