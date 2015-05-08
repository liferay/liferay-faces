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
package com.liferay.faces.util.config.internal;

import com.liferay.faces.util.config.ConfiguredElement;
import com.liferay.faces.util.lang.NameValuePair;


/**
 * @author  Neil Griffin
 */
public class ConfiguredElementImpl extends NameValuePair<String, String> implements ConfiguredElement {

	public ConfiguredElementImpl(String name, String value) {
		super(name, value);
	}
}
