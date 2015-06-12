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
package com.liferay.faces.alloy.render.internal;

import java.io.Serializable;


/**
 * This is a simple marker class that wraps a String. It marks the fact that the wrapped string is a fragment of
 * JavaScript code.
 *
 * @author  Neil Griffin
 */
public final class JavaScriptFragment implements Serializable {

	// serialVersionUID
	private static final long serialVersionUID = 5918907480864436697L;

	// Private Data Members
	private String value;

	public JavaScriptFragment(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}
}
