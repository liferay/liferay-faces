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
package com.liferay.faces.util.client.internal;

import com.liferay.faces.util.client.AlloyScript;


/**
 * @author  Kyle Stiemann
 */
public class AlloyScriptImpl extends ScriptImpl implements AlloyScript {

	// Private Data Members
	private String[] modules;

	public AlloyScriptImpl(String content, String[] modules) {

		super(content);

		if (modules == null) {
			throw new NullPointerException(
				"modules cannot be null, please use com.liferay.faces.util.client.Script instead.");
		}

		this.modules = modules;
	}

	public String[] getModules() {
		return modules;
	}
}
